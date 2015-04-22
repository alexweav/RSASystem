//Alexander Weaver
//Last update: 4-22-2015 1:37am
package Primality;

import Util.JacobiSymbolTester;

import java.math.BigInteger;

public class BailliePSWPrimalityTester {
    
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
        int p = (int)Math.sqrt(d - 4);
        int q = -1;
        LucasPrimalityTester lTester = new LucasPrimalityTester();
        return lTester.test(prime, p, q);
    }
    
    public int getD(int n) {        //change
        int d;
        int i = 1;
        JacobiSymbolTester jTester = new JacobiSymbolTester();
        while(true) {
            d = (int)Math.pow(i, 2) + 4;
            if(jTester.getSymbol(d, n) == -1) {
                return d;
            }
        }
    }
}
