package ru.geekbrains.internship;

import javafx.scene.control.Alert;

public class RestorePasswordCommand implements Command {

    @Override
    public void execute() {
        new AlertHandler(Alert.AlertType.WARNING, "Предупреждение",
                "Внимание!", "Функция на данный момент недоступна");
    }
}
