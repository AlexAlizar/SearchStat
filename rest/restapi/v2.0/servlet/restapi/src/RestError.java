public class RestError {
    public static String error;
    public static String message;

    RestError(int errorCode) {
        switch (errorCode) {
            case 0x1:
                this.message = "token is not found";
                break;
            case 0x2:
                break;
            default:
                this.message = "unknown error";
        }
    }
}
