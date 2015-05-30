//Alexander Weaver
//Last update: 5-30-2015 5:45pm
package GUI;

import Encryption.KeyGroup;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import javax.swing.*;

public class CreateBox extends JPanel implements ActionListener {
    
    private boolean isActive;
    private JButton button;
    private KeyValuesBox keyValuesBox;
    private NameBox nameBox;
    private FilepathBox filepathBox;
    
    public CreateBox(KeyValuesBox kvb, NameBox nb, FilepathBox fb) {
        this.setPreferredSize(new Dimension(0, 40));
        this.setMaximumSize(new Dimension(600, 40));
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        button = new JButton("Create");
        button.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, button, 5, SpringLayout.WEST, this);
        this.add(button);
        deactivate();
        keyValuesBox = kvb;
        nameBox = nb;
        filepathBox = fb;
    }
    
    public void activate() {
        isActive = true;
        button.setEnabled(true);
    }
    
    public void deactivate() {
        isActive = false;
        button.setEnabled(false);
    }
    
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String publicKeyStr = keyValuesBox.getValidPublicText();
        String privateKeyStr = keyValuesBox.getValidPrivateText();
        String exponentStr = keyValuesBox.getValidExponentText();
        String name = nameBox.getValidName();
        String directory = filepathBox.getValidDirectory();
        if(publicKeyStr == null || privateKeyStr == null || exponentStr == null || name == null || directory == null) {
            return;
        }
        BigInteger publicKey = new BigInteger(publicKeyStr);
        BigInteger privateKey = new BigInteger(privateKeyStr);
        int exponent = Integer.parseInt(exponentStr);
        KeyGroup keys = new KeyGroup(publicKey, privateKey, exponent);
        name = name + ".bin";
        saveCertificate(keys, directory, name);
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
}
