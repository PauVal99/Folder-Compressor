package src.persistencia;

import java.io.FileInputStream;
import java.io.IOException;

public class File extends java.io.File
{
    FileInputStream fileInputStream;

    public File(String pathName)
    {
        super(pathName);
        try{
            this.fileInputStream = new FileInputStream(this);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public byte[] readContent(int nBytes)
    {
        byte[] bytes = new byte[nBytes];
        try{
            int read = this.fileInputStream.read(bytes);
            if(read < nBytes){
                if(read == -1) read = 0;
                byte[] b = new byte[read];
                for(int i=0; i<read; i++){
                    b[i] = bytes[i];
                }
                fileInputStream.close();
                return b;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return bytes;
    }

    public static final long serialVersionUID = 1L;
}