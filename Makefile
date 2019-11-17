#javac -cp <junit-jar-file>;. TestClass1.java TestClass2.java
#javac -cp junit-4.12.jar;. UserDAOTest.java ProductDAOTest.java
#java -cp <junit-jar>;<hamcrest-jar>;. org.junit.runner.JUnitCore  TestClass1 TestClass2

compile:
        find src -name "*.java" > src.txt
        javac @sources.txt -d bin
        javac -cp junit-4.13-rc-1.jar;. tests/JUnit/ByteArrayHelperTest.java -d bin

run:
        java src.Main
