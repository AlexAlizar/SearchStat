public class RestActionsSQLQueries {
    public static class GeneralStatisticQuery{
        public String query = null;

        private String queryLine1 = "SELECT pe.name as person_name, sum(rank)\n" +
                "FROM persons as pe\n" +
                "JOIN person_page_rank as ppr ON (ppr.person_id = pe.id)\n" +
                "JOIN pages as pa ON (pa.id = ppr.page_id)\n" +
                "JOIN sites as s ON (s.id = pa.site_id)\n" +
                "WHERE s.name = ";
        private String queryLine2 = "\n" +
                "GROUP BY person_name";

        GeneralStatisticQuery(String site) {
            this.query = queryLine1 + site + queryLine2;
        }
    }

    public static class DailyStatisticQuery{
        public String query = null;

        private String queryLine1 = "SELECT p.found_date_time as date, count(p.id) as count \n" +
                "FROM pages as p\n" +
                "JOIN sites as s\n" +
                "JOIN person_page_rank as ppr ON (p.id = ppr.page_id)\n" +
                "JOIN persons as pe ON (ppr.person_id = pe.id)\n" +
                "WHERE pe.name = ";
        private String queryLine2 = " AND p.found_date_time BETWEEN ";
        private String queryLine3 = " AND ";
        private String queryLine4 = " AND s.name = ";
        private String queryLine5 = " GROUP BY date";

        DailyStatisticQuery(String person, String date1, String date2, String site) {
            query = queryLine1 + person +
                    queryLine2 + date1 + queryLine3 + date2 +
                    queryLine4 + site + queryLine5;
        }
    }

    public static class GetPersonsQuery {
        public String query = "SELECT * FROM persons";
    }

    public static class GetSitesQuery {
        public String query = "SELECT * FROM sites";
    }

    public static class GetKeywordsQuery {
        public String query = "SELECT * FROM keywords";
    }
}
