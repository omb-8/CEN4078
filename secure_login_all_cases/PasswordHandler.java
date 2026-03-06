/**
 * CEN4078 Secure Software Development
 * PasswordHandler.java
 *
 * @author Olivia Bunch
 * @version: 2.0
 */

import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;

public class PasswordHandler {
    private static final int MAX_ATTEMPTS = 2;
    private static final int DEFAULT_LENGTH = 12;
    private static final int MAX_GENERATION_ATTEMPTS = 10;
    private static final String SECURE_EMAIL_DIR = "secure_emails/";

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public void validatePassword(String password) throws ValidationException, PasswordPolicyException {
        if (password == null) {
            throw new ValidationException("Password cannot be null.");
        }

        if (!Validator.validCredentials(password)) {
            throw new PasswordPolicyException();
        }
    }

    public String generateDefaultPassword(int length) throws DefaultPasswordException {
        if (length < 8) {
            length = DEFAULT_LENGTH;
        }

        for (int attempt = 0; attempt < MAX_GENERATION_ATTEMPTS; attempt++) {
            try {
                String password = generateRandomPassword(length);
                if (Validator.validCredentials(password)) {
                    return password;
                }
            } catch (Exception e) {
                if (attempt == MAX_GENERATION_ATTEMPTS - 1) {
                    throw new DefaultPasswordException("Unable to generate secure password after " + MAX_GENERATION_ATTEMPTS + " attempts.");
                }
            }
        }

        throw new DefaultPasswordException("Failed to generate secure default password.");
    }

    private String generateRandomPassword(int length) {
        StringBuilder password = new StringBuilder();

        password.append(UPPERCASE.charAt(RANDOM.nextInt(UPPERCASE.length())));
        password.append(LOWERCASE.charAt(RANDOM.nextInt(LOWERCASE.length())));
        password.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));

        String allChars = UPPERCASE + LOWERCASE + DIGITS;
        for (int i = 3; i < length; i++) {
            password.append(allChars.charAt(RANDOM.nextInt(allChars.length())));
        }

        char[] chars = password.toString().toCharArray();
        for (int i = chars.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }

        return new String(chars);
    }

    public void sendSecureDefaultPassword(String username, String password) throws DefaultPasswordException {
        String filename = SECURE_EMAIL_DIR + "secure_email_" + sanitizeFilename(username) + ".txt";

        try {
            // create directory if it doesn't exist
            java.io.File dir = new java.io.File(SECURE_EMAIL_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            try (FileWriter fw = new FileWriter(filename)) {
                fw.write("SECURE EMAIL - DO NOT SHARE\n");
                fw.write("================================\n");
                fw.write("To: " + username + "@example.com\n");
                fw.write("Subject: Your Temporary Account Password\n\n");
                fw.write("Your account has been set with a temporary password.\n");
                fw.write("Password: " + password + "\n\n");
                fw.write("Please change this password upon first login.\n");
                fw.write("================================\n");
            }
        } catch (IOException e) {
            throw new DefaultPasswordException("Secure email delivery failed.");
        } catch (SecurityException e) {
            throw new DefaultPasswordException("Secure email delivery failed.");
        }
    }

    // sanitize filename to prevent path traversal and invalid characters
    private String sanitizeFilename(String filename) {
        return filename.replaceAll("[^a-zA-Z0-9_-]", "_");
    }

    public String getUserPassword(java.util.Scanner scanner, int maxAttempts) {
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            System.out.print("Enter a password: ");
            String password = scanner.nextLine();

            try {
                validatePassword(password);
                System.out.println("Password accepted.");
                return password;
            } catch (ValidationException | PasswordPolicyException e) {
                // Exception handled at lowest level
                int remaining = maxAttempts - attempt - 1;
                System.out.println("Password does not meet security requirements. Attempts remaining: " + remaining);
            }
        }
        return null;
    }

    public String getPasswordPolicy() {
        return PasswordPolicy.getPasswordPolicy();
    }
}
