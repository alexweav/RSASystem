//Alexander Weaver
//Last update: 7-1-2015 5:22pm
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
import java.util.Arrays;
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
    
    //Replaces every instance of the character "\" in a string with "\\"
    //Used because Java filepaths require backslashes to be doubled due to character escaping
    //This method is the wonderful product of regular expressions and Java's escape character
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
                    String decryptedText;
                    try {
                        decryptedText = pad.hexToTextASCII(dHex);
                    } catch(StringIndexOutOfBoundsException e) {
                        decryptedText = "\n\n";
                    }
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
    
    //Gets a valid filepath from the corresponding field in the GUI
    //Returns null if the user-given filepath is invalid (i.e. it does not exist, it contains unrecognized characaters, or the field is empty)
    private String getFilepath(FileBox source) {
        String fp = source.getValidFile();
        if(fp == null) {
            return null;
        } else {
            fp = doubleBackslashes(fp);
            return fp;
        }
    }
    
    //Creates the filename of a decrypted file
    //If the encrypted file is titled "filename.extension"
    //Then the decrypted file is titles "filename_decrypted.extension"
    //Preserves any directory information before the filename
    private String getDecryptedFilename(String filepath, String extension) {
        String name = filepath.substring(0, filepath.length() - extension.length());
        name = name + "_decrypted" + extension;
        return name;
    }
    
    private void decryptBinaryFile(KeyGroup keys) {
        String filepath = getFilepath(otherFileBox);
        //If invalid filepath, stop process immediately
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
            byte[] currentSegment;
            do {
                //Due to natural properties of RSA encryption, if the encryption key length is higher than 1024 bit, then the length of encrypted numbers will be greater than 128 bits.
                //If this occurs, the segment length will be negative because Java does not play well with unsigned primitives
                //This is (slightly broken) code that will ensure that segment lengths of up to 128 bits will be properly interpreted
                int currentSegmentLength;
                try {
                    currentSegmentLength = (int)is.readByte();
                    if(currentSegmentLength < 1) {
                        currentSegmentLength = -currentSegmentLength;
                    }
                //If no segment length marker exists, then the file has ended and we may stop decrypting.
                } catch (IOException e) {
                    
                    break;
                }
                System.out.println("next length: " + currentSegmentLength);
                //Given the length of the current segment of binary code,
                //the correct amount of code is read and saved into a byte array (in big-endian format)
                currentSegment = readNextSegment(is, currentSegmentLength);
                if(currentSegment == null) {
                    break;
                }
                //loop. read section, decrypt, write
                System.out.println("Incoming segment length: " + currentSegment.length);
                //Maps the binary segment into its corresponding unsigned BigInteger
                //Decrypts the BigInteger
                //Writes the BigInteger to the decrypted file
                //Loop until all segments have been decrypted
                BigInteger segmentValue = new BigInteger(1, currentSegment);
                BigInteger decryptedValue = encryptor.decrypt(segmentValue, privateKey, publicKey);
                byte[] decryptedSegment = decryptedValue.toByteArray();
                System.out.println("Before trimming: " + decryptedSegment.length);
                if(decryptedSegment.length == 32 + 1 && decryptedSegment[0] == 0x00) {
                    decryptedSegment = Arrays.copyOfRange(decryptedSegment, 1, 33);
                }
                if(decryptedSegment.length < 32 && decryptedSegment[decryptedSegment.length-1] == 0x00) {
                    decryptedSegment = Arrays.copyOfRange(decryptedSegment, 0, decryptedSegment.length - 1);
                }
                if(decryptedSegment.length < 32 && is.available() > 0) {
                    int numZeroBytes = 32 - decryptedSegment.length;
                    System.out.println("Adding " + numZeroBytes + " bytes to left of segment.");
                    decryptedSegment = addZeroBytes(decryptedSegment, numZeroBytes);
                }
                
                System.out.println("Outgoing segment length: " + decryptedSegment.length);
                os.write(decryptedSegment);
            } while (true);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DecryptButton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DecryptButton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Takes a filepath in the form of a string
    //Returns the file extension of the referenced file
    private String getFileExtension(String filepath) {
        String extension = "";
        int i = filepath.lastIndexOf('.');
        if (i >= 0) {
            extension = filepath.substring(i);
        }
        return extension;
    }
    
    //Takes an binary input stream and an integer segment length
    //Reads segmentLength number of bytes from the stream
    //Returns the collected data from the stream in the form of a big-endian byte array
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
        
        /*if(endOfFileIndex > -1) {
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
        System.out.println("Complete segment.");*/
        return segment;
    }
    
    private byte[] addZeroBytes(byte[] array, int numZeros) {
        byte[] output = new byte[array.length + numZeros];
        for(int i = 0; i < output.length; i++) {
            if(i < numZeros) {
                output[i] = 0x00;
            } else {
                output[i] = array[i - numZeros];
            }
        }
        return output;
    }
}
