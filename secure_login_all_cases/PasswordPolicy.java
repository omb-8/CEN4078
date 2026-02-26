public class PasswordPolicy {
    public static String getPasswordPolicy() {
        return "Password must be alphanumeric and include uppercase, lowercase, and a digit (no special characters).";
    }

    public static boolean validatePassword(String pw) {
        return Validator.validCredentials(pw);
    }
}
