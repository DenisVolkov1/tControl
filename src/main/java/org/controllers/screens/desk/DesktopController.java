package org.controllers.screens.desk;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Duration;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DesktopController {

    private List<AuthorizedEmployeeInfo> authorizedEmployeesList = new ArrayList<AuthorizedEmployeeInfo>();
    ObservableList<AuthorizedEmployeeInfo> observableList = FXCollections.observableArrayList();
    @FXML
    private ListView<AuthorizedEmployeeInfo> authorizedEmployeesListView;
    @FXML
    private AnchorPane extendedInformationWindows;
    // extendedInformationWindows components start
    @FXML
    private Label fioLabel;
    @FXML
    private Label divisionLabel;
    @FXML
    private Label positionLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label tCLabel;
    @FXML
    private Label timeEnterLabel;
    @FXML
    private Button rollUpButton;
    @FXML
    private ImageView photoImageView;
    // extendedInformationWindows components end
    @FXML
    private Label timeLabel;
    @FXML
    private Label dateLabel;

    @FXML
    private void initialize() {
        //time and date
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        DateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy");
        final Timeline timeline = new Timeline(new KeyFrame(Duration.millis( 500 ), event -> {
                            timeLabel.setText(timeFormat.format( System.currentTimeMillis()));
                            dateLabel.setText(dateFormat.format( System.currentTimeMillis()));
                        }
                )
        );
        timeline.setCycleCount( Animation.INDEFINITE );
        timeline.play();
        //end time and date
        System.out.println("init_Desktop");
        System.out.println(authorizedEmployeesListView);
        authorizedEmployeesList.add(new AuthorizedEmployeeInfo("photo/images.jpg","Мщдлщм Вутшы Фдулыуумшср","Dnjhjt","Director","797987987987","-40","20:40"));
        authorizedEmployeesList.add(new AuthorizedEmployeeInfo("photo/images.jpg","Misha Ruslanov","23Tyu","Worker","797987987987","-70","20:50"));
        authorizedEmployeesList.add(new AuthorizedEmployeeInfo("photo/images.jpg","Инокентий смактуновский","Кино","Актёр","77879845552","36.6","8:40"));
        authorizedEmployeesList.add(new AuthorizedEmployeeInfo("photo/images.jpg","Зульхия Романова","broker","Spiker","777777777777","35.5","00:50"));
        authorizedEmployeesList.add(new AuthorizedEmployeeInfo("photo/images.jpg","Безруков Павел Ильич","Продажи","Менеджер","82456793122","40","2:21"));
        authorizedEmployeesList.add(new AuthorizedEmployeeInfo("photo/images.jpg","Misha Ruslanov","23Tyu","Worker","797987987987","-70","20:50"));
        authorizedEmployeesList.add(new AuthorizedEmployeeInfo("photo/images.jpg","Мщдлщм Вутшы Фдулыуумшср","Dnjhjt","Director","797987987987","-40","20:40"));
        authorizedEmployeesList.add(new AuthorizedEmployeeInfo("photo/images.jpg","Misha Ruslanov","23Tyu","Worker","797987987987","-70","20:50"));
        observableList.setAll(authorizedEmployeesList);
        authorizedEmployeesListView.setItems(observableList);
        Callback<ListView<AuthorizedEmployeeInfo>,ListCell<AuthorizedEmployeeInfo>> callback = new Callback<ListView<AuthorizedEmployeeInfo>, ListCell<AuthorizedEmployeeInfo>>() {
            @Override
            public ListCell<AuthorizedEmployeeInfo> call(ListView<AuthorizedEmployeeInfo> authorizedEmployeeInfoListView) {

                return new AuthorizedEmployeeListCell();
            }
        };
        authorizedEmployeesListView.setCellFactory(callback);

        AnchorPane.setTopAnchor(authorizedEmployeesListView, 0.0);
        extendedInformationWindows.setDisable(true);
        extendedInformationWindows.setVisible(false);
        authorizedEmployeesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AuthorizedEmployeeInfo>() {

            @Override
            public void changed(ObservableValue<? extends AuthorizedEmployeeInfo> observable, AuthorizedEmployeeInfo oldValue, AuthorizedEmployeeInfo newValue) {
                // Your action here
                extendedInformationWindows.setDisable(false);
                extendedInformationWindows.setVisible(true);
                AnchorPane.setTopAnchor(authorizedEmployeesListView, 260.0);
                fioLabel.setText(newValue.getFio());// set fio in extended panel
                divisionLabel.setText(newValue.getDivision());// set fio in extended panel
                positionLabel.setText(newValue.getPosition());// set fio in extended panel
                idLabel.setText(newValue.getId());// set fio in extended panel
                tCLabel.setText(newValue.gettC());// set fio in extended panel
                timeEnterLabel.setText(newValue.getTimeEnter());// set fio in extended panel
                photoImageView.setImage(new Image(newValue.getPathImagePhoto()));// set fio in extended panel

                System.out.println(authorizedEmployeesListView.getLayoutY());
            }
        });

        rollUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                AnchorPane.setTopAnchor(authorizedEmployeesListView, 0.0);
                extendedInformationWindows.setDisable(true);
                extendedInformationWindows.setVisible(false);
            }
        });
    }

}
