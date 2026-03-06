/**
 * CEN4078 Secure Software Development
 * PasswordHandler.java
 *
 * Central password handling class that processes all password operations.
 * Implements exceptions at the lowest level for password validation, policy,
 * and default password generation. Ensures secure error handling without
 * information leakage.
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

    // Character sets for password generation
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Validates a password against security requirements.
     * Throws ValidationException if null or format is invalid.
     * Throws PasswordPolicyException if policy requirements not met.
     *
     * @param password The password to validate
     * @throws ValidationException If password is null or invalid format
     * @throws PasswordPolicyException If password fails policy requirements
     */
    public void validatePassword(String password) throws ValidationException, PasswordPolicyException {
        // Validation at lowest level - null check
        if (password == null) {
            throw new ValidationException("Password cannot be null.");
        }

        // Validation at lowest level - format check using Validator
        if (!Validator.validCredentials(password)) {
            throw new PasswordPolicyException();
        }
    }

    /**
     * Generates a secure default password that meets policy requirements.
     * Throws DefaultPasswordException if unable to generate valid password.
     *
     * @param length Desired password length (minimum 8)
     * @return A securely generated password meeting policy requirements
     * @throws DefaultPasswordException If unable to generate valid password after retries
     */
    public String generateDefaultPassword(int length) throws DefaultPasswordException {
        if (length < 8) {
            length = DEFAULT_LENGTH;
        }

        // Attempt to generate a valid password
        for (int attempt = 0; attempt < MAX_GENERATION_ATTEMPTS; attempt++) {
            try {
                String password = generateRandomPassword(length);
                // Validate generated password meets policy
                if (Validator.validCredentials(password)) {
                    return password;
                }
            } catch (Exception e) {
                // Handle generation errors at lowest level
                if (attempt == MAX_GENERATION_ATTEMPTS - 1) {
                    throw new DefaultPasswordException("Unable to generate secure password after " + MAX_GENERATION_ATTEMPTS + " attempts.");
                }
            }
        }

        throw new DefaultPasswordException("Failed to generate secure default password.");
    }

    /**
     * Generates a random password with guaranteed uppercase, lowercase, and digit.
     * Called by generateDefaultPassword to ensure variety.
     *
     * @param length The desired length
     * @return A randomly generated password
     */
    private String generateRandomPassword(int length) {
        StringBuilder password = new StringBuilder();

        // Ensure at least one of each required character type
        password.append(UPPERCASE.charAt(RANDOM.nextInt(UPPERCASE.length())));
        password.append(LOWERCASE.charAt(RANDOM.nextInt(LOWERCASE.length())));
        password.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));

        // Fill remaining length with random characters from all sets
        String allChars = UPPERCASE + LOWERCASE + DIGITS;
        for (int i = 3; i < length; i++) {
            password.append(allChars.charAt(RANDOM.nextInt(allChars.length())));
        }

        // Shuffle the password for better randomness
        char[] chars = password.toString().toCharArray();
        for (int i = chars.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }

        return new String(chars);
    }

    /**
     * Sends secure default password to user via secure email file.
     * Exception handling ensures security without information leakage.
     *
     * @param username The username of the account
     * @param password The secure default password
     * @throws DefaultPasswordException If email delivery fails
     */
    public void sendSecureDefaultPassword(String username, String password) throws DefaultPasswordException {
        String filename = SECURE_EMAIL_DIR + "secure_email_" + sanitizeFilename(username) + ".txt";

        try {
            // Create directory if it doesn't exist
            java.io.File dir = new java.io.File(SECURE_EMAIL_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Write secure email with default password
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
            // Handle IO error at lowest level without leaking details
            throw new DefaultPasswordException("Secure email delivery failed.");
        } catch (SecurityException e) {
            // Handle security error at lowest level without leaking details
            throw new DefaultPasswordException("Secure email delivery failed.");
        }
    }

    /**
     * Sanitizes filename to prevent directory traversal attacks.
     *
     * @param filename The filename to sanitize
     * @return A safe filename
     */
    private String sanitizeFilename(String filename) {
        return filename.replaceAll("[^a-zA-Z0-9_-]", "_");
    }

    /**
     * High-level method for user password input with attempt tracking.
     * Handles user input validation and policy compliance.
     * Returns password if valid, null if max attempts exceeded.
     *
     * @param scanner Scanner for user input
     * @param maxAttempts Maximum password entry attempts
     * @return The validated password or null if attempts exceeded
     */
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

    /**
     * Gets the password policy requirements as a user-friendly string.
     *
     * @return Password policy description
     */
    public String getPasswordPolicy() {
        return PasswordPolicy.getPasswordPolicy();
    }
}
