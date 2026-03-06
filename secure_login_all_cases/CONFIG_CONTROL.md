# Configuration Control Document

## Project: Secure Login - All Cases with Exception Handling
## Module: secure_login_all_cases
## Version: 2.0
## Date: March 6, 2026
## Previous Version: 1.0 (January/February 2026)

---

## Changes Made in Version 2.0

### 1. Created PasswordHandler Class
- File: PasswordHandler.java (new file)
  - `validatePassword(String password)`: Validates password and throws ValidationException
  - `generateDefaultPassword(int length)`: Generates secure default password, throws DefaultPasswordException
  - `sendSecureDefaultPassword(String username, String password)`: Sends password via secure email file
  File: DefaultPasswordException (new file)
  - `getUserPassword(java.util.Scanner scanner, int maxAttempts)`: High-level method for user password input

### 2. Created Custom Exception Classes
- File: PasswordPolicyException.java (new file)
- File: ValidationException.java (new file)
- File: DefaultPasswordException.java (new file)

### 3. Updated CoveringAllCasesApp.java
- File: CoveringAllCasesApp.java
  - Now uses `handler.getUserPassword()` for password input validation
  - Now uses `handler.generateDefaultPassword()` and `handler.sendSecureDefaultPassword()` for default password handling