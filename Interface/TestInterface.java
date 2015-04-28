//Alexander Weaver
//Last update: 4-27-2015 6:01pm
package Interface;

import Encryption.ExtendedEuclidean;
import Primality.FermatPrimalityTester;
import Primality.FactorizationPrimalityTester;
import Primality.MillerRabinPrimalityTester;
import Primality.SolovayStrassenPrimalityTester;
import Primality.LucasPrimalityTester;
import Primality.BailliePSWPrimalityTester;
import Util.EncodingManager;
import Encryption.*;

import java.math.BigInteger;
import java.util.Arrays;

//Main class interface
//Used for functionally testing pieces as they are made
//Will not be used in final program, purely for build testing
public class TestInterface {
    


    public static void main2(String[] args) {
        //Creates the testers
        FermatPrimalityTester ftester = new FermatPrimalityTester();
        SolovayStrassenPrimalityTester sstester = new SolovayStrassenPrimalityTester();
        FactorizationPrimalityTester reference = new FactorizationPrimalityTester();
        MillerRabinPrimalityTester mrtester = new MillerRabinPrimalityTester();
        LucasPrimalityTester ltester = new LucasPrimalityTester();
        BailliePSWPrimalityTester bPSWTester = new BailliePSWPrimalityTester();
        EncodingManager eManager = new EncodingManager();
        ExtendedEuclidean euclidean = new ExtendedEuclidean();
        Encryptor encryptor = new Encryptor();
        
        System.out.println(euclidean.getModularInverse(new BigInteger("17"), new BigInteger("3120")).toString());
        
        //byte[] vals = {97, 98};
        //System.out.println(eManager.valuesToTextASCII(vals));
        //System.out.println(Arrays.toString(eManager.textToValuesASCII("abcd")));
        
        /*int i = 2;
        while(true) {
            boolean passesTest = bPSWTester.isPrime(new BigInteger(Integer.toString(i)));
            if(passesTest) {
                System.out.println(i + " is prime by the bPSW test.");
            } else {
                System.out.println(i + " is composite.");
            }
            i++;
        }*/
        
        //Set the numIterations to a positive integer
        //This loops through the natural numbers, increasingly
        //For each natural number, the value is tested with the SS primality test and with the factorization test (which is guaranteed accuracy)
        //Stops when there is a difference (i.e. the SS primality test fails)
        //Prints that number
        //User can change the number of iterations in order to see the impact on accuracy
        /*int i = 2;
        int numIterations = 5;
        while(true) {
            boolean passesTest = mrtester.isPrime(new BigInteger(Integer.toString(i)), numIterations);
            boolean prime = reference.isPrime(i);
            if(passesTest != prime) {
                System.out.println("The first number to fail " + numIterations + " iterations is " + i + "\n");
                break;
            } else {
                System.out.println("The value " + i + " is consistent.\n");
            }
            i++;
        }*/
    }
    
}
