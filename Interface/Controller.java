//Alexander Weaver
//Last update: 5-8-2015 6:24pm
package Interface;

import Encryption.*;

import java.math.BigInteger;
import Util.EncodingManager;

public class Controller {
    
    //Time distributions (on my machine)
    //2, 4, 8, 16, 32, 64, 128, 256 bit keys take less than one second
    //512 bit keys take one second
    //1024 bit keys take 30 seconds
    //2048 bit keys take longer
    public void run() { 
        KeyGroup keys = new KeyGroup(512);
        System.out.println("Public Key is " + keys.getPublicKey());
        System.out.println("Private Key is " + keys.getPrivateKey());
        System.out.println("Exponent is " + keys.getKeyExponent());
        String message = "you wot m8";
        System.out.println("The message is " + message);
        EncodingManager pad = new EncodingManager();
        String hex = pad.textToHexASCII(message);
        BigInteger padded = pad.hexToBigInteger(hex);
        System.out.println("Message value is " + padded.toString());
        Encryptor encryptor = new Encryptor();
        BigInteger encryptedMessage = encryptor.encrypt(padded, keys.getKeyExponent(), keys.getPublicKey());
        System.out.println("Encrypted message is " + encryptedMessage.toString());
        BigInteger decryptedMessage = encryptor.decrypt(encryptedMessage, keys.getPrivateKey(), keys.getPublicKey());
        System.out.println("After decryption, the message is " + decryptedMessage.toString());
        String hexResult = pad.bigIntegerToHex(decryptedMessage);
        String messageResult = pad.hexToTextASCII(hexResult);
        System.out.println("The received message is " + messageResult);
    }
}
