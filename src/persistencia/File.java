package src.persistencia;

/**
 * Esta clase representa un archivo.
 * Su cometido es gestionar todas las necesidades del progama respecto este fichero.
 * Extiende la clase java.io.File.
 * 
 * @see java.io.File
 * @author Pau Val
 */
public class File extends java.io.File
{
    /**
     * Constructora de un archivo.
     * 
     * @param pathName ruta al archivo sin comprimir
     */
    public File(String pathName)
    {
        super(pathName);
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

    public long getSize()
    {
        long size = 0;
        if(isFile()){
            return length();
        }
        else {
            File[] list = listFiles();
            for(File file: list){
                size += file.getSize();
            }
        }
        return size;
    }

    public File[] listFiles()
    {
        java.io.File[] content = super.listFiles();
        File[] returnContent = new File[content.length];
        int i = 0;
        for(java.io.File file:content){
            returnContent[i] = new File(file.getPath());
            ++i;
        }
        return returnContent;
    }

    public boolean delete()
    {
        boolean delete = true;
        if(isFile()){
            return super.delete();
        }
        else {
            File[] list = listFiles();
            for(File file: list){
                delete &= file.delete();
            }
            delete &= super.delete();
        }
        return delete;
    }

    public static final long serialVersionUID = 1L;
}