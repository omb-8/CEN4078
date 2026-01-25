# Input Validation Module

A Java-based login application with secure authentication, input validation, and multi-factor authentication (MFA).

## Overview

This login module provides a secure authentication system for users with predefined roles (scientist, engineer, security). It includes input validation to prevent SQL injection, enforce password policies, and prevent integer overflow. Multi-factor authentication is implemented using 10-digit numeric codes.

## Features

- Username validation: Checks for SQL injection characters (/, -, ;, ")
- Password validation: SQL injection check + policy (8-12 chars, upper, lower, numeric)
- MFA validation: 10-digit number, integer overflow check
- Secure error handling: Generic failure messages to prevent information leakage
- Account lockout after 3 failed attempts

## How to Run

### 1. Compile

```bash
javac LoginApp.java Main.java Validation.java
```

### 2. Run

```bash
java Main
```

### 3. Test Credentials

Use the following credentials to test the application:

Username  | Password     | MFA
scientist | Secure123!   | 1234567890
engineer  | Eng456@      | 0987654321
security  | Sec789#      | 1122334455

To log out for any user, enter 1 and press 'Enter'.
