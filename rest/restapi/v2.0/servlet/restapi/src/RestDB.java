import java.sql.*;

public class RestDB {
    private static RestDB instance = new RestDB();

    private static boolean ready = false;

    private String mysqlDriver = "com.mysql.jdbc.Driver";
    private String mysqlURL = "jdbc:mysql://localhost:3306/searchstat?useUnicode=true&characterEncoding=utf-8";
    private String login = "phpmyadmin";
    private String password = "phpmyadmin";

    public Connection con = null;
    public Statement stmt = null;
    public ResultSet rs = null;

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
                            ready = true;
                            RestMessages.constructMessage("DB is ready");
                            return "DB is ready.";
                        } else {
                            RestMessages.constructMessage(result);
                            return result;
                        }
                    } else {
                        RestMessages.constructMessage(result);
                        return result;
                    }
                } else {
                    RestMessages.constructMessage(result);
                    return result;
                }
            default:
                return "DB is NOT ready.";
        }
    }

    public boolean getReady() {
        return ready;
    }

    public String executeDBQuery(String query) {
        try {
            rs = stmt.executeQuery(query);
            return String.valueOf(rs);
        } catch (Exception e) {
            return e.toString();
        }
    }

    public String executeDBQueryUpdate(String query) {
        try {
            if (stmt.executeUpdate(query) == 1) {
                return "Updated";
            } else {
                return "Failed";
            }

        } catch (Exception e) {
            return e.toString();
        }
    }




}
