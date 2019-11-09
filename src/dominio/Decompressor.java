package src.dominio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import src.dominio.algoritmos.*;
import src.persistencia.*;

public class Decompressor
{
    private CompressedFile compressedFile;
    private File destinationFile;
    private Algorithm algorithm;

    public Decompressor(UncompressedFile compressedFile, File destinationFolder)
    {
        this.compressedFile = new CompressedFile(compressedFile.getPath());
        this.destinationFile = new File(destinationFolder.toString() + File.separator + this.compressedFile.getOriginalName());
        this.algorithm = setAlgorithm(this.compressedFile.getAlgorithm());
    }

    public void decompress()
    {
        createDestinationFile();
        writeInDestiantionFile(algorithm.decompress(compressedFile));
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

    private Algorithm setAlgorithm(String algorithmName)
    {
        if(algorithmName.equals("LZ78")) return new LZ78();
        else if(algorithmName.equals("LZSS")) return new LZSS();
        else if(algorithmName.equals("LZW")) return new LZW();
        return new JPEG();
    }
}