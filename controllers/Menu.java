package controllers;

import java.io.Console;
import models.*;
import controllers.Compressor;
import tests.TestMenu;
import java.util.*;

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
        UncompressedFile uncompressedCompress = new UncompressedFile(this.console.readLine("Please enter the file path: "));
        String destinationPath = this.console.readLine("Please enter the destination path: ");
        String[] possibleAlgorithms = {"LZ78", "LZSS", "LZW", "JPEG", "auto"};
        String algorithm = this.console.readLine("Especify the algorithm (LZ78, LZSS, LZW, JPEG, auto): ");
        while(!Arrays.asList(possibleAlgorithms).contains(algorithm)) algorithm = this.console.readLine("Especify the algorithm (LZ78, LZSS, LZW, JPEG, auto): ");
        
        Compressor compressor = new Compressor(uncompressedCompress,destinationPath,algorithm);
        compressor.compress();
    }

    
    private void decompress()
    {
        CompressedFile compressedFile = new CompressedFile(this.console.readLine("Please enter the file path: "));
        String destinationFolder = this.console.readLine("Please enter the destination folder: ");
        Decompressor decompressor = new Decompressor();
        decompressor.decompressFile(compressedFile,destinationFolder);
    }

    private void runTest()
    {
        TestMenu testMenu = new TestMenu();
        testMenu.start();
    }
}