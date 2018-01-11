package ru.geekbrains.internship;

import javafx.scene.control.TextField;

public class PassAuthorizationCommand implements Command {

    private final StartWindow mainApp;
    private final TextField login;
    private final TextField password;

    PassAuthorizationCommand(StartWindow mainApp, TextField login, TextField password) {
        this.mainApp = mainApp;
        this.login = login;
        this.password = password;
    }

    @Override
    public void execute() {
        if (mainApp.getRequestDB().checkAuthorization(login.getText(), password.getText())) {
            new DesktopUI(mainApp);
        }
    }
}
