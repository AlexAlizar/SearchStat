package ru.geekbrains.internship;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;

public class ConnectionDB {

    private ObservableList<TotalStatistics> totalStatisticsList;
    private ObservableList<DailyStatistics> dailyStatisticsList;
    private ObservableList<String> sites;
    private ObservableList<String> names;

    public boolean checkAuthorization(String login, String password) throws Exception {
        return true;
    }

    public ObservableList getTotalStatisticsList(String site) throws Exception {
        totalStatisticsList = FXCollections.observableArrayList(
                new TotalStatistics("Путин",5000),
                new TotalStatistics("Медведев",4000),
                new TotalStatistics("Навальный",3000));
        return totalStatisticsList;
    }

    public ObservableList getTotalStatisticsChartData() throws  Exception {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (TotalStatistics ts: totalStatisticsList) {
            pieChartData.add(new PieChart.Data(ts.nameProperty().getValue().toString(),ts.quantityProperty().intValue()));
        }
        return pieChartData;
    }

    public ObservableList getDailyStatisticsList(String site, String name, LocalDate beginDate, LocalDate endDate) throws Exception {
        dailyStatisticsList = FXCollections.observableArrayList(
                new DailyStatistics("01/12/2017", 50),
                new DailyStatistics("02/12/2017",40),
                new DailyStatistics("03/12/2017",30),
                new DailyStatistics("04/12/2017",40),
                new DailyStatistics("05/12/2017",60),
                new DailyStatistics("06/12/2017",10),
                new DailyStatistics("07/12/2017",20));

        return dailyStatisticsList;
    }

    public int getDailyStatisticsTotal() throws Exception {
        int totalPages = 0;
        for (DailyStatistics ds: dailyStatisticsList) {
            totalPages += ds.quantityProperty().intValue();
        }
        return totalPages;
    }

    public XYChart.Series getDailyStatisticsChartData() throws Exception {
        XYChart.Series series = new XYChart.Series();
        for (DailyStatistics ds: dailyStatisticsList) {
            series.getData().add(new XYChart.Data(ds.dateProperty().getValue(), ds.quantityProperty().intValue()));
        }
        return series;
    }

    public ObservableList getSites() throws Exception {
        sites = FXCollections.observableArrayList(
                "lenta.ru","rbc.ru","rambler.ru");
        return sites;
    }
    public ObservableList getNames() throws Exception {
        names = FXCollections.observableArrayList(
                "Путин","Медведев","Навальный");
        return names;
    }

}
