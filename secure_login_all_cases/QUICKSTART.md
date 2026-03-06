# QUICK START GUIDE

## Exercise Implementation: Exception Handling at the Lowest Level

### What Was Done

This implementation fulfills all CEN 4078 requirements by:

✓ Creating a centralized **PasswordHandler** class for all password operations
✓ Implementing **three custom exception classes** for specific error handling:
  - PasswordPolicyException - for policy violations
  - ValidationException - for validation failures
  - DefaultPasswordException - for default password generation/delivery failures
✓ Handling **all exceptions at the lowest level** where they occur
✓ Preventing **information leakage** with generic error messages
✓ Maintaining **backward compatibility** with existing code
✓ Documenting all changes in **configuration control document**
✓ Proper headers, versioning, and code organization

### Compiling the Project

```bash
cd /workspaces/CEN4078/secure_login_all_cases

# Option 1: Using Makefile (recommended)
make build

# Option 2: Using javac directly
javac *.java
```

### Running the Application

```bash
# Using Makefile
make run-covering

# Using Java directly
java CoveringAllCasesApp

# Interactive Example:
# Enter username: testuser
# Password must be alphanumeric and include uppercase, lowercase, and a digit (no special characters).
# Enter a password: Password123
# Password accepted.
# Account created successfully.
```

### Files to Submit

**New Files:**
- PasswordHandler.java
- PasswordPolicyException.java
- ValidationException.java
- DefaultPasswordException.java
- CONFIG_CONTROL.md
- README_EXERCISE.md

**Modified Files:**
- CoveringAllCasesApp.java ← Uses PasswordHandler
- Validator.java ← Version 2.0
- PasswordPolicy.java ← Version 2.0
- DefaultPassword.java ← Version 2.0
- SecureLogin.java ← Version 2.0
- Cryptographer.java ← Version 2.0
- Makefile ← Updated for v2.0

**Unchanged Files (for reference):**
- All other existing files

### Key Design Decisions

**1. Lowest Level Exception Handling**
- Exceptions caught where they occur (in PasswordHandler methods)
- Main application calls caught with generic error messages
- No information leakage to users or potential attackers

**2. Custom Exceptions**
- PasswordPolicyException: For policy requirement failures
- ValidationException: For format/null validation failures
- DefaultPasswordException: For generation and delivery failures

**3. Security Measures**
- Filename sanitization prevents directory traversal
- Generic error messages ("security error") prevent information leakage
- Secure random generation for default passwords
- Proper exception handling without stack traces

**4. Code Organization**
- PasswordHandler: Lowest-level validation and generation
- CoveringAllCasesApp: High-level orchestration with generic error handling
- Supporting classes: Validator, PasswordPolicy, DefaultPassword kept for modularity

### Testing Scenarios

**Test 1: Valid Password**
```bash
echo -e "user1\nValidPassword123\n" | java CoveringAllCasesApp
# Expected: Account created successfully
```

**Test 2: Invalid Passwords (triggers default)**
```bash
echo -e "user2\ninvalid\ntoosmall\n" | java CoveringAllCasesApp
# Expected: Default password generated and secure email created
```

**Test 3: Verify Secure Email**
```bash
cat secure_emails/secure_email_user2.txt
# Shows temporary password and instructions
```

### Documentation

- **CONFIG_CONTROL.md**: Detailed version history and changes
- **README_EXERCISE.md**: Complete implementation guide and API reference
- **This file**: Quick reference and testing guide

### Version Information

- **Version**: 2.0
- **Date**: March 6, 2026
- **Previous Version**: 1.0
- **Status**: Ready for deployment

### Compliance Checklist

✓ Exception handling at the lowest level
✓ Custom exceptions for password policy failure
✓ Custom exceptions for password validation failure
✓ Custom exceptions for default password failure
✓ No information leakage (generic error messages)
✓ Secure software development practices
✓ Proper code headers with versioning
✓ Configuration control documentation
✓ Commented code changes
✓ Single Responsibility Principle
✓ Reduced code duplication
✓ Proper naming conventions
✓ Good code organization and formatting

### Support

For detailed API documentation, see: README_EXERCISE.md
For version history and changes, see: CONFIG_CONTROL.md
