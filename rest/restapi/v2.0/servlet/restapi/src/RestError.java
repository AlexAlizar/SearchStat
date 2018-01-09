public class RestError {
    public static String error;
    public static String message;

    RestError(int errorCode) {
        switch (errorCode) {
            case 0x1:
                this.message = "Token is not found.";
                break;
            case 0x2:
                this.message = "Token is invalid.";
                break;
            case 0x3:
                this.message = "Authorization failed.";
                break;
            case 0x4:
                this.message = "";
                break;
            default:
                this.message = "Unknown error.";
        }
    }
}
