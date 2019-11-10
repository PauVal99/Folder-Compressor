package src.persistencia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Exception;

import src.persistencia.UncompressedFile;

public class CompressedFile extends UncompressedFile
{
    private String name = null, algorithm = null;

    public CompressedFile(String pathName) throws Exception
    {
        super(pathName);
        readAttributes();
    }

    private void readAttributes() throws Exception
    {
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.getPath()));
            int read = 0;
            for(int i=0; i<2; i++){
                String line = bufferedReader.readLine();
                read = read + (line+"\n").getBytes().length;
                String[] split = line.split(":");
                if(split[0].equals("name")) name = split[1];
                else if(split[0].equals("algorithm")) algorithm = split[1];
            }
            fileInputStream.skip(read);
            bufferedReader.close();
            if((name == null) || (algorithm == null)) throw new Exception("Bad compressed");
            
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getOriginalName()
    {
        return name;
    }

    public String getOriginalExtension()
    {
        String ext = null;
        int pos = name.lastIndexOf(".");
        if (pos > 0) {
            ext = name.substring(pos, name.length());
        }
        return ext;
    }

    public String getAlgorithm()
    {
        return algorithm;   
    }

    public static final long serialVersionUID = 1L;
}