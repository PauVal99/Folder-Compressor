package src.dominio.algoritmos;

import java.io.*;
import java.util.*;
import java.io.IOException;

public class LZSS extends Algorithm {

    private static final int SEARCH_BUFFER = 4096;
    private static final int LOOKAHEAD = 4096;

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

    public byte[] compress(String decompressed) {
        // Inicializacion de datos
        CharSequence src = decompressed;
        BitSet match = new BitSet();  // flag --> conjunto de bits inicializados a false
        StringBuilder out = new StringBuilder(); // chars y pair<offset,lenght> de retorno
        int size = 0;
        Map<Character, List<Integer>> pos_ini = new HashMap<>();  // diccionario para almacenar los chars recientemente vistos
        int n = src.length(); // tamaño de la secuencia de chars inicial

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
                int jn_aux = Math.min(i + matchLen, LOOKAHEAD);
                int jn = Math.min(jn_aux, n);
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
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(result);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] datacompressed = bos.toByteArray();

        return datacompressed;
    }

    public String decompress(byte[] compressedBytes) {
        LZSSData objcompressed = null;
        try {
            ByteArrayInputStream bs = new ByteArrayInputStream(compressedBytes);
            ObjectInputStream is = new ObjectInputStream(bs);
            try {
                objcompressed = (LZSSData) is.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            is.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        StringBuilder b = new StringBuilder();
        decompress_alg(objcompressed, b);

        return b.toString();
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

    public String getName()
    {
        return "LZSS";
    }
}