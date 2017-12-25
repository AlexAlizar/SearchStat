package ru.geekbrains.internship;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DesktopUI{

    public DesktopUI(StartWindow mainApp, RequestDB connDB) throws Exception {
        FXMLLoader loader = new FXMLLoader(mainApp.getClass().getResource("/searchstat.fxml"));
        AnchorPane load = (AnchorPane) loader.load();
        ControllerUI controller = loader.getController();
        controller.setMainApp(mainApp);
        controller.setDBApp(connDB);
        controller.fillLists();
        Stage stage = mainApp.getStage();
        stage.setTitle("SearchStat");
        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
}
