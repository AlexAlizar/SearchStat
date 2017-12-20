package ru.geekbrains.internship;

import javafx.fxml.*;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControllerUI implements Initializable {

    private StartWindow mainApp;
    private ConnectionDB connDB;

    @FXML
    private ChoiceBox<String> totalStatisticsSite;
    @FXML
    private Button totalStatisticsUpdateButton;
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
    private Button dailyStatisticsUpdateButton;
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
        this.mainApp = mainApp;
    }

    public void setDBApp(ConnectionDB connDB) {
        this.connDB = connDB;
    }

    public void pressTotalStatisticsUpdateButton() throws Exception {
        if (totalStatisticsSite.getValue() != null) {
            totalStatisticsTable.setItems(connDB.getTotalStatisticsList(totalStatisticsSite.getValue()));
            totalStatisticsChart.setData(connDB.getTotalStatisticsChartData());
            totalStatisticsChart.setLabelLineLength(10);
            totalStatisticsChart.setLegendSide(Side.LEFT);
        }
    }

    public void pressDailyStatisticsUpdateButton() throws Exception {
        LocalDate beginDate = dailyStatisticsBeginDate.getValue();
        LocalDate endDate = dailyStatisticsEndDate.getValue();
        if (!(dailyStatisticsSite.getValue() == null || dailyStatisticsName.getValue() == null ||
                beginDate == null || endDate == null)) {
            if (endDate.compareTo(beginDate) >= 0) {
                dailyStatisticsTable.setItems(
                        connDB.getDailyStatisticsList(
                                dailyStatisticsSite.getValue(), dailyStatisticsName.getValue(),
                                beginDate, endDate));
                dailyStatisticsTotalQuantity.setText(Integer.toString(connDB.getDailyStatisticsTotal()));
                dailyStatisticsChart.getData().add(connDB.getDailyStatisticsChartData());
                dailyStatisticsChart.setTitle("");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tstColumnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tstColumnQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        dstColumnDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        dstColumnQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
    }

    public void fillLists() throws Exception {
        totalStatisticsSite.setItems(connDB.getSites());
        dailyStatisticsSite.setItems(connDB.getSites());
        dailyStatisticsName.setItems(connDB.getNames());
    }

}
