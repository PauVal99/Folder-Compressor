package src.dominio;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.File;
import java.nio.file.StandardOpenOption;

import src.persistencia.*;
import src.dominio.algoritmos.*;

public class Decompressor
{
    private CompressedFile compressedFile;
    private Path destinationPath;
    private Algorithm algorithm;

    public Decompressor(CompressedFile compressedFile, String destination)
    {
        this.compressedFile = compressedFile;
        this.destinationPath = Paths.get(destination + File.separator + compressedFile.getFileName());
        this.algorithm = this.setAlgorithm(compressedFile.getAlgorithm());
    }

    public void decompress()
    {
        try
        { 
            Files.createFile(this.destinationPath);
            byte[] readBytes = compressedFile.readContent(1024);
            while(readBytes.length == 1024){
                String decompressedBytes = algorithm.decompress(readBytes);
                this.writeInDestiantionFile(decompressedBytes.getBytes());
                readBytes = compressedFile.readContent(1024);
            }
            String decompressedBytes = algorithm.decompress(readBytes);
            this.writeInDestiantionFile(decompressedBytes.getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void writeInDestiantionFile(byte[] bytes)
    {   
        try{
            Files.write(this.destinationPath, bytes, StandardOpenOption.APPEND);}
        catch (IOException e){
            e.printStackTrace();}
    }

    private Algorithm setAlgorithm(String algorithmName)
    {
        if(algorithmName.equals("LZ78")) return new LZ78();
        else if(algorithmName.equals("LZSS")) return new LZSS();
        else if(algorithmName.equals("LZW")) return new LZW();
        return new JPEG();
    }
}