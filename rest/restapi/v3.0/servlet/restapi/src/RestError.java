public class RestError {
    private String error;
//    private String message;

    RestError(int errorCode) {
        switch (errorCode) {
            case 0x1:
                this.error = "Token is not found.";
                break;
            case 0x2:
                this.error = "Token is invalid.";
                break;
            case 0x3:
                this.error = "Authorization failed.";
                break;
            case 0x4:
                this.error = "Access denied.";
                break;
            default:
                this.error = "Unknown error.";
        }
    }

    RestError(String errorMessage) {
        this.error = errorMessage;
    }

    public String getError() {
        return error;
    }
}
