package tests.testers;

import tests.Tester;

public class JPEGTester {
    private Tester tester;

    public JPEGTester(){ this.tester= new Tester();}

    public void test1_4M() {
        tester.testJPEG("1_4MB.ppm");
    }
    public void testN8() {
        tester.testJPEG("N8.ppm");
    }
    public void test6M() {
        tester.testJPEG("6M.ppm");
    }
}