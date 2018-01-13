import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RestMessages {
    private static RestMessages instance = new RestMessages();
    public static String outputJSONMessage = null;

    private RestMessages() {
    }

    public static RestMessages getInstance() {
        return instance;
    }

    public static void constructMessage(Object object) {
        outputJSONMessage = constructJSON(object);
    }

    public static String getOutputMessage() {
        return outputJSONMessage;
    }

    public class Message{
    }

    public static class Error {
        private String error;

        Error(int errorCode) {
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

        Error(String errorMessage) {
            this.error = errorMessage;
        }

        public String getError() {
            return error;
        }
    }

    public static String constructJSON(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
//        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

}
