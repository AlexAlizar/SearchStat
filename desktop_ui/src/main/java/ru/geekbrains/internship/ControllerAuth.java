package ru.geekbrains.internship;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ControllerAuth {

    private StartWindow mainApp;

    @FXML
    private TextField login;
    @FXML
    private TextField password;

    public void setMainApp(StartWindow mainApp) {
        this.mainApp = mainApp;
    }

    public void pressOkButton() {
        if (mainApp.getRequestDB().checkAuthorization(login.getText(), password.getText())) {
            new DesktopUI(mainApp);
        }
    }

    public void pressCancelButton() {
        mainApp.paint(mainApp.getStage());
    }

    public void pressRestorePasswordButton() {

    }

}
