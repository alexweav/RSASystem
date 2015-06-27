//Alexander Weaver
//Last update: 6-26-2015 8:47pm
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

public class EncryptButton extends JPanel implements ActionListener {
    
    private boolean isActive;
    private JButton button;
    
    private EncryptionPanel encryptionPanel;
    private StringBox stringBox;
    private FileBox textFileBox;
    private FileBox otherFileBox;
    private KeyLengthBox keyLengthBox;
    private NameBox nameBox;
    private FilepathBox filepathBox;
    private FileBox certificateFileBox;
    
    private OutputPanel outputPanel;
    
    private int SEGMENT_LENGTH = 8;
    
    public EncryptButton(EncryptionPanel ep, StringBox sb, FileBox tfb, FileBox ofb, KeyLengthBox klb, NameBox nb, FilepathBox fpb, FileBox cfb, OutputPanel op) {
        encryptionPanel = ep;
        stringBox = sb;
        textFileBox = tfb;
        otherFileBox = ofb;
        keyLengthBox = klb;
        nameBox = nb;
        filepathBox = fpb;
        certificateFileBox = cfb;
        outputPanel = op;
        this.setPreferredSize(new Dimension(0, 40));
        this.setMaximumSize(new Dimension(600, 40));
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        button = new JButton("Encrypt");
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
        KeyGroup keys = null;
        if(encryptionPanel.getKeysetOption() == 1) {
            keys = createNewKeyset();
        } else if(encryptionPanel.getKeysetOption() == 2) {
            keys = useExistingKeyset();
        }
        
        if(encryptionPanel.getPlaintextOption() == 1) {
            encryptGivenText(keys);
        } else if (encryptionPanel.getPlaintextOption() == 2) {
            encryptTextFile(keys);
        } else if (encryptionPanel.getPlaintextOption() == 3) {
            encryptBinaryFile(keys);
        }
    }
    
    private KeyGroup createNewKeyset() {
        String keyLengthStr = keyLengthBox.getValidText();
        String name = nameBox.getValidName();
        String directory = filepathBox.getValidDirectory();
        if(keyLengthStr == null || name == null || directory == null) {
            return null;
        }
        int keyLength = Integer.parseInt(keyLengthStr);
        int factorLength = keyLength/2;
        KeyGroup keys = new KeyGroup(factorLength);
        name = name + ".bin";
        saveCertificate(keys, directory, name);
        return keys;
    }
    
    private KeyGroup useExistingKeyset() {
        String fp = certificateFileBox.getValidFile();
        if(fp == null) {
            return null;
        } else {
            return readCertificate(fp);
        }
    }
    
    private void saveCertificate(KeyGroup keys, String directory, String filename) {
        try {
            FileWriter fileStream = new FileWriter(directory + "\\" + filename);
        } catch (IOException ex) {
            //handle
        }
        try {
            DataOutputStream os = new DataOutputStream(new FileOutputStream(directory + "\\" + filename));
            byte[] buffer = keys.getPublicKey().toByteArray();
            int length = buffer.length;
            os.writeInt(length);
            os.write(buffer);
            buffer = keys.getPrivateKey().toByteArray();
            length = buffer.length;
            os.writeInt(length);
            os.write(buffer);
            int intBuffer = keys.getKeyExponent();
            os.writeInt(intBuffer);
        } catch (FileNotFoundException ex) {
            //
        } catch (IOException ex) {
            //
        }
    }
    
