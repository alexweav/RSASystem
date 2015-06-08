//Alexander Weaver
//Last update: 6-8-2015 5:49pm
package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;

public class DecryptionPanel extends JPanel {
    
    private OutputPanel outputPanel;
    
    private int textOption;
    
    public DecryptionPanel(OutputPanel op) {
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
       textOption = 1;
    }
    
    public void setOutputPanel(OutputPanel op) {
        outputPanel = op;
    }
    
    public OutputPanel getOutputPanel() {
        return outputPanel;
    }
    
    public void setInputSelection() {
        
        textOption = 1;
    }
    
    public void setFileSelection() {
        
        textOption = 2;
    }
    
    public void setOtherSelection() {
        textOption = 3;
    }
    
    private void buildLeftPanel(JPanel container) {
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        InputTypeChooser inputTypeChooser = new InputTypeChooser(this, InputTypeChooser.DECRYPT);
        container.add(inputTypeChooser);
    }
    
    private void buildRightPanel(JPanel container) {
        
    }
}
