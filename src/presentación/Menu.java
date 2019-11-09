package src.presentación;

import java.io.Console;
import java.util.*;

import src.dominio.Compressor;
import src.dominio.Decompressor;
import src.persistencia.File;

import tests.TestMenu;

public class Menu
{
    private Console console;

    public void start()
    {
        console = System.console();
        String operation = console.readLine("Wellcome to compressor/decompressor. Please select an operation:\n 1.Compress\n 2.Uncompress\n 3.Run tests\n");
        
        if(operation.equals("1")) compress();
        else if(operation.equals("2")) decompress();
        else if(operation.equals("3")) runTest();
        else{ System.out.print("Please enter a valid operation.\n"); start();}
    }

    private void compress()
    {
        File uncompressedFile = readFile("Please enter the file path: ");
        File destinationFolder = readFolder("Please enter the destination folder: ");
        File destinationFile = new File(destinationFolder.toString() + java.io.File.separator + readName(uncompressedFile));
        String algorithm = readAlgorithm();

        Compressor compressor = new Compressor(uncompressedFile,destinationFile,algorithm);
        compressor.compress();
    }
    
    private void decompress()
    {
        File compressedFile = readFile("Please enter the file path: ");
        File destinationFolder = readFile("Please enter the destination folder: ");
        Decompressor decompressor = new Decompressor(compressedFile,destinationFolder);
        decompressor.decompress();
    }

    private void runTest()
    {
        TestMenu testMenu = new TestMenu();
        testMenu.start();
    }

    private String readAlgorithm()
    {
        String[] possibleAlgorithms = {"LZ78", "LZSS", "LZW", "JPEG", ""};
        String algorithm = this.console.readLine("Especify the algorithm (LZ78, LZSS, LZW, JPEG) (enter for auto): ");
        while(!Arrays.asList(possibleAlgorithms).contains(algorithm)) algorithm = this.console.readLine("Invalid algorithm (LZ78, LZSS, LZW, JPEG) (enter for auto): ");
        if(algorithm.equals("")) algorithm = "auto";
        return algorithm;
    }

    private String readName(File file)
    {
        String name = console.readLine("Please enter the name of the compressed file (enter for same name): ");
        if(name.equals("")) name = file.getFileName();
        return name;
    }

    private File readFolder(String message)
    {
        File folder = new File(this.console.readLine(message));
        if(!folder.exists()){
            System.out.print("Folder not found.\n");
            readFolder(message);
        }
        else if(!folder.isDirectory()){
            System.out.print("Path is not a folder.\n");
            readFolder(message);
        }
        return folder;
    }

    private File readFile(String message)
    {
        File file = new File(this.console.readLine(message));
        if(!file.exists()){
            System.out.print("File not found.\n");
            readFile(message);
        }
        else if(!file.isFile()){
            System.out.print("Path is not a file.\n");
            readFile(message);
        }
        return file;
    }
}