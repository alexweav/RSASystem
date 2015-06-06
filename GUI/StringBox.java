//Alexander Weaver
//Last update: 6-6-2015 6:42pm
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StringBox extends JPanel implements ActionListener {
    
    private boolean isActive;
    private JLabel label;
    private JTextField field;
    private JLabel errorText;
    private String errorMessage = "";
    
    public StringBox() {
        this.setPreferredSize(new Dimension(0, 40));
        this.setMaximumSize(new Dimension(600, 40));
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        label = new JLabel("String");
        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, this);
        this.add(label);
        field = new JTextField("", 10);
        field.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, field, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, field, 1, SpringLayout.SOUTH, label);
        this.add(field);
        errorText = new JLabel(errorMessage);
        errorText.setForeground(Color.RED);
        layout.putConstraint(SpringLayout.WEST, errorText, 5, SpringLayout.EAST, field);
        layout.putConstraint(SpringLayout.NORTH, errorText, 17, SpringLayout.NORTH, this);
        this.add(errorText);
        activate();
    }
    
    public void activate() {
        isActive = true;
        label.setEnabled(true);
        field.setEnabled(true);
        errorText.setEnabled(true);
    }
    
    public void deactivate() {
        isActive = false;
        label.setEnabled(false);
        field.setEnabled(false);
        errorText.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = field.getText();
        if(isWhitespace(text)) {
            setErrorText("Value needed.");
        } else {
            setErrorText("");
        }
    }
    
    public void setErrorText(String message) {
        errorMessage = message;
        errorText.setText(errorMessage);
    }
    
    private boolean isWhitespace(String string) {
        return string.matches("^\\s*$");
    }
    
    public String getString() {
        String text = field.getText();
        if(isWhitespace(text)) {
            setErrorText("Value needed.");
            return null;
        } else {
            setErrorText("");
            return text;
        }
    }
}
