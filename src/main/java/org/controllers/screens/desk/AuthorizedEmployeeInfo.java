package org.controllers.screens.desk;

public class AuthorizedEmployeeInfo {

    private String pathImagePhoto;
    private String fio;
    private String division;
    private String position;
    private String id;
    private String tC;
    private String timeEnter;

    public AuthorizedEmployeeInfo(String pathImagePhoto, String FIO, String division, String position, String id, String tC, String timeEnter) {
        this.pathImagePhoto = pathImagePhoto;
        this.fio = FIO;
        this.division = division;
        this.position = position;
        this.id = id;
        this.tC = tC;
        this.timeEnter = timeEnter;
    }

    public String getPathImagePhoto() {
        return pathImagePhoto;
    }

    public void setPathImagePhoto(String pathImagePhoto) {
        this.pathImagePhoto = pathImagePhoto;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String gettC() {
        return tC;
    }

    public void settC(String tC) {
        this.tC = tC;
    }

    public String getTimeEnter() {
        return timeEnter;
    }

    public void setTimeEnter(String timeEnter) {
        this.timeEnter = timeEnter;
    }
}
