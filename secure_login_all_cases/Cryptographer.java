/**
 * CEN4078 Secure Software Development
 * Cryptographer.java
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
        return (char) (((p - '0') + (k - '0')) % 10 + '0');
    }

    private char decryptDigit(char c, char k) {
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
