package controllers.algorithms;

import java.util.*;

public class LZW implements IAlgorithm
{
    public String compress(String uncompressed)
    {
        // Build the dictionary.
        int dictSize = 128;
        Map<String,Integer> dictionary = new HashMap<String,Integer>();
        for (int i = 0; i < 128; i++)
            dictionary.put("" + (char)i, i);
 
        String w = "";
        String result = "";
        for (char c : uncompressed.toCharArray()) {
            String wc = w + c;
            if (dictionary.containsKey(wc))
                w = wc;
            else {
                result = result + dictionary.get(w);
                // Add wc to the dictionary.
                dictionary.put(wc, dictSize++);
                w = "" + c;
            }
        }
 
        // Output the code for w.
        if (!w.equals(""))
            result = result + dictionary.get(w);
        return result;
    }
    
    public String decompress(String compressed)
    {
        // Build the dictionary.
        int dictSize = 128;
        Map<Integer,String> dictionary = new HashMap<Integer,String>();
        for (int i = 0; i < 128; i++)
            dictionary.put(i, "" + (char)i);
 
        String w = "";// + compressed.substring(1);
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
 
            // Add w+entry[0] to the dictionary.
            dictionary.put(dictSize++, w + entry.charAt(0));
 
            w = entry;
        }
        return result.toString();
    }
}