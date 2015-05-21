//Alexander Weaver
//Last update: 5-21-2015 4:55pm
package GUI;

import javax.swing.*;
import java.awt.*;

public class ControlsPanel extends JTabbedPane {
    
    public ControlsPanel(JComponent parent, int width, int height) {
        this.setMaximumSize(new Dimension(width, height));
        JPanel encryptionPanel = new JPanel();
        encryptionPanel.setMaximumSize(new Dimension(width, height));
        JPanel decryptionPanel = new JPanel();
        decryptionPanel.setMaximumSize(new Dimension(width, height));
        JPanel generationPanel = new JPanel();
        generationPanel.setMaximumSize(new Dimension(width, height));
        this.addTab("Encryption", encryptionPanel);
        this.addTab("Decryption", decryptionPanel);
        this.addTab("Key Generation", generationPanel);
        parent.add(this);
    }
}
