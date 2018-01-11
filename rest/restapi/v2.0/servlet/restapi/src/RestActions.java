import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<RestSites> sitesList = new ArrayList<>();
        List<RestKeywords> keywordsList = new ArrayList<>();

        if (adminActionsL.contains(action)) {
            String result = db.prepareDB("mySQL");
            if (result == "DB is ready.") {
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
//START: Return result to the client
                                RestMessages.outputMessages(generalStatisticList);
                                return generalStatisticList;
//END: Return result to the client
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
//START: Return result to the client
                                RestMessages.outputMessages(dailyStatisticList);
                                return dailyStatisticList;
//END: Return result to the client
                            } catch (Exception e) {
                                return e.toString();
                            }
                        } else {
                            return "Not enough parameters";
                        }
                    case "get-persons":
                        try {
                            result = db.executeDBQuery(
                                    new RestActionsSQLQueries.GetPersonsQuery().query);
                            while (db.rs.next()) {
                                String id = db.rs.getString(1);
                                String name = db.rs.getString(2);
                                personsList.add(new RestPersons(id, name));
                            }
//START: Return result to the client
                            RestMessages.outputMessages(personsList);
                            return personsList;
//END: Return result to the client
                        } catch (Exception e) {
                            return e.toString();
                        }
                    case "add-person":
                        try {
                            String name = request.getParameter("name");
                            if (name != null) {
                                return db.executeDBQueryUpdate(
                                        "INSERT INTO `persons` (`id`, `name`) VALUES (NULL, '" + name +"')");
                            } else {
                                return "Not enough parameters";
                            }
                        } catch (Exception e) {
                            return e.toString();
                        }
                    case "edit-person":

                        return "Success! edit-person"; //temporary
                    case "remove-person":
                        try {
                            String id = request.getParameter("id");
                            if (id != null) {
                                return db.executeDBQueryUpdate(
                                        "DELETE FROM persons WHERE id = " + id);
                            } else {
                                return "Not enough parameters";
                            }
                        } catch (Exception e) {
                            return e.toString();
                        }
                    case "get-sites":
                        try {
                            result = db.executeDBQuery(
                                    new RestActionsSQLQueries.GetSitesQuery().query);
                            while (db.rs.next()) {
                                String id = db.rs.getString(1);
                                String name = db.rs.getString(2);
                                sitesList.add(new RestSites(id, name));
                            }
//START: Return result to the client
                            RestMessages.outputMessages(sitesList);
                            return sitesList;
//END: Return result to the client
                        } catch (Exception e) {
                            return e.toString();
                        }
                    case "add-site":
                        try {
                            String name = request.getParameter("name");
                            if (name != null) {
                                return db.executeDBQueryUpdate(
                                        "INSERT INTO `sites` (`id`, `name`) VALUES (NULL, '" + name +"')");
                            } else {
                                return "Not enough parameters";
                            }
                        } catch (Exception e) {
                            return e.toString();
                        }
                    case "edit-site":

                        return "Success! edit-site"; //temporary
                    case "remove-site":
                        try {
                            String id = request.getParameter("id");
                            if (id != null) {
                                return db.executeDBQueryUpdate(
                                        "DELETE FROM sites WHERE id = " + id);
                            } else {
                                return "Not enough parameters";
                            }
                        } catch (Exception e) {
                            return e.toString();
                        }
                    case "get-keywords":
                        try {
                            result = db.executeDBQuery(
                                    new RestActionsSQLQueries.GetKeywordsQuery().query);
                            while (db.rs.next()) {
                                String id = db.rs.getString(1);
                                String name = db.rs.getString(2);
                                String person_id = db.rs.getString(3);
                                keywordsList.add(new RestKeywords(id, name, person_id));
                            }
//START: Return result to the client
                            RestMessages.outputMessages(keywordsList);
                            return keywordsList;
//END: Return result to the client
                        } catch (Exception e) {
                            return e.toString();
                        }
                    case "add-keyword":
                        try {
                            String name = request.getParameter("name");
                            String person_id = request.getParameter("person_id");
                            person_id = (person_id != null) ? person_id : "NULL";
                            if (name != null) {
                                return db.executeDBQueryUpdate(
                                        "INSERT INTO keywords (id, name, person_id) VALUES (NULL, \"" + name + "\", " + person_id +")");
                            } else {
                                return "Not enough parameters";
                            }
                        } catch (Exception e) {
                            return e.toString();
                        }
                    case "edit-keyword":

                        return "Success! edit-keyword"; //temporary
                    case "remove-keyword":
                        try {
                            String id = request.getParameter("id");
                            if (id != null) {
                                return db.executeDBQueryUpdate(
                                        "DELETE FROM keywords WHERE id = " + id);
                            } else {
                                return "Not enough parameters";
                            }
                        } catch (Exception e) {
                            return e.toString();
                        }
                    default:

                        return "Failed!"; //temporary
                }
            } else {
                return result;
            }
        } else {
            return  "Action not found."; //Temporary - need to return somehow RestError message
        }
    }


    public String userActionExecute(String action, HttpServletRequest request) {

        return "userActionExecute response"; //temporary
    }
}
