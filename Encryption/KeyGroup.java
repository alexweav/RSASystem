//Alexander Weaver
//Last update: 4-27-2015 10:50am
package Encryption;

import java.math.BigInteger;

import Primality.PrimeGenerator;

public class KeyGroup {
    
    private static final BigInteger ONE = new BigInteger("1");
    
    private BigInteger factor1;
    private BigInteger factor2;
    private BigInteger privateKey;
    private BigInteger publicKey;
    
    //gets
    public BigInteger getFactor1() {
        return factor1;
    }
    
    public BigInteger getFactor2() {
        return factor2;
    }
    
    public BigInteger getPrivateKey() {
        return privateKey;
    }
    
    public BigInteger getPublicKey() {
        return publicKey;
    }
    
    public KeyGroup(int bitLength) {
        int iterations = 10; //This will eventually be calculated. placeholder value.
        PrimeGenerator generator = new PrimeGenerator();
        factor1 = generator.generatePrime(bitLength, iterations);
        factor2 = generator.generatePrime(bitLength, iterations);
        while(factor1.compareTo(factor2) == 0) {
            factor2 = generator.generatePrime(bitLength, iterations);
        }
        publicKey = factor1.multiply(factor2);
        privateKey = publicKey.subtract(factor1.add(factor2).subtract(ONE));
    }
}
