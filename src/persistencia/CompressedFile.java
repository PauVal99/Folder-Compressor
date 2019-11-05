package src.persistencia;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import src.dominio.ArrayHelper;

public class CompressedFile extends File
{
    private String name, algorithm;

    public CompressedFile(String pathName)
    {
        super(pathName);
        this.readFile();
    }

    private void readFile()
    {
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.getPath()));
            this.readAttributes(bufferedReader);
            bufferedReader.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void readAttributes(BufferedReader bufferedReader)
    {
        try{
            for(int i=0; i<2; i++){
                String line = bufferedReader.readLine();
                String[] split = line.split(":");
                if(split[0].equals("name")) this.name = split[1];
                else if(split[0].equals("algorithm")) this.algorithm = split[1];
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getFileName()
    {
        return this.name;
    }

    public String getAlgorithm()
    {
        return this.algorithm;   
    }

    public byte[] readContent(int nBytes)
    {
        byte[] content = new byte[0];
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.getPath()));
            this.readAttributes(bufferedReader);
            bufferedReader.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return content;
    }

    public static final long serialVersionUID = 1L;
}