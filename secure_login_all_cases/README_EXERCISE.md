# CEN 4078 Exercise: Exception Handling at the Lowest Level

## Overview

This project implements exception handling at the lowest level by creating a centralized `PasswordHandler` class that manages all password-related operations. Custom exceptions are caught and handled where they occur, preventing information leakage while providing meaningful error handling.

## Project Structure

### New Files (Version 2.0)
- **PasswordHandler.java** - Centralized password handling class
- **PasswordPolicyException.java** - Exception for policy violations
- **ValidationException.java** - Exception for validation failures
- **DefaultPasswordException.java** - Exception for default password failures

### Modified Files (Version 2.0)
- **CoveringAllCasesApp.java** - Updated to use PasswordHandler
- **Validator.java** - Version update (v2.0)
- **PasswordPolicy.java** - Version update (v2.0)
- **DefaultPassword.java** - Version update (v2.0)
- **SecureLogin.java** - Version update (v2.0)
- **Cryptographer.java** - Version update (v2.0)

### Documentation
- **CONFIG_CONTROL.md** - Configuration control document tracking all changes

---

## Key Features

### Exception Handling at the Lowest Level

All exceptions are caught and handled at the point where they occur:

1. **ValidationException** - Thrown by `validatePassword()` when password is null or invalid format
2. **PasswordPolicyException** - Thrown by `validatePassword()` when password fails policy requirements
3. **DefaultPasswordException** - Thrown by `generateDefaultPassword()` or `sendSecureDefaultPassword()` on failures

### Security Measures

- **No Information Leakage**: Generic error messages prevent attackers from learning system details
- **Filename Sanitization**: Prevents directory traversal attacks in email storage
- **Secure Directory Handling**: Creates secure_emails directory only when needed
- **Generic Error Reporting**: IOException and SecurityException caught and reported generically

### Design Improvements

- **Single Responsibility**: PasswordHandler handles all password operations
- **Better Cohesion**: Related functionality grouped in one class
- **Reduced Duplication**: Password validation logic centralized
- **Easier Testing**: Each method can be tested independently

---

## Usage

### Compilation

```bash
cd secure_login_all_cases
javac *.java
```

### Running the Application

```bash
java CoveringAllCasesApp
```

### Example Session

```
Covering All Cases - Password Setup
Enter username: john123
Password must be alphanumeric and include uppercase, lowercase, and a digit (no special characters).
Enter a password: invalid
Password does not meet security requirements. Attempts remaining: 1
Enter a password: Password123
Password accepted.
Account created successfully.
```

---

## PasswordHandler API

### Public Methods

#### `validatePassword(String password)`
Validates a password against security requirements.
- **Throws**: `ValidationException` if null or invalid format
- **Throws**: `PasswordPolicyException` if policy requirements not met
- **Usage**: Called internally by `getUserPassword()`

#### `generateDefaultPassword(int length)`
Generates a secure default password that meets policy requirements.
- **Parameter**: length (minimum 8 characters)
- **Returns**: A policy-compliant password string
- **Throws**: `DefaultPasswordException` if unable to generate after 10 attempts
- **Security**: Ensures at least one uppercase, lowercase, and digit; shuffles for randomness

#### `sendSecureDefaultPassword(String username, String password)`
Sends an encrypted password notification via secure email file.
- **Parameters**: username, password
- **Throws**: `DefaultPasswordException` on IO or security failures
- **Security**: Creates secure_emails directory, prevents directory traversal attacks

#### `getUserPassword(Scanner scanner, int maxAttempts)`
High-level method for getting validated password from user with attempt tracking.
- **Parameters**: Scanner object, maximum attempts (typically 2)
- **Returns**: Validated password string or null if max attempts exceeded
- **Exception Handling**: Catches `ValidationException` and `PasswordPolicyException` internally

#### `getPasswordPolicy()`
Returns the password policy requirements as a user-friendly string.
- **Returns**: Policy description string

---

## Exception Handling Pattern

### Low-Level Exception Handling

