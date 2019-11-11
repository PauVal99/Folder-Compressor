package src.persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Esta clase representa un archivo sin comprimir.
 * Su cometido es gestionar todas las necesidades del progama respecto este fichero.
 * Extiende la clase java.io.File.
 * 
 * @author Pau Val
 */

public class UncompressedFile extends File
{
    /**
     * Lector del archivo
     * 
     * @see java.io.FileInputStream
     */
    FileInputStream fileInputStream;

    /**
     * Constructora de un UncompressedFile.
     * Inicializa el lector del archivo.
     * 
     * @param pathName ruta al archivo sin comprimir
     */
    public UncompressedFile(String pathName)
    {
        super(pathName);
        try{
            fileInputStream = new FileInputStream(this);
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * Esta función lee todos los bytes del archivo.
     * 
     * @return byte array con todos los bytes del archivo.
     * 
     * @see this.readContent(nBytes)
     */
    public byte[] readAll()
    {
        return readContent((int)this.length());
    }

    /**
     * Esta función lee el siguiente caracter sin leer del fichero.
     * 
     * @return siguiente caracter
     * 
     * @see this.readContent(nBytes)
     */
    public char readChar()
    {
        byte[] bc = readContent(1);
        if(bc.length == 0) return 0;
        return new String(bc).charAt(0);
    }

    /**
     * Esta función retorna un byte array con el numero de bytes siguientes sin leer.
     * 
     * @param nBytes numero de bytes a leer
     * @return contenido de los siguientes nBytes
     * 
     * @see java.io.FileInputStream::read(byte[])
     */
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

    /**
     * Retorna el nombre sin extension de este fichero.
     * 
     * @return nombre sin extension de este fichero
     * 
     * @see java.io.File::getName()
     */
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