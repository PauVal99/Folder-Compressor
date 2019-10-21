package controllers;

import models.File;
import java.nio.file.Files;
import controllers.algorithms.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Compressor
{
    public void compressFile(File fileToCompress, String destination)
    {
        Algorithm algorithm = this.setAlgorithm();
        Path destinationPath = Paths.get(destination);
        try
        { 
            Files.createFile(destinationPath);
            Files.write(destinationPath, "{\nalgorithm:LZW\ncontent:".getBytes());
            byte[] compressedBytes = algorithm.compress(new String(fileToCompress.getContent()));
            Files.write(destinationPath, compressedBytes);
            Files.write(destinationPath, "\n}".getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    private Algorithm setAlgorithm()
    {
        return new LZW();
    }
}