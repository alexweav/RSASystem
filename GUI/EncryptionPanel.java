//Alexander Weaver
//Last update: 6-8-2015 5:41pm
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;

public class EncryptionPanel extends JPanel {
    
    private OutputPanel outputPanel;
    private KeyLengthBox keyLengthBox;
    private NameBox certificateNameBox;
    private FilepathBox certificateFilepathBox;
    private FileBox certificateFileBox;
    private StringBox stringBox;
    private FileBox textFileBox;
    private FileBox otherFileBox;
    
    private int plaintextOption;
    private int keysetOption;
    
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
       EncryptButton encryptButton = new EncryptButton(this, stringBox, textFileBox, otherFileBox, keyLengthBox, certificateNameBox, certificateFilepathBox, certificateFileBox, outputPanel);
       leftPanel.add(encryptButton);
       c.gridx = 1;
       this.add(rightPanel, c);
       plaintextOption = 1;
       keysetOption = 1;
    }
    
    public void setOutputPanel(OutputPanel op) {
        outputPanel = op;
    }
    
    public OutputPanel getOutputPanel() {
        return outputPanel;
    }
    
    private void buildLeftPanel(JPanel container) {
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        InputTypeChooser inputTypeChooser = new InputTypeChooser(this, InputTypeChooser.ENCRYPT);
        container.add(inputTypeChooser);
        stringBox = new StringBox();
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
        KeySourceChooser keySourceChooser = new KeySourceChooser(this);
        container.add(keySourceChooser);
        keyLengthBox = new KeyLengthBox();
        container.add(keyLengthBox);
        certificateNameBox = new NameBox();
        container.add(certificateNameBox);
        certificateFilepathBox = new FilepathBox();
        container.add(certificateFilepathBox);
        certificateFileBox = new FileBox();
        certificateFileBox.deactivate();
        container.add(certificateFileBox);
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
        stringBox.activate();
        textFileBox.deactivate();
        otherFileBox.deactivate();
        plaintextOption = 1;
    }
    
    protected void setFileSelection() {
        stringBox.deactivate();
        textFileBox.activate();
        otherFileBox.deactivate();
        plaintextOption = 2;
    }
    
    protected void setOtherSelection() {
        stringBox.deactivate();
        textFileBox.deactivate();
        otherFileBox.activate();
        plaintextOption = 3;
    }
    
    protected void setNewKeysetSelection() {
        keyLengthBox.activate();
        certificateNameBox.activate();
        certificateFilepathBox.activate();
        certificateFileBox.deactivate();
        keysetOption = 1;
    }
    
    protected void setUseExistingSelection() {
        keyLengthBox.deactivate();
        certificateNameBox.deactivate();
        certificateFilepathBox.deactivate();
        certificateFileBox.activate();
        keysetOption = 2;
    }
    
    protected int getPlaintextOption() {
        return plaintextOption;
    }
    
    protected int getKeysetOption() {
        return keysetOption;
    }
}
