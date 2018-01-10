import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RESTAPI extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        response.setContentType("application/json; charset=utf-8");

        String rToken = request.getParameter("token");
        String rAction = request.getParameter("action");

        if ((rToken != null) || (rAction == "auth")) {
            RestAuthentication auth;
            if (rAction == "auth") {
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                auth = new RestAuthentication(login, password);
                if (!auth.Check()) {
                    //Send Error: 0x3 "Authorization failed.",
                    //halt doGet ...
                }
            } else {
                auth = new RestAuthentication(rToken);
            }

            if (auth.Check()) {
                if (auth.getRole() == "user") {
                    out.println(
                            new RestActions().adminActionExecute(rAction, request)
                    );

                } else if (auth.getRole() == "admin") {
                    out.println(
                            new RestActions().userActionExecute(rAction, request)
                    );
                } else {
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
                            new RestError(0x1)
                    )
            ); // "Token is not found."
        }
    }

    private String constructJSON(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        return gson.toJson(object);
    }
}
