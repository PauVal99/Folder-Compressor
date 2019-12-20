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
       tester.test("void.txt","LZ78");
    } 

    public void testLine()
    {
        tester.test("line.txt","LZ78");
    }

    public void testChars()
    {
        tester.test("chars.txt","LZ78");
    }

    public void test4kb()
    {
        tester.test("4kb.txt","LZ78");
    }

    public void test8kb()
    {
        tester.test("8kb.txt","LZ78");
    }

    public void test6mb()
    {
        tester.test("6mb.txt","LZ78");
    }

    public void test15mb()
    {
        tester.test("15mb.txt","LZ78");
    }

    public void testCatala()
    {
        tester.test("catala.txt","LZ78");
    }

    public void testEspanol()
    {
        tester.test("espanol.txt","LZ78");
    }

    public void testEnglish()
    {
        tester.test("english.txt","LZ78");
    }

    public void testTxtFolder()
    {
        tester.test("txt_folder","LZ78");
    }

    public void testMixedFolder()
    {
        tester.test("mixed_folder","LZ78");
    }
}