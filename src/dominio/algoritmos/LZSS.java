package src.dominio.algoritmos;

import java.util.*;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.io.ByteArrayInputStream;
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
    private static final int WINDOW = 4095;
    /** Tamaño máximo de coincidencia - lookahead */
    private static final int MAX_MATCH_LENGHT = 18;
    /** Auxiliar que permite poder obtener un LOOKAHEAD de 18, guardandolo en 4 bits */
    private static final int RECOVERVALUE = 3;

    /**
     * Comprime todo el texto del archivo representado por decompressed.
     * Inicialmente guarda el contendio del fichero como una secuencia de carácteres y lo recorre para buscar las coincidencias
     *
     * @param inputOriginal archivo a comprimir
     * @return array de bytes con la información del texto comprimido (no es legible, hay que descomprimirlo)
     *
     * @see src.persistencia.UncompressedFile
     */
    public ByteArrayOutputStream compress(ByteArrayInputStream input)
    {
        ByteArrayOutputStream compressedComplete = new ByteArrayOutputStream();
        int emptyFile = input.read();
        if(emptyFile == -1) return compressedComplete;
        
        byte[] fileBytes = readInput(input);
        String aux = new String (fileBytes, StandardCharsets.UTF_8);
        CharSequence fileUncompressed = aux;
        
        ByteArrayOutputStream pairData = new ByteArrayOutputStream();
        byte[] auxPairData = new byte[2];
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
            if (found && matchLen >= 3) {
                match.set(sizeStringCompressed);
                auxPairData[0] = (byte)inici;
                auxPairData[1] = (byte)(((inici >> 4) & 0xF0) | (matchLen - RECOVERVALUE));
                pairData.write(auxPairData,0,2);
                i += matchLen - 1;
            } else {
                match.set(sizeStringCompressed, false);
                compressedString.append(actualChar);
            }
            sizeStringCompressed++;
        }
        try {
            compressedComplete = getCompressedBytes(compressedString.toString(), match.length(), match, sizeStringCompressed, pairData.toByteArray());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return compressedComplete;
    }

    /**
     * Descomprime el archivo representado por compressedBytes.
     * Inicialmente lee los bytes en un cierto orden para recuperar la información necesaria y proceder a la descompresión
     *
     * @param inputCopmpressed archivo a descomprimir
     * @return array de bytes con el texto descomprimido
     *
     * @see src.persistencia.CompressedFile
     * @see src.persistencia.File
     */
    public ByteArrayOutputStream decompress(ByteArrayInputStream input)
    {
        ByteArrayOutputStream decompressedFile = new ByteArrayOutputStream();
        int emptyFile = input.read();
        if(emptyFile == -1) return decompressedFile;

        byte[] intReader = new byte[4];
            input.read(intReader, 0, 4);
            int n = fromByteArray(intReader);
            input.read(intReader, 0, 4);
            int nBits = fromByteArray(intReader);
            int bsetSize = nBits / 8 + (((nBits) % 8 == 0) ? 0 : 1);
            input.read(intReader, 0, 4);
            int x = fromByteArray(intReader);
        byte[] bset = new byte[bsetSize]; input.read(bset, 0, bsetSize);
            BitSet match = byteToBits(bset);
        byte[] bstring = new byte[x]; input.read(bstring, 0, x);
            String decomp = new String(bstring);

        StringBuilder decompressedString = new StringBuilder();
        decompressedString = decompressedString.append(decomp);
        StringBuilder result = new StringBuilder();

        int index = 0;
        for (int i = 0; i < n; i++) {
            if (match.get(i)) {
                byte[] pairs = new byte[2]; input.read(pairs, 0, 2);
                int start = (short)((pairs[0] & 0xFF) | ((pairs[1] & 0xF0) << 4));
                int matchedLen = (short)((pairs[1] & 0x0F) + RECOVERVALUE);
                int ini = result.length() - start;
                int end = ini + matchedLen;
                for (; ini < end; ini++)
                    result.append(result.charAt(ini));
            } else {
                result.append(decompressedString.charAt(index++));
            }
        }
        byte[] resultAux = result.toString().getBytes();
        decompressedFile.write(resultAux,0,resultAux.length);

        return decompressedFile;
    }

    /**
     * Construye el array de bytes con la información necesaria para la posterior descompresion
     *
     * @param input cadena de ByteArrayInputStream
     * @return cadena de bytes con la información necesaria para la compresión
     *
     */
    private byte[] readInput(ByteArrayInputStream input) throws IOException {
        byte[] array = new byte[input.available()];
        input.read(array);

        return array;
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
    private ByteArrayOutputStream getCompressedBytes(String resultantString, int sizeBits, BitSet match, int size, byte[] pairData) throws UnsupportedEncodingException
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
        complete.write(pairData,0,pairData.length);

        return complete;
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