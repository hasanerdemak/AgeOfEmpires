package exceptions;

public class AgeOfEmpiresException extends Exception {

    public AgeOfEmpiresException() {
        super("Default AgeOfEmpiresException");
    }

    public AgeOfEmpiresException(String message) {
        super(message);
    }

}