```java
// In getUserPassword() - exceptions caught at point of use
try {
    validatePassword(password);
    System.out.println("Password accepted.");
    return password;
} catch (ValidationException | PasswordPolicyException e) {
    // Exception handled at lowest level
    int remaining = maxAttempts - attempt - 1;
    System.out.println("Password does not meet security requirements. Attempts remaining: " + remaining);
}
```

### Main Application Exception Handling

```java
// In CoveringAllCasesApp - generic error message prevents information leakage
try {
    String defaultPw = handler.generateDefaultPassword(12);
    handler.sendSecureDefaultPassword(username, defaultPw);
    System.out.println("A default password has been set and a secure email has been sent to the user.");
} catch (DefaultPasswordException e) {
    // Exception handled at lowest level - no sensitive information leaked
    System.out.println("Account setup encountered a security error. Please contact support.");
}
```

---

## Password Policy Requirements

- **Minimum length**: 8 characters
- **Maximum length**: Unlimited
- **Character types required**: At least one uppercase letter, one lowercase letter, and one digit
- **Allowed characters**: Letters (A-Z, a-z) and digits (0-9) only
- **Special characters**: NOT allowed

### Examples of Valid Passwords
- `Password123`
- `MyPass42Secure`
- `Test1234Password`

### Examples of Invalid Passwords
- `password123` (no uppercase)
- `PASSWORD123` (no lowercase)
- `Password` (no digit)
- `Pass123!` (special character)
- `Pass12` (too short)

---

## Configuration Control

See `CONFIG_CONTROL.md` for detailed version history and changes.

### Current Version
- **Version**: 2.0
- **Date**: March 6, 2026
- **Previous Version**: 1.0

### Key Changes in v2.0
- Centralized password handling in PasswordHandler class
- Custom exceptions for specific error types
- Exception handling at the lowest level
- Secure email storage with filename sanitization
- Generic error messages preventing information leakage

---

## Security Considerations

### Information Leakage Prevention
- User-facing error messages are generic and non-informative
- Specific validation errors are not exposed to users
- File IO errors reported as generic "security error"
- No stack traces displayed to users

### Secure Password Generation
- Uses `SecureRandom` for cryptographic randomness
- Ensures character variety (upper, lower, digit)
- Shuffles password characters to avoid predictable patterns
- Validates generated password meets policy before returning

### File Operations
- Sanitizes usernames to prevent directory traversal
- Creates secure_emails directory with safe permissions
- Email files contain clear security warnings
- Errors creating directories handled gracefully

---

## Testing the Application

### Test Case 1: Valid Password
```
Input: username=testuser, password=ValidPassword123
Expected: "Account created successfully."
```

### Test Case 2: Invalid Passwords Requiring Default
```
Input: username=testuser, password1=invalid, password2=toolow
Expected: "A default password has been set..."
Verify: Secure email file created in secure_emails/
```

### Test Case 3: Null/Empty Password
```
Input: (when applicable through direct method call)
Expected: ValidationException thrown
```

---

## Files Generated at Runtime

When running with failed password attempts:
- **Directory**: `secure_login_all_cases/secure_emails/`
- **File**: `secure_email_[sanitized_username].txt`
- **Content**: Temporary password and instructions

---

## Compliance Notes

### CEN 4078 Requirements Met
✓ Exception handling at the lowest level
✓ Custom exceptions for password policy failure
✓ Custom exceptions for validation failure
✓ Custom exceptions for default password failure
✓ No information leakage to users
✓ Proper headers with version and change comments
✓ Configuration control documentation
✓ Secure software development practices

### Code Quality Standards
✓ Proper naming conventions
✓ Code block segmentation
✓ Appropriate formatting and indentation
✓ Good cohesion and coupling
✓ Minimal code duplication
✓ Fewest lines possible for intended functionality
✓ Standardized headers

---

## Author
- **Original**: Olivia Bunch
- **Version 2.0**: March 6, 2026

## License
CEN 4078 Secure Software Development Course Project
