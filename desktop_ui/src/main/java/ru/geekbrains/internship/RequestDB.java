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

    public String checkAuthorization(String DBStringURL, String login, String password) {

        if (!DBStringURL.toUpperCase().equals(FAKEDB.toUpperCase())) {
            try {
                String getAuthorization = String.format(ACTION_GET_AUTH + ACTION_GET_AUTH_PARAMS,
                        URLEncoder.encode(login, "UTF-8"), URLEncoder.encode(password, "UTF-8"));
                return new TokenReading().readToken(DBStringURL + DBSTRINGURLAPI + getAuthorization);
            } catch (UnsupportedEncodingException e) {
                new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                        "Внимание!", "Ошибка формирования запроса");
                return "";
            } catch (IllegalStateException e) {
                new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                    "Внимание!", "Ошибка в полученных данных");
                return "";
            } catch (Exception e) {
                new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                    "Внимание!", "Ошибка подключения к базе данных");
                return "";
            }
        } else {
            return "00";
        }
    }

    public ObservableList getTotalStatisticsList(String DBStringURL, String site, String token) {

        totalStatisticsList = FXCollections.observableArrayList();
        if (!DBStringURL.toUpperCase().equals(FAKEDB.toUpperCase())) {
            try {
                String getTotalStatistics = String.format(ACTION_GET_TOTALSTATISTICS + ACTION_GET_TOTALSTATISTICS_PARAMS,
                        URLEncoder.encode(site, "UTF-8"));
                String actionToken = String.format(ACTION_TOKEN, URLEncoder.encode(token, "UTF-8"));
                JSONReparsing tsJSONReparsing = new TotalStatisticsJSONReparsing();
                tsJSONReparsing.readJSON(DBStringURL + DBSTRINGURLAPI + actionToken + getTotalStatistics,
                        totalStatisticsList);
            } catch (UnsupportedEncodingException e) {
                new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                        "Внимание!", "Ошибка формирования запроса");
            } catch (IllegalStateException e) {
                new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                        "Внимание!", "Ошибка в полученных данных");
            } catch (UnsupportedOperationException e) {
                new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                        "Внимание!", "Ошибка разборки данных");
            } catch (Exception e) {
                new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                        "Внимание!", "Ошибка подключения к базе данных");
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
                                                 String site, String name, LocalDate beginDate,
                                                 LocalDate endDate, String token) {
        dailyStatisticsList = FXCollections.observableArrayList();
        if (!DBStringURL.toUpperCase().equals(FAKEDB.toUpperCase())) {
            try {
                String getDailyStatistics = String.format(ACTION_GET_DAILYSTATISTICS + ACTION_GET_GETDAILYSTATISTICS_PARAMS,
                        URLEncoder.encode(name, "UTF-8"), beginDate, endDate, URLEncoder.encode(site, "UTF-8"));
                String actionToken = String.format(ACTION_TOKEN, URLEncoder.encode(token, "UTF-8"));
                JSONReparsing dsJSONReparsing = new DailyStatisticsJSONReparsing();
                dsJSONReparsing.readJSON(DBStringURL + DBSTRINGURLAPI + actionToken + getDailyStatistics,
                        dailyStatisticsList);
            } catch (UnsupportedEncodingException e) {
                new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                        "Внимание!", "Ошибка формирования запроса");
            } catch (IllegalStateException e) {
                new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                        "Внимание!", "Ошибка в полученных данных");
            } catch (UnsupportedOperationException e) {
                new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                        "Внимание!", "Ошибка разборки данных");
            } catch (Exception e) {
                new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                        "Внимание!", "Ошибка подключения к базе данных");
            }
        } else {
            new FakeData().getFakeDailyStatistics(dailyStatisticsList, beginDate, endDate);
        }
        return dailyStatisticsList;
    }

    public void clearDailyStatisticsList() {
        dailyStatisticsList = FXCollections.observableArrayList();
        dailyStatisticsList.clear();
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

    public ObservableList getList(String DBStringURL, String getList, String token) {
        ObservableList<String> list = FXCollections.observableArrayList();
        ObservableList<StringList> stringList = FXCollections.observableArrayList();
        if (!DBStringURL.toUpperCase().equals(FAKEDB.toUpperCase())) {
            try {
                String actionToken = String.format(ACTION_TOKEN, URLEncoder.encode(token, "UTF-8"));
                ListJSONReparsing sitesJSONReparsing = new ListJSONReparsing();
                sitesJSONReparsing.readJSON(DBStringURL + DBSTRINGURLAPI + actionToken + getList, stringList);
                for (StringList sl: stringList) {
                    list.add(sl.nameProperty().getValue());
                }
            } catch (UnsupportedEncodingException e) {
                new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                    "Внимание!", "Ошибка формирования запроса");
            } catch (IllegalStateException e) {
                new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                    "Внимание!", "Ошибка в полученных данных");
            } catch (UnsupportedOperationException e) {
                new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                    "Внимание!", "Ошибка разборки данных");
            } catch (Exception e) {
                new AlertHandler(Alert.AlertType.ERROR, "Ошибка",
                    "Внимание!", "Ошибка подключения к базе данных");
            }
        } else {
            new FakeData().getFakeList(list, getList);
        }
        return list;
    }
}
