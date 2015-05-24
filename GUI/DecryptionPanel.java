//Alexander Weaver
//Last update: 5-23-2015 8:53pm
package GUI;

import javax.swing.*;

public class DecryptionPanel extends JPanel {
    
    OutputPanel outputPanel;
    
    public DecryptionPanel(OutputPanel op) {
       outputPanel = op; 
       JLabel title = new JLabel("Decryption");
       this.add(title);
    }
    
    public void setOutputPanel(OutputPanel op) {
        outputPanel = op;
    }
    
    public OutputPanel getOutputPanel() {
        return outputPanel;
    }
}
