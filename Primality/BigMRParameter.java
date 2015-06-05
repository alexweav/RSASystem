//Alexander Weaver
//Last update: 6-1-2015 4:47pm
package Primality;

import java.math.BigInteger;

//Set of parameters for the Miller-Rabin primality test, in BigInteger format
public class BigMRParameter {
    
    private static final BigInteger ZERO = new BigInteger("0");
    private static final BigInteger ONE = new BigInteger("1");
    private static final BigInteger TWO = new BigInteger("2");
    
    private BigInteger n;
    private BigInteger s;
    private BigInteger d;
    
    //gets
    public BigInteger getN() {
        return n;
    }
    
    public BigInteger getS() {
        return s;
    }
    
    public BigInteger getD() {
        return d;
    }
    
    //Takes odd integer in BigInteger format pr>= 2 to be used as n 
    //Sets s and d according to the rule n-1=d(2^s)
    //It is guaranteed that s and d exist for all values for which the function is defined
    public void setValue(BigInteger pr) {
        if(pr.mod(TWO).compareTo(ZERO) == 0) {
            throw new IllegalArgumentException("Value must be odd.");
        }
        if(pr.compareTo(TWO) < 1) {
            throw new IllegalArgumentException("Value must be greater than or equal to 2.");
        }
        
        n = pr;
        n = n.subtract(ONE);
        s = ZERO;
        while(n.mod(TWO).compareTo(ZERO) == 0) {
            n = n.divide(TWO);
            s = s.add(ONE);
        }
        d = n;
        n = pr;
    }
    
    public BigMRParameter(BigInteger pr) {
        setValue(pr);
    }
}
