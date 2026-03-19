/**
 * CEN4078 Secure Software Development
 * Main.java
 *
 * @author Olivia Bunch
 * @version: 1.0
 */

public class Main {
    public static void main(String[] args) {
        FlawsToFixes.flawedInput("abc");      // Should fail
        FlawsToFixes.fixedInput("abc");       // Should safely handle

        FlawsToFixes.flawedDivision(10, 0);    // Should fail
        FlawsToFixes.fixedDivision(10, 0);     // Should safely handle

        FlawsToFixes.fixedInput("123");       // Should process successfully
        FlawsToFixes.fixedDivision(10, 2);     // Should process successfully
    }
}