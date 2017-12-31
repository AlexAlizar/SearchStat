
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class RESTAPI extends HttpServlet {

    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    String mysqlDriver = "com.mysql.jdbc.Driver";
    String mysqlURL = "jdbc:mysql://localhost:3306/searchstat?useUnicode=true&characterEncoding=utf-8";
    String login = "phpmyadmin";
    String password = "phpmyadmin";

    String mysqlGeneralRequest = null;
    String mysqlGeneralRequest1 = "SELECT pe.name as person_name, sum(rank)\n" +
            "FROM persons as pe\n" +
            "JOIN person_page_rank as ppr ON (ppr.person_id = pe.id)\n" +
            "JOIN pages as pa ON (pa.id = ppr.page_id)\n" +
            "JOIN sites as s ON (s.id = pa.site_id)\n" +
            "WHERE s.name = ";
    String mysqlGeneralRequest2 = "\n" +
            "GROUP BY person_name";


    String mysqlDailyRequest = null;
    String mysqlDailyRequest1 = "SELECT p.found_date_time as date, count(p.id) as count \n" +
            "FROM pages as p\n" +
            "JOIN sites as s\n" +
            "JOIN person_page_rank as ppr ON (p.id = ppr.page_id)\n" +
            "JOIN persons as pe ON (ppr.person_id = pe.id)\n" +
            "WHERE pe.name = ";
    String mysqlDailyRequest2 = " AND p.found_date_time BETWEEN ";
    String mysqlDailyRequest3 = " AND ";
    String mysqlDailyRequest4 = " AND s.name = ";
    String mysqlDailyRequest5 = " GROUP BY date";



    String site = null;
    String person = null;
    String date1 = null;
    String date2 = null;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        req.setCharacterEncoding ("UTF-8");

        List<DailyStatistic> ds = new ArrayList();
        List<GeneralStatistic> gs = new ArrayList();

        try {
            Class.forName(mysqlDriver);
        } catch (ClassNotFoundException e) {
            out.println("{ \"error\" : \"" +e.toString()+ "\"}");
        }

        String reqStatisticType = req.getParameter("statistic");

        if (reqStatisticType != null) {
            try {
                switch (reqStatisticType) {
                    case "daily":
//                      ----- Start of "Daily statistic" -----
                        person = "\"" + req.getParameter("person") + "\"";
                        date1 = "\"" + req.getParameter("date1") + "\"";
                        date2 = "\"" + req.getParameter("date2") + "\"";
                        site = "\"" + req.getParameter("site") + "\"";

//                      Checking for existance of parameters

                        mysqlDailyRequest = mysqlDailyRequest1 + person +
                            mysqlDailyRequest2 + date1 + mysqlDailyRequest3 + date2 +
                            mysqlDailyRequest4 + site + mysqlDailyRequest5;

//                        out.println(mysqlDailyRequest);

//                      -> ----- Start of "DB connection" -----
                        con = DriverManager.getConnection(mysqlURL, login, password);
//                      <- ----- End of "DB connection" -----

//                      -> ----- Start of "SQL query preparation" -----
                        stmt = con.createStatement();
//                       <- ----- End of "SQL query preparation" -----

//                      -> ----- Start of "Parsing of SQL query" -----
                        rs = stmt.executeQuery(mysqlDailyRequest);
                        while (rs.next()) {
                            String date = rs.getString(1);
                            String count = rs.getString(2);
                            ds.add(new DailyStatistic(date, count));
                        }
//                      <- ----- End of "Parsing of SQL query" -----

//                      -> ----- Start of "Making JSON response" -----
//                      later it wll be changed to GSON framework
                        out.println("{\"daily_statistic\": [{ ");
                        for (int i = 0; i < ds.size(); i++) {
                            out.print("                     \"date\" : \"");
                            out.print(ds.get(i).getDate());
                            out.println("\",");
                            out.print("                     \"count\" : \"");
                            out.print(ds.get(i).getCountOfPages());
                            out.print("\"}");
                            if (i != ds.size() - 1) {
                                out.println(",");
                            }
                        }
                        out.println("]");
//                       <- ----- End of "Making JSON response" -----

                        break;
                    case "general":
//                      ----- Start of "General statistic" -----
                        site = "\"" + req.getParameter("site") + "\"";

                        mysqlGeneralRequest = mysqlGeneralRequest1 + site +mysqlGeneralRequest2;

//                      -> ----- Start of "DB connection" -----
                        con = DriverManager.getConnection(mysqlURL, login, password);
//                      <- ----- End of "DB connection" -----

//                      -> ----- Start of "SQL query preparation" -----
                        stmt = con.createStatement();
//                       <- ----- End of "SQL query preparation" -----

//                      -> ----- Start of "Parsing of SQL query" -----
                        rs = stmt.executeQuery(mysqlGeneralRequest);
                        while (rs.next()) {
                            String person_name = rs.getString(1);
//                            out.print(person_name + " ");
                            String rank = rs.getString(2);
//                            out.println(rank);
                            gs.add(new GeneralStatistic(person_name, rank));
                        }
//                      <- ----- End of "Parsing of SQL query" -----

//                      -> ----- Start of "Making JSON response" -----
//                      later it wll be changed to GSON framework
                        out.println("{\"general_statistic\": [{ ");
                        for (int i = 0; i < gs.size(); i++) {
                            out.print("                     \"name\" : \"");
                            out.print(gs.get(i).getName());
                            out.println("\",");
                            out.print("                     \"rank\" : \"");
                            out.print(gs.get(i).getRank());
                            out.print("\"}");
                            if (i != gs.size() - 1) {
                                out.println(",");
                            }
                        }
                        out.println("]");
//                       <- ----- End of "Making JSON response" -----

                        break;
                    default:
                        out.println("{ \"error\" : \"Unknown type of statistic\"}");
                }

            } catch (Exception e) {
                out.println(e.toString());
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException ex) {
                    out.println("{ \"error\" : \"" +ex.toString()+ "\"}");
                }
            }
        } else {
            out.println("{ \"error\" : \"Empty request\"}");
        }

//
//        Next Step (#3): Making authentication
//
//        if (authentication()) {
//            response(req, resp, out);
//        } else {
//            out.println("Access denied!");
//        }

    }
}
