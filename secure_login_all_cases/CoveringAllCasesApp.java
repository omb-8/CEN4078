/**
 * CEN4078 Secure Software Development
 * CoveringAllCasesApp.java
 *
 * Handles password setup with policy using centralized PasswordHandler.
 * CHANGED v2.0: Refactored to use PasswordHandler for centralized password management
 * with proper exception handling at the lowest level. All password operations now
 * delegate to PasswordHandler, ensuring consistent security practices.
 *
 * @author Olivia Bunch
 * @version: 2.0
 */

import java.util.Scanner;

public class CoveringAllCasesApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PasswordHandler handler = new PasswordHandler();

        System.out.println("Covering All Cases - Password Setup");
        System.out.print("Enter username: ");
        String username = sc.nextLine().trim();

        System.out.println(handler.getPasswordPolicy());

        String password = handler.getUserPassword(sc, 2);

        if (password == null) {
            try {
                String defaultPw = handler.generateDefaultPassword(12);
                handler.sendSecureDefaultPassword(username, defaultPw);
                System.out.println("A default password has been set and a secure email has been sent to the user.");
            } catch (DefaultPasswordException e) {
                // Exception handled at lowest level - no sensitive information leaked
                System.out.println("Account setup encountered a security error. Please contact support.");
            }
        } else {
            System.out.println("Account created successfully.");
        }

        sc.close();
    }
}
