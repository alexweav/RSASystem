//Alexander Weaver
//Last update: 5-23-2015 11:37pm
package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class KeyGenerationPanel extends JPanel implements ActionListener {
    
    private OutputPanel outputPanel;
    
    private boolean creatingNewSet;
    
    public KeyGenerationPanel(OutputPanel op) {
        outputPanel = op;
        creatingNewSet = true;
        

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.PAGE_AXIS));
        JLabel title = new JLabel("How do you wish to obtain the key values?");
        title.setAlignmentX((float)0.5);
        header.add(title);
        addRadioButtons(header);
        this.add(header);
        
        JPanel leftPanel = new JPanel();
        this.add(leftPanel);
        
        JPanel rightPanel = new JPanel();
        this.add(rightPanel);
        
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
        } else if ("useExisting".equals(e.getActionCommand())) {
            System.out.println("existing");
            creatingNewSet = false;
        }
    }
    
}
