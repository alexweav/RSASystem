//Alexander Weaver
//Last update: 4-20-2015 9:55pm
package Primality;

import java.math.BigInteger;

import Util.RandomInt;

public class MillerRabinPrimalityTester {
    
    private static final BigInteger MINUS_ONE = new BigInteger("-1");
    private static final BigInteger ZERO = new BigInteger("0");
    private static final BigInteger ONE = new BigInteger("1");
    private static final BigInteger TWO = new BigInteger("2");
    
    public boolean isPrime(int prime, int iterations) {
        if(prime < 2) {
            return false;
        }
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
    
    public boolean isStrongProbablePrime(int prime, int base) {
        if(prime < 2) {
            return false;
        }
        if(prime == 2) {
            return true;
        }
        if(prime % 2 == 0) {
            return false;
        }
        return test(prime, base);
    }
    
    private boolean test(int prime, int base) {
        MRParameter params = new MRParameter(prime);
        return equation1(params.getN(), base, params.getD()) || equation2(params.getN(), base, params.getD(), params.getS());
    }
    
    private boolean equation1(int n, int a, int d) {
        return ((int)Math.pow(a, d)) % n == 1;
    }
    
    private boolean equation2(int n, int a, int d, int s) {
        for(int r = 0; r < s; r++) {
            if(((int)Math.pow(a, d * (int)Math.pow(2, r)))%n == (-1%n + n)%n) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isPrime(BigInteger prime, int iterations) {
        if(prime.compareTo(TWO) == -1) {
            return false;
        }
        if(prime.compareTo(TWO) == 0) {
            return true;
        }
        if(prime.mod(TWO).compareTo(ZERO) == 0) {
            return false;
        }
        for(int i = 0; i < iterations; i++) {
            RandomInt rand = new RandomInt();
            BigInteger base = rand.randBigInteger(TWO, prime.subtract(ONE));
            if(!(test(prime, base))) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isStrongProbablePrime(BigInteger prime, BigInteger base) {
        if(prime.compareTo(TWO) == -1) {
            return false;
        }
        if(prime.compareTo(TWO) == 0) {
            return true;
        }
        if(prime.mod(TWO).compareTo(ZERO) == 0) {
            return false;
        }
        return test(prime, base);
    }
    
    private boolean test(BigInteger prime, BigInteger base) {
        BigMRParameter params = new BigMRParameter(prime);
        return equation1(params.getN(), base, params.getD()) || equation2(params.getN(), base, params.getD(), params.getS());
    }
    
    private boolean equation1(BigInteger n, BigInteger a, BigInteger d) {
        return a.modPow(d, n).compareTo(ONE) == 0;
    }
    
    private boolean equation2(BigInteger n, BigInteger a, BigInteger d, BigInteger s) {
        BigInteger rh = ((MINUS_ONE.mod(n)).add(n)).mod(n);
        for(BigInteger r = new BigInteger("0"); r.compareTo(s) == -1; r = r.add(ONE)) {
            
            if(a.modPow(d.multiply(exp(TWO, r)), n).compareTo(rh) == 0) {
                return true;
            }
        }
        return false;
    }
    
    public BigInteger exp(BigInteger base, BigInteger exp) {
        BigInteger result = base;
        if(exp.compareTo(ZERO) == 0) {
            return ONE;
        }
        for(BigInteger i = new BigInteger("1"); i.compareTo(exp) == -1; i = i.add(ONE)) {
            result = result.multiply(base);
        }
        return result;
    }
}
