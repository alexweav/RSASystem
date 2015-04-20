//Alexander Weaver
//Last update: 4-20-2015 2:39am
package Primality;

import java.math.BigInteger;

import Util.RandomInt;

//Manager object which determines primality probabilistically, using the Fermat Primality Test
public class FermatPrimalityTester {
    
    private static final BigInteger ZERO = new BigInteger("0"); //constants
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
        if(prime == 2) {    //2 is prime
            return true;
        }
        if(prime%2 == 0) {  //even numbers that are not 2 are not prime
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
    //Returns true if the given value and base passes a single instance of the Fermat Primality Test, returns false otherwise
    private boolean test(int prime, int base) {
        int lhpm = (int) Math.pow(base, (prime - 1));
        int lh = lhpm % prime;
        int rh = 1 % prime;
        return lh == rh;
    }
    
    //Takes an int to be tested in BigInteger format, and a positive number of iterations
    //The more iterations, the higher probability of accuracy
    //Returns a boolean indicating the outcome of the test
    public boolean isPrime(BigInteger prime, int iterations) {
        if(iterations < 1) {
            throw new IllegalArgumentException("Number of iterations must be greater than 0.");
        }
        if(prime.compareTo(TWO) == -1) {  //anything less than 2 is not prime
            return false;
        }
        if(prime.compareTo(TWO) == 0) {     //2 is prime
            return true;
        }
        if(prime.mod(TWO).compareTo(ZERO) == 0) {   //even numbers that are not 2 are not prime
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
        BigInteger lh = base.modPow(prime.subtract(ONE), prime);
        BigInteger rh = (ONE).mod(prime);
        return lh.compareTo(rh) == 0;
    }
}
