/**
 * CEN4078 Secure Software Development
 * PasswordPolicyException.java
 *
 * Custom exception for password policy violations.
 * Handles password policy validation failures at the lowest level.
 *
 * @author Olivia Bunch
 * @version: 2.0
 */

public class PasswordPolicyException extends Exception {
    public PasswordPolicyException() {
        super("Password does not meet security requirements.");
    }

    public PasswordPolicyException(String message) {
        super(message);
    }
}
