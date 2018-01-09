public class RestAuthentication {
    private String role;
    private String token;

    RestAuthentication(String token) {
        this.token = token;
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
}
