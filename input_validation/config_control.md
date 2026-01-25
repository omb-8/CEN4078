# Configuration Control Document

## Project: Input Validation Module
## Version: 1.1
## Date: January 25, 2026
## Time: 12:00 PM

### Changes Made

#### 1. Added Validation Class
- **File:** Validation.java (new file)
- **Change:** Created a new class `Validation` with three methods:
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

#### 4. Updated README.md
- **File:** README.md
- **Change:** Added description of new features, updated compile/run instructions to include Validation.java, added MFA column to test credentials table.
- **Reason:** To document the enhanced functionality.
- **Requirement:** Provide clear instructions for running the updated program.

### Previous Version: 1.0
- Basic login functionality with username/password authentication.
- No input validation or MFA.

### Notes
- All changes comply with secure software development practices, including secure error handling to prevent information leakage.
- Code is written in Java, as chosen for this exercise.
- No changes were made to previous code from login_module; this is an enhancement based on the baseline.