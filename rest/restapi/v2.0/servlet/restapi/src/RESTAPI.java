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
            Authentication auth = new Authentication(rToken);
            if (auth.Check()) {
                //Proceed with request
            } else {
                //Token is invalid
            }
        } else {
            //Token was not found.
        }
    }
}
