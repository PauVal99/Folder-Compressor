package src.dominio;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import src.dominio.algoritmos.*;
import src.persistencia.UncompressedFile;;

public class Compressor
{
    private UncompressedFile uncompressedFile;
    private Path destinationPath;
    private Algorithm algorithm;

    public Compressor(UncompressedFile uncompressedFile, String destination, String algorithmName)
    {
        this.uncompressedFile = uncompressedFile;
        this.destinationPath = Paths.get(destination);
        this.algorithm = this.setAlgorithm(algorithmName);
    }

    public void compress()
    {
        this.createDestinationFile();
        this.writeInDestiantionFile(this.getHeader().getBytes());

        byte[] readBytes = uncompressedFile.readContent(1024);
        while(readBytes.length == 1024){
            byte[] compressedBytes = algorithm.compress(new String(readBytes));
            this.writeInDestiantionFile(compressedBytes);
            readBytes = uncompressedFile.readContent(1024);
        }
        byte[] compressedBytes = algorithm.compress(new String(readBytes));
        this.writeInDestiantionFile(compressedBytes);

        this.writeInDestiantionFile("\n".getBytes());
    }

    private void createDestinationFile()
    {   
        try{
            Files.createFile(this.destinationPath);}
        catch (IOException e){
            e.printStackTrace();}
    }

    private void writeInDestiantionFile(byte[] bytes)
    {   
        try{
            Files.write(this.destinationPath, bytes, StandardOpenOption.APPEND);}
        catch (IOException e){
            e.printStackTrace();}
    }

    private String getHeader()
    {
        String header = "name:"      + this.uncompressedFile.getName() + "\n" +
                        "algorithm:" + this.algorithm.getName()        + "\n" ;
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