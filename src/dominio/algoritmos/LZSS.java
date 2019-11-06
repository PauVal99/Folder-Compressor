package src.dominio.algoritmos;

import java.io.*;
import java.util.*;

public class LZSS extends Algorithm
{
    public byte[] compress(String uncompressed)
    {
        int windowSize = WINDOW;  //4KBytes
        LZSSData result = window_compress(uncompressed, windowSize);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(result);
        oos.flush();
        byte [] datacompressed = bos.toByteArray();

        return datacompressed;
    }

    public static LZSSData window_compress(CharSequence src, int windowSize) {
        //Inicializacion de datos
        BitSet match = new BitSet();                 //flag --> nuevo conjunto de bits inicializados a false
        StringBuilder out = new StringBuilder();     //offset --> caracters de retorno
        int size = 0;                                //tamaño de retorno
        Map<Character, List<Integer>> startPoss = new HashMap<>();
        int n = src.length();                        //tamaño de la secuencia de chars

        for(int i = 0; i < n; i++){
            char target = src.charAt(i);             //char actual -> posicio i
            // Encontrar el match mas grande
            boolean found = false;
            int start = 0;
            int matchLen = 0;                        //tamaño del match
            List<Integer> poss = startPoss.get(target);
            if(poss != null){
                Iterator<Integer> it = poss.iterator();
                while(it.hasNext()){
                    int s = it.next();               //s = valor de la posicion actual del iterador
                    if((i - s) > windowSize){        //comprobamos que no sobrepase el SearchBuffer
                        it.remove();
                        continue;  // se salta una iteracion si se cumpla una cierta condicion
                    }
                    int len = getMatchedLen(src, s + 1, i + 1, n) + 1;
                    if(len > matchLen){
                        start = i - s;
                        matchLen = len;
                    }
                    found = true;
                }
                poss.add(i);
                int jn = Math.min(i + matchLen, n);
                for(int j = i + 1; j < jn; j++){
                    List<Integer> p = startPoss.get(src.charAt(j));
                    if(p == null){
                        p = new LinkedList<>();       //Integer no se necesita especificar
                        startPoss.put(src.charAt(j), p);
                    }
                    p.add(j);
                }
            } else{
                poss = new LinkedList<>();
                poss.add(i);
                startPoss.put(target, poss);
            }
            if(found && matchLen > 1){
                match.set(size);
                out.append((char)start).append((char)matchLen);
                //System.out.println(start + "," + matchLen );
                i += matchLen - 1;
            } else{
                match.set(size, false);
                out.append(target);
            }
            size++;
        }
        return new LZSSData(match, out, size);
    }

    public String decompress(byte[] compressedBytes) throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream bs= new ByteArrayInputStream(compressedBytes);
        ObjectInputStream is = new ObjectInputStream(bs);
        LZSSData objcompressed = (LZSSData)is.readObject();
        is.close();

        StringBuilder b = new StringBuilder();
        final_decompress(objcompressed, b);

        return b.toString();
    }

    public static void final_decompress(LZSSData src, StringBuilder out){
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

    public static class LZSSData implements Serializable{
        public LZSSData(BitSet match, StringBuilder dest, int size) {  // <flag, offset, lenght>
            this.match = match;
            this.dest = dest;
            this.size = size;
        }
        private BitSet match;
        private StringBuilder dest;
        private int size;
    }

    private static int getMatchedLen(CharSequence src, int i1, int i2, int end){
        int n = Math.min(i2 - i1, end - i2);
        for(int i = 0; i < n; i++){
            if(src.charAt(i1++) != src.charAt(i2++)) return i;
        }
        return 0;
    }

    public String getName()
    {
        return "LZSS";
    }

    private static final int WINDOW = 4096;
}