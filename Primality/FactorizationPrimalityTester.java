//Alexander Weaver
//Last update: 4-19-2015 1:18pm
package Primality;

import java.math.BigInteger;

//Manager object for determining primality of a number
//Deterministic, guaranteed to be accurate
//EXTREMELY SLOW. Only use for small numbers, not for practice
public class FactorizationPrimalityTester {
    
    private static final BigInteger ZERO = new BigInteger("0"); //Constants
    private static final BigInteger ONE = new BigInteger("1");
    private static final BigInteger TWO = new BigInteger("2");
    
    //Takes an integer n
    //Returns true if the n is prime, false otherwise
    public boolean isPrime(int n) {
        for(int i = 2; i < n; i++) {
            if(n % i == 0) {
                return false;
            }
        }
        return true;
    }
    
    //Takes an integer n in BigInteger format
    //Returns true if n is prime, false otherwise
    public boolean isPrime(BigInteger n) {
        for(BigInteger i = new BigInteger("2"); i.compareTo(n) == -1; i.add(ONE)) {
            if(n.mod(i).compareTo(ZERO) == 0) {
                return false;
            }
        }
        return true;
    }
}
