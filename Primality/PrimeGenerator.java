/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Primality;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author Alexander
 */
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
            mRTester.isPrime(candidate, iterations);
        }
    }
}
