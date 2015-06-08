//Alexander Weaver
//Last update: 6-8-2015 5:37pm
package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class InputTypeChooser extends JPanel implements ActionListener {
    
    private EncryptionPanel encryptionPanel;
    private DecryptionPanel decryptionPanel;
    private String status;
    private int mode;
    
    public static final int ENCRYPT = 0;
    public static final int DECRYPT = 1;
    
    public InputTypeChooser(EncryptionPanel ep, int m) {
        encryptionPanel = ep;
        mode = m;
        this.setPreferredSize(new Dimension(0, 100));
        this.setMaximumSize(new Dimension(600, 100));
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        JLabel label;
        if(mode == ENCRYPT) {
            label = new JLabel("How will the plaintext be provided?");
        } else {
            label = new JLabel("How will the ciphertext be provided?");
        }
        
        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
        this.add(label);
        
        ButtonGroup group = new ButtonGroup();
        
        JRadioButton input;
        if(mode == ENCRYPT) {
            input = new JRadioButton("Encrypt a string of text");
        } else {
            input = new JRadioButton("Decrypt a string of text");
        }
        group.add(input);
        input.setSelected(true);
        status = "input";
        input.setActionCommand("input");
        input.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, input, 1, SpringLayout.SOUTH, label);
        this.add(input);
        
        JRadioButton file;
        if(mode ==  ENCRYPT) {
            file = new JRadioButton("Encrypt a text file");
        } else {
            file = new JRadioButton("Decrypt a text file");
        }
        group.add(file);
        file.setActionCommand("file");
        file.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, file, 1, SpringLayout.SOUTH, input);
        this.add(file);
        
        JRadioButton other;
        if(mode == ENCRYPT) {
            other = new JRadioButton("Encrypt a different filetype");
        } else {
            other = new JRadioButton("Decrypt a different filetype");
        }
        group.add(other);
        other.setActionCommand("other");
        other.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, other, 1, SpringLayout.SOUTH, file);
        this.add(other);
    }
    
    public InputTypeChooser(DecryptionPanel ep, int m) {
        decryptionPanel = ep;
        mode = m;
        this.setPreferredSize(new Dimension(0, 100));
        this.setMaximumSize(new Dimension(600, 100));
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        JLabel label;
        if(mode == ENCRYPT) {
            label = new JLabel("How will the plaintext be provided?");
        } else {
            label = new JLabel("How will the ciphertext be provided?");
        }
        
        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
        this.add(label);
        
        ButtonGroup group = new ButtonGroup();
        
        JRadioButton input;
        if(mode == ENCRYPT) {
            input = new JRadioButton("Encrypt a string of text");
        } else {
            input = new JRadioButton("Decrypt a string of text");
        }
        group.add(input);
        input.setSelected(true);
        status = "input";
        input.setActionCommand("input");
        input.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, input, 1, SpringLayout.SOUTH, label);
        this.add(input);
        
        JRadioButton file;
        if(mode ==  ENCRYPT) {
            file = new JRadioButton("Encrypt a text file");
        } else {
            file = new JRadioButton("Decrypt a text file");
        }
        group.add(file);
        file.setActionCommand("file");
        file.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, file, 1, SpringLayout.SOUTH, input);
        this.add(file);
        
        JRadioButton other;
        if(mode == ENCRYPT) {
            other = new JRadioButton("Encrypt a different filetype");
        } else {
            other = new JRadioButton("Decrypt a different filetype");
        }
        group.add(other);
        other.setActionCommand("other");
        other.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, other, 1, SpringLayout.SOUTH, file);
        this.add(other);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if("input".equals(cmd)) {
            if(mode == ENCRYPT) {
                encryptionPanel.setInputSelection();
            } else {
                decryptionPanel.setInputSelection();
            }
            status = cmd;
        } else if("file".equals(cmd)) {
            if(mode == ENCRYPT) {
                encryptionPanel.setFileSelection();
            } else {
                decryptionPanel.setFileSelection();
            }
            status = cmd;
        } else if("other".equals(cmd)) {
            if(mode == ENCRYPT) {
                encryptionPanel.setOtherSelection();
            } else {
                decryptionPanel.setOtherSelection();
            }
            status = cmd;
        }
    }
    
    public String getStatus() {
        return status;
    }
}
