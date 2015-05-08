//Alexander Weaver
//Last updated: 4-27-2015 10:46pm
package Encryption;

import java.math.BigInteger;

public class Encryptor {
    
    private static final BigInteger ZERO = new BigInteger("0");
    private static final BigInteger ONE = new BigInteger("1");
    private static final BigInteger TWO = new BigInteger("2");
    
    //Encrypts a message with a given exponent and public key
    //Takes the message, exponent, and key
    //Outputs the cipher message
    public BigInteger encrypt(BigInteger message, int exponent, BigInteger publicKey) {
        return modularPow(message, exponent, publicKey);
    }
    
    //Decrypts a cipher message using its corresponding public and private keys
    //Takes the cipher, the private key, and the public key
    //Outputs the original message, provided correct values
    public BigInteger decrypt(BigInteger cipher, BigInteger privateKey, BigInteger publicKey) {
        return modularPow(cipher, privateKey, publicKey);
    }
    
    //computes (base^exponent) mod modulus
    //rapid, low memory cost
    private int modularPow(int base, int exponent, int modulus) {
        int result = 1;
        base = base%modulus;
        while(exponent > 0) {
            if(exponent%2 == 1) {
                result = (result*base)%modulus;
            }
            exponent = exponent >> 1;
            base = (base*base)%modulus;
        }
        return result;
    }
    
    //version of modularPow(base, exponent, modulus) above
    //compatible with BigInteger base and modulus, and int exponent
    public BigInteger modularPow(BigInteger base, int exponent, BigInteger modulus) {
        BigInteger result = new BigInteger("1");
        base = base.mod(modulus);
        while(exponent > 0) {
            if(exponent%2 == 1) {
                result = (result.multiply(base)).mod(modulus);
            }
            exponent = exponent >> 1;
            base = (base.multiply(base)).mod(modulus);
        }
        return result;
    }
    
    //version of modularPow(base, exponent, modulus) above
    //compatible with all arguments being in BigInteger format
    public BigInteger modularPow(BigInteger base, BigInteger exponent, BigInteger modulus) {
        BigInteger result = new BigInteger("1");
        base = base.mod(modulus);
        while(exponent.compareTo(ZERO) == 1) {
            if(exponent.mod(TWO).compareTo(ONE) == 0) {
                result = (result.multiply(base)).mod(modulus);
            }
            exponent = exponent.shiftRight(1); 
            base = (base.multiply(base)).mod(modulus);
        }
        return result;
    }
}
