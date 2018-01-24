package ru.geekbrains.internship;

import javafx.scene.control.Alert;

import java.io.IOException;

public class ExitToMainMenuCommand implements Command {

    private final StartWindow mainApp;

    ExitToMainMenuCommand(StartWindow mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void execute() {
        try {
            mainApp.paint(mainApp.getStage());
        } catch (IOException e) {
            new AlertHandler(Alert.AlertType.ERROR,
                    "Ошибка", "Внимание!", "Ошибка ввода-вывода");
        }
    }
}
