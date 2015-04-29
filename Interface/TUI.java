//Alexander Weaver
//Last update: 4-27-2015 10:39pm
package Interface;

import java.util.Scanner;

import Encryption.*;

public class TUI {
    
    public void init() {
        printHeader();
        mainMenu();
    }
    
    private void printHeader() {
        System.out.println("******************************************************************************");
        System.out.println("*****************************WELCOME TO RSASYSTEM*****************************");
        System.out.println("*************************---(still in development)---*************************");
        System.out.println("******************************************************************************\n");
    }
    
    private void mainMenu() {
        while(true) {
            System.out.println("What would you like to do? Enter a number for its corresponding option.");
            System.out.println("1. Encrypt a plaintext message");
            System.out.println("2. Decrypt an encrypted message");
            System.out.println("3. Generate a new keyset");
            System.out.println("4. Exit");
            Scanner in = new Scanner(System.in);
            int option = in.nextInt();
            while(option < 1 || option > 4) {
                System.out.println("Invalid input. Please choose one of the given options.");
                option = in.nextInt();
            }
        
            if(option == 1) {
                System.out.println("Enter a string of text to encrypt.");
            } else if (option == 2) {
            
            } else if (option == 3) {
                generateKeySet();
            } else if (option == 4) {
                System.exit(0);
            }
        }
    }
    
    private KeyGroup generateKeySet() {
        System.out.println("Please input the key length in bits.  Must be greater than or equal to 4, and must be even.");
        Scanner in = new Scanner(System.in);
        int length = in.nextInt();
        while(length < 4 || length%2 == 1) {
            System.out.println("Invalid input. Please give a length that is both greater than or equal to 4 and even.");
            length = in.nextInt();
        }
        int factorLength = length/2;
        KeyGroup keys = new KeyGroup(factorLength);
        System.out.println("\nPublic Key is " + keys.getPublicKey());
        System.out.println("Private Key is " + keys.getPrivateKey());
        System.out.println("Exponent is " + keys.getKeyExponent());
        System.out.println("\nSave these values for your records.");
        System.out.println("Enter any value to progress.");
        in.next();
        return keys;
    }
}
