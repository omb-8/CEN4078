/**
 * CEN4078 Secure Software Development
 * DefaultPassword.java
 *
 * Generates and sends secure default passwords.
 *
 * @author Olivia Bunch
 * @version: 1.0
 */

import java.security.SecureRandom;
import java.io.FileWriter;
import java.io.IOException;

public class DefaultPassword {
    private static final String U = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String L = "abcdefghijklmnopqrstuvwxyz";
    private static final String D = "0123456789";
    private static final SecureRandom rnd = new SecureRandom();

    public static String generate(int length) {
        if (length < 8) length = 12;
        StringBuilder sb = new StringBuilder();
        sb.append(U.charAt(rnd.nextInt(U.length())));
        sb.append(L.charAt(rnd.nextInt(L.length())));
        sb.append(D.charAt(rnd.nextInt(D.length())));
        String all = U + L + D;
        for (int i = 4; i < length; i++) {
            sb.append(all.charAt(rnd.nextInt(all.length())));
        }
        char[] arr = sb.toString().toCharArray();
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            char t = arr[i]; arr[i] = arr[j]; arr[j] = t;
        }
        String result = new String(arr);
        int tries = 0;
        while (!PasswordPolicy.validatePassword(result) && tries < 10) {
            result = generate(length);
            tries++;
        }
        return result;
    }

    public static void sendSecureEmail(String username, String password) {
        String filename = "covering_all_cases_secure_email_" + username + ".txt";
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write("To: " + username + "@example.com\n");
            fw.write("Subject: Your default password\n\n");
            fw.write("Your account has been set to a default password.\n");
            fw.write("Default password: " + password + "\n");
            fw.write("Please change it after first login.\n");
        } catch (IOException e) {
            System.err.println("Failed to send secure email.");
        }
    }
}
