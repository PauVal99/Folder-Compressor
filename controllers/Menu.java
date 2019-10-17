package controllers;

import java.io.Console;
import models.File;
import controllers.Compressor;

public class Menu
{
    private Console console;

    public void start()
    {
        System.out.print("Wellcome to compressor/decompressor. Please select an operation:\n 1.Compress\n 2.Uncompress\n 3.Run tests\n");
        this.console = System.console();
        int operation = Integer.parseInt(this.console.readLine());
        if(operation == 1) this.compress();
        else if(operation == 2) this.decompress();
    }

    private void compress()
    {
        File file = new File();
        file.setPath(this.console.readLine("Please enter the file path: "));
        Compressor compressor = new Compressor();
        compressor.compressFile(file);
    }

    
    private void decompress()
    {
        File file = new File();
        file.setPath(this.console.readLine("Please enter the file path: "));
        Decompressor decompressor = new Decompressor();
        decompressor.decompressFile(file);
    }
}