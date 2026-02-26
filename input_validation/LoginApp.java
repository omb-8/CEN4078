/**
 * CEN 4078 Secure Software Development
 * Programming Exercise – Input Validation & Type Checking
 * 
 * The LoginApp class allows the user to log in with username, password, and MFA,
 * implementing input validation to prevent SQL injection, enforce password policy,
 * and check integer overflow for MFA.
 * 
 * @author Olivia Bunch
 * @version 1.0
 * @date January 25, 2026
 * 
 * Updates to this file to meet the new requirements were assisted by autocomplete tool Github Copilot.
 */

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
    // Change: use Cryptographer to encrypt credentials at rest
    private static Cryptographer crypto = new Cryptographer();
    
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
            
            // Change: validate username for SQL injection
            // Requirement: Username must not contain SQL injection characters (/, -, ;, ")
            if (!Validation.checkSQLInjection(user)) {
                System.out.println("\nLogin failed. Invalid input.");
                attempts++;
                continue;
            }
            
            // prompt for password (hidden)
            String pass = getHiddenPassword("Password: ", scanner);
            
            // Change: validate password for SQL injection and policy
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
            
            // Change: prompt for and validate MFA
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
                } 
                else {
                    System.out.println("\nLogin failed. Invalid input.");
                    attempts++;
                    continue;
                }
            } 
            else {
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

                // Change: load MFA along with username and password
                if (parts.length == 3) {
                    // Attempt to decrypt stored values. Existing files may be plaintext
                    // (previous versions). Try decrypting and fall back to plaintext
                    // if decrypted values do not meet validation checks.
                    String userCandidate = parts[0];
                    String passCandidate = parts[1];
                    String mfaCandidate = parts[2];

                    // Try decrypting
                    try {
                        String decUser = crypto.decrypt(parts[0]);
                        String decPass = crypto.decrypt(parts[1]);
                        String decMfa = crypto.decrypt(parts[2]);
                        // Basic validation to decide whether decryption produced valid values
                        if (decUser.matches("[A-Za-z0-9]+") && Validation.checkPasswordPolicy(decPass) && Validation.checkMFALength(decMfa)) {
                            userCandidate = decUser;
                            passCandidate = decPass;
                            mfaCandidate = decMfa;
                        }
                    } catch (Exception e) {
                        // Decryption failed — assume plaintext store from older version
                    }

                    usernames.add(userCandidate);
                    passwords.add(passCandidate);
                    try {
                        mfas.add(Integer.parseInt(mfaCandidate));
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
    
    // Change: method to create and write default credentials to file
    // Updated for new requirement: Passwords must be 8-12 characters, include MFA
    private static void initializeCredentialsFile() {
        try {
            FileWriter writer = new FileWriter(CREDENTIALS_FILE);
            // Change: store encrypted credentials on disk using Cryptographer
            writer.write(crypto.encrypt("scientist") + ":" + crypto.encrypt("Secure123!") + ":" + crypto.encrypt("1234567890") + "\n");
            writer.write(crypto.encrypt("engineer") + ":" + crypto.encrypt("Eng4567@") + ":" + crypto.encrypt("0987654321") + "\n");
            writer.write(crypto.encrypt("security") + ":" + crypto.encrypt("Sec7890#") + ":" + crypto.encrypt("1122334455") + "\n");
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
