/**
 * CEN 4078 Secure Software Development
 * Programming Exercise – Input Validation & Type Checking
 * 
 * The Validation class provides methods for input validation including SQL injection checks,
 * password policy enforcement, and integer overflow prevention.
 * 
 * @author Olivia Bunch
 * @version 1.0
 * @date January 25, 2026
 */

public class Validation {

    /**
     * SQL Injection
     * @param input the string to validate
     * @return true if no forbidden characters, false otherwise
     */
    public static boolean checkSQLInjection(String input) {
        if ((!input.contains("/")) && (!input.contains("-")) && (!input.contains(";")) && (!input.contains("\""))) {
            return true;
        }
        return false;
    }

    /**
     * Password Policy
     * @param password the password string to validate
     * @return true if valid, false otherwise
     */
    public static boolean checkPasswordPolicy(String password) {
        if ((password == null) || (password.length() < 8) || (password.length() > 12)) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } 
            else if (Character.isLowerCase(c)) {
                hasLower = true;
            } 
            else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }

        return hasUpper && hasLower && hasDigit;
    }

    /**
     * Integer Overflow
     * @param input the string to validate as integer
     * @return true if valid integer within range, false otherwise
     */
    public static boolean checkIntegerOverflow(String input) {
        if ((input == null) || (input.isEmpty())) {
            return false;
        }
        try {
            double value = Double.parseDouble(input);
            return value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE;
        } 
        catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Additional check if MFA is exactly 10 digits
     * @param mfa the MFA string
     * @return true if exactly 10 digits, false otherwise
     */
    public static boolean checkMFALength(String mfa) {
        if (mfa.length() == 10 && mfa.matches("\\d{10}")) {
            return true;
        }
        return false;
    }
}