/**
 * CEN4078 Secure Software Development
 * ValidationException.java
 *
 * Custom exception for validation failures.
 * Handles password validation errors at the lowest level.
 *
 * @author Olivia Bunch
 * @version: 2.0
 */

public class ValidationException extends Exception {
    public ValidationException() {
        super("Validation failed.");
    }

    public ValidationException(String message) {
        super(message);
    }
}
