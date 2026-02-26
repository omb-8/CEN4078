/**
 * CEN4078 Secure Software Development
 * PasswordPolicy.java
 *
 * Defines and validates password policies.
 *
 * @author Olivia Bunch
 * @version: 1.0
 */

public class PasswordPolicy {
    public static String getPasswordPolicy() {
        return "Password must be alphanumeric and include uppercase, lowercase, and a digit (no special characters).";
    }

    public static boolean validatePassword(String pw) {
        return Validator.validCredentials(pw);
    }
}
