package models;

import java.io.File;
import java.nio.file.Files;
import java.io.IOException;

public class UncompressedFile extends File
{
    public UncompressedFile(String pathName)
    {
        super(pathName);
    }

    public byte[] getContent()
    {
        byte[] bytes = new byte[0];
        try{
            bytes = Files.readAllBytes(this.toPath());}
        catch (IOException e){
            e.printStackTrace();}
        return bytes;
    }

    public static final long serialVersionUID = 1L;
}