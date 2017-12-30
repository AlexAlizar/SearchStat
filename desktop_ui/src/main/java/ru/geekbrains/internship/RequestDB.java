package ru.geekbrains.internship;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import java.time.LocalDate;

public class RequestDB {

    private ObservableList<TotalStatistics> totalStatisticsList;
    private ObservableList<DailyStatistics> dailyStatisticsList;
    private ObservableList<String> sites;
    private ObservableList<String> names;

    public boolean checkAuthorization(String login, String password) {
        return true;
    }

    public ObservableList getTotalStatisticsList(String DBStringURL, String site) {

        totalStatisticsList = FXCollections.observableArrayList();
/*
        totalStatisticsList.add(new TotalStatistics("Путин",5000));
        totalStatisticsList.add(new TotalStatistics("Медведев",4000));
        totalStatisticsList.add(new TotalStatistics("Навальный",3000));
*/

        // +++ тест чтения и репарсинга json
        try {
            ConnectionDB connectionDB = new ConnectionDB(DBStringURL);
            String out = connectionDB.readDBResult();

            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(out);

            JsonObject rootObject = jsonElement.getAsJsonObject();
            JsonArray sitesArray = (JsonArray) rootObject.get("sites");
            for (int i = 0; i < sitesArray.size(); i++) {
                JsonObject siteJSON = (JsonObject) sitesArray.get(i);
                String stringSiteID = siteJSON.get("SiteID").getAsString();
                String stringSiteName = siteJSON.get("SiteName").getAsString();
                //System.out.println("SiteID = " + stringSiteID + " SiteName = " + stringSiteName);
                JsonArray personArray = (JsonArray) siteJSON.get("persons");
                TotalStatisticsJSONReparsing tsJSONReparsing = new TotalStatisticsJSONReparsing();
                for (int j = 0; j < personArray.size(); j++) {
                    JsonObject personJSON = (JsonObject) personArray.get(j);
                    totalStatisticsList.add((TotalStatistics) tsJSONReparsing.readJSONObject(personJSON));
                }
            }
            connectionDB.closeConnectionDB();
        } catch (IllegalStateException e) {
            new AlertHandler(Alert.AlertType.ERROR, "Ошибка", "Внимание!", "Ошибка в полученных данных");
            //e.printStackTrace();
        } catch (Exception e) {
            new AlertHandler(Alert.AlertType.ERROR, "Ошибка", "Внимание!", "Ошибка подключения к базе данных");
            //e.printStackTrace();
        }
        // --- тест чтения и репарсинга json
        return totalStatisticsList;
    }

    public ObservableList getTotalStatisticsChartData() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (TotalStatistics ts: totalStatisticsList) {
            pieChartData.add(new PieChart.Data(ts.nameProperty().getValue().toString(),ts.quantityProperty().intValue()));
        }
        return pieChartData;
    }

    public ObservableList getDailyStatisticsList(String DBStringURL, String site, String name, LocalDate beginDate, LocalDate endDate) {
        dailyStatisticsList = FXCollections.observableArrayList();
        // +++ тестовые данные
        LocalDate d = beginDate;
        while (!d.isAfter(endDate)) {
            dailyStatisticsList.add(new DailyStatistics(String.valueOf(d), d.getDayOfMonth() * 11 % 50));
            d = d.plusDays(1);
        }
        // --- тестовые данные

        try {
            ConnectionDB connectionDB = new ConnectionDB(DBStringURL);


            connectionDB.closeConnectionDB();
        } catch (IllegalStateException e) {
            new AlertHandler(Alert.AlertType.ERROR, "Ошибка", "Внимание!", "Ошибка в полученных данных");
            //e.printStackTrace();
        } catch (Exception e) {
            new AlertHandler(Alert.AlertType.ERROR, "Ошибка", "Внимание!", "Ошибка подключения к базе данных");
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
        // +++ тестовые данные
        sites.add("lenta.ru");
        sites.add("rbc.ru");
        sites.add("rambler.ru");
        // --- тестовые данные
        try {
            ConnectionDB connectionDB = new ConnectionDB(DBStringURL);


            connectionDB.closeConnectionDB();
        } catch (IllegalStateException e) {
            new AlertHandler(Alert.AlertType.ERROR, "Ошибка", "Внимание!", "Ошибка в полученных данных");
            //e.printStackTrace();
        } catch (Exception e) {
            new AlertHandler(Alert.AlertType.ERROR, "Ошибка", "Внимание!", "Ошибка подключения к базе данных");
            //e.printStackTrace();
        }
        return sites;
    }

    public ObservableList getNames(String DBStringURL) {
        names = FXCollections.observableArrayList();
        // +++ тестовые данные
        names.add("Person #1");
        names.add("Person #2");
        names.add("Person #3");
        // --- тестовые данные
        try {
            ConnectionDB connectionDB = new ConnectionDB(DBStringURL);


            connectionDB.closeConnectionDB();
        } catch (IllegalStateException e) {
            new AlertHandler(Alert.AlertType.ERROR, "Ошибка", "Внимание!", "Ошибка в полученных данных");
            //e.printStackTrace();
        } catch (Exception e) {
            new AlertHandler(Alert.AlertType.ERROR, "Ошибка", "Внимание!", "Ошибка подключения к базе данных");
            //e.printStackTrace();
        }
        return names;
    }

}
