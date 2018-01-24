package ru.geekbrains.internship;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class PassAuthorizationCommand implements Command, ConnectionDBConst {

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
        if (!token.isEmpty() && !token.equals(AUTH_FAILED)) {
            token = token.substring(1, token.length() - 1);
            mainApp.setToken(token);
            mainApp.setUserName(login.getText());
            try {
                new DesktopUI(mainApp);
            } catch (IOException e) {
                new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                        "Внимание!", "Ошибка ввода-вывода");
            }
        } else {
            new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                    "Внимание!", "Неверный логин и/или пароль");
        }
    }
}
