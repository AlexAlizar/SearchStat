package ru.geekbrains.internship;

import javafx.scene.control.Alert;

import java.io.IOException;

public class StartApplicationCommand implements Command {

    private final StartWindow mainApp;

    StartApplicationCommand(StartWindow mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void execute() {

        try {
            mainApp.setRequestDB(new RequestDB());
            new AuthorizationWindow(mainApp);
        } catch (IOException e ) {
            new AlertHandler(Alert.AlertType.ERROR,
                    "Ошибка", "Внимание!", "Ошибка ввода-вывода");
        } catch (Exception e) {
            new AlertHandler(Alert.AlertType.ERROR,
                    "Ошибка", "Внимание!", "Ошибка подключения к БД");
        }
    }
}
