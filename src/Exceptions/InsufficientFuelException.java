package Exceptions;

public class InsufficientFuelException extends RuntimeException {
    public InsufficientFuelException(String message) {
        super(message);
    }
}
