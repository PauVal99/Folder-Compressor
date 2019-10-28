package controllers;

import models.*;
import java.nio.file.Files;
import controllers.algorithms.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.File;

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
            String decompressedBytes = algorithm.decompress(this.compressedFile.getContent());
            Files.createFile(this.destinationPath);
            Files.write(this.destinationPath, decompressedBytes.getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
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