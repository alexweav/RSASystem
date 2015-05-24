//Alexander Weaver
//Last update: 5-23-2015 8:53pm
package GUI;

import javax.swing.*;
import java.awt.*;

public class ControlsPanel extends JTabbedPane {
    
    OutputPanel outputPanel;
    EncryptionPanel encryptionPanel;
    DecryptionPanel decryptionPanel;
    KeyGenerationPanel generationPanel;
    
    
    public ControlsPanel(int width, int height, OutputPanel op) {
        this.setPreferredSize(new Dimension(width, height));
        encryptionPanel = new EncryptionPanel(op);
        encryptionPanel.setMaximumSize(new Dimension(width, height));
        decryptionPanel = new DecryptionPanel(op);
        decryptionPanel.setMaximumSize(new Dimension(width, height));
        generationPanel = new KeyGenerationPanel(op);
        generationPanel.setMaximumSize(new Dimension(width, height));
        this.addTab("Encryption", encryptionPanel);
        this.addTab("Decryption", decryptionPanel);
        this.addTab("Key Generation", generationPanel);
    }
    
    public void setOutputPanel(OutputPanel op) {
        outputPanel = op;
        encryptionPanel.setOutputPanel(op);
        decryptionPanel.setOutputPanel(op);
        generationPanel.setOutputPanel(op);
    }
    
    public OutputPanel getOutputPanel() {
        return outputPanel;
    }
}
