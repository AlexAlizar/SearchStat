import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ReastAPIServlet extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        response.setContentType("application/json; charset=utf-8");

        PrintWriter out = response.getWriter();
        out.println("{\"sites\": [{");
        out.println("        \"SiteID\": \"0000\",");
        out.println("        \"SiteName\": \"Site #1\",");
        out.println("\t\t\"persons\":");
        out.println("\t\t\t[{\"PersonID\": \"0000\",");
        out.println("\t\t\t\"PersonName\": \"Person #1\",");
        out.println("\t\t\t\"PersonRank\": \"100500\"},");
        out.println("\t\t\t{\"PersonID\": \"0001\",");
        out.println("\t\t\t\"PersonName\": \"Person #2\",");
        out.println("\t\t\t\"PersonRank\": \"10050\"},");
        out.println("\t\t\t{\"PersonID\": \"0003\",");
        out.println("\t\t\t\"PersonName\": \"Person #3\",");
        out.println("\t\t\t\"PersonRank\": \"1050\"}]");
        out.println("\t\t\t}]");
        out.println("}");

    }

}
