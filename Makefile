compile:
	find -name "*.java" > classes.txt
	javac -g -cp AbsoluteLayout.jar:lib/junit-4.13-rc-1.jar:lib/hamcrest-core-1.3.jar:. @classes.txt -d bin
	rm classes.txt

run:
	java -cp bin src.Main

runConsole:
	java -cp bin src.Console

runDriver:
	java -cp bin tests.Main

runJUnit:
	java -cp lib/junit-4.13-rc-1.jar:lib/hamcrest-core-1.3.jar:bin:. org.junit.runner.JUnitCore tests.JUnit.ByteArrayHelperTest

clean:
	rm -Rf bin/*
