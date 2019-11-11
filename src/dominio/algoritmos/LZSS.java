package src.dominio.algoritmos;

import java.io.*;
import java.util.*;
import java.io.IOException;
import java.nio.ByteBuffer;

import src.persistencia.*;

public class LZSS extends Algorithm {

    private static final int SEARCH_BUFFER = 4096;
    //private static final int LOOKAHEAD = 4096;

    public static class LZSSData implements Serializable {
        public LZSSData(BitSet match, StringBuilder dest, int size) { // <flag, offset, lenght>
            this.match = match;
            this.dest = dest;
            this.size = size;
        }
        private BitSet match;
        private StringBuilder dest;
        private int size;
        public static final long serialVersionUID = 1L;
    }

    public byte[] compress(UncompressedFile decompressed) {
        
        // Inicializacion de datos
        String dec = "";
        char c;
        while ( (c=decompressed.readChar()) != 0 ) {
            dec += c ;
        }
        CharSequence src = dec; //Secuencia de chars a comprimir
        int n = src.length();    //Medida de los chars
        BitSet match = new BitSet();  // flag --> conjunto de bits inicializados a false
        StringBuilder out = new StringBuilder(); // chars y pair<offset,lenght> de retorno
        int size = 0;
        Map<Character, List<Integer>> pos_ini = new HashMap<>();  // diccionario para almacenar los chars recientemente vistos

        for (int i = 0; i < n; i++) {
            char act = src.charAt(i);
            boolean found = false;
            int inici = 0;
            int matchLen = 0; // tamaño del match
            List<Integer> positions = pos_ini.get(act);

            if (positions != null) {
                Iterator<Integer> it = positions.iterator();
                while (it.hasNext()) {
                    int p = it.next();
                    if ((i - p) > SEARCH_BUFFER) { // comprobamos que no sobrepase el SearchBuffer
                        it.remove();
                        continue; // se salta una iteracion si se cumpla una cierta condicion
                    }
                    int len = getMatchedLen(src, p + 1, i + 1, n);
                    if (len > matchLen) {
                        inici = i - p;   //puntero inicial del match
                        matchLen = len;  //numero de matchs
                    }
                    found = true;
                }
                positions.add(i);
                int jn = Math.min(i + matchLen, n);
                //int jn = Math.min(jn_aux, n);
                for (int j = i + 1; j < jn; j++) {
                    List<Integer> q = pos_ini.get(src.charAt(j));
                    if (q == null) {
                        q = new LinkedList<>(); // Integer no se necesita especificar
                        pos_ini.put(src.charAt(j), q);
                    }
                    q.add(j);
                }
            } else{
                    positions = new LinkedList<>();  //lista de positions vacia
                    positions.add(i);                //agregamos la primera posicion
                    pos_ini.put(act, positions);     //agregamos al diccionario el char con su lista de positions
            }

            if (found && matchLen > 1) {
                match.set(size);
                out.append((char) inici).append((char) matchLen);
                i += matchLen - 1;
            } else {
                match.set(size, false);
                out.append(act);
            }
            size++;
        }
        
        LZSSData result = new LZSSData(match,out,size);

        String res = result.dest.toString();
        byte[] b_result = res.getBytes();
        byte[] bytes2 = result.match.toByteArray();
        byte[] compressed = intToByteArray(result.size);
        compressed = concatenate(compressed, bytes2);
        compressed = concatenate(compressed, b_result);
 
        return compressed;
    }

    public byte[] decompress(CompressedFile compressedBytes) {

        byte[] result = compressedBytes.readAll();

        byte[] rec_size = readBytes(result,0, 4);
        int x = byteArrayToInt(rec_size);
        int bset_size = x / 8 + (((x) % 8 == 0) ? 0 : 1);  //calculo de bytes pertenecientes al BitSet
        byte[] bset = readBytes(result, 4, bset_size);
        BitSet k = byteToBits(bset);
        //int bs = result.length;
        byte[] bstring = readBytes(result, bset_size+4, result.length-(bset_size+4));
        String decomp = new String(bstring);
        StringBuilder a = new StringBuilder(); a = a.append(decomp);

        LZSSData objcompressed = new LZSSData(k,a,x);
        
        StringBuilder b = new StringBuilder();
        decompress_alg(objcompressed, b);
        String ult = b.toString();

        return ult.getBytes();
    }

    public static void decompress_alg(LZSSData src, StringBuilder out){
        int index = 0;
        int n = src.size;
        for(int i = 0; i < n; i++){
            if(src.match.get(i)){
                int start = src.dest.charAt(index++);
                int matchedLen = src.dest.charAt(index++);
                int s = out.length() - start;
                int e = s + matchedLen;
                for(; s < e; s++){
                    out.append(out.charAt(s));
                }
            } else{
                out.append(src.dest.charAt(index++));
            }
        }
    }

    private static int getMatchedLen(CharSequence src, int i1, int i2, int end){
        int n = Math.min(i2 - i1, end - i2);
        for(int i = 1; i <= n; i++){
            if(src.charAt(i1++) != src.charAt(i2++)) return i;
        }
        return 0;
    }

    private byte[] intToByteArray(int i)
    {
        byte[] buffer = new byte[4];
        buffer[0] = (byte) (i >> 0);
        buffer[1] = (byte) (i >> 8);
        buffer[2] = (byte) (i >> 16);
        buffer[3] = (byte) (i >> 24);
        return buffer;
    }

    private int byteArrayToInt(byte[] buffer)
    {
        int i = 0;
        i += buffer[0] &  0xFF;
        i += buffer[1] << 8;
        i += buffer[2] << 16;
        i += buffer[3] << 24;
        return i;
    }

    private static byte[] readBytes(byte[] a, int ini, int nbytes) {
        byte[] res = new byte[nbytes];
        for (int i=ini; i<nbytes+ini; i++) {
            res[i-ini] = a[i];
        }
        return res;
    }

    public static BitSet byteToBits(byte[] bytearray){
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

    private static Boolean isBitSet(byte b, int bit)
    {
        return (b & (1 << bit)) != 0;
    }

    private byte[] concatenate(byte[] a, byte[] b) {
        int aLen = a.length;
        int bLen = b.length;

        byte[] c = new byte[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    public String getName()
    {
        return "LZSS";
    }
}