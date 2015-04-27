//Alexander Weaver
//Last update: 4-27-2015 2:33pm
package Euclidean;

import java.math.BigInteger;

class EuclideanEquation {
    
    protected int dividend;
    protected int divisor;
    protected int quotient;
    protected int remainder;
    
    protected BigInteger bDividend;
    protected BigInteger bDivisor;
    protected BigInteger bQuotient;
    protected BigInteger bRemainder;
    
    //gets
    protected int getDividend() {
        return dividend;
    }
    
    protected int getDivisor() {
        return divisor;
    }
    
    protected int getQuotient() {
        return quotient;
    }
    
    protected int getRemainder() {
        return remainder;
    }
    
    protected BigInteger getBDividend() {
        return bDividend;
    }
    
    protected BigInteger getBDivisor() {
        return bDivisor;
    }
    
    protected BigInteger getBQuotient() {
        return bQuotient;
    }
    
    protected BigInteger getBRemainder() {
        return bRemainder;
    }
}
