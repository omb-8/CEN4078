/**
 * COP 4078 Exercise: Covering All Cases
 * File Name: CoveringAllCasesApp.java
 * Purpose: Application that allows a user two attempts to create a valid password
 *          and falls back to a default password generator if both attempts fail.
 * Changes: New application implementing the assignment requirements.
 */
import java.util.Scanner;

public class CoveringAllCasesApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Covering All Cases - Password Setup");
        System.out.print("Enter username: ");
        String username = sc.nextLine().trim();

        // Show policy using the dedicated method
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
                // Generic failure message to avoid leaking policy details beyond the policy text already shown.
                System.out.println("Password did not meet the policy. Attempts remaining: " + (2 - attempts));
            }
        }

        if (password == null) {
            // User failed two attempts — use default password generator
            String defaultPw = DefaultPassword.generate(12);
            // Extra safety: ensure returned default meets policy (should by design)
            int regen = 0;
            while (!PasswordPolicy.validatePassword(defaultPw) && regen < 10) {
                defaultPw = DefaultPassword.generate(12);
                regen++;
            }
            // Simulate secure delivery (do not print password to console)
            DefaultPassword.sendSecureEmail(username, defaultPw);
            System.out.println("A default password has been set and a secure email has been sent to the user.");
        } else {
            System.out.println("Account created successfully.");
        }

        sc.close();
    }
}
