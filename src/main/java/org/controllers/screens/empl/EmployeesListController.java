package org.controllers.screens.empl;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import org.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeesListController {
    //table start
    @FXML
    private TableColumn<EmployeeInfo,String> personnelNumberColumn;
    @FXML
    private TableColumn<EmployeeInfo,String> fioColumn;
    @FXML
    private TableColumn<EmployeeInfo,String>idColumn;
    @FXML
    private TableColumn<EmployeeInfo,String> divisionColumn;
    @FXML
    private TableColumn<EmployeeInfo,String> positionColumn;
    //table end
    @FXML
    private TextField fioTextField;
    @FXML
    private Button searchButton;
    @FXML
    private TextField idTextField;
    @FXML
    private ComboBox<EmployeeInfo> fioComboBox;
    @FXML
    private TableView<EmployeeInfo> employeesTableView;
    @FXML
    private Button startButton;
    @FXML
    private Button endButton;
    @FXML
    private Spinner<Integer> paginatorSpinner;

    @FXML
    private Pagination pageScrollPagination;

    //private ObservableList<String> options;
    private List<EmployeeInfo> employeesList;
    private Integer rowsPerPage = 20;
    private static int totalPages;

    @FXML
    private void initialize() {
        employeesList = new ArrayList<>();
        employeesList.add(new EmployeeInfo(String.valueOf(1),"Волков Василий Егорович","10077338","Новое","Дежурный"));
        employeesList.add(new EmployeeInfo(String.valueOf(2),"Ардаков Игорь Герасимович","48210962","Новое","Дежурный"));
        employeesList.add(new EmployeeInfo(String.valueOf(3),"Донченко Иван Андреевич","83037252","Новое","Дежурный"));
        employeesList.add(new EmployeeInfo(String.valueOf(4),"Кулагина Юлия Анатольевна","44504450","Новое","Дежурный"));
        employeesList.add(new EmployeeInfo(String.valueOf(5),"Бирюков Евгений Евгеньевич","10727594","Новое","Дежурный"));
        employeesList.add(new EmployeeInfo(String.valueOf(6),"Гришина Ольга Константиновна","88292518","Новое","Дежурный"));
        employeesList.add(new EmployeeInfo(String.valueOf(7),"Карсева Полина Алексеевна","49268997","Новое","Дежурный"));
        employeesList.add(new EmployeeInfo(String.valueOf(8),"Логинов Сергей Николаевич","79367476","Новое","Дежурный"));
        employeesList.add(new EmployeeInfo(String.valueOf(9),"Жаркова Алла Юрьевна","79093021","Новое","Дежурный"));
        for (int i = 10; i < 100; i++) {
            if(i == 66) employeesList.add(new EmployeeInfo(String.valueOf(i),"Зебра Юлия Анатольевна","27786010","Новое","Дежурный"));
            else employeesList.add(new EmployeeInfo(String.valueOf(i),"Жаркова Алла Юрьевна","53481833","Новое","Дежурный"));
        }

        //SET FIELD fio AND COMBO BOX
        fioTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            fioComboBox.setItems(filterOnTypingFio(newValue));
            fioComboBox.show();
        });
        fioComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            fioTextField.setText(newValue.fio.get());
            int indexRow =  employeesList.indexOf(newValue) + 1;
            pageScrollPagination.setCurrentPageIndex(getIndexPageOn(newValue));
            selectRow(getIndexRowOnPage(newValue));
        });
        //SET SEARCH BUTTON
        //id field only numbers
        idTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    idTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }

        });
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if (!idTextField.getText().isEmpty()) {
                    EmployeeInfo employeeInfo = null;
                    for (EmployeeInfo ei : employeesTableView.getItems()) {
                        if (ei.id.get().equals(idTextField.getText())) employeeInfo = ei;
                    }
                    if (employeeInfo != null) selectRow(employeesTableView.getItems().indexOf(employeeInfo));
                    else Util.informationDialog("Пользователь с таким ID не найден!");
                } else Util.informationDialog("Заполните поле ID пользователя!");
            }
        });
        //SET START_BUTTON
        startButton.setOnAction(e -> {
            if (totalPages > 0) {
                pageScrollPagination.setCurrentPageIndex(0);
            }
        });
        //SET END_BUTTON
        endButton.setOnAction(e -> {
            if (totalPages > 0) {
                pageScrollPagination.setCurrentPageIndex(totalPages);
            }
        });
        //SET PAGE ON LIST 20, 30, 50
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.ListSpinnerValueFactory<Integer>( FXCollections.observableArrayList( Arrays.asList(20,30,50)));
        paginatorSpinner.setValueFactory(valueFactory);
        //ADD LISTENER TO SPINNER PAGE
        paginatorSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            switch (newValue) {
                case 20 : {
                    rowsPerPage = 20;
                    employeesTableView.setFixedCellSize(27.0);
                    totalPages = (int) Math.ceil(employeesList.size() / (float) rowsPerPage);
                    pageScrollPagination.setPageCount(totalPages);
                    pageScrollPagination.setPageFactory(this::createPage); }
                    break;
                case 30 : {
                    rowsPerPage = 30;
                    employeesTableView.setFixedCellSize(21.0);
                    totalPages = (int) Math.ceil(employeesList.size() / (float) rowsPerPage);
                    pageScrollPagination.setPageCount(totalPages);
                    pageScrollPagination.setPageFactory(this::createPage);
                    break;
                }
                case 50 : {
                    rowsPerPage = 50;
                    employeesTableView.setFixedCellSize(21.0);
                    totalPages = (int) Math.ceil(employeesList.size() / (float) rowsPerPage);
                    pageScrollPagination.setPageCount(totalPages);
                    pageScrollPagination.setPageFactory(this::createPage); }
                    break;
                default: break;
            }
        });
        //ADD LISTENER TO SPINNER PAGE END
        //INIT TOTAL PAGES
        totalPages = (int) Math.ceil(employeesList.size() / (float) rowsPerPage);
        pageScrollPagination.setPageCount(totalPages);
        //SET DATA OBJECT TO COLUMNS
        personnelNumberColumn.setCellValueFactory(param->param.getValue().personnelNumber);
        fioColumn.setCellValueFactory(param->param.getValue().fio);
        idColumn.setCellValueFactory(param->param.getValue().id);
        divisionColumn.setCellValueFactory(param->param.getValue().division);
        positionColumn.setCellValueFactory(param->param.getValue().position);
        pageScrollPagination.setPageFactory(this::createPage);

    }
    private ObservableList<EmployeeInfo> filterOnTypingFio(String pattern) {
        List<EmployeeInfo> resultFiltering = employeesList.stream()
                .filter(employeeInfo -> employeeInfo.fio.get().matches(".*"+pattern+".*")).collect(Collectors.toList());
        System.out.println(resultFiltering);
        return FXCollections.observableArrayList(resultFiltering);
    }
    private int getIndexPageOn(EmployeeInfo employeeInfo) {
        int i = employeesTableView.getItems().indexOf(employeeInfo) + 1;
        int res = (int) Math.ceil(i / (float) rowsPerPage) - 1;
        if(res<0) throw new RuntimeException("page -1 ");
        return res;
    }
    private int getIndexRowOnPage(EmployeeInfo employeeInfo) {
        int i = employeesTableView.getItems().indexOf(employeeInfo);
        int res = i % rowsPerPage;
        if(res<0) throw new RuntimeException("page -1 ");
        return res;
    }
    private void selectRow(int index) {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                employeesTableView.requestFocus();
                employeesTableView.getSelectionModel().select(index);
                employeesTableView.getFocusModel().focus(index);
            }
        });
    }

    private Node createPage(Integer pageIndex) {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex+ rowsPerPage, employeesList.size());
        employeesTableView.setItems(FXCollections.observableArrayList(employeesList.subList(fromIndex,toIndex)));
        return employeesTableView;
    }

}
