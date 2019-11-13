package tests.testers;

import tests.Tester;

public class LZSSTester {

    private Tester tester;

    public LZSSTester()
    {
        this.tester = new Tester();
    }

    public void testVoid() 
    {
       tester.test("void","LZSS");
    } 

    public void testLine()
    {
        tester.test("line","LZSS");

    }

    public void testAscii()
    {
        tester.test("ascii","LZSS");
    }

    public void test4kb()
    {
        tester.test("4kb","LZSS");
    }

    public void test8kb()
    {
        tester.test("8kb","LZSS");
    }

}