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
    private ObservableList<String> sites;
    private ObservableList<String> names;

    public boolean checkAuthorization(String login, String password) {
        return true;
    }

    public ObservableList getTotalStatisticsList(String DBStringURL, String site) {

        totalStatisticsList = FXCollections.observableArrayList();
        try {
            String getTotalStatistics = String.format(GETTOTALSTATISTICS + GETTOTALSTATISTICSPARAMS,
                    URLEncoder.encode(site, "UTF-8"));
            JSONReparsing tsJSONReparsing = new TotalStatisticsJSONReparsing();
            tsJSONReparsing.readJSON(DBStringURL + getTotalStatistics, totalStatisticsList);
        } catch (UnsupportedEncodingException e) {
            new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                    "Внимание!", "Ошибка формирования запроса");
            //e.printStackTrace();
        }
        return totalStatisticsList;
    }

    public ObservableList getTotalStatisticsChartData() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (TotalStatistics ts: totalStatisticsList) {
            pieChartData.add(new PieChart.Data(ts.nameProperty().getValue().toString(),ts.quantityProperty().intValue()));
        }
        return pieChartData;
    }

    public ObservableList getDailyStatisticsList(String DBStringURL,
                                                 String site, String name, LocalDate beginDate, LocalDate endDate) {
        dailyStatisticsList = FXCollections.observableArrayList();
        try {
            String getDailyStatistics = String.format(GETDAILYSTATISTICS + GETDAILYSTATISTICSPARAMS,
                    URLEncoder.encode(name, "UTF-8"), beginDate, endDate, URLEncoder.encode(site, "UTF-8"));
            JSONReparsing dsJSONReparsing = new DailyStatisticsJSONReparsing();
            dsJSONReparsing.readJSON(DBStringURL + getDailyStatistics, dailyStatisticsList);
        } catch (UnsupportedEncodingException e) {
            new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                    "Внимание!", "Ошибка формирования запроса");
            //e.printStackTrace();
        }
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

    public ObservableList getSites(String DBStringURL) {
        sites = FXCollections.observableArrayList();
        JSONReparsing sitesJSONReparsing = new StringJSONReparsing();
        sitesJSONReparsing.readJSON(DBStringURL + GETSITES, sites);
        return sites;
    }

    public ObservableList getNames(String DBStringURL) {
        names = FXCollections.observableArrayList();
        JSONReparsing namesJSONReparsing = new StringJSONReparsing();
        namesJSONReparsing.readJSON(DBStringURL + GETPERSONS, names);
        return names;
    }

}
