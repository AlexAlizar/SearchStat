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

    public void setMainApp(StartWindow mainApp) {
        getTotalStatisticsCommand = new GetTotalStatisticsCommand(mainApp, totalStatisticsSite,
                totalStatisticsTable, totalStatisticsChart);
        getDailylStatisticsCommand = new GetDailylStatisticsCommand(mainApp, dailyStatisticsSite,dailyStatisticsName, dailyStatisticsBeginDate,
                dailyStatisticsEndDate, dailyStatisticsTable, dailyStatisticsTotalQuantity,
                dailyStatisticsChart);
        fillLists(mainApp);
        totalStatisticsSite.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                getTotalStatisticsCommand.execute();
            }
        });
        dailyStatisticsSite.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                getDailylStatisticsCommand.execute();
            }
        });
        dailyStatisticsName.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                getDailylStatisticsCommand.execute();
            }
        });
    }

    public void onActionDailyStatisticsBeginDate() {
        getDailylStatisticsCommand.execute();
    }

    public void onActionDailyStatisticsEndDate() {
        getDailylStatisticsCommand.execute();
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
        /*
        totalStatisticsSite.setItems(mainApp.getRequestDB().getSites(mainApp.getDBStringURL()));
        dailyStatisticsSite.setItems(mainApp.getRequestDB().getSites(mainApp.getDBStringURL()));
        dailyStatisticsName.setItems(mainApp.getRequestDB().getNames(mainApp.getDBStringURL()));
        */
        sites = mainApp.getRequestDB().getList(mainApp.getDBStringURL(), GETSITES);
        totalStatisticsSite.setItems(sites);
        dailyStatisticsSite.setItems(sites);
        dailyStatisticsName.setItems(mainApp.getRequestDB().getList(mainApp.getDBStringURL(), GETPERSONS));
    }

}
