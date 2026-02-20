/**
 * Cryptographer.java
 *
 * Handles alphabetic and numeric Vigenère encryption/decryption.
 *
 * Author: Olivia Bunch
 * Version: 1.0
 */

public class Cryptographer {

    private static final String ALPHA_KEY = "ARGOSROCK";
    private static final String NUMBER_KEY = "1963";

    private char encryptAlpha(char p, char k) {
        int base = Character.isUpperCase(p) ? 'A' : 'a';
        return (char) ((p - base + (Character.toUpperCase(k) - 'A')) % 26 + base);
    }

    private char decryptAlpha(char c, char k) {
        int base = Character.isUpperCase(c) ? 'A' : 'a';
        return (char) ((c - base - (Character.toUpperCase(k) - 'A') + 26) % 26 + base);
    }

    private char encryptDigit(char p, char k) {
        // Standard numeric Vigenère: (p + k) mod 10
        return (char) (((p - '0') + (k - '0')) % 10 + '0');
    }

    private char decryptDigit(char c, char k) {
        // Reverse of encryptDigit: (c - k + 10) mod 10
        return (char) ((((c - '0') - (k - '0') + 10) % 10) + '0');
    }

    public String encryptVigenere(String text) {
        StringBuilder result = new StringBuilder();
        int j = 0;

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                result.append(encryptAlpha(c, ALPHA_KEY.charAt(j++ % ALPHA_KEY.length())));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public String decryptVigenere(String text) {
        StringBuilder result = new StringBuilder();
        int j = 0;

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                result.append(decryptAlpha(c, ALPHA_KEY.charAt(j++ % ALPHA_KEY.length())));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public String encryptNumber(String text) {
        StringBuilder result = new StringBuilder();
        int j = 0;

        for (char c : text.toCharArray()) {
            if (Character.isDigit(c)) {
                result.append(encryptDigit(c, NUMBER_KEY.charAt(j++ % NUMBER_KEY.length())));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public String decryptNumber(String text) {
        StringBuilder result = new StringBuilder();
        int j = 0;

        for (char c : text.toCharArray()) {
            if (Character.isDigit(c)) {
                result.append(decryptDigit(c, NUMBER_KEY.charAt(j++ % NUMBER_KEY.length())));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    // Combined encryption: applies alphabetic and numeric Vigenère
    // to the appropriate characters in the input. Non-alphanumeric
    // characters are left unchanged (but input validation should
    // prohibit them).
    public String encrypt(String text) {
        StringBuilder result = new StringBuilder();
        int jAlpha = 0;
        int jNum = 0;

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                result.append(encryptAlpha(c, ALPHA_KEY.charAt(jAlpha++ % ALPHA_KEY.length())));
            } else if (Character.isDigit(c)) {
                result.append(encryptDigit(c, NUMBER_KEY.charAt(jNum++ % NUMBER_KEY.length())));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    // Combined decryption matching the above encrypt()
    public String decrypt(String text) {
        StringBuilder result = new StringBuilder();
        int jAlpha = 0;
        int jNum = 0;

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                result.append(decryptAlpha(c, ALPHA_KEY.charAt(jAlpha++ % ALPHA_KEY.length())));
            } else if (Character.isDigit(c)) {
                result.append(decryptDigit(c, NUMBER_KEY.charAt(jNum++ % NUMBER_KEY.length())));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}