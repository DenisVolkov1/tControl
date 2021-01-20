package org.util;

import javafx.scene.control.Alert;

public final class Util {

    private Util() {}

    // DIALOG WINDOWS START
    public static void informationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setTitle("Info");
        alert.setHeaderText(message);
        //alert.setContentText("I have a great message for you!");
        alert.showAndWait();
    }
    public static void informationDialogLOg(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setTitle("Info");
        alert.setHeaderText(message);
            LOg.println(LOg.INFO,message);
        alert.showAndWait();
    }
    public static void errorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        //alert.setTitle("Error!");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
    public static void errorDialog(String message, Throwable throwable) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        //alert.setTitle("Error!");
        alert.setHeaderText(message);
            LOg.println(throwable);
        alert.showAndWait();
    }
    // DIALOG WINDOWS END


}
