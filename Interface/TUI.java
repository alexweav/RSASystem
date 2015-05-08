//Alexander Weaver
//Last update: 5-8-2015 6:24pm
package Interface;


import Encryption.*;
import Util.EncodingManager;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TUI {
    
    private static final BigInteger FOUR = new BigInteger("4");
    
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
                encrypt();
            } else if (option == 2) {
                decrypt();
            } else if (option == 3) {
                generateKeySet();
            } else if (option == 4) {
                System.exit(0);
            }
        }
    }
    
    private void encrypt() {
        System.out.println("Which keyset would you like to use?");
        System.out.println("1. Generate a new keyset");
        System.out.println("2. Use an existing keyset");
        System.out.println("3. Use an existing certificate file");
        Scanner in = new Scanner(System.in);
        int option = in.nextInt();
        while(option < 1 || option > 3) {
            System.out.println("Invalid input. Please choose one of the given options.");
            option = in.nextInt();
        }
        KeyGroup keys;
        BigInteger publicKey = new BigInteger("0");
        int exponent = 0;
        if(option == 1) {
            keys = generateKeySet();
            publicKey = keys.getPublicKey();
            exponent = keys.getKeyExponent();
        } else if (option == 2) {
            publicKey = getPublicKey();
            exponent = getExponent();
        } else if (option == 3) {
            keys = getCertificate();
            publicKey = keys.getPublicKey();
            exponent = keys.getKeyExponent();
        }
        String message = getMessage();
        System.out.println("The message is " + message);
        EncodingManager pad = new EncodingManager();
        String hex = pad.textToHexASCII(message);
        BigInteger padded = pad.hexToBigInteger(hex);
        System.out.println("Message value is " + padded.toString());
        Encryptor encryptor = new Encryptor();
        BigInteger encryptedMessage = encryptor.encrypt(padded, exponent, publicKey);
        System.out.println("Encrypted message value is " + encryptedMessage.toString());
    }
    
    private void decrypt() {
        BigInteger publicKey = getPublicKey();
        BigInteger privateKey = getPrivateKey();
        BigInteger cipherText = getCipherText();
        
        EncodingManager pad = new EncodingManager();
        System.out.println("Cipher text value is " + cipherText.toString());
        Encryptor encryptor = new Encryptor();
        BigInteger decryptedMessage = encryptor.decrypt(cipherText, privateKey, publicKey);
        System.out.println("Decrypted message value is " + decryptedMessage.toString());
        String dHex = pad.bigIntegerToHex(decryptedMessage);
        String decryptedText = pad.hexToTextASCII(dHex);
        System.out.println("Decrypted text:");
        System.out.println(decryptedText);
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
        
        
        System.out.println("\nWould you like to save this keyset as a certificate on your hard drive?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        int choice = in.nextInt();
        while(choice < 1 || choice > 2) {
            System.out.println("Invalid input. Please choose one of the given options.");
            choice = in.nextInt();
        }
        if(choice == 1) {
            saveCertificate(keys);
        }
        if(choice == 2) {
            System.out.println("Save these values for your records.");
        }
        return keys;
    }
    
    private BigInteger getPublicKey() {
        System.out.println("Please input the public key.");
        Scanner in = new Scanner(System.in);
        BigInteger key = in.nextBigInteger();
        while(key.compareTo(FOUR) == -1) {
            System.out.println("Invalid input. Public key cannot be less than 4.");
            key = in.nextBigInteger();
        }
        return key;
    }
    
    private BigInteger getPrivateKey() {
        System.out.println("Please input the private key.");
        Scanner in = new Scanner(System.in);
        BigInteger key = in.nextBigInteger();
        while(key.compareTo(FOUR) == -1) {
            System.out.println("Invalid input. Private key cannot be less than 4.");
            key = in.nextBigInteger();
        }
        return key;
    }
    
    private int getExponent() {
        System.out.println("Please input the key exponent.");
        Scanner in = new Scanner(System.in);
        int exp = in.nextInt();
        while(exp < 2) {
            System.out.println("Invalid input. Exponent cannot be less than 2.");
            exp = in.nextInt();
        }
        return exp;
    }
    
    private String getMessage() {
        System.out.println("Please input the plaintext to be encrypted.");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
    
    private BigInteger getCipherText() {
        System.out.println("Please input the cipher value to be decrypted.");
        Scanner in = new Scanner(System.in);
        return in.nextBigInteger();
    }
    
    /*
    CERTIFICATE FILE FORMAT:
    
    INT -> BYTE[] -> INT -> BYTE[] -> INT
    
    PublicKeyArrayLength -> PublicKey -> PrivateKeyArrayLength -> PrivateKey -> KeyExponent
    
    The first int denotes the length of the first byte array.
    The second int denotes the length of the second byte array.
    */
    private void saveCertificate(KeyGroup keys) {
        System.out.println("Please enter a name for the certificate file.");
        Scanner in = new Scanner(System.in);
        String name = in.next();
        name = name + ".bin";
        //System.out.println("Please enter a filepath indicating where you want the file to be saved.");
        //TODO: make a custom filepath work here
        try {
            FileWriter fileStream = new FileWriter(name);
        } catch (IOException ex) {
            Logger.getLogger(TUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            DataOutputStream os = new DataOutputStream(new FileOutputStream(name));
            byte[] buffer = keys.getPublicKey().toByteArray();
            int length = buffer.length;
            os.writeInt(length);
            System.out.println(length + " written");
            os.write(buffer);
            buffer = keys.getPrivateKey().toByteArray();
            length = buffer.length;
            os.writeInt(length);
            os.write(buffer);
            int intBuffer = keys.getKeyExponent();
            os.write(intBuffer);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("New file, named " + name + " has been created and the key set has been written to it.");
    }
    
    private KeyGroup getCertificate() {
        int publicKeyLength;
        BigInteger publicKey;
        System.out.println("Enter the name of the existing certificate file (without file extension).");
        Scanner in = new Scanner(System.in);
        String name = in.next();
        name = name + ".bin";
        try {
            DataInputStream is;
            is = new DataInputStream(new FileInputStream(name));
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            
            publicKeyLength = is.readInt();     //length of first byte array
            System.out.println("public key length is " + publicKeyLength);
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
