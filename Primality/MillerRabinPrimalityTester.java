//Alexander Weaver
//Last update: 6-1-2015 4:44pm
package Primality;

import java.math.BigInteger;

import Util.RandomInt;

//Controller object which performs the Miller-Rabin primality test
public class MillerRabinPrimalityTester {
    
    private static final BigInteger MINUS_ONE = new BigInteger("-1");
    private static final BigInteger ZERO = new BigInteger("0");
    private static final BigInteger ONE = new BigInteger("1");
    private static final BigInteger TWO = new BigInteger("2");
    
    //Takes an integer and a number of iterations
    //Returns a boolean indicating whether or not the integer passes the given amount of iterations of the miller-rabin primality test, with randomly chosen bases
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
    
    //Takes a prime and a base, both integers
    //Returns whether the given prime is a strong probable prime relative to the given base
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
    
    //Takes a prime and a base, both integers
    //Returns whether the given prime passes the Miller-Rabin primality test on the given base
    private boolean test(int prime, int base) {
        MRParameter params = new MRParameter(prime);
        return equation1(params.getN(), base, params.getD()) || equation2(params.getN(), base, params.getD(), params.getS());
    }
    
    //The first equation of the MR test, a^d % n = 1
    //Takes integers a, d, and n
    //Returns a boolean indicating whether or not the equation holds
    private boolean equation1(int n, int a, int d) {
        return ((int)Math.pow(a, d)) % n == 1;
    }
    
    //The second equation of the MR test
    //Takes integers a, d, n, and s
    //Returns a boolean indicating whether or not the equation holds
    private boolean equation2(int n, int a, int d, int s) {
        for(int r = 0; r < s; r++) {
            if(((int)Math.pow(a, d * (int)Math.pow(2, r)))%n == (-1%n + n)%n) {
                return true;
            }
        }
        return false;
    }
    
    //Overloaded isPrime() function
    //Takes an integer and a number of iterations, both in BigInteger format
    //Returns a boolean indicating whether or not the integer passes the given amount of iterations of the miller-rabin primality test, with randomly chosen bases
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
    
    //Takes a prime and a base, both integers in BigInteger format
    //Returns whether the given prime is a strong probable prime relative to the given base
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
    
    //Takes a prime and a base, both integers in BigInteger format
    //Returns whether the given prime passes the Miller-Rabin primality test on the given base
    private boolean test(BigInteger prime, BigInteger base) {
        BigMRParameter params = new BigMRParameter(prime);
        return equation1(params.getN(), base, params.getD()) || equation2(params.getN(), base, params.getD(), params.getS());
    }
    
    //The first equation of the MR test, a^d % n = 1
    //Takes integers a, d, and n in BigInteger format
    //Returns a boolean indicating whether or not the equation holds
    private boolean equation1(BigInteger n, BigInteger a, BigInteger d) {
        return a.modPow(d, n).compareTo(ONE) == 0;
    }
    
    //The second equation of the MR test
    //Takes integers a, d, n, and s in BigInteger format
    //Returns a boolean indicating whether or not the equation holds
    private boolean equation2(BigInteger n, BigInteger a, BigInteger d, BigInteger s) {
        BigInteger rh = ((MINUS_ONE.mod(n)).add(n)).mod(n);
        for(BigInteger r = new BigInteger("0"); r.compareTo(s) == -1; r = r.add(ONE)) {
            
            if(a.modPow(d.multiply(exp(TWO, r)), n).compareTo(rh) == 0) {
                return true;
            }
        }
        return false;
    }
    
    //Takes two BigIntegers, base and exp
    //Returns base^exp in BigInteger format
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
