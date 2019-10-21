package controllers;

import models.File;
import java.nio.file.Files;
import controllers.algorithms.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Decompressor
{
    public void decompressFile(File fileToDecompress, String destination)
    {
        Algorithm algorithm = this.getAlgorithm(fileToDecompress);
        Path destinationPath = Paths.get(destination);
        try
        { 
            String decompressedBytes = algorithm.decompress(fileToDecompress.getContent());
            Files.createFile(destinationPath);
            Files.write(destinationPath, decompressedBytes.getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private Algorithm getAlgorithm(File fileToDecompress)
    {
        return new LZW();
    }
}