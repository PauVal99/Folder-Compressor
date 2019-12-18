package src.dominio;

import src.dominio.Actor;
import src.dominio.algoritmos.Algorithm;

import src.persistencia.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.Integer;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Esta clase representa un actor de descompresión.
 * Su cometido es llamar al algoritmo descompressor requerido y escribir el resultado en el fichero de destino.
 * 
 * @author Pau Val
 */
public class Decompressor extends Actor
{
    /**
     * Construye un Compressor.
     * 
     * @param compressedFile archivo a descomprimir
     * @param destinationFile archivo de destino
     * 
     * @see src.persistencia.File
     * @see src.persistencia.CompressedFile
     */
    public Decompressor(File source, File destination)
    {
        super(source, destination);
    }

    /**
     * Realiza la acción de descomprimir un fichero con los parametros de la constructora.
     * Se encaraga de recojer las estadísticas y escribir el resultado.
     */
    public void decompress()
    {
        try{
            initStadistics();
            decompressSource();
            setStadistics();
        }
        catch(Exception e){
            System.out.println("Error decompressing: " + source.getPath());
        }
    }

    private void decompressSource() throws Exception
    {
        FileInputStream compressedFileReader = new FileInputStream(source.getPath());
        BufferedReader headerReader = new BufferedReader(new FileReader(source.getPath()));
        String header;
        while((header = headerReader.readLine()) != null){
            compressedFileReader.skip((header+"\n").getBytes().length);
            String[] camp = header.split(";");
            String type = camp[0];
            File file = new File(destination.getPath() + File.separator + camp[1]);
            if(type.equals("folder")) {
                file.mkdirs();
            } else {
                Algorithm algorithm = getAlgorithm(camp[2]);
                int size = Integer.parseInt(camp[3]);

                byte[] decom = new byte[size];
                compressedFileReader.read(decom);

                ByteArrayInputStream compressedFileBytes = new ByteArrayInputStream(decom);
                ByteArrayOutputStream decompressedFileBytes = algorithm.decompress(compressedFileBytes);

                FileOutputStream fileWritter = new FileOutputStream(file);
                fileWritter.write(decompressedFileBytes.toByteArray());
                fileWritter.close();

                headerReader.skip((new String(decom)).toCharArray().length);
            }
        }
        compressedFileReader.close();
        headerReader.close();
    }
}