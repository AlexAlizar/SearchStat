package ru.geekbrains.internship;

import javafx.scene.chart.LineChart;
import javafx.scene.control.*;

import java.time.LocalDate;

public class GetDailylStatisticsCommand implements Command {
    private final StartWindow mainApp;
    private final ChoiceBox<String> dailyStatisticsSite;
    private final ChoiceBox<String> dailyStatisticsName;
    private final DatePicker dailyStatisticsBeginDate;
    private final DatePicker dailyStatisticsEndDate;
    private final TableView<DailyStatistics> dailyStatisticsTable;
    private final TextField dailyStatisticsTotalQuantity;
    private final LineChart<String, Number> dailyStatisticsChart;

    GetDailylStatisticsCommand(StartWindow mainApp, ChoiceBox<String> dailyStatisticsSite,
                                      ChoiceBox<String> dailyStatisticsName, DatePicker dailyStatisticsBeginDate,
                                      DatePicker dailyStatisticsEndDate, TableView<DailyStatistics> dailyStatisticsTable,
                                      TextField dailyStatisticsTotalQuantity, LineChart<String, Number> dailyStatisticsChart) {
        this.mainApp = mainApp;
        this.dailyStatisticsSite = dailyStatisticsSite;
        this.dailyStatisticsName = dailyStatisticsName;
        this.dailyStatisticsBeginDate = dailyStatisticsBeginDate;
        this.dailyStatisticsEndDate = dailyStatisticsEndDate;
        this.dailyStatisticsTable = dailyStatisticsTable;
        this.dailyStatisticsTotalQuantity = dailyStatisticsTotalQuantity;
        this.dailyStatisticsChart = dailyStatisticsChart;
    }

    @Override
    public void execute() {
        LocalDate beginDate = dailyStatisticsBeginDate.getValue();
        LocalDate endDate = dailyStatisticsEndDate.getValue();
        if (dailyStatisticsSite.getValue() != null) {
            if (dailyStatisticsName.getValue() != null) {
                if (beginDate != null) {
                    if (endDate != null) {
                        if (endDate.compareTo(beginDate) < 0) {
                            mainApp.getRequestDB().clearDailyStatisticsList();
                            new AlertHandler(Alert.AlertType.WARNING, "Не верно заполнены параметры",
                                    "Внимание!", "Начальная дата должна быть раньше конечной");
                        }
                        dailyStatisticsTable.setItems(
                                mainApp.getRequestDB().getDailyStatisticsList(mainApp.getDBStringURL(),
                                        dailyStatisticsSite.getValue(), dailyStatisticsName.getValue(),
                                        beginDate, endDate));
                        dailyStatisticsTotalQuantity.setText(Integer.toString(mainApp.getRequestDB().getDailyStatisticsTotal()));
                        dailyStatisticsChart.getData().clear();
                        dailyStatisticsChart.getData().add(mainApp.getRequestDB().getDailyStatisticsChartData(dailyStatisticsName.getValue()));
                        dailyStatisticsChart.setTitle("");
                    }
                }
            }
        }
    }
}
