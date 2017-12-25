package ru.geekbrains.internship;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class StartWindow extends Application {

    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            RequestDB connDB = new RequestDB();
            setStage(primaryStage);
            paint(primaryStage, connDB);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void paint(Stage stage, RequestDB connDB) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/startwin.fxml"));
        AnchorPane load = (AnchorPane) loader.load();
        ControllerStart controller = loader.getController();
        controller.setMainApp(this);
        controller.setDBApp(connDB);
        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("SearchStat");
        stage.show();
    }

}
