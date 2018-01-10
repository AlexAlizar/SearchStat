import java.sql.*;

public class RestDB {
    private static RestDB instance = new RestDB();

    private String mysqlDriver = "com.mysql.jdbc.Driver";
    private String mysqlURL = "jdbc:mysql://localhost:3306/searchstat?useUnicode=true&characterEncoding=utf-8";
    private String login = "phpmyadmin";
    private String password = "phpmyadmin";

    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    private RestDB() {

    }

    public static RestDB getInstance() {
        return instance;
    }

    public String setDBDriver(String dbDriver) {
        try {
            Class.forName(dbDriver);
            return "DB Driver is ready.";
        } catch (ClassNotFoundException e) {
            return e.toString();
        }
    }

    private String connectToDB() {
        try {
            this.con = DriverManager.getConnection(mysqlURL, login, password);
            return "Connected to DB.";
        } catch (SQLException e) {
            return e.toString();
        }
    }

    private String makeDBStatement() {
        try {
            this.stmt = con.createStatement();
            return "Statement is ready.";
        } catch (SQLException e) {
            return e.toString();
        }
    }

    public String prepareDB(String dbType) {
        String result;
        switch (dbType) {
            case "mySQL":
                result = setDBDriver(mysqlDriver);
                if (result == "DB Driver is ready.") {
                    result = connectToDB();
                    if (result == "Connected to DB.") {
                        result = makeDBStatement();
                        if (result == "Statement is ready.") {
                            return "DB is ready.";
                        } else {
                            return result;
                        }
                    } else {
                        return result;
                    }
                } else {
                    return result;
                }
            default:
                return "DB is NOT ready.";
        }
    }

    public String executeDBQuery(String query) {

        return null;
    }




}
