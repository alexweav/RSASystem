//Alexander Weaver
//Last update: 5-30-2015 4:49pm
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class KeyValuesBox extends JPanel implements ActionListener {
    
    private JLabel publicLabel;
    private JLabel privateLabel;
    private JLabel exponentLabel;
    
    private JTextField publicField;
    private JTextField privateField;
    private JTextField exponentField;
    
    private JLabel publicError;
    private JLabel privateError;
    private JLabel exponentError;
    
    private String publicErrorText = "";
    private String privateErrorText = "";
    private String exponentErrorText = "";
    
    private boolean isActive;
    
    public KeyValuesBox() {
        this.setPreferredSize(new Dimension(0, 122));
        this.setMaximumSize(new Dimension(600, 122));
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        publicLabel = new JLabel("Public Key");
        layout.putConstraint(SpringLayout.WEST, publicLabel, 5, SpringLayout.WEST, this);
        this.add(publicLabel);
        publicField = new JTextField("", 10);
        publicField.setActionCommand("public");
        publicField.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, publicField, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, publicField, 2, SpringLayout.SOUTH, publicLabel);
        this.add(publicField);
        publicError = new JLabel(publicErrorText);
        publicError.setForeground(Color.RED);
        layout.putConstraint(SpringLayout.WEST, publicError, 5, SpringLayout.EAST, publicField);
        layout.putConstraint(SpringLayout.NORTH, publicError, 18, SpringLayout.NORTH, this);
        this.add(publicError);
        
        privateLabel = new JLabel("Private Key");
        layout.putConstraint(SpringLayout.WEST, privateLabel, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, privateLabel, 2, SpringLayout.SOUTH, publicField);
        this.add(privateLabel);
        privateField = new JTextField("", 10);
        privateField.setActionCommand("private");
        privateField.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, privateField, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, privateField, 2, SpringLayout.SOUTH, privateLabel);
        this.add(privateField);
        privateError = new JLabel(privateErrorText);
        privateError.setForeground(Color.RED);
        layout.putConstraint(SpringLayout.WEST, privateError, 5, SpringLayout.EAST, privateField);
        layout.putConstraint(SpringLayout.NORTH, privateError, 59, SpringLayout.NORTH, this);
        this.add(privateError);
        
        exponentLabel = new JLabel("Key Exponent");
        layout.putConstraint(SpringLayout.WEST, exponentLabel, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, exponentLabel, 2, SpringLayout.SOUTH, privateField);
        this.add(exponentLabel);
        exponentField = new JTextField("", 10);
        exponentField.setActionCommand("exponent");
        exponentField.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, exponentField, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, exponentField, 2, SpringLayout.SOUTH, exponentLabel);
        this.add(exponentField);
        exponentError = new JLabel(exponentErrorText);
        exponentError.setForeground(Color.RED);
        layout.putConstraint(SpringLayout.WEST, exponentError, 5, SpringLayout.EAST, exponentField);
        layout.putConstraint(SpringLayout.NORTH, exponentError, 99, SpringLayout.NORTH, this);
        this.add(exponentError);
        deactivate();
    }
    
    public void activate() {
        isActive = true;
        publicLabel.setEnabled(true);
        privateLabel.setEnabled(true);
        exponentLabel.setEnabled(true);
        publicField.setEnabled(true);
        privateField.setEnabled(true);
        exponentField.setEnabled(true);
        publicError.setEnabled(true);
        privateError.setEnabled(true);
        exponentError.setEnabled(true);
    }
    
    public void deactivate() {
        isActive = false;
        publicLabel.setEnabled(false);
        privateLabel.setEnabled(false);
        exponentLabel.setEnabled(false);
        publicField.setEnabled(false);
        privateField.setEnabled(false);
        exponentField.setEnabled(false);
        publicError.setEnabled(false);
        privateError.setEnabled(false);
        exponentError.setEnabled(false);
    }
    
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String mnemonic = e.getActionCommand();
        if("public".equals(mnemonic)) {
            String text = publicField.getText();
            if(isWhitespace(text)) {
                setPublicErrorText("Value needed.");
            } else if (!isPositiveInteger(text)) {
                setPublicErrorText("Invalid characters.");
            } else if(Integer.parseInt(text) < 1) {
                setPublicErrorText("Cannot be zero.");
            } else {
                setPublicErrorText("");
            }
        } else if ("private".equals(mnemonic)) {
            String text = privateField.getText();
            if(isWhitespace(text)) {
                setPrivateErrorText("Value needed.");
            } else if (!isPositiveInteger(text)) {
                setPrivateErrorText("Invalid characters.");
            } else if(Integer.parseInt(text) < 1) {
                setPrivateErrorText("Cannot be zero.");
            } else {
                setPrivateErrorText("");
            }
        } else if ("exponent".equals(mnemonic)) {
            String text = exponentField.getText();
            if(isWhitespace(text)) {
                setExponentErrorText("Value needed.");
            } else if (!isPositiveInteger(text)) {
                setExponentErrorText("Invalid characters.");
            } else if(Integer.parseInt(text) < 1) {
                setPrivateErrorText("Cannot be zero.");
            } else {
                setExponentErrorText("");
            }
        }
    }
    
    private boolean isPositiveInteger(String string) {
        return string.matches("\\d+");
    }
    private boolean isWhitespace(String string) {
        return string.matches("^\\s*$");
    }
    
    public void setPublicErrorText(String text) {
        publicErrorText = text;
        publicError.setText(text);
    }
    
    public void setPrivateErrorText(String text) {
        privateErrorText = text;
        privateError.setText(text);
    }
    
    public void setExponentErrorText(String text) {
        exponentErrorText = text;
        exponentError.setText(text);
    }
    
    public String getValidPublicText() {
        String text = publicField.getText();
        if(isWhitespace(text)) {
            setPublicErrorText("Value needed.");
            return null;
        } else if (!isPositiveInteger(text)) {
            setPublicErrorText("Invalid characters.");
            return null;
        } else if(Integer.parseInt(text) < 1) {
            setPublicErrorText("Cannot be zero.");
            return null;
        } else {
            setPublicErrorText("");
            return text;
        }
    }
    
    public String getValidPrivateText() {
        String text = privateField.getText();
        if(isWhitespace(text)) {
            setPrivateErrorText("Value needed.");
            return null;
        } else if (!isPositiveInteger(text)) {
            setPrivateErrorText("Invalid characters.");
            return null;
        } else if(Integer.parseInt(text) < 1) {
            setPrivateErrorText("Cannot be zero.");
            return null;
        } else {
            setPrivateErrorText("");
            return text;
        }
    }
    
    public String getValidExponentText() {
        String text = exponentField.getText();
        if(isWhitespace(text)) {
            setExponentErrorText("Value needed.");
            return null;
        } else if (!isPositiveInteger(text)) {
            setExponentErrorText("Invalid characters.");
            return null;
        } else if(Integer.parseInt(text) < 1) {
            setPrivateErrorText("Cannot be zero.");
            return null;
        } else {
            setExponentErrorText("");
            return text;
        }
    }
}
