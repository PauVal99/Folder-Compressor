package src.dominio.algoritmos;

import java.util.*;
import java.nio.ByteBuffer;
import src.persistencia.*;
import src.dominio.ByteArrayHelper;

public class LZSS extends Algorithm {

    private static final int WINDOW = 4096;
    private static final int MAX_MATCH_LENGHT = 18;

    public byte[] compress(UncompressedFile decompressed) 
    {    
        String file = ""; char c;
        while ( (c=decompressed.readChar()) != 0 ) file += c ;

        CharSequence src = file; //Secuencia de chars a comprimir
        int n = src.length();    //Medida de los chars
        BitSet match = new BitSet();  // flag --> conjunto de bits inicializados a false
        StringBuilder out = new StringBuilder(); // chars y pair<offset,lenght> de retorno
        int size = 0;
        Map<Character, List<Integer>> pos_ini = new HashMap<>();  // diccionario para almacenar los chars recientemente vistos

        for (int i = 0; i < n; i++) {
            char act = src.charAt(i);
            boolean found = false; int inici = 0; int matchLen = 0;
            List<Integer> positions = pos_ini.get(act);

            if (positions != null) {
                Iterator<Integer> it = positions.iterator();
                while (it.hasNext()) {
                    int p = it.next();
                    if ((i - p) > WINDOW) { // comprobamos que no sobrepase la ventana corrediza
                        it.remove();
                        continue; // saltamos a la siguiente iteracion si se cumpla la condicion
                    }
                    int len = getMatchedLen(src, p + 1, i + 1, n) + 1;
                    if (len > matchLen) {
                        inici = i - p;   //puntero inicial del match
                        matchLen = len;  //numero de matchs
                    }
                    found = true;
                }
                positions.add(i);
                int jn = Math.min(i + matchLen, n);
                for (int j = i + 1; j < jn; j++) {
                    List<Integer> q = pos_ini.get(src.charAt(j));
                    if (q == null) {
                        q = new LinkedList<>();
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
        return getCompressedBytes(out.toString(), match.length(), match, size);
    }

    public byte[] decompress(CompressedFile compressedBytes) 
    {
        //Recuperacion de los datos para comprimir
        byte[] result = compressedBytes.readAll();

        byte[] rec_size = readBytes(result,0, 4);
        int n = byteArrayToInt(rec_size); 
        byte[] bits_size = readBytes(result,4, 8);
        int y = byteArrayToInt(bits_size);
        System.out.print(y+"\n");
        int bset_size = y / 8 + (((y) % 8 == 0) ? 0 : 1);  //calculo de bytes pertenecientes al BitSet
        byte[] bset = readBytes(result, 8, bset_size);
        BitSet match = byteToBits(bset);
        byte[] bstring = readBytes(result, bset_size+8, result.length-(bset_size+8));
        String decomp = new String(bstring);

        StringBuilder src = new StringBuilder(); src = src.append(decomp);
        StringBuilder out = new StringBuilder();

        int index = 0;
        for(int i = 0; i < n; i++){
            if(match.get(i)){
                int start = src.charAt(index++);
                int matchedLen = src.charAt(index++);
                int s = out.length() - start;
                int e = s + matchedLen;
                for(; s < e; s++){
                    out.append(out.charAt(s));
                }
            } else{
                out.append(src.charAt(index++));
            }
        }
        String ult = out.toString();

        return ult.getBytes();
    }

    private byte[] getCompressedBytes(String res, int sizeBits, BitSet match, int size) 
    {
        byte[] bitsSize = ByteArrayHelper.intToByteArray(sizeBits,4);
        byte[] bytes2 = match.toByteArray();
        byte[] b_result = res.getBytes();
        byte[] compressed = ByteArrayHelper.intToByteArray(size,4);
        compressed = ByteArrayHelper.concatenate(compressed, bitsSize);
        compressed = ByteArrayHelper.concatenate(compressed, bytes2);
        compressed = ByteArrayHelper.concatenate(compressed, b_result);
        return compressed;
    }

    private static int getMatchedLen(CharSequence src, int i1, int i2, int end)
    {
        int n_aux = Math.min(i2 - i1, end - i2);
        int n = Math.min(n_aux, MAX_MATCH_LENGHT);
        for(int i = 0; i < n; i++){
            if(src.charAt(i1++) != src.charAt(i2++)) return i;
        }
        return 0;
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

    private static byte[] readBytes(byte[] a, int ini, int nbytes)
    {
        System.out.print(a.length+" "+ini+" "+nbytes+"\n");
        byte[] res = new byte[nbytes];
        for (int i=ini; i<nbytes+ini; i++) {
            res[i-ini] = a[i];
        }
        return res;
    }

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

    private static Boolean isBitSet(byte b, int bit)
    {
        return (b & (1 << bit)) != 0;
    }

    public String getName()
    {
        return "LZSS";
    }
}