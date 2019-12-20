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
       tester.test("void.txt","LZW");
    } 

    public void testLine()
    {
        tester.test("line.txt","LZW");
    }

    public void testChars()
    {
        tester.test("chars.txt","LZW");
    }

    public void test4kb()
    {
        tester.test("4kb.txt","LZW");
    }

    public void test8kb()
    {
        tester.test("8kb.txt","LZW");
    }

    public void test6mb()
    {
        tester.test("6mb.txt","LZW");
    }

    public void test15mb()
    {
        tester.test("15mb.txt","LZW");
    }

    public void testCatala()
    {
        tester.test("catala.txt","LZW");
    }

    public void testEspanol()
    {
        tester.test("espanol.txt","LZW");
    }

    public void testEnglish()
    {
        tester.test("english.txt","LZW");
    }

    public void testTxtFolder()
    {
        tester.test("txt_folder","LZW");
    }

    public void testMixedFolder()
    {
        tester.test("mixed_folder","LZW");
    }
}