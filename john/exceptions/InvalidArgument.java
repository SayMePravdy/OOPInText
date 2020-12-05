package john.exceptions;

public class InvalidArgument extends RuntimeException {

    public InvalidArgument(String message) {
        super(message);
    }
}
