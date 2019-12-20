package src.dominio;

import src.dominio.Actor;
import src.persistencia.ActorStadistics;
import src.persistencia.InputBuffer;
import src.persistencia.OutputBuffer;
import src.persistencia.File;
import src.persistencia.Header;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Esta clase representa un actor de descompresión.
 * Su cometido es llamar al algoritmo descompressor requerido y escribir el resultado en el fichero de destino.
 * 
 * @author Pau Val
 */
public class Decompressor extends Actor
{
    /**
     * Construye un Descompressor.
     * 
     * @param source archivo a descomprimir
     * @param destination carpeta de destino
     * 
     * @see src.persistencia.File
     */
    public Decompressor(File source, File destination)
    {
        super(source, destination);
    }

    /**
     * Realiza la acción de descomprimir un fichero con los parametros de la constructora.
     * Se encaraga de recojer las estadísticas y escribir el resultado.
     * 
     * @return estadísticas de descompressión.
     * 
     * @see src.persistencia.ActorStadistics
     */
    public ActorStadistics execute()
    {
        ActorStadistics stadistics = new ActorStadistics();
        stadistics.setStartTime(System.currentTimeMillis());
        stadistics.setOriginalSize(source.getSize());
        try{
            decompressSource();
        }
        catch(Exception e){
            System.out.println("Error decompressing: " + source.getPath());
        }
        stadistics.setStopTime(System.currentTimeMillis());
        return stadistics;
    }

    /**
     * Realiza la descompression del fichero source en cuestión. Por comodidad se usan dos readers. En la documentación se explica su lógica.
     * 
     * @throws Exception en caso de error en la lectura o escritura
     */
    private void decompressSource() throws Exception
    {
        FileInputStream compressedFileReader = new FileInputStream(source.getPath());
        BufferedReader headerReader = new BufferedReader(new FileReader(source.getPath()));
        String sHeader;
        while((sHeader = headerReader.readLine()) != null){
            Header header = new Header(sHeader);
            compressedFileReader.skip((sHeader+"\n").getBytes().length);
            File actFile = new File(destination.getPath() + File.separator + header.getRelativePath());
            if(header.getType().equals("folder")) {
                actFile.mkdirs();
            } else {
                byte[] decom = new byte[header.getSize()];
                compressedFileReader.read(decom);

                InputBuffer compressedFileBytes = new InputBuffer(decom);
                OutputBuffer decompressedFileBytes = header.getAlgorithm().decompress(compressedFileBytes);

                FileOutputStream fileWritter = new FileOutputStream(actFile);
                fileWritter.write(decompressedFileBytes.toByteArray());
                fileWritter.close();

                headerReader.skip((new String(decom)).toCharArray().length);
            }
        }
        compressedFileReader.close();
        headerReader.close();
    }
}