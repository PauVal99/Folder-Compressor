package src.dominio;

import src.persistencia.File;
import src.dominio.algoritmos.*;
import src.persistencia.InputBuffer;
import src.persistencia.OutputBuffer;
import src.persistencia.Header;

import java.io.FileInputStream;

/**
 * Esta clase representa la compresión de un único archivo.
 * Según su extensión decide si usar un algoritmo de texto o JPEG.
 * 
 * @author Pau Val
 */
public class FileCompressor
{
    /** Archivo a comprimir */
    private File source;

    /** Algoritmo usado en caso de texto. */
    private Algorithm algorithm;

    /** Path relativo que deve escribirse un el header */
    private String relativePath;

    /** Bytes que ocupa el archivo comprimido */
    private long compressedLength;

    /** Calidad con la que se comprimira (si el algoritmo lo permite) */
    private int quality;

    /**
     * Construye el compresor de un fichero
     * 
     * @param source fichero a comprimir
     * @param algorithm algoritmo que se usara en caso de texto
     * @param relativePath parametro necesario del header
     */
    public FileCompressor(File source, String algorithmName, String relativePath, int quality)
    {
        this.source = source;
        this.quality = quality;
        this.algorithm = setAlgorithm(algorithmName);
        this.relativePath = relativePath;
    }

    /**
     * Realiza la acción de comprimir un fichero.
     * 
     * @return el fichero comprimido con su header
     * @throws Exception En caso de error en la entrada o salida
     * 
     * @see src.persistencia.OutputBuffer
     */
    public OutputBuffer compress() throws Exception
    {
        OutputBuffer compressedFile = new OutputBuffer();

        if(source.isFile()){
            InputBuffer fileBytes = readSource();
            OutputBuffer compressedFileBytes = compressFile(fileBytes);
            compressedLength = compressedFileBytes.toByteArray().length;

            compressedFile.write(getHeader().getBytes());
            compressedFile.write(compressedFileBytes.toByteArray());
        } else 
            compressedFile.write(getHeader().getBytes());

        return compressedFile;
    }

    /**
     * Retorna el header del fichero a comprimir.
     * Este le indica al descompresor información necesaria.
     * 
     * @return header de source
     */
    private String getHeader()
    {
        Header header = new Header(source.isFile(), relativePath, algorithm, (int)compressedLength);
        return header.toString();
    }

    /**
     * Lee y guarda en un buffer el fichero source.
     * 
     * @return bytes de source
     * @throws Exception En caso de error en la lectura del fichero
     * 
     * @see java.io.ByteArrayInputStream
     */
    private InputBuffer readSource() throws Exception
    {
        byte[] read = new byte[(int) source.length()];
        FileInputStream sourceReader = new FileInputStream(source);
        sourceReader.read(read);
        sourceReader.close();
        return new InputBuffer(read);
    }

    /**
     * Llama al algoritmo decidido para comprimir source
     * 
     * @param fileBytes bytes a comprimir
     * @return bytes comprimidos
     * 
     * @see src.persistencia.OutputBuffer
     */
    private OutputBuffer compressFile(InputBuffer fileBytes)
    {
        return algorithm.compress(fileBytes);
    }

    /**
     * Decide que algoritmo comprimira soruce.
     * Si source es una foto usa JPEG. Si es texto el algoritmo especificado.
     * Si se ha especificado automatico se escoje uno en función del tamaño de source
     * 
     * @param algorithmName nombre del algorimto especificado
     * @return algoritmo decidido
     */
    private Algorithm setAlgorithm(String algorithmName)
    {
        Algorithm algorithm = new LZW();
        if(source.getExtension().equals("ppm")){
            algorithm = new JPEG();
            algorithm.setQuality(quality);
        }
        else if(algorithmName.equals("LZ78")) algorithm = new LZ78();
        else if(algorithmName.equals("LZSS")) algorithm = new LZSS();
        else if(algorithmName.equals("LZW")) algorithm = new LZW();
        else if(source.length() < 2097152) algorithm = new LZW();
        else if(source.length() < 10485760) algorithm = new LZSS();
        else if(source.length() > 10485760) algorithm = new LZ78();

        return algorithm;
    }
}