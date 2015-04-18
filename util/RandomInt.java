//Alexander Weaver
//Last update: 4-18-2015 5:30pm
package Util;

import java.math.BigInteger;
import java.util.Random;

//Manager class for creating random integers
//Creates random integers based upon a given range
//Supports BigInteger format
public class RandomInt {
    
    //Produces a random integer on the range [min, max)
    public int randInt(int min, int max) {
        try {
            Random rand = new Random();
            return rand.nextInt((max - min) + 1) + min;
        } catch (Exception e) {
            throw new IllegalArgumentException("max must be greater than or equal to min.", e);
        }
    }
    
    //Produces a random integer in BigInteger format on the range [min, max)
    public BigInteger randBigInteger(BigInteger min, BigInteger max) {
        if(max.compareTo(min) < 0) {
            throw new IllegalArgumentException("max must be greater than or equal to min.");
        }
        BigInteger value;
        Random rand = new Random();
        int minLength = min.bitLength();
        int maxLength = max.bitLength();
        while(true) {
            value = new BigInteger(maxLength, rand);
            if(!(value.compareTo(min) >= 0)) {
                continue;
            }
            if(!(value.compareTo(max) < 1)) {
                continue;
            }
            return value;
        }
    }
}
