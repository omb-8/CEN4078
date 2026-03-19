#
# CEN4078 Secure Software Development
# Makefile
#
# Runs the program with "make" and "make clean"
#
# @author Olivia Bunch
# @version: 1.0
#

JAVAC = javac
JAVA = java

MAIN = Main

all:
	$(JAVAC) Main.java FlawsToFixes.java InvalidInputException.java DivisionByZeroException.java
	$(JAVA) $(MAIN)

run: all

clean:
	rm -f *.class