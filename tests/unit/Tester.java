package tests.unit;

import java.lang.RuntimeException;

public class Tester
{
    public void assertEqualStirng(String s1, String s2)
    {
        if(s1 != s2) throw new RuntimeException("Operands non equal."+s1+" - "+s2);
    }
}