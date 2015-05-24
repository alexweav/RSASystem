//Alexander Weaver
//Last update: 5-23-2015 8:53pm
package GUI;

import javax.swing.*;

public class KeyGenerationPanel extends JPanel {
    
    OutputPanel outputPanel;
    
    public KeyGenerationPanel(OutputPanel op) {
        outputPanel = op;
        JLabel title = new JLabel("Key Generation");
        this.add(title);
    }
    
    public void setOutputPanel(OutputPanel op) {
        outputPanel = op;
    }
    
    public OutputPanel getOutputPanel() {
        return outputPanel;
    }
}
