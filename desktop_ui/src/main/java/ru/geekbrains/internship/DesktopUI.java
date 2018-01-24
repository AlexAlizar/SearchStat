package ru.geekbrains.internship;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

class DesktopUI{

    DesktopUI(StartWindow mainApp) throws IOException {
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
    }
}
