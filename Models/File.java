package models;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

public class File
{
    private Path path;

    public void setPath(String path)
    {
        this.path = Paths.get(path);
    }

    public Path getPath()
    {
        return this.path;
    }

    public String getAlgorithmName()
    {
        String algorithmName;
        return algorithmName;
    }

    public byte[] getContent()
    {
        byte[] bytes = new byte[0];
        try{
            bytes = Files.readAllBytes(this.path);}
        catch (IOException e){
            e.printStackTrace();}
        return bytes;
    }
}