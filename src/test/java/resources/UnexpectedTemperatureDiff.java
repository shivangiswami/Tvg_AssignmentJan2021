package resources;

public class UnexpectedTemperatureDiff extends Exception {

    public UnexpectedTemperatureDiff(String message)
    {
        super(message);
    }

    public UnexpectedTemperatureDiff(String message, Throwable cause) {
        super(message, cause);
    }
}
