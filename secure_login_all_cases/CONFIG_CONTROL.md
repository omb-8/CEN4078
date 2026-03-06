# Configuration Control Document

## Project: Secure Login - All Cases with Exception Handling
## Module: secure_login_all_cases
## Version: 2.0
## Date: March 6, 2026
## Time: Current Session
## Previous Version: 1.0 (January/February 2026)

---

## Changes Made in Version 2.0

### 1. Created PasswordHandler Class
- **File:** PasswordHandler.java (new file)
- **Change:** New centralized password handling class with the following methods:
  - `validatePassword(String password)`: Validates password and throws ValidationException or PasswordPolicyException at the lowest level
  - `generateDefaultPassword(int length)`: Generates secure default password, throws DefaultPasswordException
  - `sendSecureDefaultPassword(String username, String password)`: Sends password via secure email file, throws DefaultPasswordException
  - `getUserPassword(java.util.Scanner scanner, int maxAttempts)`: High-level method for user password input with attempt handling
  - `getPasswordPolicy()`: Returns password policy requirements
  - Helper method `generateRandomPassword(int length)`: Generates random password ensuring character variety
  - Helper method `sanitizeFilename(String filename)`: Prevents directory traversal attacks
- **Reason:** Required by CEN 4078 Exercise for exception handling at the lowest level. Centralizes all password operations to ensure consistent security practices and prevent information leakage.
- **Requirement:** Create a password handler class that handles validation, policy checking, default password generation, and implements exceptions at the lowest level.
- **Security Improvements:**
  - All exceptions caught at the lowest level with generic error messages
  - No sensitive information leaked to users or attackers
  - Secure password generation with guaranteed character variety
  - Filename sanitization to prevent directory traversal attacks

### 2. Created Custom Exception Classes
- **Files:**
  - PasswordPolicyException.java (new file)
  - ValidationException.java (new file)
  - DefaultPasswordException.java (new file)
- **Change:** Three new exception classes for specific error handling:
  - `PasswordPolicyException`: Thrown when password doesn't meet policy requirements
  - `ValidationException`: Thrown when password validation fails
  - `DefaultPasswordException`: Thrown when default password generation or delivery fails
- **Reason:** Required by CEN 4078 Exercise for formalized exception handling. Each exception is caught and handled at the lowest level, providing specific error context without leaking sensitive information.
- **Requirement:** Implement exceptions to handle password policy failure, password validation failure, and default password failure.

### 3. Updated CoveringAllCasesApp.java
- **File:** CoveringAllCasesApp.java
- **Change:**
  - Replaced direct calls to PasswordPolicy, DefaultPassword, and manual validation with PasswordHandler
  - Now uses `handler.getUserPassword()` for password input validation
  - Now uses `handler.generateDefaultPassword()` and `handler.sendSecureDefaultPassword()` for default password handling
  - Added try-catch block for DefaultPasswordException with generic error message
  - Version updated from 1.0 to 2.0
  - Added change comment indicating this is refactored version
- **Reason:** To integrate centralized password handling with proper exception handling at the lowest level. Ensures all password operations follow secure practices.
- **Requirement:** Change password handling process to use the password handler class with proper exception handling.
- **Before:** Manual validation loop with direct calls to multiple classes
- **After:** Centralized through PasswordHandler with proper exception handling ensuring no information leakage

### 4. Updated Validator.java
- **File:** Validator.java
- **Change:** 
  - Updated version from 1.0 to 2.0
  - Added change comment indicating it's used by PasswordHandler
  - No functional code changes; file kept as foundation for validation logic
- **Reason:** Part of version 2.0 release for configuration control consistency.

### 5. Updated PasswordPolicy.java
- **File:** PasswordPolicy.java
- **Change:**
  - Updated version from 1.0 to 2.0
  - Added change comment indicating integration with PasswordHandler
  - No functional code changes; file kept as policy definition
- **Reason:** Part of version 2.0 release for configuration control consistency. Policy definition maintained separately for clarity.

### 6. Updated DefaultPassword.java
- **File:** DefaultPassword.java
- **Change:**
  - Updated version from 1.0 to 2.0
  - Added change comment noting functionality moved to PasswordHandler
  - Code retained for backward compatibility
- **Reason:** Functionality now centralized in PasswordHandler, but kept for potential backward compatibility.

### 7. Updated SecureLogin.java
- **File:** SecureLogin.java
- **Change:**
  - Updated version from 1.0 to 2.0
  - Added change comment about compatibility with PasswordHandler
  - No functional code changes
- **Reason:** Version update for consistency with release 2.0.

### 8. Updated Cryptographer.java
- **File:** Cryptographer.java
- **Change:**
  - Updated version from 1.0 to 2.0
  - No functional code changes
- **Reason:** Version update for consistency with release 2.0.

---

## Key Improvements in Version 2.0

### Exception Handling at the Lowest Level
- All exceptions are caught and handled where they occur
- Validation exceptions thrown from `validatePassword()` method
- Policy exceptions thrown from policy checking
- Default password exceptions thrown from generation and delivery methods
- Exception handlers in main application provide generic messages to prevent information leakage

### Security Enhancements
- Centralized password handling ensures consistent security practices
- Filename sanitization prevents directory traversal attacks
- Exception messages do not leak security-sensitive information
- Secure directory creation for email storage
- Generic error messages to users ("security error, contact support") instead of specific details

### Error Handling Without Information Leakage
- IOException and SecurityException caught separately but reported generically
- No stack traces or specific error details shown to users
- All validation errors report the same generic message from exceptions

### Code Quality Improvements
- Single Responsibility Principle: PasswordHandler handles all password operations
- Better cohesion: Related functionality grouped in one class
- Easier testing: Can test password validation, generation, and delivery independently
- Reduced code duplication: Password validation logic centralized

---

## Testing Considerations

### Test Cases for PasswordHandler
1. Valid password input - should be accepted
2. Null password - should throw ValidationException
3. Invalid format (too short, missing uppercase/lowercase/digit) - should throw PasswordPolicyException
4. Default password generation - should generate policy-compliant password
5. Default password after max retries - should throw DefaultPasswordException
6. Email file creation - should create secure email file
7. Cannot write to filesystem - should throw DefaultPasswordException with generic message

### Backward Compatibility
- Existing functionality maintained
- Old classes kept but refactored to use PasswordHandler
- No breaking changes to public interfaces

---

## Deployment Notes

### Files to Deploy
- PasswordHandler.java (new)
- PasswordPolicyException.java (new)
- ValidationException.java (new)
- DefaultPasswordException.java (new)
- CoveringAllCasesApp.java (modified)
- Validator.java (version updated)
- PasswordPolicy.java (version updated)
- DefaultPassword.java (version updated)
- SecureLogin.java (version updated)
- Cryptographer.java (version updated)

### Directory Structure Required
- secure_emails/ directory will be created automatically by PasswordHandler if it doesn't exist

### Compilation Command
```
javac secure_login_all_cases/*.java
```

### Execution Command
```
java -cp secure_login_all_cases CoveringAllCasesApp
```

---

## Version History

| Version | Date | Author | Description |
|---------|------|--------|-------------|
| 1.0 | Jan-Feb 2026 | Olivia Bunch | Initial covering all cases implementation |
| 2.0 | Mar 6, 2026 | Olivia Bunch | Added PasswordHandler with exception handling at lowest level |

---

## Sign-Off
- **Modified By:** Olivia Bunch
- **Date:** March 6, 2026
- **Reason:** Exercise requirement - Exception handling at the lowest level
- **Status:** Ready for testing and deployment
