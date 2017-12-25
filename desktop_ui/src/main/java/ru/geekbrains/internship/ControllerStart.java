package ru.geekbrains.internship;

public class ControllerStart {

    private StartWindow mainApp;
    private RequestDB connDB;

    public void setMainApp(StartWindow mainApp) {
        this.mainApp = mainApp;
    }

    public void setDBApp(RequestDB connDB) {
        this.connDB = connDB;
    }

    public void pressStartButton() throws Exception {
        new AuthorizationWindow(mainApp, connDB);
    }

}
