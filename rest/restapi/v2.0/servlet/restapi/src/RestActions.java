import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;

public class RestActions {
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

    RestDB db = RestDB.getInstance();


    public String adminActionExecute(String action, HttpServletRequest request) {

        if (adminActionsL.contains(action)) {
            //connect to DB
            String result = db.prepareDB("mySQL");
            if (result == "DB is ready.") {
                switch (action) {
                    case "general-statistic":
                        String query = null; //temporary
                        db.executeDBQuery(query); //sending query to DB
                        break;
                    case "daily-statistic":

                        break;
                    case "get-persons":

                        break;
                    case "add-person":

                        break;
                    case "edit-person":

                        break;
                    case "remove-person":

                        break;
                    case "get-sites":

                        break;
                    case "add-site":

                        break;
                    case "edit-site":

                        break;
                    case "remove-site":

                        break;
                    case "get-keywords":

                        break;
                    case "add-keyword":

                        break;
                    case "edit-keyword":

                        break;
                    case "remove-keyword":

                        break;
                    default:

                }
            } else {
                return result;
            }
        } else {
            return  "Action not found."; //Temporary - need to return somehow RestError message
        }
        return "adminActionExecute response"; //temporary
    }


    public String userActionExecute(String action, HttpServletRequest request) {

        return "userActionExecute response"; //temporary
    }
}
