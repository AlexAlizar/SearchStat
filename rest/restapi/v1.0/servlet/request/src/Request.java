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
import java.util.ArrayList;
import java.util.List;

public class Request extends HttpServlet{

    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    String mysqlDriver = "com.mysql.jdbc.Driver";
    String mysqlURL = "jdbc:mysql://localhost:3306/searchstat?useUnicode=true&characterEncoding=utf-8";
    String login = "phpmyadmin";
    String password = "phpmyadmin";

    List<String> sites = new ArrayList<>();
    List<String> persons = new ArrayList<>();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        req.setCharacterEncoding ("UTF-8");

        try {
            Class.forName(mysqlDriver);

//          -> ----- Start of "DB connection" -----
            con = DriverManager.getConnection(mysqlURL, login, password);
//          <- ----- End of "DB connection" -----

//          -> ----- Start of "SQL query preparation" -----
            stmt = con.createStatement();
//           <- ----- End of "SQL query preparation" -----

            String reqRequest = req.getParameter("request");
            String reqStatisticType = req.getParameter("statistic");

            if (reqRequest != null) {
                switch (reqRequest) {
                    case "sites":
                        rs = stmt.executeQuery("SELECT * FROM sites");
                        while (rs.next()) {
                            sites.add(rs.getString(2));
                        }
                        out.println(constructJSON(sites));
                        break;
                    case "persons":
                        rs = stmt.executeQuery("SELECT * FROM persons");
                        while (rs.next()) {
                            persons.add(rs.getString(2));
                        }
                        out.println(constructJSON(persons));
                        break;
                    default:
                        out.println("{\"error\" : \"Unknown request.\"}");
                }
            }

        } catch (Exception e) {
            out.println("{ \"error\" : \"" +e.toString()+ "\"}");
        }


    }

    private String constructJSON(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        return gson.toJson(object);
    }
}
