package src.persistencia;

/**
 * Esta clase representa un fichero.
 * Su cometido es gestionar todas las necesidades del progama respecto este fichero.
 * Extiende la clase java.io.File.
 * 
 * @see java.io.File
 * @author Pau Val
 */
public class File extends java.io.File
{
    /**
     * Constructora de un fichero.
     * 
     * @param pathName ruta al fichero
     */
    public File(String pathName)
    {
        super(pathName);
    }

    /**
     * Retorna el nombre sin la extensión del fichero.
     * 
     * @return nombre sin la extensión del fichero
     */
    public String getFileName()
    {
        String name = getName();
        int pos = name.lastIndexOf(".");
        if (pos > 0)
            name = name.substring(0, pos);
        return name;
    }

    /**
     * Retorna la extensión del fichero.
     * 
     * @return extensión del fichero
     */
    public String getExtension()
    {
        String extension = "";
        String name = getName();
        int pos = name.lastIndexOf(".");
        if (pos > 0)
            extension = name.substring(pos+1, name.length());
        return extension;
    }

    /**
     * Retorna el tamaño del fichero o de la suma de sus ficheros si es una carpeta.
     * 
     * @return tamaño del fichero
     */
    public long getSize()
    {
        long size = 0;
        if(isFile())
            return length();
        else {
            File[] list = listFiles();
            for(File file: list)
                size += file.getSize();
        }
        return size;
    }

    /**
     * Si es una carpeta retorna una lista con todos los ficheros que contiene.
     * 
     * @return todos los ficheros (archivos o carpetas) que contiene
     */
    public File[] listFiles()
    {
        java.io.File[] content = super.listFiles();
        File[] returnContent = new File[content.length];
        int i = 0;
        for(java.io.File child:content){
            returnContent[i] = new File(child.getPath());
            ++i;
        }
        return returnContent;
    }

    /**
     * Elimina el fichero o la carpeta entera que representa esta clase.
     * 
     * @return cierto si se ha eliminado, falso en otro caso
     */
    public boolean delete()
    {
        boolean delete = true;
        if(isFile())
            return super.delete();
        else {
            File[] list = listFiles();
            for(File file: list)
                delete &= file.delete();
            delete &= super.delete();
        }
        return delete;
    }

    public static final long serialVersionUID = 1L;
}