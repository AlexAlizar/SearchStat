package ru.geekbrains.internship;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class StartWindow extends Application implements ConnectionDBConst  {

    private Stage stage;
    private RequestDB requestDB;
    private String DBStringURL;
    private String token;
    private String userName;

    public Stage getStage() {
        return stage;
    }

    private void setStage(Stage stage) {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void start(Stage primaryStage){
        DBStringURL = DBSTRINGURL;
        setStage(primaryStage);
        stage.getIcons().add(new Image("/icon.png"));
        try {
            paint(primaryStage);
        } catch (IOException e) {
            new AlertHandler(Alert.AlertType.ERROR,
                    "Ошибка", "Внимание!", "Ошибка ввода-вывода");
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void paint(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/startwin.fxml"));
        AnchorPane load = loader.load();
        ControllerStart controller = loader.getController();
        controller.setMainApp(this);
        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("SearchStat");
        stage.show();
    }

}
