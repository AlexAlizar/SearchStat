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

        if (rToken != null) {
            RestAuthentication auth = new RestAuthentication(rToken);
            if (auth.Check()) {
                //Proceed with request
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
