//Alexander Weaver
//Last update: 5-28-2015 7:51pm
package GUI;

import Encryption.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

public class GenerateBox extends JPanel implements ActionListener {
    
    private boolean isActive;
    private JButton button;
    private KeyLengthBox keyLengthBox;
    private NameBox nameBox;
    private FilepathBox filepathBox;
    
    public GenerateBox(KeyLengthBox klb, NameBox nb, FilepathBox fb) {
        this.setPreferredSize(new Dimension(0, 40));
        this.setMaximumSize(new Dimension(600, 40));
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        button = new JButton("Generate");
        button.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, button, 5, SpringLayout.WEST, this);
        this.add(button);
        activate();
        keyLengthBox = klb;
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

    @Override
    public void actionPerformed(ActionEvent ae) {
        String keyLengthStr = keyLengthBox.getValidText();
        String name = nameBox.getValidName();
        String directory = filepathBox.getValidDirectory();
        if(keyLengthStr == null || name == null || directory == null) {
            return;
        }
        int keyLength = Integer.parseInt(keyLengthStr);
        int factorLength = keyLength/2;
        KeyGroup keys = new KeyGroup(factorLength);
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
