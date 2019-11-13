package tests.testers;

import tests.Tester;

public class LZ78Tester {

    private Tester tester;

    public LZ78Tester()
    {
        this.tester = new Tester();
    }

    public void testVoid() 
    {
       tester.test("void","LZ78");
    } 

    public void testLine()
    {
        tester.test("line","LZ78");

    }

    public void testAscii()
    {
        tester.test("ascii","LZ78");
    }

    public void test4kb()
    {
        tester.test("4kb","LZ78");
    }

    public void test8kb()
    {
        tester.test("8kb","LZ78");
    }

}