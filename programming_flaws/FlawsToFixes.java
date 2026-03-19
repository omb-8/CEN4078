/**
 * CEN4078 Secure Software Development
 * FlawsToFixes.java
 *
 * @author Olivia Bunch
 * @version: 1.0
 */

public class FlawsToFixes {

    public static void flawedInput(String input) {
        try {
            if (input == null || !input.matches("\\d+")) {
                throw new InvalidInputException();
            }
            int number = Integer.parseInt(input);
            System.out.println("Processed number: " + number);
        } 
        catch (InvalidInputException e) {
            System.out.println("Error: Invalid input.");
        }
    }

    public static void fixedInput(String input) {
        try {
            if (input == null || !input.matches("\\d+")) {
                throw new InvalidInputException();
            }
            int number = Integer.parseInt(input);
            System.out.println("Processed number: " + number);
        } 
        catch (InvalidInputException e) {
            System.out.println("Error: Unable to process request.");
        }
    }

    public static void flawedDivision(int a, int b) {
        try {
            if (b == 0) {
                throw new DivisionByZeroException();
            }
            int result = a / b;
            System.out.println("Result: " + result);
        } 
        catch (DivisionByZeroException e) {
            System.out.println("Error: Operation failed.");
        }
    }

    public static void fixedDivision(int a, int b) {
        try {
            if (b == 0) {
                throw new DivisionByZeroException();
            }
            int result = a / b;
            System.out.println("Result: " + result);
        } 
        catch (DivisionByZeroException e) {
            System.out.println("Error: Invalid operation.");
        }
    }
}