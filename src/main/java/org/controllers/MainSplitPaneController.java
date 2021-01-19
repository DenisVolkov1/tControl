package org.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import org.run.RunApp;

import java.io.IOException;


public class MainSplitPaneController {

    private enum MainScreens { ARCHIVE_CONTROLLER, DESKTOP_CONTROLLER, EMPLOYEES_LIST_CONTROLLER, SYNCHRONIZATION}
    private static FXMLLoader archiveFxmlLoader = new FXMLLoader(MainSplitPaneController.class.getResource("/org/fxml/controllers/screens/arch/Archive.fxml"));
    private static FXMLLoader desktopFxmlLoader = new FXMLLoader(MainSplitPaneController.class.getResource("/org/fxml/controllers/screens/desk/Desktop.fxml"));
    private static FXMLLoader employeesListFxmlLoader = new FXMLLoader(MainSplitPaneController.class.getResource("/org/fxml/controllers/screens/empl/EmployeesList.fxml"));
    private static FXMLLoader synchronizationFxmlLoader = new FXMLLoader(MainSplitPaneController.class.getResource("/org/fxml/controllers/screens/sync/Synchronization.fxml"));

    private static AnchorPane archivePane = null;
    private static AnchorPane desktopPane = null;
    private static AnchorPane employeesListPane = null;
    private static AnchorPane synchronizationPane = null;

    @FXML
    private ToggleButton desktopToggleButton;
    @FXML
    private ToggleButton employeesListToggleButton;
    @FXML
    private void initialize() throws IOException {
        //RunApp.getMainSplitPane().getI
        archivePane = archiveFxmlLoader.load();
            archivePane.setId(MainScreens.ARCHIVE_CONTROLLER.toString());
        desktopPane = desktopFxmlLoader.load();
            desktopPane.setId(MainScreens.DESKTOP_CONTROLLER.toString());
        employeesListPane = employeesListFxmlLoader.load();
            employeesListPane.setId(MainScreens.EMPLOYEES_LIST_CONTROLLER.toString());
        synchronizationPane = synchronizationFxmlLoader.load();
            synchronizationPane.setId(MainScreens.SYNCHRONIZATION.toString());
    }
    @FXML
    private void selectDesktop() {
        if (!desktopToggleButton.isSelected()) desktopToggleButton.setSelected(true);
        setupMainScreen(MainScreens.DESKTOP_CONTROLLER);
    }
    @FXML
    private void selectEmployeesList() {
        if (!employeesListToggleButton.isSelected()) employeesListToggleButton.setSelected(true);
        setupMainScreen(MainScreens.EMPLOYEES_LIST_CONTROLLER);
    }

    private void setupMainScreen(MainScreens mainScreens) {
        SplitPane mainSplitPane = RunApp.getMainSplitPane();
        AnchorPane anchorPane;
        switch (mainScreens) {
            case ARCHIVE_CONTROLLER: anchorPane = archivePane; //anchorPane.setId(MainScreens.ARCHIVE_CONTROLLER.toString());
                break;
            case DESKTOP_CONTROLLER: anchorPane = desktopPane; //anchorPane.setId(MainScreens.DESKTOP_CONTROLLER.toString());
                break;
            case EMPLOYEES_LIST_CONTROLLER: anchorPane = employeesListPane; //anchorPane.setId(MainScreens.EMPLOYEES_LIST_CONTROLLER.toString());
                break;
            case SYNCHRONIZATION: anchorPane = synchronizationPane; //anchorPane.setId(MainScreens.SYNCHRONIZATION.toString());
                break;
            default: throw new RuntimeException("Unknown screen!");
        }
        int indexPaneForScreens = 0;
        System.out.println(mainSplitPane.getItems());
        for (Node node : mainSplitPane.getItems()) {
            if (!node.getId().equals("paneTabs")) indexPaneForScreens = mainSplitPane.getItems().indexOf(node);
        }
        System.out.println(indexPaneForScreens);
        mainSplitPane.getItems().remove(indexPaneForScreens);
        mainSplitPane.getItems().add(anchorPane);
        System.out.println(mainSplitPane.getItems());

    }

    public static AnchorPane getArchivePane() {
        return archivePane;
    }

    public static AnchorPane getDesktopPane() {
        return desktopPane;
    }

    public static AnchorPane getEmployeesListPane() {
        return employeesListPane;
    }

    public static AnchorPane getSynchronizationPane() {
        return synchronizationPane;
    }
}
