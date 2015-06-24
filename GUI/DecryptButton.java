//Alexander Weaver
//Last update: 6-24-2015 5:01pm
package GUI;

import Encryption.Encryptor;
import Encryption.KeyGroup;
import Interface.TUI;
import Util.EncodingManager;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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
    
    private int SEGMENT_LENGTH = 32;
    
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
            decryptTextFile(keys);
        } else if (decryptionPanel.getTextOption() == 3) {
            decryptBinaryFile(keys);
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
    
    private void decryptTextFile(KeyGroup keys) {
        String filepath = getFilepath(textFileBox);
        if(filepath == null) {
            return;
        }
        Encryptor encryptor = new Encryptor();
        EncodingManager pad = new EncodingManager();
        String decryptedFile = getDecryptedFilename(filepath, ".txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            FileWriter fileStream = new FileWriter(decryptedFile);
            BufferedWriter writer = new BufferedWriter(fileStream);
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            //
            while(line != null) {
                BigInteger[] cipherTextSequence = parseValues(line);
                int length = cipherTextSequence.length;
                String[] decryptedMessages = new String[length];
                for(int i = 0; i < length; i++) {
                    BigInteger mValue = encryptor.decrypt(cipherTextSequence[i], keys.getPrivateKey(), keys.getPublicKey());
                    String dHex = pad.bigIntegerToHex(mValue);
                    String decryptedText = pad.hexToTextASCII(dHex);
                    decryptedMessages[i] = decryptedText;
                }
                String decryptedText = pad.joinStrings(decryptedMessages);
                writer.write(decryptedText);
                writer.newLine();
                line = reader.readLine();
            }
            writer.close();
            System.out.println("An decrypted version of the given file has been created.");
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String getFilepath(FileBox source) {
        String fp = source.getValidFile();
        if(fp == null) {
            return null;
        } else {
            fp = doubleBackslashes(fp);
            return fp;
        }
    }
    
    private String getDecryptedFilename(String filepath, String extension) {
        String name = filepath.substring(0, filepath.length() - extension.length());
        name = name + "_decrypted" + extension;
        return name;
    }
    
    private void decryptBinaryFile(KeyGroup keys) {
        String filepath = getFilepath(otherFileBox);
        if(filepath == null) {
            return;
        }
        Encryptor encryptor = new Encryptor();
        EncodingManager pad = new EncodingManager();
        BigInteger privateKey = keys.getPrivateKey();
        BigInteger publicKey = keys.getPublicKey();
        String extension = getFileExtension(filepath);
        String decryptedFile = getDecryptedFilename(filepath, extension);
        
        DataInputStream is;
        try {
            is = new DataInputStream(new FileInputStream(filepath));
            DataOutputStream os = new DataOutputStream(new FileOutputStream(decryptedFile));
            //Data is read in 32 byte segments
            byte[] currentSegment;
            do {
                currentSegment = readNextSegment(is, SEGMENT_LENGTH);
                //loop. read section, decrypt, write
                BigInteger segmentValue = new BigInteger(currentSegment);
                BigInteger decryptedValue = encryptor.decrypt(segmentValue, privateKey, publicKey);
                byte[] decryptedSegment = decryptedValue.toByteArray();
                os.write(decryptedSegment);
            } while (currentSegment.length == 32);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DecryptButton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DecryptButton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String getFileExtension(String filepath) {
        String extension = "";
        int i = filepath.lastIndexOf('.');
        if (i >= 0) {
            extension = filepath.substring(i);
        }
        return extension;
    }
    
    private byte[] readNextSegment(DataInputStream stream, int segmentLength) {
        byte[] segment = new byte[32];
        int endOfFileIndex = -1;
        for(int i = 0; i < segmentLength; i++) {
            try {
                segment[i] = stream.readByte();
            } catch (IOException ex) {
                endOfFileIndex = i;
                break;
            }
        }
        
        if(endOfFileIndex > -1) {
            if(endOfFileIndex == 0) {
                return null;
            }
            segment = new byte[endOfFileIndex+1];
            for(int i = 0; i < endOfFileIndex + 1; i++) {
                try {
                    segment[i] = stream.readByte();
                } catch (IOException ex) {
                    //if this method is correct, this should not happen
                }
            }
            return segment;
        }
        
        return segment;
    }
}
