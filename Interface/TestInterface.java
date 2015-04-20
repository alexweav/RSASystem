//Alexander Weaver
//Last update: 4-20-2015 2:39am
package Interface;

import Primality.FermatPrimalityTester;
import Primality.FactorizationPrimalityTester;
import Primality.MillerRabinPrimalityTester;
import Primality.SolovayStrassenPrimalityTester;

import java.math.BigInteger;

//Main class interface
//Used for functionally testing pieces as they are made
//Will not be used in final program, purely for build testing
public class TestInterface {
    
    

    public static void main(String[] args) {
        //Creates the testers
        FermatPrimalityTester ftester = new FermatPrimalityTester();
        SolovayStrassenPrimalityTester sstester = new SolovayStrassenPrimalityTester();
        FactorizationPrimalityTester reference = new FactorizationPrimalityTester();
        MillerRabinPrimalityTester mrtester = new MillerRabinPrimalityTester();
        
        System.out.println(mrtester.isPrime(5, 1));
        System.out.println(mrtester.isPrime(new BigInteger("5"), 1));
        
        //Set the numIterations to a positive integer
        //This loops through the natural numbers, increasingly
        //For each natural number, the value is tested with the SS primality test and with the factorization test (which is guaranteed accuracy)
        //Stops when there is a difference (i.e. the SS primality test fails)
        //Prints that number
        //User can change the number of iterations in order to see the impact on accuracy
        int i = 2;
        int numIterations = 2;
        while(true) {
            boolean passesTest = mrtester.isPrime((new BigInteger(Integer.toString(i))), numIterations);
            boolean prime = reference.isPrime(i);
            if(passesTest != prime) {
                System.out.println("The first number to fail " + numIterations + " iterations is " + i + "\n");
                break;
            }
            i++;
        }
    }
    
}
