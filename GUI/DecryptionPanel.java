//Alexander Weaver
//Last update: 6-16-2015 6:52pm
package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;

public class DecryptionPanel extends JPanel {
    
    private OutputPanel outputPanel;
    
    private int textOption;
    
    private StringBox stringBox;
    private FileBox textFileBox;
    private FileBox otherFileBox;
    private FileBox locationBox;
    
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
       DecryptButton decryptButton = new DecryptButton(this, stringBox, textFileBox, otherFileBox, locationBox, outputPanel);
       leftPanel.add(decryptButton);
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
        stringBox.activate();
        textFileBox.deactivate();
        otherFileBox.deactivate();
        textOption = 1;
    }
    
    public void setFileSelection() {
        stringBox.deactivate();
        textFileBox.activate();
        otherFileBox.deactivate();
        textOption = 2;
    }
    
    public void setOtherSelection() {
        stringBox.deactivate();
        textFileBox.deactivate();
        otherFileBox.activate();
        textOption = 3;
    }
    
    private void buildLeftPanel(JPanel container) {
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        InputTypeChooser inputTypeChooser = new InputTypeChooser(this, InputTypeChooser.DECRYPT);
        container.add(inputTypeChooser);
        stringBox = new StringBox();
        stringBox.activate();
        container.add(stringBox);
        textFileBox = new FileBox();
        textFileBox.deactivate();
        container.add(textFileBox);
        otherFileBox = new FileBox();
        otherFileBox.deactivate();
        container.add(otherFileBox);
        
    }
    
    private void buildRightPanel(JPanel container) {
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        locationBox = new FileBox();
        container.add(locationBox);
    }
    
    public int getTextOption() {
        return textOption;
    }
}
