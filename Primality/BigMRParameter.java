
package Primality;

import java.math.BigInteger;
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
