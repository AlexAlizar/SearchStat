package ru.geekbrains.internship;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class StartWindow extends Application implements ConnectionDBConst  {

    private Stage stage;
    private RequestDB requestDB;
    private String DBStringURL;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public RequestDB getRequestDB() {
        return requestDB;
    }

    public void setRequestDB(RequestDB requestDB) {
        this.requestDB = requestDB;
    }

    public String getDBStringURL() {
        return DBStringURL;
    }

    public void setDBStringURL(String DBStringURL) {
        this.DBStringURL = DBStringURL;
    }

    @Override
    public void start(Stage primaryStage){
        DBStringURL = DBSTRINGURL;
        setStage(primaryStage);
        paint(primaryStage);

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void paint(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/startwin.fxml"));
            AnchorPane load = (AnchorPane) loader.load();
            ControllerStart controller = loader.getController();
            controller.setMainApp(this);
            Scene scene = new Scene(load);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("SearchStat");
            stage.show();
        } catch (IOException e) {
            new AlertHandler(Alert.AlertType.ERROR, "Ошибка", "Внимание!", "Ошибка ввода-вывода");
            //e.printStackTrace();
        }
    }

}
