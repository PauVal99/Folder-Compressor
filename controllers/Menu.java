package controllers;

import java.io.Console;
import models.File;
import controllers.Compressor;
import tests.TestMenu;

public class Menu
{
    private Console console;

    public void start()
    {
        System.out.print("Wellcome to compressor/decompressor. Please select an operation:\n 1.Compress\n 2.Uncompress\n 3.Run tests\n");
        this.console = System.console();
        String operation = this.console.readLine();
        if(operation.equals("1")) this.compress();
        else if(operation.equals("2")) this.decompress();
        else if(operation.equals("3")) this.runTest();
        else{ System.out.print("Please enter a valid operation.\n"); this.start();}
    }

    private void compress()
    {
        File fileToCompress = new File();
        fileToCompress.setPath(this.console.readLine("Please enter the file path: "));
        String destinationPath = this.console.readLine("Please enter the destination path: ");
        Compressor compressor = new Compressor();
        compressor.compressFile(fileToCompress,destinationPath);
    }

    
    private void decompress()
    {
        File fileToDecompress = new File();
        fileToDecompress.setPath(this.console.readLine("Please enter the file path: "));
        String destinationPath = this.console.readLine("Please enter the destination path: ");
        Decompressor decompressor = new Decompressor();
        decompressor.decompressFile(fileToDecompress,destinationPath);
    }

    private void runTest()
    {
        TestMenu testMenu = new TestMenu();
        testMenu.start();
    }
}