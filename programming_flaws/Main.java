/**
 * CEN4078 Secure Software Development
 * Main.java
 *
 * @author Olivia Bunch
 * @version: 1.0
 */

public class Main {
    public static void main(String[] args) {
        Flawstofixes.flawedInput("abc");      // Should fail
        Flawstofixes.fixedInput("abc");       // Should safely handle

        // Test division flaw
        Flawstofixes.flawedDivision(10, 0);    // Should fail
        Flawstofixes.fixedDivision(10, 0);     // Should safely handle

        Flawstofixes.fixedInput("123");       // Should process successfully
        Flawstofixes.fixedDivision(10, 2);     // Should process successfully
    }
}