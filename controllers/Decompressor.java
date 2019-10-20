package controllers;

import models.File;
import java.nio.file.Files;
import controllers.algorithms.LZW;
import java.io.IOException;
import java.nio.file.Paths;

public class Decompressor
{
    public void decompressFile(File fileToDecompress, String destinationPath)
    {
        try
        { 
            String decompressedBytes = LZW.decompress(fileToDecompress.getContent());
            Files.createFile(Paths.get(destinationPath));
            Files.write(Paths.get(destinationPath), decompressedBytes.getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}