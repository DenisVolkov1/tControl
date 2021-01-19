package org.run;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import org.controllers.MainSplitPaneController;

import java.io.IOException;


public class RunApp extends Application {
    private static FXMLLoader fxmlLoader = new FXMLLoader(RunApp.class.getResource("/org/fxml/controllers/MainSplitPaneWindow.fxml"));
    private static SplitPane mainSplitPane = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        mainSplitPane = fxmlLoader.load();
        int indexPaneForScreens = 0;
        for (Node node : mainSplitPane.getItems()) {
            if (!node.getId().equals("paneTabs")) indexPaneForScreens = mainSplitPane.getItems().indexOf(node);
        }
        mainSplitPane.getItems().remove(indexPaneForScreens);
        mainSplitPane.getItems().add(MainSplitPaneController.getDesktopPane());

        Scene scene = new Scene(mainSplitPane);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static SplitPane getMainSplitPane() {
        return mainSplitPane;
    }
}
