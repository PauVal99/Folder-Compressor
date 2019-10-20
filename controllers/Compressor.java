package controllers;

import models.File;
import java.nio.file.Files;
import controllers.algorithms.LZW;
import java.io.IOException;
import java.nio.file.Paths;

public class Compressor
{
    public void compressFile(File fileToCompress, String destinationPath)
    {
        try
        { 
            byte[] compressedBytes = LZW.compress(new String(fileToCompress.getContent()));
            Files.createFile(Paths.get(destinationPath));
            Files.write(Paths.get(destinationPath), compressedBytes);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}