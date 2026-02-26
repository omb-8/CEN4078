Covering All Cases - Programming Exercise

Build & Run (command line):

Compile:

```bash
javac *.java
```

Run:

```bash
java CoveringAllCasesApp
```

Notes:
- The program allows two attempts to create a password meeting the policy.
- If both attempts fail, a default secure password is generated and written to
  a file named `covering_all_cases_secure_email_<username>.txt` to simulate
  a secure email delivery. The password is NOT printed to stdout.
