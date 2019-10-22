package controllers;

import models.*;
import java.nio.file.Files;
import controllers.algorithms.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Decompressor
{
    public void decompressFile(CompressedFile compressedFile, String destination)
    {
        Algorithm algorithm = this.getAlgorithm(compressedFile);
        Path destinationPath = Paths.get(destination);
        try
        { 
            String decompressedBytes = algorithm.decompress(compressedFile.getContent());
            Files.createFile(destinationPath);
            Files.write(destinationPath, decompressedBytes.getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private Algorithm getAlgorithm(CompressedFile compressedFile)
    {
        return new LZW();
    }
}