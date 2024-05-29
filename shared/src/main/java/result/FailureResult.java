package result;

public class FailureResult {
    private final String message;

    public FailureResult(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
