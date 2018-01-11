package ru.geekbrains.internship;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;

public class RequestDB implements ConnectionDBConst{

    private ObservableList<TotalStatistics> totalStatisticsList;
    private ObservableList<DailyStatistics> dailyStatisticsList;

    public boolean checkAuthorization(String login, String password) {
        return true;
    }

    public ObservableList getTotalStatisticsList(String DBStringURL, String site) {

        totalStatisticsList = FXCollections.observableArrayList();
        if (!DBStringURL.toUpperCase().equals(FAKEDB.toUpperCase())) {
            try {
                String getTotalStatistics = String.format(GETTOTALSTATISTICS + GETTOTALSTATISTICSPARAMS,
                        URLEncoder.encode(site, "UTF-8"));
                JSONReparsing tsJSONReparsing = new TotalStatisticsJSONReparsing();
                tsJSONReparsing.readJSON(DBStringURL + DBSTRINGURLAPI + getTotalStatistics, totalStatisticsList);
            } catch (UnsupportedEncodingException e) {
                new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                        "Внимание!", "Ошибка формирования запроса");
                //e.printStackTrace();
            }
        } else {
            new FakeData().getFakeTotalStatistics(totalStatisticsList);
        }
        return totalStatisticsList;
    }

    public ObservableList getTotalStatisticsChartData() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (TotalStatistics ts: totalStatisticsList) {
            pieChartData.add(new PieChart.Data(ts.nameProperty().getValue(),ts.quantityProperty().intValue()));
    }
        return pieChartData;
    }

    public ObservableList getDailyStatisticsList(String DBStringURL,
                                                 String site, String name, LocalDate beginDate, LocalDate endDate) {
        dailyStatisticsList = FXCollections.observableArrayList();
        if (!DBStringURL.toUpperCase().equals(FAKEDB.toUpperCase())) {
            try {
                String getDailyStatistics = String.format(GETDAILYSTATISTICS + GETDAILYSTATISTICSPARAMS,
                        URLEncoder.encode(name, "UTF-8"), beginDate, endDate, URLEncoder.encode(site, "UTF-8"));
                JSONReparsing dsJSONReparsing = new DailyStatisticsJSONReparsing();
                dsJSONReparsing.readJSON(DBStringURL + DBSTRINGURLAPI + getDailyStatistics, dailyStatisticsList);
            } catch (UnsupportedEncodingException e) {
                new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                        "Внимание!", "Ошибка формирования запроса");
                //e.printStackTrace();
            }
        } else {
            new FakeData().getFakeDailyStatistics(dailyStatisticsList, beginDate, endDate);
        }
        return dailyStatisticsList;
    }

    public ObservableList clearDailyStatisticsList() {
        dailyStatisticsList = FXCollections.observableArrayList();
        dailyStatisticsList.clear();
        return dailyStatisticsList;
    }

    public int getDailyStatisticsTotal() {
        int totalPages = 0;
        for (DailyStatistics ds: dailyStatisticsList) {
            totalPages += ds.quantityProperty().intValue();
        }
        return totalPages;
    }

    public XYChart.Series getDailyStatisticsChartData(String name) {
        XYChart.Series series = new XYChart.Series();
        series.setName(name);
        for (DailyStatistics ds: dailyStatisticsList) {
            series.getData().add(new XYChart.Data(ds.dateProperty().getValue(), ds.quantityProperty().intValue()));
        }
        return series;
    }

    public ObservableList getList(String DBStringURL, String getList) {
        ObservableList<String> list = FXCollections.observableArrayList();
        if (!DBStringURL.toUpperCase().equals(FAKEDB.toUpperCase())) {
            JSONReparsing sitesJSONReparsing = new StringJSONReparsing();
            sitesJSONReparsing.readJSON(DBStringURL + DBSTRINGURLREQUEST + getList, list);
        } else {
            new FakeData().getFakeList(list, getList);
        }
        return list;
    }
}
