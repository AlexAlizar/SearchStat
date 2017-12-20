package ru.geekbrains.internship;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ControllerAuth {

    private StartWindow mainApp;
    private ConnectionDB connDB;

    @FXML
    private TextField login;
    @FXML
    private TextField password;

    public void setMainApp(StartWindow mainApp) {
        this.mainApp = mainApp;
    }

    public void setDBApp(ConnectionDB connDB) {
        this.connDB = connDB;
    }

    public void pressOkButton() throws Exception {
        if (connDB.checkAuthorization(login.getText(), password.getText())) {
            new DesktopUI(mainApp, connDB);
        }
    }

    public void pressCancelButton() throws Exception{
        mainApp.paint(mainApp.getStage(), connDB);
    }

    public void pressRestorePasswordButton() throws Exception{

    }

}
