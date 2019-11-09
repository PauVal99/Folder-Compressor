package src.persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class File extends java.io.File
{
    FileInputStream fileInputStream;

    public File(String pathName)
    {
        super(pathName);
        try{
            fileInputStream = new FileInputStream(this);
        }
        catch(FileNotFoundException e){
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

    public String getFileName()
    {
        String name = this.getName();
        int pos = name.lastIndexOf(".");
        if (pos > 0) {
            name = name.substring(0, pos);
        }
        return name;
    }

    public static final long serialVersionUID = 1L;
}