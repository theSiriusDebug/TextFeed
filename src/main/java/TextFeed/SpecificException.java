package TextFeed;

public class SpecificException extends Exception {

    public SpecificException() {
        super();
    }

    public SpecificException(String message) {
        super(message);
    }

    public SpecificException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpecificException(Throwable cause) {
        super(cause);
    }
}
