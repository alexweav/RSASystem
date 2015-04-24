//Alexander Weaver
//Last update: 4-24-2015 10:40am
package Primality;

import Util.JacobiSymbolTester;

import java.math.BigInteger;

//Manager object which determines primality probabilistically, using the Lucas Primality Test
public class LucasPrimalityTester {
    
    private static final BigInteger ZERO = new BigInteger("0");
    private static final BigInteger ONE = new BigInteger("1");
    private static final BigInteger TWO = new BigInteger("2");
    private static final BigInteger FOUR = new BigInteger("4");
    
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
        int d = getD(p, q);
        int delt = delta(prime, d);
        return lucasNumberU(delt, p, q)%prime == 0;
    }
    
    private int getD(int p, int q) {
        return (p*p) - (4*q);
    }
    
    //Define delta(n, D) = n - jacobi(D|n)
    //Takes integers n and D, returns delta(n, D)
    private int delta(int n, int d) {
        JacobiSymbolTester jTester = new JacobiSymbolTester();
        return n - jTester.getSymbol(d, n);
    }
    
    //Takes a int index n>=0 and two int parameters, p and q
    //Returns the nth number of the Lucas series of the first kind, U(P,Q)
    public int lucasNumberU(int n, int p, int q) {
        if(n < 0) {
            throw new IllegalArgumentException("n must be greater than or equal to 0.");
        } else if(n <= 1) {
            return n;
        } else {
            int prev1 = 1;
            int prev2 = 0;
            int i = 2;
            int current = p*prev1 - q*prev2;
            while(i < n) {
                prev2 = prev1;
                prev1 = current;
                current = p*prev1 - q*prev2;
                i++;
            }
            return current;
        }
    }
    
    public boolean test(BigInteger prime, BigInteger p, BigInteger q) {
        if(prime.compareTo(TWO) == -1) {  //anything less than 2 is not prime
            return false;
        }
        if(prime.compareTo(TWO) == 0) {     //2 is prime
            return true;
        }
        if(prime.mod(TWO).compareTo(ZERO) == 0) { //Anything that is divisible by 2 that is not 2 is not prime
            return false;
        }
        BigInteger d = getD(p, q);
        BigInteger delt = delta(prime, d);
        return lucasNumberU(delt, p, q).mod(prime).compareTo(ZERO) == 0;
    }
    
    private BigInteger getD(BigInteger p, BigInteger q) {
        return (p.multiply(p)).subtract(q.multiply(FOUR));
    }
    
    private BigInteger delta(BigInteger n, BigInteger d) {
        JacobiSymbolTester jTester = new JacobiSymbolTester();
        return n.subtract(new BigInteger(Integer.toString(jTester.getSymbol(d, n))));
    }
    
    /*public BigInteger lucasNumberU(BigInteger n, BigInteger p, BigInteger q) {
        if(n.compareTo(ZERO) == -1) {
            throw new IllegalArgumentException("n must be greater than or equal to 0.");
        } else if (n.compareTo(ONE) < 1) {
            return n;
        } else {
            BigInteger prev1 = ONE;
            BigInteger prev2 = ZERO;
            BigInteger i = TWO;
            BigInteger current = (p.multiply(prev1)).subtract(q.multiply(prev2));
            while(i.compareTo(n) == -1) {
                prev2 = prev1;
                prev1 = current;
                current = (p.multiply(prev1)).subtract(q.multiply(prev2));
                i = i.add(ONE);
            }
            return current;
        }
    }*/
    
    //Algorithm from Aleksey Koval, source http://www.scirp.org/journal/PaperInformation.aspx?PaperID=3368#.VTpfmZPk_qp
    //Takes an index n and parameters p and q, all in BigInteger form
    //Returns the nth member of the Lucas Sequence U(P, Q)
    private BigInteger lucasNumberU(BigInteger n, BigInteger p, BigInteger q) {
        String binaryExpansion = n.toString(2);
        int len = binaryExpansion.length();
        BigInteger vl = new BigInteger("2");
        BigInteger vh = p;
        BigInteger ql = new BigInteger("1");
        BigInteger qh = new BigInteger("1");
        for(int j = (len - 1); j >= 0; j--) {
            ql = ql.multiply(qh);
            if(binaryExpansion.charAt(j) == 1) {
                qh = ql.multiply(q);
                vl = (vh.multiply(vl)).subtract(p.multiply(ql));
                vh = (vh.multiply(vh)).subtract(qh.multiply(TWO));
            } else {
                qh = ql;
                vh = (vh.multiply(vl)).subtract(p.multiply(ql));
                vl = (vl.multiply(vl)).subtract(qh.multiply(TWO));
            }
        }
        BigInteger uk = ((vh.multiply(TWO)).subtract(p.multiply(vl))).divide((p.multiply(p)).subtract(q.multiply(FOUR)));
        return uk;
    }
}
