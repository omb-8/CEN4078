# Top-level Makefile: build and run both modules together
.PHONY: build run clean
.DEFAULT_GOAL := run

JAVAC = javac
JAVA = java

all: build

build:
	$(JAVAC) shared/*.java secure_login/*.java covering_all_cases/*.java || true

run: build
	@echo "Running SecureLogin (secure_login)..."
	$(JAVA) -cp secure_login:. SecureLogin
	@echo "\nRunning CoveringAllCasesApp (covering_all_cases)..."
	$(JAVA) -cp covering_all_cases:. CoveringAllCasesApp

clean:
	-find . -name "*.class" -delete
	-rm -f covering_all_cases_secure_email_*.txt
