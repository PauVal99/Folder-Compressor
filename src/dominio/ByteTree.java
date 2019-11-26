package src.dominio;

import java.util.*;

public class ByteTree
{
    private Node root;

    public ByteTree()
    {
        root = new Node();
        root.exists = true;
    }

    public boolean contains(byte[] b)
    {
        return i_contains(root, b);
    }

    public boolean i_contains(Node act, byte[] b)
    {
        if( act.children.get(b[0] & 0xFF).exists ){
            return i_contains(act.children.get(b[0] & 0xFF), Arrays.copyOfRange(b, 1, b.length));
        }

        else{
            Node n = new Node();
            n.exists = true;
            n.cod = cod;
            act.children.set(b[0] & 0xFF, n);
        }
    }

    public int get(byte[] b)
    {
 
    }

    public void put(byte[] b, int cod)
    {
        i_put(root,b,cod);
    }

    public void i_put(Node act, byte[] b, int cod)
    {
        if( act.children.get(b[0] & 0xFF).exists ){
            i_put(act.children.get(b[0] & 0xFF), Arrays.copyOfRange(b, 1, b.length),cod);
        }

        else{
            Node n = new Node();
            n.exists = true;
            n.cod = cod;
            act.children.set(b[0] & 0xFF, n);
        }
    }

    public static class Node {
        private boolean exists = false;
        private int cod;
        private Node parent;
        private List<Node> children = new ArrayList<Node>(256);
    }
}