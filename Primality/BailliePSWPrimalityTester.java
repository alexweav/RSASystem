//Alexander Weaver
//Last update: 4-22-2015 11:12am
package Primality;

import Util.JacobiSymbolTester;

import java.math.BigInteger;

public class BailliePSWPrimalityTester {
    
    private static final BigInteger MINUS_ONE = new BigInteger("-1");
    private static final BigInteger ZERO = new BigInteger("0");
    private static final BigInteger ONE = new BigInteger("1");
    private static final BigInteger TWO = new BigInteger("2");
    private static final BigInteger FOUR = new BigInteger("4");
    private static final BigInteger FIVE = new BigInteger("5");
    
    public boolean isPrime(int prime) {
        if(prime < 2) {
            return false;
        }
        if(prime == 2) {
            return true;
        }
        if(prime%2 == 0) {
            return false;
        }
        MillerRabinPrimalityTester mRTester = new MillerRabinPrimalityTester();
        if(!mRTester.isStrongProbablePrime(prime, 2)) {
            return false;
        }
        int d = getD(prime);        //This is the wrong way of calculating parameters.  View http://www.trnicely.net/misc/bpsw.html and implement this
        int p = 1;
        int q = (int)((1 - d)/4);
        LucasPrimalityTester lTester = new LucasPrimalityTester();
        return lTester.test(prime, p, q);
    }
    
    private int getD(int n) {        //change
        int i = 1;
        JacobiSymbolTester jTester = new JacobiSymbolTester();
        while(true) {
            if(i == 1) {
                i = 5;
            } else if (i > 0) {
                i *= -1;
                i -= 2;
            } else if (i < 0) {
                i *= -1;
                i += 2;
            }
            if(jTester.getSymbol(i, n) == -1) {
                return i;
            }
        }
    }
    
    public boolean isPrime(BigInteger prime) {
        if(prime.compareTo(TWO) == -1) {
            return false;
        }
        if(prime.compareTo(TWO) == 0) {
            return true;
        }
        if(prime.mod(TWO).compareTo(ZERO) == 0) {
            return false;
        }
        /*if(isPerfectSquare(prime)) {
            return false;
        }*/
        MillerRabinPrimalityTester mRTester = new MillerRabinPrimalityTester();
        if(!mRTester.isStrongProbablePrime(prime, TWO)) {
            return false;
        }
        
        BigInteger d = getD(prime);
        BigInteger p = ONE;
        BigInteger q = ONE.subtract(d).divide(FOUR);
        LucasPrimalityTester lTester = new LucasPrimalityTester();
        return lTester.test(prime, p, q);
    }
    
    private BigInteger getD(BigInteger n) {
        BigInteger i = ONE;
        JacobiSymbolTester jTester = new JacobiSymbolTester();
        while(true) {
            if(i.compareTo(ONE) == 0) {
                i = new BigInteger("5");
            } else if (i.compareTo(ZERO) == 1) {
                i = i.multiply(MINUS_ONE);
                i = i.subtract(TWO);
            } else if (i.compareTo(ZERO) == -1) {
                i = i.multiply(MINUS_ONE);
                i = i.add(TWO);
            }
            if(jTester.getSymbol(i, n) == -1) {
                return i;
            }
        }
    }
    
   /* private boolean isPerfectSquare(BigInteger prime) {
        
    }*/
}
