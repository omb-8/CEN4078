import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class LoginApp {
    
    private static final String CREDENTIALS_FILE = "credentials.txt";
    private static final int MAX_ATTEMPTS = 3;
    private static ArrayList<String> usernames = new ArrayList<>();
    private static ArrayList<String> passwords = new ArrayList<>();
    private static ArrayList<Integer> mfas = new ArrayList<>();
    
    public static void authenticate() {
        // load credentials from file
        loadCredentials();
        
        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;
        int attempts = 0;
        String user = "";
        
        System.out.println("\n=== LOGIN ===\n");
        
        // retry loop with 3 attempt limit
        while ((attempts < MAX_ATTEMPTS) && (!loggedIn)) {

            // prompt for username
            System.out.print("Username: ");
            user = scanner.nextLine();
            
            // validate username for SQL injection
            // Requirement: Username must not contain SQL injection characters (/, -, ;, ")
            if (!Validation.checkSQLInjection(user)) {
                System.out.println("\nLogin failed. Invalid input.");
                attempts++;
                continue;
            }
            
            // prompt for password (hidden)
            String pass = getHiddenPassword("Password: ", scanner);
            
            // validate password for SQL injection and policy
            // Requirement: Password must not contain SQL injection characters and must be 8-12 characters with at least one uppercase, lowercase, and numeric
            if (!Validation.checkSQLInjection(pass) || !Validation.checkPasswordPolicy(pass)) {
                System.out.println("\nLogin failed. Invalid input.");
                attempts++;
                continue;
            }
            
            // authenticate login with database
            int userIndex = -1;
            for (int i = 0; i < usernames.size(); i++) {
                if (usernames.get(i).equals(user) && passwords.get(i).equals(pass)) {
                    userIndex = i;
                    break;
                }
            }
            
            if (userIndex != -1) {
                // prompt for MFA
                System.out.print("MFA (10-digit number): ");
                String mfaInput = scanner.nextLine();
                
                // validate MFA
                // Requirement: MFA must be exactly 10 digits and within integer range
                if (!Validation.checkMFALength(mfaInput) || !Validation.checkIntegerOverflow(mfaInput)) {
                    System.out.println("\nLogin failed. Invalid input.");
                    attempts++;
                    continue;
                }
                
                int mfaValue = Integer.parseInt(mfaInput);
                if (mfas.get(userIndex) == mfaValue) {
                    loggedIn = true;
                } else {
                    System.out.println("\nLogin failed. Invalid input.");
                    attempts++;
                    continue;
                }
            } else {
                System.out.println("\nLogin failed. Invalid input.");
                attempts++;
                continue;
            }
            
            // secure software dev error handling
            if (loggedIn) {
                System.out.println("\nWelcome, " + user + "!");
                handleLoggedInSession(scanner, user);
            } 
            else {
                attempts++;
                int triesLeft = MAX_ATTEMPTS - attempts;

                if (triesLeft > 0) {
                    System.out.println("\nLogin failed. Invalid username or password.");
                    System.out.println("Attempts remaining: " + triesLeft + "\n");
                }
            }
        }
        
        // account lockout message
        if (!loggedIn) {
            System.out.println("\nAccount locked due to multiple failed login attempts.");
        }
        
        scanner.close();
    }
    
    // method to read hidden password
    public static String getHiddenPassword(String prompt, Scanner scanner) {

        Console console = System.console();
        
        if (console != null) {
            // terminal hide password input
            System.out.print(prompt);
            char[] passwordChars = console.readPassword();
            return new String(passwordChars);
        } 
        else {
            // regular input for testing
            System.out.print(prompt);
            return scanner.nextLine();
        }
    }
    
    // method to handle user session and logout
    private static void handleLoggedInSession(Scanner scanner, String username) {
        boolean sessionActive = true;
        
        while (sessionActive) {
            System.out.println("\n=== SESSION MENU ===");
            System.out.println("1. Logout");
            System.out.print("Select option: ");
            
            String choice = scanner.nextLine();
            
            if (choice.equals("1")) {
                System.out.println("\nLogging out...");
                System.out.println("Goodbye, " + username + "!");
                sessionActive = false;
            } 
            else {
                System.out.println("\nInvalid option. Please try again.");
            }
        }
    }
    
    // method to load credentials from file
    private static void loadCredentials() {
        try {
            File file = new File(CREDENTIALS_FILE);
            
            if (!file.exists()) {
                System.out.println("Credentials file not found.");
                initializeCredentialsFile();
                return;
            }
            
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(":");

                if (parts.length == 3) {
                    usernames.add(parts[0]);
                    passwords.add(parts[1]);
                    try {
                        mfas.add(Integer.parseInt(parts[2]));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid MFA in credentials file.");
                    }
                }
            }
            fileScanner.close();
            System.out.println("Credentials loaded from " + CREDENTIALS_FILE);
        } 
        catch (IOException e) {
            System.out.println("Error reading credentials file: " + e.getMessage());
        }
    }
    
    // method to create and write default credentials to file
    // Updated for new requirement: Passwords must be 8-12 characters, include MFA
    private static void initializeCredentialsFile() {
        try {
            FileWriter writer = new FileWriter(CREDENTIALS_FILE);
            writer.write("scientist:Secure123!:1234567890\n");
            writer.write("engineer:Eng456@:0987654321\n");
            writer.write("security:Sec789#:1122334455\n");
            writer.close();
            System.out.println("Credentials file created: " + CREDENTIALS_FILE);
            
            // load the newly created credentials
            loadCredentials();
        } 
        catch (IOException e) {
            System.out.println("Error creating credentials file: " + e.getMessage());
        }
    }
}
