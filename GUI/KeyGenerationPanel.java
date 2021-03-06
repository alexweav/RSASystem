//Alexander Weaver
//Last update: 5-30-2015 4:51pm
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;

public class KeyGenerationPanel extends JPanel implements ActionListener {
    
    private OutputPanel outputPanel;
    
    private boolean creatingNewSet;
    private KeyLengthBox keyLengthBox;
    private FilepathBox filepathBox;
    private NameBox nameBox;
    private GenerateBox generateBox;
    
    private KeyValuesBox keyValuesBox;
    private NameBox rightNameBox;
    private FilepathBox rightFilepathBox;
    private CreateBox createBox;
    
    public KeyGenerationPanel(OutputPanel op) {
        outputPanel = op;
        creatingNewSet = true;
        
        JLabel test = new JLabel("test");
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.PAGE_AXIS));
        JLabel title = new JLabel("How do you wish to obtain the key values?");
        title.setAlignmentX((float)0.5);
        header.add(title);
        addRadioButtons(header);
        this.add(header);
        JPanel body = new JPanel();
        body.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        JPanel leftPanel = new JPanel();
        buildLeftPanel(leftPanel);
        body.add(leftPanel, c);
        JPanel rightPanel = new JPanel();
        buildRightPanel(rightPanel);
        c.gridx = 1;
        body.add(rightPanel, c);
        this.add(body);
    }
    
    public void setOutputPanel(OutputPanel op) {
        outputPanel = op;
    }
    
    public OutputPanel getOutputPanel() {
        return outputPanel;
    }
    
    private void addRadioButtons(JComponent container) {
        JPanel radioButtonContainer = new JPanel();
        radioButtonContainer.setLayout(new BoxLayout(radioButtonContainer, BoxLayout.LINE_AXIS));
        container.add(radioButtonContainer);
        ButtonGroup group = new ButtonGroup();
        
        JRadioButton creatingNewSet = new JRadioButton("Create a brand new keyset.");
        creatingNewSet.setActionCommand("createNew");
        creatingNewSet.setSelected(true);
        group.add(creatingNewSet);
        radioButtonContainer.add(creatingNewSet);
        creatingNewSet.addActionListener(this);
        
        JRadioButton useExistingSet = new JRadioButton("Use values which already exist.");
        useExistingSet.setActionCommand("useExisting");
        group.add(useExistingSet);
        radioButtonContainer.add(useExistingSet);
        useExistingSet.addActionListener(this);
        
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if("createNew".equals(e.getActionCommand())) {
            System.out.println("new");
            creatingNewSet = true;
            keyLengthBox.activate();
            filepathBox.activate();
            nameBox.activate();
            generateBox.activate();
            keyValuesBox.deactivate();
            rightNameBox.deactivate();
            rightFilepathBox.deactivate();
            createBox.deactivate();
        } else if ("useExisting".equals(e.getActionCommand())) {
            System.out.println("existing");
            creatingNewSet = false;
            keyLengthBox.deactivate();
            filepathBox.deactivate();
            nameBox.deactivate();
            generateBox.deactivate();
            keyValuesBox.activate();
            rightNameBox.activate();
            rightFilepathBox.activate();
            createBox.activate();
        }
    }
    
    private void buildLeftPanel(JPanel container) {
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        keyLengthBox = new KeyLengthBox();
        container.add(keyLengthBox);
        nameBox = new NameBox();
        container.add(nameBox);
        filepathBox = new FilepathBox();
        container.add(filepathBox);
        generateBox = new GenerateBox(keyLengthBox, nameBox, filepathBox);
        container.add(generateBox);
        
        
    }
    
    private void buildRightPanel(JPanel container) {
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        keyValuesBox = new KeyValuesBox();
        container.add(keyValuesBox);
        rightNameBox = new NameBox();
        rightNameBox.deactivate();
        container.add(rightNameBox);
        rightFilepathBox = new FilepathBox();
        rightFilepathBox.deactivate();
        container.add(rightFilepathBox);
        createBox = new CreateBox(keyValuesBox, rightNameBox, rightFilepathBox);
        container.add(createBox);
        
    }
    
}
