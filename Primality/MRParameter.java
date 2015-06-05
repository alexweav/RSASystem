//Alexander Weaver
//Last update: 6-1-2015 4:46pm
package Primality;

//Set of parameters for the Miller Rabin primality test
public class MRParameter {
    
    private int n;
    private int s;
    private int d;
    
    //gets
    public int getN() {
        return n;
    }
    
    public int getS() {
        return s;
    }
    
    public int getD() {
        return d;
    }
    
    //Takes odd integer pr>= 2 to be used as n
    //Sets s and d according to the rule n-1=d(2^s)
    //It is guaranteed that s and d exist for all values for which the function is defined
    public void setValue(int pr) {
        if(pr%2 == 0) {
            throw new IllegalArgumentException("Value must be odd.");
        }
        if(pr <= 2) {
            throw new IllegalArgumentException("Value must be greater than or equal to 2.");
        }
        n = pr;
        
        n = n - 1;
        s = 0;
        while(n%2 == 0) {
            n = n/2;
            s++;
        }
        d = n;
        n = pr;
    }
    
    public MRParameter(int pr) {
        setValue(pr);
    }
    
}
