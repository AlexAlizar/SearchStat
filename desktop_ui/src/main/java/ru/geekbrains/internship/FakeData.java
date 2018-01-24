package ru.geekbrains.internship;

import javafx.collections.ObservableList;
import java.time.LocalDate;

class FakeData implements ConnectionDBConst{

    public void getFakeList(ObservableList<String> list, String getList) {
        if (getList.equals(ACTION_GET_PERSONS)) {
            list.add("Путин");
            list.add("Медведев");
            list.add("Навальный");
        } else {
            if (getList.equals(ACTION_GET_SITES)) {
                list.add("lenta.ru");
                list.add("rambler.ru");
                list.add("rbc.ru");
            }
        }
    }

    public void getFakeTotalStatistics(ObservableList<TotalStatistics> totalStatisticsList) {
        totalStatisticsList.add(new TotalStatistics("Путин",5000));
        totalStatisticsList.add(new TotalStatistics("Медведев",4000));
        totalStatisticsList.add(new TotalStatistics("Навальный",3000));
    }

    public void getFakeDailyStatistics(ObservableList<DailyStatistics> dailyStatisticsList, LocalDate beginDate, LocalDate endDate) {
        LocalDate d = beginDate;
        while (!d.isAfter(endDate)) {
            dailyStatisticsList.add(new DailyStatistics(String.valueOf(d), d.getDayOfMonth() * 11 % 50));
            d = d.plusDays(1);
        }
    }
}
