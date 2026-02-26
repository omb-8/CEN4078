# Configuration Control Document

## Project: Input Validation Module
## Version: 1.1
## Date: January 25, 2026
## Time: 6:36pm

### Changes Made

#### 1. Added Validation Class
- **File:** Validation.java (new file)
- **Change:** Created a new class `Validation` with three methods and one helper method:
  - `checkSQLInjection(String input)`: Checks for forbidden characters / - ; "
  - `checkPasswordPolicy(String password)`: Enforces 8-12 chars, at least one upper, lower, numeric
  - `checkIntegerOverflow(String input)`: Validates input as integer within int range
  - `checkMFALength(String mfa)`: Checks if MFA is exactly 10 digits
- **Reason:** Required by CEN 4078 Exercise for input validation and type checking.
- **Requirement:** Create a class to perform validation with three methods for SQL Injection, Password Policy, and Integer Overflow.

#### 2. Modified Credentials Storage
- **File:** credentials.txt
- **Change:** Updated format from `username:password` to `username:password:mfa` with 10-digit MFA codes.
- **Reason:** To support Multi-Factor Authentication as per requirements.
- **Requirement:** Create a variable for each user as a Multi-Factor Authentication (10-digit number).

#### 3. Updated LoginApp.java
- **File:** LoginApp.java
- **Change:** 
  - Added `ArrayList<Integer> mfas` to store MFA codes.
  - Modified `authenticate()` method to validate username with SQL Injection, password with SQL Injection and Password Policy, and MFA with Integer Overflow and 10-digit check.
  - Updated `loadCredentials()` to parse three parts and store MFA as int.
  - Updated `initializeCredentialsFile()` to include MFA in default credentials.
- **Reason:** To implement secure input validation and MFA as required.
- **Requirement:** Validate username with SQL Injection, password with SQL Injection and Password Policy, MFA with Integer Overflow. Handle failures securely.

#### 5. Adjusted Credentials for Password Policy Compliance
- **File:** credentials.txt
- **Change:** Updated passwords to be 8-12 characters long: scientist:Secure123!, engineer:Eng456@, security:Sec789#.
- **Reason:** Original passwords exceeded the 12-character limit, causing login failures.
- **Requirement:** Password policy requires 8-12 characters.

#### 7. Fixed Scanner Issue in getHiddenPassword
- **File:** LoginApp.java
- **Change:** Modified getHiddenPassword to accept a Scanner parameter to avoid multiple Scanner instances on System.in, which caused NoSuchElementException in piped input.
- **Reason:** To ensure the application works correctly when input is piped for testing.
- **Requirement:** Handle input correctly in all environments.

### Previous Version: 1.0
- Basic login functionality with username/password authentication.
- No input validation or MFA.

### Notes
- All changes comply with secure software development practices, including secure error handling to prevent information leakage.
- Code is written in Java.
- No changes were made to previous code from login_module; this is an enhancement based on the baseline.
- Autocomplete tool Github Copilot assisted in creating this document.

### 8. Secure Login Module Updates
- **Files:** secure_login/Cryptographer.java, secure_login/SecureLogin.java
- **Date:** February 20, 2026
- **Version:** 1.2
- **Change:**
  - Implemented a `Cryptographer` class that provides alphabetic and numeric Vigenère encryption/decryption per the exercise requirements.
  - Fixed numeric Vigenère math so encryption uses `(p + k) mod 10` and decryption uses `(c - k + 10) mod 10`.
  - Added combined `encrypt(String)` and `decrypt(String)` methods that apply alphabetic and numeric keys per-character.
  - Updated `SecureLogin.java` to validate usernames (alphanumeric only) and passwords (must contain at least one uppercase, one lowercase, and one digit), store ciphertext produced by `Cryptographer.encrypt()`, and verify authentication by comparing ciphertexts.
  - Added minimal non-revealing self-tests to check encryption/decryption correctness.
- **Reason:** Bring the `secure_login` module into compliance with assignment requirements: use `ARGOSROCK` as the alphabetic key and `1963` as the numeric key, provide required encryption/decryption methods, and ensure secure input validation and storage of ciphertext.
- **Notes:** Changes include code comments indicating edits. No plaintext secrets are printed; test outputs are non-revealing pass/fail indicators.

### 9. Covering All Cases Module (new)
- **Files:** covering_all_cases/CoveringAllCasesApp.java, covering_all_cases/PasswordPolicy.java, covering_all_cases/DefaultPassword.java, covering_all_cases/README.md
- **Date:** February 26, 2026
- **Time:** 14:20
- **Version:** 1.0
- **Change:** Added new module implementing the "Programming Exercise – Covering All Cases". The module:
  - Provides `PasswordPolicy.getPasswordPolicy()` which returns the human-readable password policy.
  - Uses `PasswordPolicy.validatePassword()` to check passwords.
  - Allows the user two attempts to create a valid password; if both attempts fail the system uses `DefaultPassword` (assignment asked for class named `default-password`; implemented as `DefaultPassword` because hyphens are invalid in Java identifiers) to generate a random secure default password that meets the policy.
  - `DefaultPassword.sendSecureEmail(username, password)` simulates secure delivery by writing a file named `covering_all_cases_secure_email_<username>.txt` containing the default password. The program does not print the password to stdout.
  - Console messages are intentionally non-revealing to avoid leaking extra information to attackers.
- **Reason:** Implement assignment requirement to cover unhandled cases by using secure defaults and to demonstrate configuration control and documentation of changes.
- **Requirement:** Two attempts for password creation; fallback to default password generator; method returning password policy; use secure handling of defaults and notifications.

### 10. Encrypted Credentials Storage for LoginApp
- **Files:** input_validation/LoginApp.java
- **Date:** February 26, 2026
- **Time:** 15:10
- **Version:** 1.2
- **Change:** Updated `LoginApp` to store credentials encrypted at rest using `secure_login/Cryptographer`. The `initializeCredentialsFile()` method now writes encrypted `username:password:mfa` entries. The `loadCredentials()` method attempts to decrypt stored values and falls back to plaintext if decryption does not produce valid credentials (for backward compatibility with older unencrypted files).
- **Reason:** Improve security by encrypting stored credentials while preserving compatibility and avoiding data loss.
- **Requirement:** Encrypt credentials on disk; ensure runtime behavior and validation remain unchanged; maintain secure error handling and no leakage of plaintext secrets to stdout.

