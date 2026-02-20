/**
 * Validator.java
 *
 * Performs secure input validation.
 *
 * Author: Olivia Bunch
 * Version: 1.0
 */

public class Validator {

    public static boolean validCredentials(String input) {
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
