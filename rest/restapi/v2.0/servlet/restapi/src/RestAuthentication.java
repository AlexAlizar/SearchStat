public class RestAuthentication {

    private boolean authenticated = false;
    private String id;
    private String login;
    private String password;
    private String email;
    private String token;
    private String role;
    private String persons;
    private String creation_date;
    private String last_login_date;

    private RestDB db = RestDB.getInstance();


    RestAuthentication(String token) {
        this.token = token;
        authorization("admin"); //Temporary
    }

    RestAuthentication(String login, String password) {

        //Looking in DB that login and comparing password,
        //if success - generating Token
        try {
            String result = db.prepareDB("mySQL");
            if (result == "DB is ready.") {
                String query = "SELECT * FROM users WHERE login = " + login;
                result = db.executeDBQuery("SELECT * FROM users WHERE login = \"" + login + "\"");
                while (db.rs.next()) {
                    RestMessages.constructMessage("we are inside");
                    this.id = db.rs.getString("id");
                    this.login = db.rs.getString("login");
                    this.password = db.rs.getString("password");
                    this.email = db.rs.getString("email");
                    this.token = db.rs.getString("token");
                    this.role = db.rs.getString("role");
                    this.persons = db.rs.getString("persons");
                    this.creation_date = db.rs.getString("creation_date");
                    this.last_login_date = db.rs.getString("last_login_date");
                }
                if (this.login == null) {
                    RestMessages.constructMessage(new RestMessages.Error("User doesn\'t exist"));
                } else {
                    RestMessages.constructMessage("User exist");
//                    //Here will be method for existence users
                    if (this.password.equals(password)) {
                        this.authenticated = true;
                    } else {
                        this.authenticated = false;
                        RestMessages.constructMessage(new RestMessages.Error("Wrong password"));
                    }
                }
            } else {
                RestMessages.constructMessage(new RestMessages.Error("DB is not ready"));
            }
        } catch (Exception e) {
            RestMessages.constructMessage(new RestMessages.Error(e.toString()));
        }
        authorization("admin"); //Temporary
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void authorization(String role) {
        this.role = role;
    }

    public boolean checkToken() {
//            //checkToken the token:
//            //connect to DB and look for the user with token,
//            //check expiration date of the token,
//            //identifying role of the user: authorization(user||admin);
        return true; //Temporary
    }

    public String getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }
}
