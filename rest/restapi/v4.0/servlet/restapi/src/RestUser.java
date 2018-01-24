public class RestUser {
    private String id;
    private String login;
    private String password;
    private String email;
    private String token;
    private String role;
    private String persons;

    public RestUser(String id, String login, String password, String email, String token, String role, String persons) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.token = token;
        this.role = role;
        this.persons = persons;
    }
}
