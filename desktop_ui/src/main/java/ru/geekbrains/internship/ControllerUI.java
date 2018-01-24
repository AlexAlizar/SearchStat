package ru.geekbrains.internship;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerUI implements Initializable, ConnectionDBConst {

    private Command getTotalStatisticsCommand;
    private Command getDailylStatisticsCommand;
    private Command exitToMainMenuCommand;
    private Command exitToOSCommand;
    private Command aboutCommand;
    private Command changePasswordCommand;

    @FXML
    private ChoiceBox<String> totalStatisticsSite;
    @FXML
    private TableView<TotalStatistics> totalStatisticsTable;
    @FXML
    private TableColumn<TotalStatistics, String> tstColumnName;
    @FXML
    private TableColumn<TotalStatistics, Number> tstColumnQuantity;
    @FXML
    private ChoiceBox<String> dailyStatisticsSite;
    @FXML
    private ChoiceBox<String> dailyStatisticsName;
    @FXML
    private DatePicker dailyStatisticsBeginDate;
    @FXML
    private DatePicker dailyStatisticsEndDate;
    @FXML
    private TableView<DailyStatistics> dailyStatisticsTable;
    @FXML
    private TableColumn<DailyStatistics, String> dstColumnDate;
    @FXML
    private TableColumn<DailyStatistics, Number> dstColumnQuantity;
    @FXML
    private TextField dailyStatisticsTotalQuantity;
    @FXML
    private PieChart totalStatisticsChart;
    @FXML
    private LineChart<String, Number> dailyStatisticsChart;
    @FXML
    private MenuItem userName;

    public void setMainApp(StartWindow mainApp) {
        getTotalStatisticsCommand = new GetTotalStatisticsCommand(mainApp, totalStatisticsSite,
                totalStatisticsTable, totalStatisticsChart);
        getDailylStatisticsCommand = new GetDailylStatisticsCommand(mainApp, dailyStatisticsSite,dailyStatisticsName, dailyStatisticsBeginDate,
                dailyStatisticsEndDate, dailyStatisticsTable, dailyStatisticsTotalQuantity,
                dailyStatisticsChart);
        exitToMainMenuCommand = new ExitToMainMenuCommand(mainApp);
        exitToOSCommand = new ExitToOSCommand(mainApp);
        aboutCommand = new AboutCommand(mainApp);
        changePasswordCommand = new ChangePasswordCommand();
        fillLists(mainApp);
        totalStatisticsSite.valueProperty().addListener((ov, t, t1) -> getTotalStatisticsCommand.execute());
        dailyStatisticsSite.valueProperty().addListener((ov, t, t1) -> getDailylStatisticsCommand.execute());
        dailyStatisticsName.valueProperty().addListener((ov, t, t1) -> getDailylStatisticsCommand.execute());
    }

    public void onActionDailyStatisticsBeginDate() {
        getDailylStatisticsCommand.execute();
    }

    public void onActionDailyStatisticsEndDate() {
        getDailylStatisticsCommand.execute();
    }

    public void onActionExitToMainMenu() {
        exitToMainMenuCommand.execute();
    }

    public void onActionExitToOS() {
        exitToOSCommand.execute();
    }

    public void onActionAbout() {
        aboutCommand.execute();
    }

    public void onActionChangePassword() {
        changePasswordCommand.execute();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tstColumnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tstColumnQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        dstColumnDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        dstColumnQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
    }

    private void fillLists(StartWindow mainApp) {
        ObservableList<String> sites;
        sites = mainApp.getRequestDB().getList(mainApp.getDBStringURL(), ACTION_GET_SITES,
                mainApp.getToken());
        totalStatisticsSite.setItems(sites);
        dailyStatisticsSite.setItems(sites);
        dailyStatisticsName.setItems(mainApp.getRequestDB().getList(mainApp.getDBStringURL(),
                ACTION_GET_PERSONS, mainApp.getToken()));
        userName.setText("Пользователь: " + mainApp.getUserName());
    }

}
