package src.dominio;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import src.dominio.algoritmos.*;

public class Actor
{
    protected long startTime, stopTime;
    protected File destinationFile;
    protected Algorithm algorithm;

    public Actor(File destinationFile, String algorithmName)
    {
        this.destinationFile = destinationFile;
        this.algorithm = setAlgorithm(algorithmName);
    }

    protected void initStadistics()
    {
        startTime = System.currentTimeMillis();
    }

    protected void printStadistics()
    {
        stopTime = System.currentTimeMillis();
        System.out.print("Done in "+ (stopTime - startTime) +" milliseconds.\n");
    }

    protected void createDestinationFile()
    {   
        try{
            destinationFile.createNewFile();
        }
        catch(IOException e){
            System.out.print("Destination file already exists.");
        }
    }

    protected void writeInDestiantionFile(byte[] bytes)
    {   
        try{
            Files.write(destinationFile.toPath(), bytes, StandardOpenOption.APPEND);}
        catch (IOException e){
            System.out.print("Error writing in destination file.");
        }
    }

    protected Algorithm setAlgorithm(String algorithmName)
    {
        if(algorithmName.equals("LZ78")) return new LZ78();
        else if(algorithmName.equals("LZSS")) return new LZSS();
        else if(algorithmName.equals("LZW")) return new LZW();
        else if(algorithmName.equals("JPEG")) return new JPEG();
        return new LZW();
    }
}