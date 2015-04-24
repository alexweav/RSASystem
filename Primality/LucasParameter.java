//Alexander Weaver
//Last update: 4-24-2015 11:11am
package Primality;

import java.math.BigInteger;
import Util.BadParameterException;

//A Lucas Parameter is a 5-tuple of integers (n, p, q, un, vn).  n is to be taken as an index,
//p and q are two integer parameters.  
//un is the nth member of the lucas sequence of the first kind U(p, q)
//vn is the nth member of the lucas sequence of the second kind V(p, q)
public class LucasParameter {
    
    private static final BigInteger TWO = new BigInteger("2");
    private static final BigInteger FOUR = new BigInteger("4");
    
    private BigInteger n;
    
    private BigInteger p;
    
    private BigInteger q;
    
    private BigInteger un;
    
    private BigInteger vn;
    
    //gets
    public BigInteger getN() {
        return n;
    }
    
    public BigInteger getP() {
        return p;
    }
    
    public BigInteger getQ() {
        return q;
    }
    
    public BigInteger getUN() {
        return un;
    }
    
    public BigInteger getVN() {
        return vn;
    }
    
    //Algorithm from Aleksey Koval, source http://www.scirp.org/journal/PaperInformation.aspx?PaperID=3368#.VTpfmZPk_qp
    //Takes an index n and parameters p and q, all in BigInteger form
    //Returns the nth member of the Lucas Sequence U(P, Q)
    private void findLucasNumbers(BigInteger n, BigInteger p, BigInteger q) throws BadParameterException {
        if(n == null || p == null || q == null) {
            throw new BadParameterException("n, p, or q not set.");
        }
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
        un = uk;
        vn = vl;
    }
    
    public LucasParameter() {
        n = p = q = un = vn = null;
    }
    
    public LucasParameter(BigInteger nn, BigInteger np, BigInteger nq) {
        n = nn;
        p = np;  //I solved the p=np problem
        q = nq;
        
        try {
            findLucasNumbers(n, p, q);
        } catch (BadParameterException ex) {
            throw new IllegalArgumentException("One of the BigIntegers passed to the constructor was null or invalid.");
        }
    }
    
    public void updateValues(BigInteger nn, BigInteger np, BigInteger nq) {
        n = nn;
        p = np;  //I did it again
        q = nq;
        
        try {
            findLucasNumbers(n, p, q);
        } catch (BadParameterException ex) {
            throw new IllegalArgumentException("One of the BigIntegers passed to the method was null or invalid.");
        }
    }
}
