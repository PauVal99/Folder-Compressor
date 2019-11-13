package tests.testers;

import tests.Tester;

public class LZWTester {

    private Tester tester;

    public LZWTester()
    {
        this.tester = new Tester();
    }

    public void testVoid() 
    {
       tester.test("void","LZW");
    } 

    public void testLine()
    {
        tester.test("line","LZW");

    }

    public void testAscii()
    {
        tester.test("ascii","LZW");
    }

    public void test4kb()
    {
        tester.test("4kb","LZW");
    }

    public void test8kb()
    {
        tester.test("8kb","LZW");
    }

}