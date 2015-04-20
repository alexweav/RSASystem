
package Primality;

import Util.RandomInt;

public class MillerRabinPrimalityTester {
    
    public boolean isPrime(int prime, int iterations) {
        if(prime == 2) {
            return true;
        }
        if(prime % 2 == 0) {
            return false;
        }
        for(int i = 0; i < iterations; i++) {
            RandomInt rand = new RandomInt();
            int base = rand.randInt(1, prime - 1);
            if(!(test(prime, base))) {
                return false;
            }
        }
        return true;
    }
    
    private boolean test(int prime, int base) {
        MRParameter params = new MRParameter(prime);
        return equation1(params.getN(), base, params.getD()) || equation2(params.getN(), base, params.getD(), params.getS());
    }
    
    private boolean equation1(int n, int a, int d) {
        return ((int)Math.pow(a, d)) % n == 1;
    }
    
    private boolean equation2(int n, int a, int d, int s) {
        for(int r = 0; r < s; r++) {
            if(((int)Math.pow(a, d * (int)Math.pow(2, r)))%n == (-1%n + n)%n) {
                return true;
            }
        }
        return false;
    }
}
