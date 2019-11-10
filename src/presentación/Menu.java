package src.presentación;

import java.io.File;
import java.io.Console;
import java.util.Arrays;

import src.dominio.Compressor;
import src.dominio.Decompressor;
import src.persistencia.*;

public class Menu
{
    private Console console;

    public void start()
    {
        console = System.console();
        String[] possibleOperations = {"1", "2", "3"};
        String operation = console.readLine("Wellcome to compressor/decompressor. Please select an operation:\n 1.Compress\n 2.Uncompress\n 3.Run tests\n");
        while(!Arrays.asList(possibleOperations).contains(operation)) 
            operation = console.readLine("Please enter a valid operation: ");
        if(operation.equals("1")) compress();
        else if(operation.equals("2")) decompress();
        else if(operation.equals("3")) runTest();
    }

    private void compress()
    {
        UncompressedFile uncompressedFile = readFile("Please enter the file path: ");
        File destinationFolder = readFolder("Please enter the destination folder: ");
        File destinationFile = new File(destinationFolder.toString() + java.io.File.separator + readName(uncompressedFile));
        String algorithm = readAlgorithm();

        Compressor compressor = new Compressor(uncompressedFile,destinationFile,algorithm);
        compressor.compress();
    }
    
    private void decompress()
    {
        CompressedFile compressedFile = new CompressedFile(readFile("Please enter the file path: ").getPath());
        File destinationFolder = readFolder("Please enter the destination folder: ");
        Decompressor decompressor = new Decompressor(compressedFile,destinationFolder);
        decompressor.decompress();
    }

    private void runTest()
    {

    }

    private String readAlgorithm()
    {
        String[] possibleAlgorithms = {"LZ78", "LZSS", "LZW", "JPEG", ""};
        String algorithm = console.readLine("Especify the algorithm (LZ78, LZSS, LZW, JPEG) (enter for auto): ");
        while(!Arrays.asList(possibleAlgorithms).contains(algorithm)) algorithm = console.readLine("Invalid algorithm (LZ78, LZSS, LZW, JPEG) (enter for auto): ");
        if(algorithm.equals("")) algorithm = "auto";
        return algorithm;
    }

    private String readName(UncompressedFile file)
    {
        String name = console.readLine("Please enter the name of the compressed file (enter for same name): ");
        if(name.equals("")) name = file.getFileName();
        return name;
    }

    private File readFolder(String message)
    {
        File folder = new File(console.readLine(message));
        while(!folder.exists() || !folder.isDirectory())
            folder = new File(console.readLine("Folder not found. Please insert a valid folder: "));
        return folder;
    }

    private UncompressedFile readFile(String message)
    {
        String path = console.readLine(message);
        File file = new File(path);
        while(!file.exists() || !file.isFile()){
            path = console.readLine("File not found. Please insert a valid file: ");
            file = new File(path);
        }
        UncompressedFile uncompressedFile = new UncompressedFile(path);
        return uncompressedFile;
    }
}