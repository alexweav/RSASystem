//Alexander Weaver
//Last update: 4-21-2015 1:16am
package Primality;

import Util.JacobiSymbolTester;

//Manager object which determines primality probabilistically, using the Lucas Primality Test
public class LucasPrimalityTester {
    
    //Takes an int to be tested, and two int parameters
    //Returns true if the int passes the Lucas test given the parameters, false otherwise
    public boolean test(int prime, int p, int q) {
        if(prime < 2) {
            return false;
        }
        if(prime == 2) {
            return true;
        }
        if(prime%2 == 0) {
            return false;
        }
        int d = (p*p) - (4*q);
        int delt = delta(prime, d);
        return lucasNumberU(delt, p, q)%prime == 0;
    }
    
    //Define delta(n, D) = n - jacobi(D|n)
    //Takes integers n and D, returns delta(n, D)
    private int delta(int n, int d) {
        JacobiSymbolTester jTester = new JacobiSymbolTester();
        return n - jTester.getSymbol(d, n);
    }
    
    //Takes a int index n>=0 and two int parameters, p and q
    //Returns the nth number of the Lucas series of the first kind, U(P,Q)
    private int lucasNumberU(int n, int p, int q) {
        if(n < 0) {
            throw new IllegalArgumentException("n must be greater than or equal to 0.");
        } else if(n <= 1) {
            return n;
        } else {
            return p*lucasNumberU(n - 1, p, q) - q*lucasNumberU(n - 2, p, q);
        }
    }
    
}
