//Alexander Weaver
//Last update: 5-23-2015 9:15pm
package GUI;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;

public class EncryptionPanel extends JPanel {
    
    OutputPanel outputPanel;
    
    public EncryptionPanel(OutputPanel op) {
       outputPanel = op; 
       this.setLayout(new GridBagLayout());
       GridBagConstraints c = new GridBagConstraints();
       c.fill = GridBagConstraints.BOTH;
       c.weightx = 0.5;
       c.weighty = 1;
       c.gridx = 0;
       c.gridy = 0;
       JPanel leftPanel = new JPanel();
       buildLeftPanel(leftPanel);
       this.add(leftPanel, c);
       JPanel rightPanel = new JPanel();
       buildRightPanel(rightPanel);
       c.gridx = 1;
       this.add(rightPanel, c);
    }
    
    public void setOutputPanel(OutputPanel op) {
        outputPanel = op;
    }
    
    public OutputPanel getOutputPanel() {
        return outputPanel;
    }
    
    private void buildLeftPanel(JPanel container) {
        container.setBackground(Color.GREEN);
        
    }
    
    private void buildRightPanel(JPanel container) {
        container.setBackground(Color.YELLOW);
        
    }
}
