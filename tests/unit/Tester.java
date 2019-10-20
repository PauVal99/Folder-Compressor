package tests.unit;

import java.lang.RuntimeException;
import java.util.Arrays;

public class Tester
{
    public void assertEqualBytes(byte[] b1, byte[] b2)
    {
        if(!Arrays.equals(b1,b2)){ 
            throw new RuntimeException("Byte arrays non equal.");
        }
    }

    public void assertEqualStirng(String s1, String s2)
    {
        if(!s1.equals(s2)) throw new RuntimeException("Operands non equal. "+s1+"\n - \n"+s2);
    }

    public void imGoingTo(String s)
    {
        System.out.print("Test: " + s + "\n");
    }

    public void completed()
    {
        System.out.print("  Passed! ✓\n");
    }
}