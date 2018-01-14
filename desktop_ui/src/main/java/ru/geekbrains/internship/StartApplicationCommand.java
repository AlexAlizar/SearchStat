package ru.geekbrains.internship;

import javafx.scene.control.Alert;

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
        } catch (Exception e) {
            new AlertHandler(Alert.AlertType.ERROR,
                    "Ошибка", "Внимание!", "Ошибка подключения к БД");
        }
    }
}
