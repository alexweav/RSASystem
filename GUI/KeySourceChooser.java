//Alexander Weaver
//Last update: 6-5-2015 6:55pm
package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class KeySourceChooser extends JPanel implements ActionListener {
    
    private EncryptionPanel encryptionPanel;
    private String status;
    
    public KeySourceChooser(EncryptionPanel ep) {
        encryptionPanel = ep;
        this.setPreferredSize(new Dimension(0, 70));
        this.setMaximumSize(new Dimension(600, 70));
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        JLabel label = new JLabel("Which keyset would you like to use?");
        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
        this.add(label);
        
        ButtonGroup group = new ButtonGroup();
        
        JRadioButton newCertificate = new JRadioButton("Create a new keyset");
        group.add(newCertificate);
        newCertificate.setSelected(true);
        status = "newCertificate";
        newCertificate.setActionCommand("newCertificate");
        newCertificate.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, newCertificate, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, newCertificate, 1, SpringLayout.SOUTH, label);
        this.add(newCertificate);
        
        JRadioButton useExisting = new JRadioButton("Use an existing keyset");
        group.add(useExisting);
        useExisting.setActionCommand("useExisting");
        useExisting.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, useExisting, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, useExisting, 1, SpringLayout.SOUTH, newCertificate);
        this.add(useExisting);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if("newCertificate".equals(cmd)) {
            encryptionPanel.setNewKeysetSelection();
            status = cmd;
        } else if("useExisting".equals(cmd)) {
            encryptionPanel.setUseExistingSelection();
            status = cmd;
        }
    }
    
    public String getStatus() {
        return status;
    }
}
