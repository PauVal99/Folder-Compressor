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
       tester.test("void.txt","LZSS");     
    } 

    public void testLine()
    {
        tester.test("line.txt","LZSS");
    }

    public void testChars()
    {
        tester.test("chars.txt","LZSS");
    }

    public void test4kb()
    {
        tester.test("4kb.txt","LZSS");
    }

    public void test8kb()
    {
        tester.test("8kb.txt","LZSS");
    }

    public void test6mb()
    {
        tester.test("6mb.txt","LZSS");
    }

    public void test15mb()
    {
        tester.test("15mb.txt","LZSS");
    }

    public void testCatala()
    {
        tester.test("catala.txt","LZSS");
    }

    public void testEspanol()
    {
        tester.test("espanol.txt","LZSS");
    }

    public void testEnglish()
    {
        tester.test("english.txt","LZSS");
    }

    public void testTxtFolder()
    {
        tester.test("txt_folder","LZSS");
    }

    public void testMixedFolder()
    {
        tester.test("mixed_folder","LZSS");
    }
}