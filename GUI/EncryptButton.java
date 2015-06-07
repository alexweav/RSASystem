//Alexander Weaver
//Last update: 6-6-2015 7:50pm
package GUI;

import Encryption.KeyGroup;
import Interface.TUI;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    
    public EncryptButton(EncryptionPanel ep, StringBox sb, FileBox tfb, FileBox ofb, KeyLengthBox klb, NameBox nb, FilepathBox fpb, FileBox cfb) {
        encryptionPanel = ep;
        stringBox = sb;
        textFileBox = tfb;
        otherFileBox = ofb;
        keyLengthBox = klb;
        nameBox = nb;
        filepathBox = fpb;
        certificateFileBox = cfb;
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
        KeyGroup keys;
        if(encryptionPanel.getKeysetOption() == 1) {
            keys = createNewKeyset();
        } else if(encryptionPanel.getKeysetOption() == 2) {
            keys = useExistingKeyset();
        }
        
        if(encryptionPanel.getPlaintextOption() == 1) {
            
        } else if (encryptionPanel.getPlaintextOption() == 2) {
            
        } else if (encryptionPanel.getPlaintextOption() == 3) {
            
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
}
