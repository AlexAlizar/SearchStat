package ru.geekbrains.internship;

import javafx.scene.control.Alert;

public class ChangePasswordCommand implements Command {

    @Override
    public void execute() {
        new AlertHandler(Alert.AlertType.WARNING, "Предупреждение",
                "Внимание!", "Извините, операция на данный момент недоступна");
    }
}
