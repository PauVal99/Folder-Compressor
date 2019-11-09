package src.dominio;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import src.dominio.algoritmos.*;
import src.persistencia.File;

public class Compressor
{
    private File uncompressedFile;
    private File destinationFile;
    private Algorithm algorithm;

    public Compressor(File uncompressedFile, File destinationFile, String algorithmName)
    {
        this.uncompressedFile = uncompressedFile;
        this.destinationFile = destinationFile;
        this.algorithm = this.setAlgorithm(algorithmName);
    }

    public void compress()
    {
        createDestinationFile();
        writeInDestiantionFile(getHeader().getBytes());
        writeInDestiantionFile(algorithm.compress(uncompressedFile));
        writeInDestiantionFile("\n".getBytes());
    }

    private void createDestinationFile()
    {   
        try{
            destinationFile.createNewFile();
        }
        catch(IOException e){
            System.out.print("Destination file already exists.");
        }
    }

    private void writeInDestiantionFile(byte[] bytes)
    {   
        try{
            Files.write(destinationFile.toPath(), bytes, StandardOpenOption.APPEND);}
        catch (IOException e){
            System.out.print("Error writing in destination file.");
        }
    }

    private String getHeader()
    {
        String header = "name:"      + uncompressedFile.getName() + "\n" +
                        "algorithm:" + algorithm.getName()        + "\n" ;
        return header;
    }

    private Algorithm setAlgorithm(String algorithmName)
    {
        if(algorithmName.equals("LZ78")) return new LZ78();
        else if(algorithmName.equals("LZSS")) return new LZSS();
        else if(algorithmName.equals("LZW")) return new LZW();
        else if(algorithmName.equals("JPEG")) return new JPEG();
        return new LZW();
    }
}