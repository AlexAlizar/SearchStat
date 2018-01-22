public class RestGeneralStatistic {
    private String name;
    private String rank;

    RestGeneralStatistic(String name, String rank) {
        this.name = name;
        this.rank = rank;
    }

    public String getName() {
        return this.name;
    }

    public String getRank() {
        return this.rank;
    }
}

