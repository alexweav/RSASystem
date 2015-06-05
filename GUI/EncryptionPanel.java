//Alexander Weaver
//Last update: 6-5-2015 2:27am
package GUI;

import java.awt.Color;
import java.awt.Dimension;
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
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        container.setBackground(Color.GREEN);
        InputTypeChooser inputTypeChooser = new InputTypeChooser(this);
        container.add(inputTypeChooser);
        
    }
    
    private void buildRightPanel(JPanel container) {
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        container.setBackground(Color.YELLOW);
        
    }
    
    private void addChooser(JPanel container) {
        JPanel chooserContainer = new JPanel();
        chooserContainer.setPreferredSize(new Dimension(0, 70));
        chooserContainer.setMaximumSize(new Dimension(600, 70));
        JLabel label = new JLabel("What would you like to encrypt?");
        chooserContainer.add(label);
        JPanel radioButtonContainer = new JPanel();
        chooserContainer.add(radioButtonContainer);
        container.add(chooserContainer);
    }
    
    protected void setInputSelection() {
        
    }
    
    protected void setFileSelection() {
        
    }
    
    protected void setOtherSelection() {
        
    }
}
