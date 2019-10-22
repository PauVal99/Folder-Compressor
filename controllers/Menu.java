package controllers;

import java.io.Console;
import java.io.File;
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
        UncompressedFile uncompressedFile = new UncompressedFile(this.console.readLine("Please enter the file path: "));
        String destinationFolder = this.console.readLine("Please enter the destination folder: ");
        String compressedName = this.console.readLine("Please enter the name of the compressed file (by default is the same name): ");
        if(compressedName.equals("")){
            String name = uncompressedFile.getName();
            int pos = name.lastIndexOf(".");
            if (pos > 0) {
                compressedName = name.substring(0, pos);
            }
        }
        String destinationPath = destinationFolder + File.separator + compressedName;
        String[] possibleAlgorithms = {"LZ78", "LZSS", "LZW", "JPEG", "auto"};
        String algorithm = this.console.readLine("Especify the algorithm (LZ78, LZSS, LZW, JPEG, auto): ");
        while(!Arrays.asList(possibleAlgorithms).contains(algorithm)) algorithm = this.console.readLine("Especify the algorithm (LZ78, LZSS, LZW, JPEG, auto): ");
        
        Compressor compressor = new Compressor(uncompressedFile,destinationPath,algorithm);
        compressor.compress();
    }

    
    private void decompress()
    {
        CompressedFile compressedFile = new CompressedFile(this.console.readLine("Please enter the file path: "));
        String destinationFolder = this.console.readLine("Please enter the destination folder: ");
        Decompressor decompressor = new Decompressor(compressedFile,destinationFolder);
        decompressor.decompress();
    }

    private void runTest()
    {
        TestMenu testMenu = new TestMenu();
        testMenu.start();
    }
}