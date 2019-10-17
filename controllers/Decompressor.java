package controllers;

import models.File;
import java.nio.file.Files;
import controllers.algorithms.LZW;
import java.io.IOException;
import java.nio.file.Paths;

public class Decompressor
{
    public void decompressFile(File file)
    {
        try
        { 
            LZW lzw = new LZW();
            String decompressedBytes = lzw.decompress(file.getContent());
            Files.createFile( Paths.get("C:\\Users\\Pau\\Desktop\\P1.txt") );
            Files.write( Paths.get("C:\\Users\\Pau\\Desktop\\P1.txt"),  decompressedBytes.getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}