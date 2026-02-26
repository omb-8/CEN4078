.PHONY: build run run-secure run-covering clean
.DEFAULT_GOAL := run

JAVA_FILES := $(wildcard *.java)
CLASS_FILES := $(patsubst %.java,%.class,$(JAVA_FILES))

build: $(CLASS_FILES)

%.class: %.java
	javac $<

run: build
	@echo "Running SecureLogin..."
	java SecureLogin
	@echo "\nRunning CoveringAllCasesApp..."
	java CoveringAllCasesApp

run-secure: build
	java SecureLogin

run-covering: build
	java CoveringAllCasesApp

clean:
	-rm -f *.class covering_all_cases_secure_email_*.txt
