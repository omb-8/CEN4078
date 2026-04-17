/**
 * CEN4078 Secure Software Development
 * Validator.java
 *
 * @author Olivia Bunch
 * @version: 2.0
 */

public class Validator {

    public static boolean validUsername(String input) {
        if (input == null) return false;
        if (input.isEmpty()) return false;
        return input.matches("^[A-Za-z0-9]+");
    }  

    public static boolean validCredentials(String input) {
        if (input == null) return false;
        if (input.length() < 8) return false;

        boolean upper = false;
        boolean lower = false;
        boolean digit = false;

        for (char c : input.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
            if (Character.isUpperCase(c)) upper = true;
            if (Character.isLowerCase(c)) lower = true;
            if (Character.isDigit(c)) digit = true;
        }
        return upper && lower && digit;
    }
}
