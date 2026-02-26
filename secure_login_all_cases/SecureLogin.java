import java.util.ArrayList;
import java.util.Scanner;

public class SecureLogin {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cryptographer crypto = new Cryptographer();

        ArrayList<String> encryptedUsers = new ArrayList<>();
        ArrayList<String> encryptedPasswords = new ArrayList<>();

        System.out.print("Create Username: ");
        String username = scanner.nextLine();

        System.out.print("Create Password: ");
        String password = scanner.nextLine();

        if (!username.matches("[A-Za-z0-9]+") || !Validator.validCredentials(password)) {
            System.out.println("Invalid credentials.");
            scanner.close();
            return;
        }

        encryptedUsers.add(crypto.encrypt(username));
        encryptedPasswords.add(crypto.encrypt(password));

        System.out.println("\nLogin");

        System.out.print("Username: ");
        String loginUser = scanner.nextLine();

        System.out.print("Password: ");
        String loginPass = scanner.nextLine();

        String encUser = crypto.encrypt(loginUser);
        String encPass = crypto.encrypt(loginPass);

        boolean auth = encUser.equals(encryptedUsers.get(0)) &&
                       encPass.equals(encryptedPasswords.get(0));

        if (auth) {
            System.out.println("Access granted.");
        } else {
            System.out.println("Authentication failed.");
        }

        boolean decryptsMatch = crypto.decrypt(encryptedUsers.get(0)).equals(loginUser) &&
                                crypto.decrypt(encryptedPasswords.get(0)).equals(loginPass);

        System.out.println("Encryption check: " + (auth ? "passed" : "failed"));
        System.out.println("Decryption check: " + (decryptsMatch ? "passed" : "failed"));

        scanner.close();
    }
}
