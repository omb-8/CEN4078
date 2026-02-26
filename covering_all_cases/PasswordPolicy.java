/**
 * COP 4078 Exercise: Covering All Cases
 * File Name: PasswordPolicy.java
 * Purpose: Provide password policy string and validation method.
 * Changes: New file for the "Covering All Cases" exercise.
 * @author  Generated
 * @version 1.0
 */
public class PasswordPolicy {
    // Returns a human-readable password policy. This method satisfies the
    // assignment requirement to "create a method which returns the password policy." 
    public static String getPasswordPolicy() {
        // Align policy with the secure_login/Validator.java which requires
        // alphanumeric passwords containing at least one upper, lower, and digit.
        return "Password must be alphanumeric and include uppercase, lowercase, and a digit (no special characters).";
    }

    // Validate a password against the policy used by the application.
    // Delegate validation to the shared Validator to keep behavior consistent
    // across modules.
    public static boolean validatePassword(String pw) {
        return shared.Validator.validCredentials(pw);
    }
}
