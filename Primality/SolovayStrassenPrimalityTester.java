//Alexander Weaver
//Last update: 4-29-2015 7:12pm
package Primality;

import java.math.BigInteger;

import Util.RandomInt;
import Util.JacobiSymbolTester;


public class SolovayStrassenPrimalityTester {
    
    private static final BigInteger ZERO = new BigInteger("0");
    private static final BigInteger ONE = new BigInteger("1");
    private static final BigInteger TWO = new BigInteger("2");
    
    public boolean isPrime(int prime, int iterations) {
        if(prime == 2) {
            return true;
        }
        if(prime % 2 == 0) {
            return false;
        }
        for(int i = 0; i < iterations; i++) {
            RandomInt rand = new RandomInt();
            int base = rand.randInt(1, prime - 1);
            if(!(test(prime, base))) {
                return false;
            }
        }
        return true;
    }
    
    private boolean test(int prime, int base) {
        JacobiSymbolTester jac = new JacobiSymbolTester();
        int rh = jac.getSymbol(base, prime);
        int lh = (int)Math.pow(base, ((prime - 1)/2));
        return !(rh == 0 || (lh % prime != ((rh%prime) + prime)%prime));
    }
    
    public boolean isPrime(BigInteger prime, int iterations) {
        if(prime.compareTo(TWO) == 0) {
            return true;
        }
        if(prime.mod(TWO).compareTo(ZERO) == 0) {
            return false;
        }
        for(int i = 0; i < iterations; i++) {
            RandomInt rand = new RandomInt();
            BigInteger base = rand.randBigInteger(ONE, prime.subtract(ONE));
            if(!test(prime, base)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean test(BigInteger prime, BigInteger base) {
        JacobiSymbolTester jac = new JacobiSymbolTester();
        int rhi = jac.getSymbol(base, prime);
        BigInteger rh = new BigInteger(Integer.toString(rhi));
        rh = ((rh.mod(prime)).add(prime)).mod(prime);
        BigInteger lh = (base.modPow((prime.subtract(ONE)).divide(TWO), prime)); 
        return !((rh.compareTo(ZERO) == 0) || (lh.compareTo(rh) != 0));
    }
}
