package ru.geekbrains.internship;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AuthorizationWindow {

    public AuthorizationWindow(StartWindow mainApp, ConnectionDB connDB) throws Exception {
        FXMLLoader loader = new FXMLLoader(mainApp.getClass().getResource("/authwin.fxml"));
        AnchorPane load = (AnchorPane) loader.load();
        ControllerAuth controller = loader.getController();
        controller.setMainApp(mainApp);
        controller.setDBApp(connDB);
        Stage stage = mainApp.getStage();
        stage.setTitle("Authorization");
        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.show();
    }

}
