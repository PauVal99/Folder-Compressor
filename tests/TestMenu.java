package tests;

import java.io.Console;
import tests.unit.controllers.algorithms.*;

public class TestMenu
{
    private Console console;

    public void start()
    {
        System.out.print("Wellcome to compressor/decompressor test menu. Please select an operation:\n 1.Run All Tests\n 2.Run unit Tests\n 3.Run functional Tests\n");
        this.console = System.console();
        String operation = this.console.readLine();
        if(operation.equals("1")) this.runAllTests();
        else if(operation.equals("2")) this.runUnitTests();
        else if(operation.equals("3")) this.runFunctionalTests();
        else{ System.out.print("Please enter a valid operation.\n"); this.start();}
    }

    private void runAllTests()
    {
        this.runUnitTests();
        this.runFunctionalTests();
    }

    
    private void runUnitTests()
    {
        LZWtest lzwt = new LZWtest();
        lzwt.testCompressVoidText();
        lzwt.testCompressText();
        lzwt.testCompressEnterText();
        lzwt.testCompressLargeText();
        lzwt.testDecompressVoidText();
        lzwt.testDecompressText();
        lzwt.testDecompressEnterText();
        lzwt.testDecompressLargeText();
    }

    private void runFunctionalTests()
    {

    }
}