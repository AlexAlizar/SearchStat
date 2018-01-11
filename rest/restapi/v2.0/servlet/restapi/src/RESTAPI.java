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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        request.setCharacterEncoding("UTF-8");

        String rToken = request.getParameter("token");
        String rAction = request.getParameter("action");

        if (rToken != null) {
            out.println("Debug: Token exist");
            if (rAction != null) {
                out.println("Debug: Action exist");
                RestAuthentication auth;
                if (rAction == "auth") {
                    out.println("Debug: Action = auth");
                    String login = request.getParameter("login");
                    String password = request.getParameter("password");
                    auth = new RestAuthentication(login, password);
                    if (!auth.checkToken()) {
                        //Send Error: 0x3 "Authorization failed.",
                        //halt doGet ...
                    }
                } else {
                    out.println("Debug: Action != auth");
                    auth = new RestAuthentication(rToken);
                }

                if (auth.checkToken()) {
                    out.println("Debug: Token is OK");
                    if (auth.getRole() == "user") {
                        out.println("Debug: user role = user");
                        out.println(
                                new RestActions().userActionExecute(rAction, request)
                        );
                    } else if (auth.getRole() == "admin") {
                        out.println("Debug: user role = admin");
//                        out.println(
//                                constructJSON(
//                                        new RestActions().adminActionExecute(rAction, request)
//                                )
//                        );
//
                        new RestActions().adminActionExecute(rAction, request);
                        out.println(RestMessages.outputJSONMessage);
                    } else {
                        out.println("Debug: Unknown error");
                        //Error: 0x0 "Unknown error."
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
    }



    public String constructJSON(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
//        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

}
