package ru.geekbrains.internship;

import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class SettingsCommand implements Command {

    private final StartWindow mainApp;

    SettingsCommand(StartWindow mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void execute() {
        TextInputDialog dialog = new TextInputDialog(mainApp.getDBStringURL());
        dialog.setTitle("Параметры");
        dialog.setHeaderText("Введите URL сервера БД");
        dialog.setContentText("URL:");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/hdtDialog.css").toExternalForm());
        dialogPane.getStyleClass().add("hdtDialog");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(mainApp::setDBStringURL);
    }
}
