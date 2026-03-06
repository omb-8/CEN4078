/**
 * CEN4078 Secure Software Development
 * PasswordPolicy.java
 *
 * Defines and validates password policies.
 * CHANGED v2.0: Now integrated with PasswordHandler for centralized password management.
 * No code changes to this file, but used as policy definition by PasswordHandler.
 *
 * @author Olivia Bunch
 * @version: 2.0
 */

public class PasswordPolicy {
    public static String getPasswordPolicy() {
        return "Password must be alphanumeric and " +
               "include uppercase, lowercase, and a digit " +
               "(no special characters).";
    }

    public static boolean validatePassword(String pw) {
        return Validator.validCredentials(pw);
    }
}
