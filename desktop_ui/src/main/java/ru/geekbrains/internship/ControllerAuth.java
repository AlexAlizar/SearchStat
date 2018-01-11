package ru.geekbrains.internship;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ControllerAuth {

    private Command passAuthorizationCommand;
    private Command cancelAuthorizationCommand;
    private Command restorePasswordCommand;

    @FXML
    private TextField login;
    @FXML
    private TextField password;

    public void setMainApp(StartWindow mainApp) {
        passAuthorizationCommand = new PassAuthorizationCommand(mainApp, login, password);
        cancelAuthorizationCommand = new CancelAuthorizationCommand(mainApp);
        restorePasswordCommand = new RestorePasswordCommand();
    }

    public void pressOkButton() {
        passAuthorizationCommand.execute();
    }

    public void pressCancelButton() {
        cancelAuthorizationCommand.execute();
    }

    public void pressRestorePasswordButton() {
        restorePasswordCommand.execute();
    }

}
