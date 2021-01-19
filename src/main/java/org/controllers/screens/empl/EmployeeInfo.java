package org.controllers.screens.empl;

import javafx.beans.property.SimpleStringProperty;

public class EmployeeInfo {

     SimpleStringProperty personnelNumber;
     SimpleStringProperty fio;
     SimpleStringProperty id;
     SimpleStringProperty division;
     SimpleStringProperty position;


    public EmployeeInfo(String personnelNumber, String fio, String id, String division, String position) {
        this.personnelNumber = new SimpleStringProperty(personnelNumber);
        this.fio = new SimpleStringProperty(fio);
        this.id = new SimpleStringProperty(id);
        this.division = new SimpleStringProperty(division);
        this.position = new SimpleStringProperty(position);
    }
}