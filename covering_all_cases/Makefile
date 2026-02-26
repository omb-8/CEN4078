# Makefile for the covering_all_cases module
.PHONY: build run clean
.DEFAULT_GOAL := run

JAVA_FILES := $(wildcard *.java)
CLASS_FILES := $(patsubst %.java,%.class,$(JAVA_FILES))

# Ensure shared Validator is compiled first
build: ../shared/Validator.class $(CLASS_FILES)

../shared/Validator.class:
	javac ../shared/Validator.java

%.class: %.java
	javac -cp .. $<

# Default `make` will start the application interactively so the user can
# enter username/password at the command line. Do not pipe stdin if you want
# an interactive session; run `make` and interact directly.
run: build
	java -cp .:.. CoveringAllCasesApp

clean:
	-rm -f $(CLASS_FILES) covering_all_cases_secure_email_*.txt
