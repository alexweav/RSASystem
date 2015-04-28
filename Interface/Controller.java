//Alexander Weaver
//Last update: 4-27-2015 10:20pm
package Interface;

import Encryption.*;

import java.math.BigInteger;

public class Controller {
    
    //Time distributions (on my machine)
    //2, 4, 8, 16, 32, 64, 128, 256 bit keys take less than one second
    //512 bit keys take one second
    //1024 bit keys take 30 seconds
    //2048 bit keys take longer
    public static void main(String[] args) { 
        KeyGroup keys = new KeyGroup(512);
        System.out.println("Factor 1 is " + keys.getFactor1());
        System.out.println("Factor 2 is " + keys.getFactor2());
        System.out.println("Public Key is " + keys.getPublicKey());
        System.out.println("Totient is " + keys.getTotient());
        System.out.println("Private Key is " + keys.getPrivateKey());
        System.out.println("Exponent is " + keys.getKeyExponent());
        BigInteger message = new BigInteger("11223344556677889900");
        System.out.println("Message value is " + message.toString());
        Encryptor encryptor = new Encryptor();
        BigInteger encryptedMessage = encryptor.encrypt(message, keys.getKeyExponent(), keys.getPublicKey());
        System.out.println("Encrypted message is " + encryptedMessage.toString());
        BigInteger decryptedMessage = encryptor.decrypt(encryptedMessage, keys.getPrivateKey(), keys.getPublicKey());
        System.out.println("After decryption, the message is " + decryptedMessage.toString());
    }
}
