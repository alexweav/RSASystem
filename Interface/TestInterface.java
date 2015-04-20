
package Interface;

import Primality.FermatPrimalityTester;
import Primality.FactorizationPrimalityTester;
import Primality.MillerRabinPrimalityTester;
import Primality.SolovayStrassenPrimalityTester;

import java.math.BigInteger;

public class TestInterface {
    
    

    public static void main(String[] args) {
        FermatPrimalityTester ftester = new FermatPrimalityTester();
        SolovayStrassenPrimalityTester sstester = new SolovayStrassenPrimalityTester();
        FactorizationPrimalityTester reference = new FactorizationPrimalityTester();
        MillerRabinPrimalityTester mrtester = new MillerRabinPrimalityTester();
        
        System.out.println(mrtester.isPrime(11, 1));
        
        int i = 2;
        int numIterations = 1;
        while(true) {
            boolean passesTest = sstester.isPrime((new BigInteger(Integer.toString(i))), numIterations);
            boolean prime = reference.isPrime(i);
            if(passesTest != prime) {
                System.out.println("The first number to fail " + numIterations + " iterations is " + i + "\n");
                break;
            }
            i++;
        }
    }
    
}
