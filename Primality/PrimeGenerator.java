//Alexander Weaver
//Last update: 6-1-2015 4:36pm
package Primality;

import java.math.BigInteger;
import java.util.Random;

//Object which produces random probably prime BigIntegers 
public class PrimeGenerator {
    
    //Given an integer bit length and integer iterations,
    //Returns a probably prime BigInteger of the given bit length
    //The Miller-Rabin primality test is used to determine primality, and an increased number of iterations increases the probability of accuracy
    public BigInteger generatePrime(int bitLength, int iterations) {
        if(bitLength < 2) {
            throw new IllegalArgumentException("Bit length must be greater than 1");
        }
        MillerRabinPrimalityTester mRTester = new MillerRabinPrimalityTester();
        Random rand = new Random();
        BigInteger candidate;
        while(true) {
            candidate = new BigInteger(bitLength, rand);
            if(mRTester.isPrime(candidate, iterations)) {
                return candidate;
            }
        }
    }
}
