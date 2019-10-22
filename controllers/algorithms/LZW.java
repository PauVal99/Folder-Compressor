package controllers.algorithms;

import java.util.*;

public class LZW extends Algorithm
{
    public byte[] compress(String uncompressed)
    {
        int dictSize = 256;
        Map<String,Character> dictionary = new HashMap<String,Character>();
        for (int i = 0; i < 256; i++)
            dictionary.put("" + (char)i, (char)i);

        String w = "";
        String result = "";
        for (char c : uncompressed.toCharArray()) {
            String wc = w + c;
            if (dictionary.containsKey(wc))
                w = wc;
            else {
                dictionary.put(wc, (char)dictSize++);
                result = result + dictionary.get(w);
                w = "" + c;
            }
        }
        if (!w.equals(""))
            result = result + dictionary.get(w);
        return result.getBytes();
    }
    
    public String decompress(byte[] compressedBytes) {
        int dictSize = 256;
        Map<Character,String> dictionary = new HashMap<Character,String>();
        for (int i = 0; i < 256; i++)
            dictionary.put((char)i, "" + (char)i);

        String compressed = new String(compressedBytes);
        if(compressed.length() == 0) return "";
        String w = "" + compressed.charAt(0); compressed = compressed.substring(1);
        StringBuffer result = new StringBuffer(w);
        for (char k : compressed.toCharArray()) {
            String entry;
            if (dictionary.containsKey(k))
                entry = dictionary.get(k);
            else if (k == dictSize)
                entry = w + w.charAt(0);
            else
                throw new IllegalArgumentException("Bad compressed k: " + k);
 
            result.append(entry);
 
            dictionary.put((char) dictSize++, w + entry.charAt(0));
 
            w = entry;
        }
        return result.toString();
    }

    public String getName()
    {
        return "LZW";
    }
}