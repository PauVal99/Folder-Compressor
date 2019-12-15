package tests.testers;

import tests.Tester;

public class JPEGTester {
    private Tester tester;

    public JPEGTester(){ this.tester= new Tester();}

    public void test1_4M() {
        tester.test("1_4MB.ppm","JPEG");
    }
    public void NOdiv64() {
        tester.test("NOdiv64.ppm","JPEG");
    }
    public void test6M() {
        tester.test("6M.ppm","JPEG");
    }

}