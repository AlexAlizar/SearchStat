package ru.geekbrains.internship;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

class AuthorizationWindow {

    AuthorizationWindow(StartWindow mainApp) {
        try {
            FXMLLoader loader = new FXMLLoader(mainApp.getClass().getResource("/authwin.fxml"));
            AnchorPane load = loader.load();
            ControllerAuth controller = loader.getController();
            controller.setMainApp(mainApp);
            Stage stage = mainApp.getStage();
            stage.setTitle("Authorization");
            Scene scene = new Scene(load);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            new AlertHandler(Alert.AlertType.ERROR, "Ошибка", "Внимание!", "Ошибка ввода-вывода");
            //e.printStackTrace();
        }
    }

}
