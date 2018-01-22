import com.sun.deploy.util.StringUtils;

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
            "remove-keyword",
            "get-users",
            "add-user",
            "edit-user",
            "delete-user"
    };


    private String userActions[] = {
            "general-statistic",
            "daily-statistic",
            "get-persons",
            "get-sites"
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
        List<RestUser> usersList = new ArrayList<>();

        if (adminActionsL.contains(action)) {
            String result = db.prepareDB("mySQL");
            if (result == "DB is ready.") {
                switch (action) {
                    case "general-statistic":
//                        site = "\"" + request.getParameter("site") + "\"";
                        site = request.getParameter("site");
                        if (!com.mysql.jdbc.StringUtils.isEmptyOrWhitespaceOnly(site)) {
                            try {
                                result = db.executeDBQuery(new RestActionsSQLQueries.GeneralStatisticQuery(site).query);
                                while (db.rs.next()) {
                                    String person_name = db.rs.getString(1);
                                    String rank = db.rs.getString(2);
                                    generalStatisticList.add(new RestGeneralStatistic(person_name, rank));
                                }
//START: Return result to the client
                                RestMessages.constructMessage(generalStatisticList);
                                return generalStatisticList;
//END: Return result to the client
                            } catch (Exception e) {
                                return e.toString();
                            }
                        } else {
                            RestMessages.constructMessage(new RestMessages.Error("Not enough parameters"));
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
                                RestMessages.constructMessage(dailyStatisticList);
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
                            RestMessages.constructMessage(personsList);
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
                                        "INSERT INTO `persons` (`id`, `name`) VALUES (NULL, '" + name + "')");
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
                            RestMessages.constructMessage(sitesList);
                            return sitesList;
                        } catch (Exception e) {
                            return e.toString();
                        }
                    case "add-site":
                        try {
                            String name = request.getParameter("name");
                            if (name != null) {
                                return db.executeDBQueryUpdate(
                                        "INSERT INTO `sites` (`id`, `name`) VALUES (NULL, '" + name + "')");
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
                            RestMessages.constructMessage(keywordsList);
                            return keywordsList;
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
                                        "INSERT INTO keywords (id, name, person_id) VALUES (NULL, \"" + name + "\", " + person_id + ")");
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
                            RestMessages.constructMessage(new RestMessages.Error(e.toString()));
                            return e.toString();
                        }
                    case "get-users":
                        try {
                            result = db.executeDBQuery(
                                    new RestActionsSQLQueries.GetUsersQuery().query);
                            while (db.rs.next()) {
                                String id = db.rs.getString(1);
                                String login = db.rs.getString(2);
                                String password = db.rs.getString(3);
                                String email = db.rs.getString(4);
                                String token = db.rs.getString(5);
                                String role = db.rs.getString(6);
                                String persons = db.rs.getString(7);
                                usersList.add(new RestUser(id, login, password, email, token, role, persons));
                            }
                            RestMessages.constructMessage(usersList);
                            return usersList;
                        } catch (Exception e) {
                            RestMessages.constructMessage(new RestMessages.Error(e.toString()));
                            return e.toString();
                        }
                    case "add-user":
                        try {
                            String login = request.getParameter("login");
                            String password = request.getParameter("password");
                            String email = request.getParameter("email");
                            String role = request.getParameter("role");

                            if (!com.mysql.jdbc.StringUtils.isNullOrEmpty(login)
                                && !com.mysql.jdbc.StringUtils.isNullOrEmpty(password)
                                && !com.mysql.jdbc.StringUtils.isNullOrEmpty(email)
                                && !com.mysql.jdbc.StringUtils.isNullOrEmpty(role)) {
                                String dbExecuteResult = db.executeDBQueryUpdate(
                                        "INSERT INTO `users` (`id`, `login`, `password`, `email`, `role`) " +
                                                "VALUES (NULL, '" + login + "', '" + password + "', '" + email + "', '" + role + "')");
//                                RestMessages.constructMessage(dbExecuteResult);
                                return result;
                            } else {
                                RestMessages.constructMessage(new RestMessages.Error("Not enough parameters"));
                                return "Not enough parameters";
                            }
                        } catch (Exception e) {
                            RestMessages.constructMessage(new RestMessages.Error(e.toString()));
                            return e.toString();
                        }
                    case "edit-user":
                        try {

                        } catch (Exception e) {
                            RestMessages.constructMessage(new RestMessages.Error(e.toString()));
                            return e.toString();
                        }
                        return "edit-user";
                    case "delete-user":
                        try {
                            String id = request.getParameter("id");
                            if (!com.mysql.jdbc.StringUtils.isEmptyOrWhitespaceOnly(id)) {
                                return db.executeDBQueryUpdate(
                                        "DELETE FROM users WHERE id = " + id);
                            } else {
                                RestMessages.constructMessage(new RestMessages.Error("Not enough parameters"));
                                return "Not enough parameters";
                            }
                        } catch (Exception e) {
                            RestMessages.constructMessage(new RestMessages.Error(e.toString()));
                            return e.toString();
                        }
                    default:
                        return "Debug: Failed!";
                }

            } else {
                return result;
            }

        } else {
            RestMessages.constructMessage(new RestMessages.Error("Action is not allowed to the user."));
            return "Action is not allowed to the user.";
        }
    }

    public Object userActionExecute(String action, HttpServletRequest request) {

        String person;
        String date1;
        String date2;
        String site;

        List<RestGeneralStatistic> generalStatisticList = new ArrayList<>();
        List<RestDailyStatistic> dailyStatisticList = new ArrayList<>();
        List<RestPersons> personsList = new ArrayList<>();
        List<RestSites> sitesList = new ArrayList<>();
        List<RestKeywords> keywordsList = new ArrayList<>();

        if (userActionsL.contains(action)) {
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
                                RestMessages.constructMessage(generalStatisticList);
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
                                RestMessages.constructMessage(dailyStatisticList);
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
                            RestMessages.constructMessage(personsList);
                            return personsList;
//END: Return result to the client
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
                            RestMessages.constructMessage(sitesList);
                            return sitesList;
//END: Return result to the client
                        } catch (Exception e) {
                            return e.toString();
                        }
                    default:
                        return "Debug: Failed!";
                }
            } else {
                return result;
            }
        } else {
            RestMessages.constructMessage(new RestMessages.Error("Action is not allowed to the user."));
            return "Action is not allowed to the user.";
        }
    }
}
