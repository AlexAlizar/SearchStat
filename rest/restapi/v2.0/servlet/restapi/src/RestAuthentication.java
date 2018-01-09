public class RestAuthentication {
    private String login;
    private String role;
    private String token;

    RestAuthentication(String token) {
        this.token = token;
        authorization("user"); //Temporary
    }

    RestAuthentication(String login, String password) {
        //Looking in DB that login and comparing password,
        //if success - generating Token
        authorization("user"); //Temporary
    }

    void authorization(String role) {
        this.role = role;
    }

    public boolean Check() {
//        if (this.token != null) {
//            //Checking the token:
//            //connection to db and looking for the user,
//            //checking expiration date of the token,
//            //identifying role of the user: authorization(user||admin);
//        } else {
//            return false;
//        }
        return true; //Temporary
    }

    public String getRole() {
        return role;
    }
}
