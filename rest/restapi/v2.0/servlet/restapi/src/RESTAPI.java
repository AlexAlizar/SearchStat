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
            //Need to check if action is "Authentication",
            //then try to authenticate by login and password.
            RestAuthentication auth = new RestAuthentication(rToken);
            if (auth.Check()) {
                if (auth.getRole() == "user") {

                    //Proceed request with user role

                } else if (auth.getRole() == "admin") {

                    //Proceed request with admin role

                } else {

                    //Error: user is not authorized

                }
            } else {
                //Token is invalid
            }
        } else {

            out.println(constructJSON(new RestError(0x1)));
        }
    }

    private String constructJSON(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        return gson.toJson(object);
    }
}
