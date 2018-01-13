package ru.geekbrains.internship;

import javafx.scene.control.Alert;
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
        String token = mainApp.getRequestDB().checkAuthorization(mainApp.getDBStringURL(), login.getText(), password.getText()).trim();
        if (!token.isEmpty()) {
            token = token.substring(1, token.length() - 1);
            mainApp.setToken(token);
            new DesktopUI(mainApp);
        } else {
            new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                    "Внимание!", "Неверный логин и/или пароль");
        }
    }
}
