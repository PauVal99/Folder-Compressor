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
 * Su cometido es llamar al algoritmo requerido y escribir el resultado en el fichero de destino.
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
        initStadistics();
        decompressSource();
        printStadistics();
    }

    private void decompressSource(){
        try{
            FileInputStream fileInputStream = new FileInputStream(source.getPath());
            BufferedReader bufferedReader = new BufferedReader(new FileReader(source.getPath()));
            String paramsLine;
            while((paramsLine = bufferedReader.readLine()) != null){
                fileInputStream.skip((paramsLine+"\n").getBytes().length);
                String[] params = paramsLine.split(";");
                String type = params[0];
                String path = params[1];
                if(type.equals("folder")){
                    File folder = new File(destination.getPath() + File.separator + path);
                    folder.mkdirs();
                }
                else{
                    File dest = new File(destination.getPath() + File.separator + path);
                    FileOutputStream fos = new FileOutputStream(dest);
                    Algorithm algorithm = getAlgorithm(params[2]);
                    int size = Integer.parseInt(params[3]);

                    byte[] decom = new byte[size];
                    fileInputStream.read(decom);
                    ByteArrayInputStream bais = new ByteArrayInputStream(decom);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bufferedReader.skip((new String(decom)).toCharArray().length);
                    baos = algorithm.decompress(bais);
                    fos.write(baos.toByteArray());
                    fos.close();
                }
            }
            fileInputStream.close();
            bufferedReader.close();
        }
        catch(Exception e){
            System.out.print(e.getMessage());
        }
    }
}