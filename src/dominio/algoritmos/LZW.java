package src.dominio.algoritmos;

import java.util.*;

import src.persistencia.*;

public class LZW extends Algorithm
{
    public byte[] compress(UncompressedFile uncompressed)
    {
        int dictSize = 256;
        Map<String,Integer> dictionary = new HashMap<String,Integer>();
        for (int i = 0; i < 256; i++)
            dictionary.put("" + (char)i, i);

        String w = "";
        byte[] result = new byte[0];
        char c;
        while((c = uncompressed.readChar()) != 0){
            String wc = w + c;
            if (dictionary.containsKey(wc))
                w = wc;
            else {
                dictionary.put(wc, dictSize++);
                result = concatenate(result, intToByteArray(dictionary.get(w)));
                w = "" + c;
            }
        }

        if (!w.equals(""))
            result = concatenate(result, intToByteArray(dictionary.get(w)));
        return result;
    }
    
    public byte[] decompress(CompressedFile compressed) {
        int dictSize = 256;
        Map<Integer,String> dictionary = new HashMap<Integer,String>();
        for (int i = 0; i < 256; i++)
            dictionary.put(i, "" + (char)i);

        byte[] bc;
        String w = "" + (char)byteArrayToInt(compressed.readContent(4));
        StringBuffer result = new StringBuffer(w);
        
        while((bc = compressed.readContent(4)).length != 0){
            int k = byteArrayToInt(bc);
            String entry;
            if (dictionary.containsKey(k))
                entry = dictionary.get(k);
            else if (k == dictSize)
                entry = w + w.charAt(0);
            else
                throw new IllegalArgumentException("Bad compressed k: " + k);
 
            result.append(entry);
 
            dictionary.put(dictSize++, w + entry.charAt(0));
 
            w = entry;
        }

        return result.toString().getBytes();
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
        return "LZW";
    }
}