    private KeyGroup readCertificate(String filepath) {
        int publicKeyLength;
        int privateKeyLength;
        int keyExponent = 0;
        BigInteger publicKey = null;
        BigInteger privateKey = null;
        try {
            DataInputStream is;
            is = new DataInputStream(new FileInputStream(doubleBackslashes(filepath)));
            
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
    
    private String doubleBackslashes(String string) {
        return string.replaceAll("\\\\", "\\\\\\\\");
    }
    
    private void encryptGivenText(KeyGroup keys) {
        String text = stringBox.getString();
        if(text == null) {
            return;
        }
        EncodingManager pad = new EncodingManager();
        String[] separatedMessage = pad.separate(text, 32);
        int length = separatedMessage.length;
        BigInteger[] paddedMessages = new BigInteger[length];
        for(int i = 0; i < length; i++) {
            String hex = pad.textToHexASCII(separatedMessage[i]);
            paddedMessages[i] = pad.hexToBigInteger(hex);
        }
        Encryptor encryptor = new Encryptor();
        int exponent = keys.getKeyExponent();
        BigInteger publicKey = keys.getPublicKey();
        String output = "";
        for(int i = 0; i < length; i++) {
            BigInteger encryptedMessage = encryptor.encrypt(paddedMessages[i], exponent, publicKey);
            if(i < length - 1) {
                output += encryptedMessage.toString() + ", ";
            } else {
                output += encryptedMessage.toString() + "\n";
            }
        }
        outputPanel.setStatusText(output);
        
    }
    
    private void encryptTextFile(KeyGroup keys) {
        String filepath = getFilepath(textFileBox);
        if(filepath == null) {
            return;
        }
        Encryptor encryptor = new Encryptor();
        EncodingManager pad = new EncodingManager();
        int exponent = keys.getKeyExponent();
        BigInteger publicKey = keys.getPublicKey();
        String encryptedFile = getEncryptedFilename(filepath, ".txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            FileWriter fileStream = new FileWriter(encryptedFile);
            BufferedWriter writer = new BufferedWriter(fileStream);
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while(line != null) {
                String[] separatedMessage = pad.separate(line, 32);
                int length = separatedMessage.length;
                BigInteger paddedMessage;
                for(int i = 0; i < length; i++) {
                    String hex = pad.textToHexASCII(separatedMessage[i]);
                    try {
                        paddedMessage = pad.hexToBigInteger(hex);
                    } catch (NumberFormatException e) { //This will occur if the reader has come across a blank line, which is not the end of the file.
                        continue;
                    }
                    BigInteger encryptedMessage = encryptor.encrypt(paddedMessage, exponent, publicKey);
                    if(i < length - 1) {
                        writer.write(encryptedMessage.toString() + ", ");
                    } else {
                        writer.write(encryptedMessage.toString());
                        writer.newLine();
                    }
                }
                line = reader.readLine();
            }
            writer.close();
            
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
    
    private String getEncryptedFilename(String filepath, String extension) {
        String name = filepath.substring(0, filepath.length() - extension.length());
        name = name + "_encrypted" + extension;
        return name;
    }
    
    private void encryptBinaryFile(KeyGroup keys) {
        String filepath = getFilepath(otherFileBox);
        if(filepath == null) {
            return;
        }
        Encryptor encryptor = new Encryptor();
        EncodingManager pad = new EncodingManager();
        int exponent = keys.getKeyExponent();
        BigInteger publicKey = keys.getPublicKey();
        String extension = getFileExtension(filepath);
        String encryptedFile = getEncryptedFilename(filepath, extension);
        try {
            DataInputStream is;
            is = new DataInputStream(new FileInputStream(filepath));
            DataOutputStream os = new DataOutputStream(new FileOutputStream(encryptedFile));
            //Data is read in 32 byte segments
            byte[] currentSegment;
            do {
                currentSegment = readNextSegment(is, SEGMENT_LENGTH);
                if(currentSegment == null) {
                    break;
                }
                //loop. read section, encrypt, write
                System.out.println("Incoming segment length: " + currentSegment.length);
                BigInteger segmentValue = new BigInteger(currentSegment);
                BigInteger encryptedValue = encryptor.encrypt(segmentValue, exponent, publicKey);
                byte[] encryptedSegment = encryptedValue.toByteArray();
                int outgoingLength = encryptedSegment.length;
                System.out.println("Outgoing segment length: " + encryptedSegment.length);
                os.write(outgoingLength);
                os.write(encryptedSegment);
            } while (currentSegment.length == SEGMENT_LENGTH);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EncryptButton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EncryptButton.class.getName()).log(Level.SEVERE, null, ex);
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
        byte[] segment = new byte[segmentLength];
        int endOfFileIndex = -1;
        for(int i = 0; i < segmentLength; i++) {
            try {
                segment[i] = stream.readByte();
            } catch (IOException ex) {
                endOfFileIndex = i;
                System.out.println(i);
                break;
            }
        }
        
        if(endOfFileIndex > -1) {
            if(endOfFileIndex == 0) {
                return null;
            }
            byte[] newSegment = new byte[endOfFileIndex+1];
            System.out.println(endOfFileIndex+1);
            for(int i = 0; i < endOfFileIndex; i++) {
                newSegment[i] = segment[i];
            }
            return newSegment;
        }
        System.out.println("Complete segment.");
        return segment;
    }
}
