//Alexander Weaver
//Last update: 6-5-2015 2:27am
package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class InputTypeChooser extends JPanel implements ActionListener {
    
    private EncryptionPanel encryptionPanel;
    private String status;
    
    public InputTypeChooser(EncryptionPanel ep) {
        encryptionPanel = ep;
        this.setPreferredSize(new Dimension(0, 100));
        this.setMaximumSize(new Dimension(600, 100));
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        JLabel label = new JLabel("How will the plaintext be provided?");
        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
        this.add(label);
        
        ButtonGroup group = new ButtonGroup();
        
        JRadioButton input = new JRadioButton("Encrypt a given string");
        group.add(input);
        input.setSelected(true);
        status = "input";
        input.setActionCommand("input");
        input.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, input, 1, SpringLayout.SOUTH, label);
        this.add(input);
        
        JRadioButton file = new JRadioButton("Encrypt a text file");
        group.add(file);
        file.setActionCommand("file");
        file.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, file, 1, SpringLayout.SOUTH, input);
        this.add(file);
        
        JRadioButton other = new JRadioButton("Encrypt a different filetype");
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
            encryptionPanel.setInputSelection();
            status = cmd;
        } else if("file".equals(cmd)) {
            encryptionPanel.setFileSelection();
            status = cmd;
        } else if("other".equals(cmd)) {
            encryptionPanel.setOtherSelection();
            status = cmd;
        }
    }
    
    public String getStatus() {
        return status;
    }
}
