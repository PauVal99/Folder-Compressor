package src.dominio.algoritmos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Esta clase representa un algoritmo de compresión y descompresión.
 * Se encarga de comprimir y descomprimir archivos.
 * 
 * @author Pau Val
 */
public abstract class Algorithm
{
    /**
     * Comprime el archivo uncompressedFile
     * 
     * @param uncompressedFile archivo a comprimir
     * @return bytes comprimidos del archivo uncompressedFile
     * 
     * @see src.persistencia.UncompressedFile
     */
    public abstract ByteArrayOutputStream compress(ByteArrayInputStream input);

    /**
     * Descomprime el archivo compressedFile
     * 
     * @param compressedFile archivo a descomprimir
     * @return bytes descomprimidos del archivo compressedFile
     * 
     * @see src.persistencia.CompressedFile
     */
    public abstract ByteArrayOutputStream decompress(ByteArrayInputStream input);

    /**
     * Retorna el nombre del algoritmo
     * 
     * @return nombre del algoritmo
     */
    public abstract String getName();
}