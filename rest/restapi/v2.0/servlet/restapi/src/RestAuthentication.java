public class RestAuthentication {
    private String login;
    private String role;
    private String token;

    RestAuthentication(String token) {
        this.token = token;
        authorization("admin"); //Temporary
    }

    RestAuthentication(String login, String password) {
        //Looking in DB that login and comparing password,
        //if success - generating Token
        authorization("user"); //Temporary
    }

    void authorization(String role) {
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
}
