//Alexander Weaver
//Last update: 4-19-2015 1:13pm
package Util;

import java.math.BigInteger;



//Manager object which obtains the Legendre Symbol

//Legendre symbol definition:
//Let a be an integer and p be an odd prime
//(Legendre symbol a|p) mod p = a^((p-1)/2) mod p    and p is a member of {-1, 0, 1}
public class LegendreSymbolTester {
    
    private static final BigInteger ONE = new BigInteger("1");  //Constants
    private static final BigInteger TWO = new BigInteger("2");
    
    //Takes two integers a and p, returns the Legendre Symbol a|p
    public int getSymbol(int a, int p) {
        try {
            int val = (int)(((int)Math.pow(a, (p-1)/2)) % p);   
            if(val > 1) {           //Ensures p is a member of {-1, 0, 1} and provides proper result
                return -1;
            }
            return val;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid parameters: " + a + ", " + p, e);
        }
        
    }
    
    //Takes two numbers in BigInteger format a and p, returns the Legendre Symbol a|p
    public int getSymbol(BigInteger a, BigInteger p) {
        try {
            int val = (a.modPow((p.subtract(ONE)).divide(TWO), p)).byteValue();
            if(val > 1) {       //Ensures p is a member of {-1, 0, 1} and provides proper result
                return -1;
            }
            return 1;
        } catch(Exception e) {
            throw new IllegalArgumentException("Invalid parameters.", e);
        }
    }
}
