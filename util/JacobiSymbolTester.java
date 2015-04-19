//Alexander Weaver
//Last update: 4-19-2015 4:45pm
package Util;

import java.math.BigInteger;

//Manager object which obtains the Jacobi Symbol
public class JacobiSymbolTester {
    
    private static final BigInteger ZERO = new BigInteger("0");
    private static final BigInteger ONE = new BigInteger("1");
    private static final BigInteger TWO = new BigInteger("2");
    private static final BigInteger THREE = new BigInteger("3");
    private static final BigInteger FOUR = new BigInteger("4");
    private static final BigInteger FIVE = new BigInteger("5");
    private static final BigInteger EIGHT = new BigInteger("8");
    
    //Using the algorithm from https://primes.utm.edu/glossary/page.php?sort=JacobiSymbol
    //Takes an integer a and an odd number n, returns the jacobi symbol (a|n)
    public int getSymbol(int a, int n) {
        
        if(n%2 == 0) {      //Ensures odd n value
            throw new IllegalArgumentException("The second parameter must be odd.");
        }
            
        int j = 1;
        while (a != 0) {
            while (a % 2 == 0) {
                a = a/2;
                if(n%8 == 3%8 || n%8 == 5%8) {
                    j = -j;
                }
            }
            //interchange(a, n)
            int buffer = a;
            a = n;
            n = buffer;
            
            if(a%4 == 3%4 && n%4 == 3%4) {
                j = -j;
            }
            a = a%n;
        }
        if(n == 1) {
            return j;
        } else {
            return 0;
        }
    }
    
    //Takes integer a and integer n, both in BigInteger format, and returns the jacobi symbol (a|n)
    public int getSymbol(BigInteger a, BigInteger n) {
        if(n.mod(TWO).compareTo(ZERO) == 0) {        //Ensures odd n value
            throw new IllegalArgumentException("The second parameter must be odd.");
        }
        
        int j = 1;
        while(a.compareTo(ZERO) != 0) {
            while(a.mod(TWO).compareTo(ZERO) == 0) {
                a = a.divide(TWO);
                if(n.mod(EIGHT).compareTo(THREE) == 0 ||
                   n.mod(EIGHT).compareTo(FIVE) == 0) {
                    j = -j;
                }
            }
            //interchange(a, n)
            BigInteger buffer = a;
            a = n;
            n = buffer;
            
            if(a.mod(FOUR).compareTo(THREE) == 0 &&
               n.mod(FOUR).compareTo(THREE) == 0) {
                j = -j;
            }
            a = a.mod(n);
        }
        if(n.compareTo(ONE) == 0) {
            return j;
        } else {
            return 0;
        }
    }
    
}
