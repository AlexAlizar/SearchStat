import java.util.Date;

public class DailyStatistic {
    private static String date;
    private static String countOfPages;

    public DailyStatistic(String date, String countOfPages) {
        this.date = date;
        this.countOfPages = countOfPages;
    }

    public String getDate() {
        return this.date;
    }

    public String getCountOfPages() {
        return this.countOfPages;
    }
}
