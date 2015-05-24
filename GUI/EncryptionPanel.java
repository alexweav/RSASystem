//Alexander Weaver
//Last update: 5-23-2015 8:53pm
package GUI;

import javax.swing.*;

public class EncryptionPanel extends JPanel {
    
    OutputPanel outputPanel;
    
    public EncryptionPanel(OutputPanel op) {
       outputPanel = op; 
       JLabel title = new JLabel("Encryption");
       this.add(title);
    }
    
    public void setOutputPanel(OutputPanel op) {
        outputPanel = op;
    }
    
    public OutputPanel getOutputPanel() {
        return outputPanel;
    }
}
