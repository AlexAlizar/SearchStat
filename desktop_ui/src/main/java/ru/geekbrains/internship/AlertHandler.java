package ru.geekbrains.internship;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

class AlertHandler {

    public AlertHandler(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/hdtDialog.css").toExternalForm());
        dialogPane.getStyleClass().add("hdtDialog");
        alert.showAndWait();
    }
}
