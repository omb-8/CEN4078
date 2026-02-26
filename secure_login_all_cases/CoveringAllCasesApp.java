/**
 * CEN4078 Secure Software Development
 * CoveringAllCasesApp.java
 *
 * Handles password setup with policy.
 *
 * @author Olivia Bunch
 * @version: 1.0
 */

import java.util.Scanner;

public class CoveringAllCasesApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Covering All Cases - Password Setup");
        System.out.print("Enter username: ");
        String username = sc.nextLine().trim();

        System.out.println(PasswordPolicy.getPasswordPolicy());

        String password = null;
        int attempts = 0;
        while (attempts < 2) {
            System.out.print("Enter a password: ");
            String pw = sc.nextLine();
            if (PasswordPolicy.validatePassword(pw)) {
                password = pw;
                System.out.println("Password accepted.");
                break;
            } else {
                attempts++;
                System.out.println("Password did not meet the policy. Attempts remaining: " + (2 - attempts));
            }
        }

        if (password == null) {
            String defaultPw = DefaultPassword.generate(12);
            int regen = 0;
            while (!PasswordPolicy.validatePassword(defaultPw) && regen < 10) {
                defaultPw = DefaultPassword.generate(12);
                regen++;
            }
            DefaultPassword.sendSecureEmail(username, defaultPw);
            System.out.println("A default password has been set and a secure email has been sent to the user.");
        } else {
            System.out.println("Account created successfully.");
        }

        sc.close();
    }
}
