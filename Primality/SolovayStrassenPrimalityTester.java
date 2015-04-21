//Alexander Weaver
//Last update: 4-20-2015 9:55pm
package Primality;

import java.math.BigInteger;

import Util.RandomInt;
import Util.JacobiSymbolTester;

//Manager object which determines primality probabilistically, using the Solovay-Strassen Primality Test
public class SolovayStrassenPrimalityTester {
    
    private static final BigInteger ZERO = new BigInteger("0");
    private static final BigInteger ONE = new BigInteger("1");
    private static final BigInteger TWO = new BigInteger("2");
    
    //Takes an int to be tested and a positive number of iterations.
    //The more iterations, the higher probability of accuracy
    //Returns a boolean indicating the outcome of the test
    public boolean isPrime(int prime, int iterations) {
        if(iterations < 1) {    //ensures positive iterations
            throw new IllegalArgumentException("Number of iterations must be greater than 0.");
        }
        if(prime < 2) {     //Anything less than 2 is not prime
            return false;
        }
        if(prime == 2) {        //2 is prime
            return true;
        }
        if(prime % 2 == 0) {    //Anything divisible by 2 that is not 2 is not prime
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
    
    //Takes an int to be tested and an int to be used as the base
    //Returns true if the given value and base passes a single instance of the Solovay-Strassen Primality Test, returns false otherwise
    private boolean test(int prime, int base) {
        JacobiSymbolTester jac = new JacobiSymbolTester();
        int rh = jac.getSymbol(base, prime);
        int lh = (int)Math.pow(base, ((prime - 1)/2));
        return !(rh == 0 || (lh % prime != ((rh%prime) + prime)%prime));
    }
    
    //Takes an int to be tested in BigInteger format, and a positive number of iterations
    //The more iterations, the higher probability of accuracy
    //Returns a boolean indicating the outcome of the test
    public boolean isPrime(BigInteger prime, int iterations) {
        if(iterations < 1) {    //ensures positive iterations
            throw new IllegalArgumentException("Number of iterations must be greater than 0.");
        }
        if(prime.compareTo(TWO) == -1) {  //anything less than 2 is not prime
            return false;
        }
        if(prime.compareTo(TWO) == 0) {     //2 is prime
            return true;
        }
        if(prime.mod(TWO).compareTo(ZERO) == 0) { //Anything that is divisible by 2 that is not 2 is not prime
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
    
    //Takes an int to be tested (in BigInteger format) and a BigInteger to be used as the base
    //Returns true if the given value and base passes a single instance of the Fermat Primality Test, returns false otherwise
    private boolean test(BigInteger prime, BigInteger base) {
        JacobiSymbolTester jac = new JacobiSymbolTester();
        int rhi = jac.getSymbol(base, prime);
        BigInteger rh = new BigInteger(Integer.toString(rhi));
        rh = ((rh.mod(prime)).add(prime)).mod(prime);
        BigInteger lh = (base.modPow((prime.subtract(ONE)).divide(TWO), prime)); 
        return !((rh.compareTo(ZERO) == 0) || (lh.compareTo(rh) != 0));
    }
}
