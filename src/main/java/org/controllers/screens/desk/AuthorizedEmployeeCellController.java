package org.controllers.screens.desk;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.run.RunApp;

import java.io.IOException;

public class AuthorizedEmployeeCellController {

    private AnchorPane anchorPane;
    private FXMLLoader fxmlLoader = new FXMLLoader(RunApp.class.getResource("/org/fxml/controllers/desk/AuthorizedEmployeeCell.fxml"));
    private Image imagePhoto;

    @FXML
    private ImageView photo;
    @FXML
    private Label fio;
    @FXML
    private Label cardID;
    @FXML
    private Label temperature;
    @FXML
    private Label time;

    public AuthorizedEmployeeCellController() {
        fxmlLoader = new FXMLLoader(AuthorizedEmployeeCellController.class.getResource("/org/fxml/controllers/screens/desk/AuthorizedEmployeeCell.fxml"));
        fxmlLoader.setController(this);
        try {
            anchorPane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setInfo(AuthorizedEmployeeInfo employeeInfo) {
        ///set photogetResource("/org/fxml/controllers/desk/AuthorizedEmployeeCell.fxml")
        //InputStream input = getClass().getResource(employeeInfo.getPathImagePhotoURL());
        imagePhoto = new Image(employeeInfo.getPathImagePhoto());
       // System.out.println(imagePhoto);
        photo.setImage(imagePhoto);
        ///set fio
      //  System.out.println(fio);
        fio.setText(employeeInfo.getFio());
        ///set cardID
        cardID.setText(employeeInfo.getId());
        ///set temperature
        temperature.setText(employeeInfo.gettC());
        ///set time
        time.setText(employeeInfo.getTimeEnter());

    }

    AnchorPane getMainPane() {
        return anchorPane;
    }

}
