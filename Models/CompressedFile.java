package models;

import java.io.File;
import java.io.IOException;
import java.io.*;

public class CompressedFile extends File
{
    private String name, algorithm;
    private byte[] content;

    public CompressedFile(String pathName)
    {
        super(pathName);
        try{
            BufferedReader br = new BufferedReader(new FileReader(this.getPath()));
            String line;
            for(int i=0; i<3;i++){
                line = br.readLine();
                String[] split = line.split(":");
                if(split[0] == "name") this.name = split[1];
                if(split[0] == "algorithm") this.algorithm = split[1];
            }
            while ((line = br.readLine()) != null) {
                this.content = this.concatenate(this.content, line.getBytes());
            }
            br.close();
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

    public byte[] getContent()
    {
        return this.content;
    }

    public byte[] concatenate(byte[] a, byte[] b) {
        int aLen = a.length;
        int bLen = b.length;

        byte[] c = new byte[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    public static final long serialVersionUID = 1L;
}