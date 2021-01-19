package org.controllers.screens.empl;

import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeInfo that = (EmployeeInfo) o;
        return fio.get().equals(that.fio.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(fio);
    }

    @Override
    public String toString() {
        return fio.get();
    }
}