package org.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class LOg {
    public static final LOg.Enum INFO = new LOg.Enum(1, "INFO");
    public static final LOg.Enum WARNING = new LOg.Enum(2, "WARN");
    public static final LOg.Enum ERROR = new LOg.Enum(4, "ERROR");
    public static final LOg.Enum SQL = new LOg.Enum(8, "SQL ");
//    public static final LOg.Enum STATUS = new LOg.Enum(512, "STATUS");
//    public static final LOg.Enum PROFILE_TIMER = new LOg.Enum(16, "PROF");
//    public static final LOg.Enum STACK = new LOg.Enum(32, "STACK");
//    public static final LOg.Enum GATEWAY = new LOg.Enum(64, "GATEWAY");
//    public static final LOg.Enum EMC = new LOg.Enum(128, "EMC");
//    public static final LOg.Enum EMAIL_SUPPORT = new LOg.Enum(256, "EMAIL_TO_SUPPORT");
    private static boolean LOgOutputToFile = false;
    private static String LOgOutputPath = "";
    private static String contextName = "";
    private static String originalContext = "";
    private static String gatewayInstanceName = null;


    private static Object writingSemaphore;
    private static boolean LOgForceFlush;
    private static int logRetentionDays;
    private static Writer writer;
    private static int lineNumber;
    private static String fileName;


    private LOg() {
    }
    static{
        writingSemaphore = new Object();
        LOgForceFlush = false;
        logRetentionDays = 0;
        writer = null;
        lineNumber = 0;
        fileName = null;


        Properties propertiesInit = new Properties();
        propertiesInit.setProperty("context","log");
        propertiesInit.setProperty("gatewayInstanceName","main");
        propertiesInit.setProperty("LOgOutputToFile","true");
        propertiesInit.setProperty("LOgLogDir","logs/");
        propertiesInit.setProperty("LOgForceFlush","true");

        init(propertiesInit);
    }

    public static synchronized void init(Properties properties) {
        writer = null;
        //setLOgLevels(properties.getProperty("LOgLevels"));
        contextName = properties.getProperty("context");
        gatewayInstanceName = properties.getProperty("gatewayInstanceName");
        LOgOutputToFile = Boolean.parseBoolean(properties.getProperty("LOgOutputToFile"));
        LOgOutputPath = properties.getProperty("LOgLogDir");
        if (properties.getProperty("logRetentionDays") != null) {
            logRetentionDays = Integer.parseInt(properties.getProperty("logRetentionDays"));
        } else {
            logRetentionDays = 30;
        }

        if (properties.getProperty("LOgForceFlush") != null) {
            LOgForceFlush = Boolean.parseBoolean(properties.getProperty("LOgForceFlush"));
        }

        File directoryFile = new File(LOgOutputPath);
        if (!directoryFile.isDirectory() && directoryFile.mkdirs()) {
            System.out.println("Created new Directory: " + LOgOutputPath);
        }

    }

    private static final void println(LOg.Enum errorEnum, String msg, String classAndMethod) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String time = simpleDateFormat.format(new Date());
        simpleDateFormat = null;
        String threadID = Thread.currentThread().getName();
        msg = cleanStringOfSpecialCharacters(msg);
        String LINE_BREAK = "\n";
        String TAB = "\t";
        String METHOD = "()";
        StringBuffer buffer = new StringBuffer();
        buffer.append(time);
        buffer.append("\t");
        buffer.append(errorEnum.toString());
        buffer.append("\t");
        buffer.append(threadID);
        buffer.append("\t");
        buffer.append(classAndMethod);
        buffer.append("()");
        buffer.append("\t");
        buffer.append(msg);
        buffer.append("\n");

        try {
            synchronized(writingSemaphore) {
                Writer out = getWriter();
                out.write(buffer.toString());
                System.out.println(LOgForceFlush);
                if (LOgForceFlush) {
                    out.flush();
                } else if (errorEnum.getValue() == ERROR.getValue()) {
                    out.flush();
                }
            }
        } catch (IOException var14) {
            System.out.print("cannot write LOg: " + var14.getMessage() + ": ");
        }

    }

    public static final void println(LOg.Enum errorType, String msg) {
            String classAndMethod;
                classAndMethod = getClassAndMethod(new Throwable());
                println(errorType, msg, classAndMethod);
    }
    public static final void println(Throwable t) {
        String classAndMethod;
        classAndMethod = getClassAndMethod(new Throwable());
        String msg = getStack(t);
        println(ERROR, msg, classAndMethod);
    }

    private static final String getClassAndMethod(Throwable t) {
        String classAndMethod = "Class_Method_Not_Found";
        if (t == null) {
            return classAndMethod;
        } else {
            StackTraceElement[] trace = t.getStackTrace();
            if (trace.length == 0) {
                return classAndMethod;
            } else {
                int i;
                for(i = 0; i < trace.length - 1 && (trace[i].getClassName().equals("com.agilisys.supplyWeb.util.LOg") || trace[i].getClassName().endsWith(".ProfileTimer")); ++i) {
                }

                classAndMethod = trace[i].getClassName() + "." + trace[i].getMethodName();
                return classAndMethod;
            }
        }
    }

    private static final String cleanStringOfSpecialCharacters(String value) {
        if (value == null) {
            return null;
        } else {
            value = value.replace('\n', ',');
            value = value.replace('\r', ' ');

            for(value = value.replace('\t', ' '); value.length() > 2 && value.substring(value.length() - 2, value.length() - 1).equals("\n"); value = value.substring(0, value.length() - 1)) {
            }

            return value;
        }
    }

    private static final String getStack(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        String theStack = sw.getBuffer().toString();
        return theStack;
    }

    private static final Writer getWriter() {
        if (writer != null && !LOgOutputToFile) {
            return writer;
        } else if (!LOgOutputToFile) {
            writer = new PrintWriter(System.out);
            return writer;
        } else {
            ++lineNumber;
            if (writer != null && lineNumber < 100000) {
                return writer;
            } else {
                lineNumber = 0;

                try {
                    Writer previousWriter = writer;
                    String previousFileName = fileName;
                    fileName = generateFileName();
                    FileOutputStream fos = new FileOutputStream(fileName);
                    OutputStreamWriter osw = new OutputStreamWriter(fos);
                    writer = new BufferedWriter(osw);
                    if (previousWriter != null) {
                        previousWriter.flush();
                        previousWriter.close();
                        previousWriter = null;
                    }

                    compressAndDeleteFile(previousFileName, previousWriter);
                    if (LOgOutputPath != null && !LOgOutputPath.isEmpty()) {
                        cleanOldLogFiles(LOgOutputPath);
                    } else {
                        println(ERROR, "Can't get directory of log files!");
                    }

                    return writer;
                } catch (IOException var4) {
                    System.out.println("IOException: " + var4.getMessage());
                    return new PrintWriter(System.out);
                }
            }
        }
    }

    private static void compressAndDeleteFile(final String previousFileName, Writer previousWriter) {
        if (previousFileName != null) {
            Thread compressorThread = new Thread(new Runnable() {
                public void run() {
                    File originalFile = new File(previousFileName);

                    try {
                        Thread.sleep(2000L);
                        BufferedInputStream in = new BufferedInputStream(new FileInputStream(originalFile));
                        String zipFileName = previousFileName + ".zip";
                        File zipFile = new File(zipFileName);
                        zipFile.createNewFile();
                        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
                        ZipEntry zipEntry = new ZipEntry(previousFileName);
                        out.putNextEntry(zipEntry);
                        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(out));
                        byte[] buffer = new byte[8192];

                        int i;
                        while((i = in.read(buffer)) != -1) {
                            dataOutputStream.write(buffer, 0, i);
                        }

                        dataOutputStream.flush();
                        out.closeEntry();
                        dataOutputStream.close();
                        in.close();
                        if (originalFile.delete()) {
                            LOg.println(LOg.INFO, "file " + previousFileName + " is deleted");
                        } else {
                            for(int count = 1; count < 200; ++count) {
                                Thread.sleep(1010L);
                                if (originalFile.delete()) {
                                    LOg.println(LOg.INFO, "orignalfile was deleted. try=" + count);
                                    break;
                                }

                                LOg.println(LOg.INFO, "orignalfile not deleted. try=" + count);
                            }
                        }
                    } catch (IOException var11) {
                        LOg.println(LOg.ERROR, "Couldn't compress file " + LOg.fileName);
                        LOg.println(LOg.ERROR, "ioe:" + var11.getMessage());
                    } catch (Throwable var12) {
                        LOg.println(LOg.ERROR, "throwable: " + var12.getMessage());
                    }

                }
            });
            compressorThread.setPriority(1);
            compressorThread.start();
        }
    }

    private static void cleanOldLogFiles(String LOgLogDir) {
        if (contextName != null && !contextName.isEmpty()) {
            File logDir = new File(LOgLogDir);
            File[] logFileList = null;
            if (contextName.toLowerCase().contains("chartservice")) {
                originalContext = "chartservice";
            } else if (contextName.toLowerCase().contains("emcpoller")) {
                originalContext = "emcpoller";
            } else if (contextName.toLowerCase().contains("emcworker")) {
                originalContext = "emcworker";
            } else if (contextName.toLowerCase().contains("reportui")) {
                originalContext = "reportui";
            } else if (contextName.toLowerCase().contains("reportsr")) {
                originalContext = "reportsr";
            } else if (contextName.toLowerCase().contains("onrampinbound")) {
                originalContext = "onrampinbound";
            } else if (contextName.toLowerCase().contains("onramp")) {
                originalContext = "onramp";
            } else if (contextName.toLowerCase().contains("mailworker")) {
                originalContext = "mailworker";
            } else if (contextName.toLowerCase().contains("schedulerpoller")) {
                originalContext = "schedulerpoller";
            } else if (contextName.toLowerCase().contains("schdulerworker")) {
                originalContext = "schdulerworker";
            } else if (contextName.toLowerCase().contains("statusreporterpoller")) {
                originalContext = "statusreporterpoller";
            } else if (contextName.toLowerCase().contains("gatewayui")) {
                originalContext = "gatewayui";
            } else if (contextName.toLowerCase().contains("statusreporterworker")) {
                originalContext = "statusreporterworker";
            } else if (contextName.toLowerCase().contains("gatewayexpsched")) {
                originalContext = "gatewayexpsched";
            } else if (contextName.toLowerCase().contains("gatewayftpprocessor")) {
                originalContext = "gatewayftpprocessor";
            } else if (contextName.toLowerCase().contains("gatewayworker")) {
                originalContext = "gatewayworker";
            } else if (contextName.toLowerCase().contains("gatewaysr")) {
                originalContext = "gatewaysr";
            } else if (contextName.toLowerCase().contains("supplyweb")) {
                originalContext = "supplyweb";
            } else {
                originalContext = contextName;
            }

            FilenameFilter filter = new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().startsWith(LOg.originalContext);
                }
            };
            logFileList = logDir.listFiles(filter);
            File[] var4 = logFileList;
            int var5 = logFileList.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                File f = var4[var6];
                Date date1 = new Date();
                Date date2 = new Date(f.lastModified());
                int duration = (int)(date1.getTime() / 86400000L - date2.getTime() / 86400000L);
                if (logRetentionDays < duration) {
                    boolean isDeleted = f.delete();
                    if (isDeleted) {
                        println(INFO, "Log file " + f.getName() + " is deleted!");
                    } else {
                        println(WARNING, "Log file" + f.getName() + " could not be deleted!");
                    }
                } else if (f.getName().endsWith(".text") && f.getName().toLowerCase().startsWith(originalContext) && isZippedFileExist(f)) {
                    if (f.delete()) {
                        println(INFO, "Second delete for " + f.getName() + " is successfull!");
                    } else {
                        println(INFO, "Second delete for " + f.getName() + " still failed!");
                    }
                }
            }
        } else {
            println(ERROR, "Can't get contextName from properties!");
        }

    }

    private static boolean isZippedFileExist(File file) {
        boolean isZipped = false;
        String zipFileName = file.getAbsolutePath() + ".zip";
        File zipFile = new File(zipFileName);
        if (zipFile.exists()) {
            isZipped = true;
        }

        return isZipped;
    }

    private static final String generateFileName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = simpleDateFormat.format(new Date());
            time = time.substring(0,time.length()-1) + "0";
        System.out.println(time);
        StringBuffer value = new StringBuffer(60);
        value.append(LOgOutputPath);
        value.append(contextName.replace(' ', '.').replace(':', '-'));
        value.append("-");
        if (gatewayInstanceName != null) {
            value.append(gatewayInstanceName.replace(' ', '.').replace(':', '-'));
            value.append("-");
        }

        value.append(time.replace(':', '_'));
        value.append(".text");
        return value.toString();
    }

    public static class Enum {
        private final int val;
        private final String def;
        public static final int E_INFO = 1;
        public static final int E_WARNING = 2;
        public static final int E_ERROR = 4;
        public static final int E_SQL = 8;
        public static final int E_PROFILE_TIMER = 16;
        public static final int E_STACK = 32;
        public static final int E_GATEWAY = 64;
        public static final int E_EMC = 128;
        public static final int E_EMAIL_TO_SUPPORT = 256;
        public static final int E_STATUS = 512;

        private Enum(int i, String def) {
            this.val = i;
            this.def = def;
        }

        int getValue() {
            return this.val;
        }

        public String toString() {
            return this.def;
        }
    }
}

