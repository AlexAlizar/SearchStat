import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RESTAPI extends HttpServlet {
    public String json = null;
    private RestDB db = RestDB.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        request.setCharacterEncoding("utf-8");

        String rToken = request.getParameter("token");
        String rAction = request.getParameter("action");
        String authAction = "auth";

        if ((rToken != null) || (rAction != null)) {
//            out.println("Debug: Token or Action exist");
            if (rAction != null) {
//                out.println("Debug: Action exist");
                RestAuthentication auth;
                if (rAction.equals("auth")) {
//                    out.println("Debug: Action = auth");
                    String login = request.getParameter("login");
                    String password = request.getParameter("password");
                    if ((login == null) || (password == null)) {
                        RestMessages.constructMessage(new RestMessages.Error("Not enough parameters"));
                        out.println(RestMessages.outputJSONMessage);
                        return;
                    }
                    auth = new RestAuthentication(login, password);
//                    out.println(RestMessages.outputJSONMessage);
                    if (auth.isAuthenticated()) {
//                      Need transform to CLASS
                        out.println(RestMessages.constructJSON(auth.getToken()));
//                      Need transform to CLASS
//                        return;
                    } else {
                        RestMessages.constructMessage(new RestMessages.Error("Authentication failed"));
                        out.println(RestMessages.outputJSONMessage);
//                        return;
                    }
                    db.closeDB();
                    return;
                } else {
//                    out.println("Debug: Action != auth");
                    auth = new RestAuthentication(rToken);
                }

                if (auth.isAuthenticated()) {
//                    out.println("Debug: Token is OK");
                    if (auth.getRole().equals("user")) {
//                        out.println("Debug: user role = user");
//                        out.println(new RestActions().userActionExecute(rAction, request));
                        Object result = new RestActions().userActionExecute(rAction, request);
                        out.println(RestMessages.outputJSONMessage);
//                        out.println(constructJSON(result));
                    } else if (auth.getRole().equals("admin")) {
//                        out.println("Debug: user role = admin");
                        Object result = new RestActions().adminActionExecute(rAction, request);
                        out.println(RestMessages.outputJSONMessage);
//                        out.println(constructJSON(result));
                    } else {
                        out.println("Debug: Unknown error"); //Error: 0x0 "Unknown error."
                    }
                } else {
                    out.println(
                            constructJSON(
                                    new RestError(0x2)
                            )
                    ); //"Token is invalid."
                }
            } else {
                out.println(
                        constructJSON(
                                new RestError("Action is not found.")
                        )
                ); //"Action is not found."
            }
        } else {
            out.println(
                    constructJSON(
                            new RestError("Token is not found.")
                    )
            ); // "Token is not found."
        }
        db.closeDB();
    }



    public String constructJSON(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
//        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

}
