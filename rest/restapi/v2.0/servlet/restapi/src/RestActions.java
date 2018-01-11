import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class RestActions {
    private RestDB db = RestDB.getInstance();

    private String adminActions[] = {
            "general-statistic",
            "daily-statistic",
            "get-persons",
            "add-person",
            "edit-person",
            "remove-person",
            "get-sites",
            "add-site",
            "edit-site",
            "remove-site",
            "get-keywords",
            "add-keyword",
            "edit-keyword",
            "remove-keyword"
    };

    private String userActions[] = {
            "general-statistic",
            "daily-statistic"
    };

    private ArrayList<String> adminActionsL = new ArrayList<String>(Arrays.asList(adminActions));
    private ArrayList<String> userActionsL = new ArrayList<String>(Arrays.asList(userActions));


    public Object adminActionExecute(String action, HttpServletRequest request) {

        String person;
        String date1;
        String date2;
        String site;

        List<RestGeneralStatistic> generalStatisticList = new ArrayList<>();
        List<RestDailyStatistic> dailyStatisticList = new ArrayList<>();
        List<RestPersons> personsList = new ArrayList<>();

        if (adminActionsL.contains(action)) {
//            debug += "Action is exist in users actions.\r";
            //connect to DB
            String result = db.prepareDB("mySQL");
            if (result == "DB is ready.") {
//                return "Debug: " + result;
//                debug += "DB is ready.";
                switch (action) {
                    case "general-statistic":
                        site = "\"" + request.getParameter("site") + "\"";
                        if (site != null) {
                            try {
                                result = db.executeDBQuery(new RestActionsSQLQueries.GeneralStatisticQuery(site).query);
                                while (db.rs.next()) {
                                    String person_name = db.rs.getString(1);
                                    String rank = db.rs.getString(2);
                                    generalStatisticList.add(new RestGeneralStatistic(person_name, rank));
                                }
                                return generalStatisticList;
                            } catch (Exception e) {
                                return e.toString();
                            }
                        } else {
                            return "Not enough parameters";
                        }
                    case "daily-statistic":
                        person = "\"" + request.getParameter("person") + "\"";
                        date1 = "\"" + request.getParameter("date1") + "\"";
                        date2 = "\"" + request.getParameter("date2") + "\"";
                        site = "\"" + request.getParameter("site") + "\"";
                        if ((person != null) && (date1 != null) && (date2 != null) && (site != null)) {
                            try {
                                result = db.executeDBQuery(
                                        new RestActionsSQLQueries.DailyStatisticQuery(person, date1, date2, site).query);
                                while (db.rs.next()) {
                                    String date = db.rs.getString(1);
                                    String countOfPages = db.rs.getString(2);
                                    dailyStatisticList.add(new RestDailyStatistic(date, countOfPages));
                                }
                                return dailyStatisticList;
                            } catch (Exception e) {
                                return e.toString();
                            }
                        } else {
                            return "Not enough parameters";
                        }
//                        return "Success! daily-statistic"; //temporary
                    case "get-persons":
                        try {
                            result = db.executeDBQuery(
                                    new RestActionsSQLQueries.GetPersonsQuery().query);
                            while (db.rs.next()) {
                                String id = db.rs.getString(1);
                                String name = db.rs.getString(2);
                                personsList.add(new RestPersons(id, name));
                            }
                            return personsList;
                        } catch (Exception e) {
                            return e.toString();
                        }
                    case "add-person":

                        return "Success! add-person"; //temporary
                    case "edit-person":

                        return "Success! edit-person"; //temporary
                    case "remove-person":

                        return "Success! remove-person"; //temporary
                    case "get-sites":

                        return "Success! get-sites"; //temporary
                    case "add-site":

                        return "Success! add-site"; //temporary
                    case "edit-site":

                        return "Success! edit-site"; //temporary
                    case "remove-site":

                        return "Success! remove-site"; //temporary
                    case "get-keywords":

                        return "Success! get-keywords"; //temporary
                    case "add-keyword":

                        return "Success! add-keyword"; //temporary
                    case "edit-keyword":

                        return "Success! edit-keyword"; //temporary
                    case "remove-keyword":

                        return "Success! remove-keyword"; //temporary
                    default:

                        return "Failed!"; //temporary
                }
            } else {
                return result;
            }
        } else {
            return  "Action not found."; //Temporary - need to return somehow RestError message
        }
//        return "adminActionExecute response"; //temporary
    }


    public String userActionExecute(String action, HttpServletRequest request) {

        return "userActionExecute response"; //temporary
    }
}
