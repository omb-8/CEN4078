/**
 * CEN4078 Secure Software Development
 * DefaultPasswordException.java
 *
 * @author Olivia Bunch
 * @version: 2.0
 */

public class DefaultPasswordException extends Exception {
    public DefaultPasswordException() {
        super("Failed to generate secure default password.");
    }

    public DefaultPasswordException(String message) {
        super(message);
    }
}
