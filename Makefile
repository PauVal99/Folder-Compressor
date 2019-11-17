#javac -cp <junit-jar-file>;. TestClass1.java TestClass2.java
#javac -cp junit-4.12.jar;. UserDAOTest.java ProductDAOTest.java
#java -cp <junit-jar>;<hamcrest-jar>;. org.junit.runner.JUnitCore  TestClass1 TestClass2

compile:
        find src -name "*.java" > classes.txt
        javac @sources.txt -d bin
        find tests -name "*.java" > classes.txt
        javac -cp lib/junit-4.13-rc-1.jar;lib/hamcrest-core-1.3.jar;. @sources.txt -d bin
        rm classes.txt

run:
        java -cp bin src.Main

runDriver:
        java -cp bin tests.Main

runJUnit:
        java -cp lib/junit-4.13-rc-1.jar;lib/hamcrest-core-1.3.jar;bin. org.junit.runner.JUnitCore tests.JUnit.ByteArrayHelperTest

clean:
        rm -Rf bin
