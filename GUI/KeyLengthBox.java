//Alexander Weaver
//Last update: 5-28-2015 7:51pm
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class KeyLengthBox extends JPanel implements ActionListener {
    
    private boolean isEnabled;
    private JTextField keyField;
    private String errorMessage = "";
    private JLabel errorText;
    private JLabel label;
    
    public KeyLengthBox() {
        this.setPreferredSize(new Dimension(0, 40));
        this.setMaximumSize(new Dimension(600, 40));
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        label = new JLabel("Key Length");
        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
        this.add(label);
        keyField = new JTextField("", 10);
        keyField.addActionListener(this);
        layout.putConstraint(SpringLayout.NORTH, keyField, 1,SpringLayout.SOUTH, label);
        layout.putConstraint(SpringLayout.WEST, keyField, 5, SpringLayout.WEST, this);
        this.add(keyField);
        errorText = new JLabel(errorMessage);
        errorText.setForeground(Color.RED);
        layout.putConstraint(SpringLayout.WEST, errorText, 5, SpringLayout.EAST, keyField);
        layout.putConstraint(SpringLayout.NORTH, errorText, 17, SpringLayout.NORTH, this);
        this.add(errorText);
        activate();
    }
    
    public void activate() {
        isEnabled = true;
        label.setEnabled(true);
        errorText.setEnabled(true);
        keyField.setEnabled(true);
    }
    
    public void deactivate() {
        isEnabled = false;
        label.setEnabled(false);
        errorText.setEnabled(false);
        keyField.setEnabled(false);
    }
    
    public boolean isActive() {
        return isEnabled;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = keyField.getText();
        if(isWhitespace(text)) {
            setErrorText("Value needed.");
        } else if(!isPositiveInteger(text)) {
            setErrorText("Invalid characters.");
        } else if(Integer.parseInt(text) < 8) {
            setErrorText("Must be at least 8.");
        } else if(Integer.parseInt(text) > 2048) {
            setErrorText("Must be at most 2048.");
        } else {
            setErrorText("");
        }
    }
    
    private boolean isPositiveInteger(String string) {
        return string.matches("\\d+");
    }
    private boolean isWhitespace(String string) {
        return string.matches("^\\s*$");
    }
    
    public void setErrorText(String message) {
        errorMessage = message;
        errorText.setText(errorMessage);
    }
    
    public String getValidText() {
        String text = keyField.getText();
        if(isWhitespace(text)) {
            setErrorText("Value needed.");
            return null;
        } else if(!isPositiveInteger(text)) {
            setErrorText("Invalid characters.");
            return null;
        } else if(Integer.parseInt(text) < 8) {
            setErrorText("Must be at least 8.");
            return null;
        } else if(Integer.parseInt(text) > 2048) {
            setErrorText("Must be at most 2048.");
            return null;
        } else {
            setErrorText("");
            return text;
        }
    }
}
