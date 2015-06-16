//Alexander Weaver
//Last update: 6-16-2015 6:52pm
package GUI;

import Encryption.Encryptor;
import Encryption.KeyGroup;
import Interface.TUI;
import Util.EncodingManager;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class DecryptButton extends JPanel implements ActionListener {
    
    private JButton button;
    
    private DecryptionPanel decryptionPanel;
    private StringBox stringBox;
    private FileBox textFileBox;
    private FileBox otherFileBox;
    private FileBox certificateFileBox;
    private OutputPanel outputPanel;
    
    private boolean isActive;
    
    public DecryptButton(DecryptionPanel dp, StringBox sb, FileBox tfb, FileBox ofb, FileBox cfb, OutputPanel op) {
        decryptionPanel = dp;
        stringBox = sb;
        textFileBox = tfb;
        otherFileBox = ofb;
        certificateFileBox = cfb;
        outputPanel = op;
        this.setPreferredSize(new Dimension(0, 40));
        this.setMaximumSize(new Dimension(600, 40));
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        button = new JButton("Decrypt");
        layout.putConstraint(SpringLayout.WEST, button, 5, SpringLayout.WEST, this);
        button.addActionListener(this);
        this.add(button);
        activate();
    }
    
    public void activate() {
        isActive = true;
        button.setEnabled(true);
    }
    
    public void deactivate() {
        isActive = false;
        button.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String keyLocation = certificateFileBox.getValidFile();
        keyLocation = doubleBackslashes(keyLocation);
        KeyGroup keys = getCertificate(keyLocation);
        if(decryptionPanel.getTextOption() == 1) {
            decryptGivenText(keys);
        } else if (decryptionPanel.getTextOption() == 2) {
            
        } else if (decryptionPanel.getTextOption() == 3) {
            
        }
    }
    
    private void decryptGivenText(KeyGroup keys) {
        BigInteger publicKey = keys.getPublicKey();
        BigInteger privateKey = keys.getPrivateKey();
        BigInteger[] cipherTextSequence;
        cipherTextSequence = getCipherTextSequence();
        EncodingManager pad = new EncodingManager();
        Encryptor encryptor = new Encryptor();
        int length = cipherTextSequence.length;
        String[] decryptedMessages = new String[length];
        for(int i = 0; i < length; i++) {
            BigInteger mValue = encryptor.decrypt(cipherTextSequence[i], privateKey, publicKey);
            String dHex = pad.bigIntegerToHex(mValue);
            String decryptedText = pad.hexToTextASCII(dHex);
            decryptedMessages[i] = decryptedText;
        }
        String decryptedText = pad.joinStrings(decryptedMessages);
        outputPanel.setStatusText(decryptedText);
    }
    
    private String doubleBackslashes(String string) {
        return string.replaceAll("\\\\", "\\\\\\\\");
    }
    
    private KeyGroup getCertificate(String location) {
        int publicKeyLength;
        int privateKeyLength;
        int keyExponent = 0;
        BigInteger publicKey = null;
        BigInteger privateKey = null;
        try {
            DataInputStream is;
            is = new DataInputStream(new FileInputStream(location));
            
            publicKeyLength = is.readInt();     //length of first byte array
            byte[] buffer = new byte[publicKeyLength];
            for(int i = 0; i < publicKeyLength; i++) {
                buffer[i] = is.readByte();
            }
            publicKey = new BigInteger(buffer);
            privateKeyLength = is.readInt();
            buffer = new byte[privateKeyLength];
            for(int i = 0; i < privateKeyLength; i++) {
                buffer[i] = is.readByte();
            }
            privateKey = new BigInteger(buffer);
            keyExponent = is.readInt();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        KeyGroup keys = new KeyGroup(publicKey, privateKey, keyExponent);
        return keys;
    }
    
    private BigInteger[] getCipherTextSequence() {
        String line = stringBox.getString();
        return parseValues(line);
    }
    
    //Parses a string of comma separated integers into an array of BigIntegers
    private BigInteger[] parseValues(String str) {
        String[] stringValues = str.split(",");
        int length = stringValues.length;
        BigInteger[] values = new BigInteger[length];
        for(int i = 0; i < length; i++) {
            stringValues[i] = stringValues[i].replaceAll("\\s", "");
            System.out.println(stringValues[i]);
            values[i] = new BigInteger(stringValues[i]);
        }
        return values;
    }
}
