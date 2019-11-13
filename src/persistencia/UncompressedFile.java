package src.persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UncompressedFile extends File
{
    FileInputStream fileInputStream;

    public UncompressedFile(String pathName)
    {
        super(pathName);
        try{
            fileInputStream = new FileInputStream(new File(this.getPath()));
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public byte[] readAll()
    {
        return readContent((int)this.length());
    }

    public char readChar()
    {
        byte[] bc = readContent(1);
        if(bc.length == 0) return 0;
        return new String(bc).charAt(0);
    }

    public byte[] readContent(int nBytes)
    {
        byte[] bytes = new byte[nBytes];
        try{
            int read = fileInputStream.read(bytes);
            if(read == -1){
                fileInputStream.close();
                return new byte[0];
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return bytes;
    }

    public String getFileName()
    {
        String name = getName();
        int pos = name.lastIndexOf(".");
        if (pos > 0) {
            name = name.substring(0, pos);
        }
        return name;
    }

    public static final long serialVersionUID = 1L;
}