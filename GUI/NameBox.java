//Alexander Weaver
//Last update: 5-28-2015 7:04pm
package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class NameBox extends JPanel implements ActionListener {
    
    private boolean isActive;
    private JLabel label;
    private JTextField field;
    private JLabel errorText;
    private String errorMessage = "";
    
    public NameBox() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        label = new JLabel("Certificate Name");
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
        } else if(!isValidFilename(text)) {
            setErrorText("Invalid characters.");
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
    
    private boolean isValidFilename(String string) {
        return string.matches("[\\w\\d\\ \\^\\&\'\\@\\{\\}\\[\\]\\,\\$\\=\\!\\-\\#\\(\\)\\%\\.\\+\\~\\_]+");
    }
    
    public String getValidName() {
        String text = field.getText();
            if(isWhitespace(text)) {
                setErrorText("Value needed.");
                return null;
            } else if (!isValidFilename(text)) {
                setErrorText("Invalid characters.");
                return null;
            } else {
                setErrorText("");
                return text;
            }
    }
}
