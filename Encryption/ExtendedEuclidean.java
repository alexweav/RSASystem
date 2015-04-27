//Alexander Weaver
//Last update: 4-27-2015 6:01pm
package Encryption;

import java.math.BigInteger;

import Util.Stack;

public class ExtendedEuclidean {
    
    private static final BigInteger ZERO = new BigInteger("0");
    private static final BigInteger ONE = new BigInteger("1");

    public int getModularInverse(int value, int modulus) {
        int[] values = getExtendedGCD(value, modulus);
        if(values[0] != 1) {
            return -1; //does not exist
        } else {
            int result = values[1]%modulus;
            if(result < 0) {
                result = result + modulus;
            }
            return result;
        }
    }
    
    private int[] getExtendedGCD(int value, int modulus) {
        int x = 0;
        int y = 1;
        int u = 1;
        int v = 0;
        int q, r, m, n;
        while(value != 0) {
            q = modulus/value;
            r = modulus%value;
            m = x - u * q;
            n = y - v * q;
            modulus = value;
            value = r;
            x = u;
            y = v;
            u = m;
            v = n;
        }
        int[] result = {modulus, x, y};
        return result;
    }
    
    public BigInteger getModularInverse(BigInteger value, BigInteger modulus) {
        BigInteger[] values = getExtendedGCD(value, modulus);
        if(values[0].compareTo(ONE) != 0) {
            return null; //does not exist
        } else {
            BigInteger result = values[1].mod(modulus);
            if(result.compareTo(ZERO) == -1) {
                result = result.add(modulus);
            }
            return result;
        }
    }
    
    private BigInteger[] getExtendedGCD(BigInteger value, BigInteger modulus) {
        BigInteger x = new BigInteger("0");
        BigInteger y = new BigInteger("1");
        BigInteger u = new BigInteger("1");
        BigInteger v = new BigInteger("0");
        BigInteger q, r, m, n;
        while(value.compareTo(ZERO) != 0) {
            q = modulus.divide(value);
            r = modulus.mod(value);
            m = x.subtract(u.multiply(q));
            n = y.subtract(v.multiply(q));
            modulus = value;
            value = r;
            x = u;
            y = v;
            u = m;
            v = n;
        }
        BigInteger[] result = {modulus, x, y};
        return result;
    }
    
    
}
