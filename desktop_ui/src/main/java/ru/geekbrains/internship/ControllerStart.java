package ru.geekbrains.internship;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class ControllerStart {

    private StartWindow mainApp;

    public void setMainApp(StartWindow mainApp) {
        this.mainApp = mainApp;
    }

    public void pressStartButton() {
        try {
            mainApp.setRequestDB(new RequestDB());
            new AuthorizationWindow(mainApp);
        } catch (Exception e) {
            new AlertHandler(Alert.AlertType.ERROR,
                    "Ошибка", "Внимание!", "Ошибка подключения к БД");
        }
    }

    public void pressSettingsButton() {
        TextInputDialog dialog = new TextInputDialog(mainApp.getDBStringURL());
        dialog.setTitle("Параметры");
        dialog.setHeaderText("Введите URL сервера БД");
        dialog.setContentText("URL:");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/hdtDialog.css").toExternalForm());
        dialogPane.getStyleClass().add("hdtDialog");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(url -> mainApp.setDBStringURL(url));
    }
}
