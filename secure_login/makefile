# CEN 4078 Secure Software Development
# Java Makefile

JAVAC = javac
JAVA = java
TARGET = SecureLogin

SRC = SecureLogin.java Cryptographer.java Validator.java

all: build run

build:
	$(JAVAC) $(SRC)

run:
	$(JAVA) -cp . $(TARGET)

clean:
	rm -f *.class

.PHONY: all build run clean