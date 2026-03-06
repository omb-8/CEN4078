#
# CEN4078 Secure Software Development
# Makefile
#
# Runs the program with "make" and "make clean"
#
# @author Olivia Bunch
# @version: 2.0
#

.PHONY: build run run-secure run-covering clean
.DEFAULT_GOAL := run

JAVA_FILES := $(wildcard *.java)
CLASS_FILES := $(patsubst %.java,%.class,$(JAVA_FILES))

build: $(CLASS_FILES)
	@echo "Build complete. All classes compiled."

%.class: %.java
	javac $<

run: build
	@echo "Running CoveringAllCasesApp with exception handling..."
	java CoveringAllCasesApp
	@echo "\nRunning SecureLogin..."
	java SecureLogin

run-secure: build
	@echo "Running SecureLogin..."
	java SecureLogin

run-covering: build
	java CoveringAllCasesApp

clean:
	-rm -f *.class covering_all_cases_secure_email_*.txt
	-rm -rf secure_emails/
	@echo "Cleaned all generated files and directories."
