package ru.geekbrains.internship;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

class DesktopUI{

    DesktopUI(StartWindow mainApp) {
        try {
            FXMLLoader loader = new FXMLLoader(mainApp.getClass().getResource("/searchstat.fxml"));
            AnchorPane load = loader.load();
            ControllerUI controller = loader.getController();
            controller.setMainApp(mainApp);
            Stage stage = mainApp.getStage();
            stage.setTitle("SearchStat");
            Scene scene = new Scene(load);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            new AlertHandler(Alert.AlertType.ERROR, "Ошибка", "Внимание!", "Ошибка ввода-вывода");
            //e.printStackTrace();
        }
    }
}
