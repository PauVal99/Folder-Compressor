package src.persistencia;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;

public class UncompressedFile extends File
{
    private int read;

    public UncompressedFile(String pathName)
    {
        super(pathName);
        this.read = 0;
    }

    public byte[] readContent(int nBytes)
    {
        byte[] bytes = new byte[nBytes];

        try{
            FileInputStream fileInputStream = new FileInputStream(this);
            int read = fileInputStream.read(bytes,this.read,nBytes);
            if(read == -1){
                fileInputStream.close();
                return new byte[0];
            }
            this.read = read + this.read;
            fileInputStream.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return bytes;
    }

    public static final long serialVersionUID = 1L;
}