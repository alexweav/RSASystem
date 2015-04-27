//Alexander Weaver
//Last update: 4-27-2015 10:33am
package Primality;

import java.math.BigInteger;
import java.util.Random;

public class PrimeGenerator {
    
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
