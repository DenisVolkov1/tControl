package org.controllers.screens.empl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private ComboBox<String> fioComboBox;
    @FXML
    private TableView<EmployeeInfo> employeesTableView;
    @FXML
    private Spinner<Integer> paginatorSpinner;

    @FXML
    private Pagination pageScrollPagination;

    private ObservableList<String> options;
    private List<EmployeeInfo> employeesList;
    private Integer rowsPerPage = 20;
    private static int totalPages;

    @FXML
    private void initialize() {
        employeesList = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            employeesList.add(new EmployeeInfo(String.valueOf(i+1),"Волков Василий Егорович","465789123","Новое","Дежурный"));
        }//
        //set page on list 20, 30, 50
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.ListSpinnerValueFactory<Integer>( FXCollections.observableArrayList( Arrays.asList(20,30,50)));
        paginatorSpinner.setValueFactory(valueFactory);
        //add listener to spinner page
        paginatorSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            System.out.println("New value: "+newValue);
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

        //
        totalPages = (int) Math.ceil(employeesList.size() / (float) rowsPerPage);
        pageScrollPagination.setPageCount(totalPages);
        //

        personnelNumberColumn.setCellValueFactory(param->param.getValue().personnelNumber);
        fioColumn.setCellValueFactory(param->param.getValue().fio);
        idColumn.setCellValueFactory(param->param.getValue().id);
        divisionColumn.setCellValueFactory(param->param.getValue().division);
        positionColumn.setCellValueFactory(param->param.getValue().position);

        pageScrollPagination.setPageFactory(this::createPage);

    options = FXCollections.observableArrayList("Option 1", "Option 2", "Option 3","Option 4", "Option 5", "Option 6","Option 7", "Option 8", "Option 9","Option 10", "Option 11", "Option 12");
    fioComboBox.setItems(options);
        fioTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
            fioComboBox.show();
        });
    }

    private Node createPage(Integer pageIndex) {
        System.out.println("pageIndex "+pageIndex);
        System.out.println("rowsPerPage "+ rowsPerPage);
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex+ rowsPerPage, employeesList.size());
        System.out.println("fromIndex "+fromIndex);
        System.out.println("toIndex "+toIndex);
        System.out.println("-+---------------------------------------------");
        employeesTableView.setItems(FXCollections.observableArrayList(employeesList.subList(fromIndex,toIndex)));
        return employeesTableView;
    }

}
