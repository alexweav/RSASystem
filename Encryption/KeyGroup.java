//Alexander Weaver
//Last update: 5-8-2015 6:24pm
package Encryption;

import java.math.BigInteger;

import Primality.PrimeGenerator;

public class KeyGroup {
    
    private static final BigInteger ONE = new BigInteger("1");
    
    private BigInteger publicKey;
    private int keyExponent;
    private BigInteger privateKey;
    
    //gets
    public BigInteger getPublicKey() {
        return publicKey;
    }
    
    public int getKeyExponent() {
        return keyExponent;
    }
    
    public BigInteger getPrivateKey() {
        return privateKey;
    }
    
    //generates a new key set (randomly) given a specified bit length
    //the public key will be bitLength bits long
    public KeyGroup(int bitLength) {
        BigInteger factor1;
        BigInteger factor2;
        BigInteger totient;
        int iterations = 100; //This will eventually be calculated. placeholder value.
        PrimeGenerator generator = new PrimeGenerator();
        factor1 = generator.generatePrime(bitLength, iterations);
        factor2 = generator.generatePrime(bitLength, iterations);
        while(factor1.compareTo(factor2) == 0) {
            factor2 = generator.generatePrime(bitLength, iterations);
        }
        publicKey = factor1.multiply(factor2);
        totient = publicKey.subtract(factor1.add(factor2).subtract(ONE));
        keyExponent = 65537;  //Temporary value, commonly chosen for key exponent
        ExtendedEuclidean euclidean = new ExtendedEuclidean();
        privateKey = euclidean.getModularInverse(new BigInteger(Integer.toString(keyExponent)), totient);
    }
    
    public KeyGroup (BigInteger nPublicKey, BigInteger nPrivateKey, int nKeyExponent) {
        publicKey = nPublicKey;
        privateKey = nPrivateKey;
        keyExponent = nKeyExponent;
    }
}
