package src.dominio.algoritmos;

import java.util.*;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.io.ByteArrayOutputStream;

import src.persistencia.*;

/**
* Esta clase representa el algoritmo de compresión y descompresión LZSS.
* Se encarga de comprimir y descomprimir archivos. Devuelve una secuencia de bytes con el tamaño, los bits que serviran de flag y los caracteres comprimidos
* 
* @author Sebastian Acurio Navas
*/

public class LZSS extends Algorithm {

    /** Tamaño de la ventana corrediza */
    private static final int WINDOW = 4096;
    /** Tamaño máximo de coincidencia - lookahead */
    private static final int MAX_MATCH_LENGHT = 18;

    /**
     * Comprime todo el texto del archivo representado por decompressed.
     * Inicialmente guarda el contendio del fichero como una secuencia de carácteres y lo recorre para buscar las coincidencias
     * 
     * @param decompressed archivo a comprimir
     * @return array de bytes con la información del texto comprimido (no es legible, hay que descomprimirlo)
     * 
     * @see src.persistencia.UncompressedFile
     */
    public byte[] compress(UncompressedFile decompressed)
    {
        byte[] compressedComplete = new byte[0];
        byte[] fileBytes = decompressed.readAll();

        String aux = new String (fileBytes, StandardCharsets.UTF_8);
        CharSequence fileUncompressed = aux;
        int sizeFile = fileUncompressed.length();
        BitSet match = new BitSet();
        StringBuilder compressedString = new StringBuilder();
        int sizeStringCompressed = 0;
        Map<Character, List<Integer>> diccionari = new HashMap<>();

        for (int i = 0; i < sizeFile; i++) {
            char actualChar = fileUncompressed.charAt(i);
            boolean found = false;
            int inici = 0, matchLen = 0;
            List<Integer> positions = diccionari.get(actualChar);

            if (positions == null) {
                positions = new LinkedList<>(); positions.add(i);
                diccionari.put(actualChar, positions);

            } else {
                Iterator<Integer> it = positions.iterator();
                while (it.hasNext()) {
                    int posMatch = it.next();
                    if ((i - posMatch) > WINDOW) {
                        it.remove();
                        continue;
                    }
                    int len = getMatchedLen(fileUncompressed, posMatch + 1, i + 1, sizeFile) + 1;
                    if (len > matchLen) {
                        inici = i - posMatch;
                        matchLen = len;
                    }
                    found = true;
                }
                positions.add(i);
                int posNextChars = Math.min(i + matchLen, sizeFile);
                for (int j = i + 1; j < posNextChars; j++) {
                    List<Integer> nextPositions = diccionari.get(fileUncompressed.charAt(j));
                    if (nextPositions == null) {
                        nextPositions = new LinkedList<>();
                        diccionari.put(fileUncompressed.charAt(j), nextPositions);
                    }
                    nextPositions.add(j);
                }
            }
            if (found && matchLen >= 2) {
                match.set(sizeStringCompressed);
                compressedString.append((char) inici).append((char) matchLen);
                i += matchLen - 1;
            } else {
                match.set(sizeStringCompressed, false);
                compressedString.append(actualChar);
            }
            sizeStringCompressed++;
        }
        try {
            compressedComplete = getCompressedBytes(compressedString.toString(), match.length(), match, sizeStringCompressed);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return compressedComplete;
    }

    /**
     * Descomprime el archivo representado por compressedBytes.
     * Inicialmente lee los bytes en un cierto orden para recuperar la información necesaria y proceder a la descompresión
     * 
     * @param compressedBytes archivo a descomprimir
     * @return array de bytes con el texto descomprimido
     * 
     * @see src.persistencia.CompressedFile
     * @see src.persistencia.File
     */
    public byte[] decompress(CompressedFile compressedBytes)
    {
        byte[] rec_size = compressedBytes.readContent(4);
        int n = fromByteArray(rec_size);
        byte[] bits_size = compressedBytes.readContent(4);
        int y = fromByteArray(bits_size);
        int bset_size = y / 8 + (((y) % 8 == 0) ? 0 : 1);
        byte[] sizeString = compressedBytes.readContent(4);
        int x = fromByteArray(sizeString);
        byte[] bset = compressedBytes.readContent(bset_size);
        BitSet match = byteToBits(bset);
        byte[] bstring = compressedBytes.readContent(x);
        String decomp = new String(bstring);

        StringBuilder decompressedString = new StringBuilder();
        decompressedString = decompressedString.append(decomp);
        StringBuilder result = new StringBuilder();

        int index = 0;
        for (int i = 0; i < n; i++) {
            if (match.get(i)) {
                int start = decompressedString.charAt(index++);
                int matchedLen = decompressedString.charAt(index++);
                int ini = result.length() - start;
                int end = ini + matchedLen;
                for (; ini < end; ini++)
                    result.append(result.charAt(ini));
            } else {
                result.append(decompressedString.charAt(index++));
            }
        }

        return result.toString().getBytes();
    }

    /**
     * Construye el array de bytes con la información necesaria para la posterior descompresion
     * 
     * @param resultantString cadena de caracteres comprimidos
     * @param sizeBits tamaño en bits dels flags
     * @param match cadena de bits que trabajaran como flags
     * @param size tamaño de caracteres + pairs<offset, lenght>
     * @return cadena de bytes con la información necesaria para la descompresión
     * 
     */
    private byte[] getCompressedBytes(String resultantString, int sizeBits, BitSet match, int size) throws UnsupportedEncodingException 
    {
        ByteArrayOutputStream complete = new ByteArrayOutputStream();

        byte[] sizeString = fromInttoByteArray(size);
        byte[] sizeMatch = fromInttoByteArray(sizeBits);
        byte[] bytesMatch = match.toByteArray();
        byte[] bytesString = resultantString.getBytes("UTF-8"); 
        byte[] sizeBytesString = fromInttoByteArray(bytesString.length);

        complete.write(sizeString,0,sizeString.length);
        complete.write(sizeMatch,0,sizeMatch.length);
        complete.write(sizeBytesString,0,sizeBytesString.length);
        complete.write(bytesMatch,0,bytesMatch.length);
        complete.write(bytesString,0,bytesString.length);

        return complete.toByteArray();
    }

    /**
     * Busca el tamaño máximo de coincidencia entre el SearchBuffer y el LookAhead
     * 
     * @param file secuencia de carácteres donde buscaremos las coincidecias
     * @param matchPos posición de una coincidencia
     * @param charPos posición del carácter actual
     * @param finalPosFile última posición de carácteres
     * @return entero con el numero de coincidencias encontradas
     * 
     */
    private static int getMatchedLen(CharSequence file, int matchPos, int charPos, int finalPosFile)
    {
        int n_aux = Math.min(charPos - matchPos, finalPosFile - charPos);
        int n = Math.min(n_aux, MAX_MATCH_LENGHT);
        for(int i = 0; i < n; i++) {
            if(file.charAt(matchPos++) != file.charAt(charPos++)) return i;
        }
        return 0;
    }

    /**
     * Convierte un entero en un Array de Bytes
     * 
     * @param value entero a convertir
     * @return array de bytes que se representa value
     * 
     */
    public static byte[] fromInttoByteArray(int value) {
        return new byte[] {
                (byte)(value >> 24), (byte)(value >> 16), (byte)(value >> 8), (byte)value
        };
    }

    /**
     * Convierte un array de bytes en un entero.
     * 
     * @param buffer array de bytes a convertir
     * @return entero
     * 
     */
    private static int fromByteArray(byte[] buffer) {
        return ((buffer[0] & 0xFF) << 24) |
                ((buffer[1] & 0xFF) << 16) |
                ((buffer[2] & 0xFF) << 8 ) |
                ((buffer[3] & 0xFF) << 0 );
    }

    /**
     * Convierte un array de bytes en un BitSet
     * 
     * @param bytearray array de bytes a convertir
     * @return cadena de bits
     * 
     */
    private static BitSet byteToBits(byte[] bytearray)
    {
        BitSet returnValue = new BitSet(bytearray.length*8);
        ByteBuffer  byteBuffer = ByteBuffer.wrap(bytearray);
        for (int i = 0; i < bytearray.length; i++) {
            byte thebyte = byteBuffer.get(i);
            for (int j = 0; j <8 ; j++) {
                returnValue.set(i*8+j,isBitSet(thebyte,j));
            }
        }
        return returnValue;
    }

    /**
     * Función que retorna el valor boolea del bit
     * 
     * @param b byte del cual obtener valores
     * @param bit posición del bit dentro del byte b
     * @return Boolean
     * 
     */
    private static Boolean isBitSet(byte b, int bit)
    {
        return (b & (1 << bit)) != 0;
    }

    /**
     * Retorna el nombre de este algoritmo
     * 
     * @return nombre del algoritmo
     */
    public String getName()
    {
        return "LZSS";
    }
}