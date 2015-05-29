//Alexander Weaver
//Last update: 5-28-2015 7:27pm
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

public class FilepathBox extends JPanel implements ActionListener {
    
    private boolean isEnabled;
    private JLabel label;
    private JTextField field;
    private JLabel errorText;
    private String errorMessage = "";
    private JButton browseButton;
    
    public FilepathBox() {
        this.setPreferredSize(new Dimension(0, 70));
        this.setMaximumSize(new Dimension(600, 70));
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        label = new JLabel("File Location");
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
        browseButton = new JButton("Browse (defunct)");
        browseButton.setActionCommand("browse");
        browseButton.addActionListener(this);
        layout.putConstraint(SpringLayout.WEST, browseButton, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, browseButton, 2, SpringLayout.SOUTH, field);
        this.add(browseButton);
        activate();
    }
    
    public void activate() {
        isEnabled = true;
        label.setEnabled(true);
        field.setEnabled(true);
        errorText.setEnabled(true);
        browseButton.setEnabled(true);
    }
    
    public void deactivate() {
        isEnabled = false;
        label.setEnabled(false);
        field.setEnabled(false);
        errorText.setEnabled(false);
        browseButton.setEnabled(false);
    }
    
    public boolean isActive() {
        return isEnabled;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("browse".equals(e.getActionCommand())) {
            System.out.println("Defunct.");
        } else {
            String text = field.getText();
            if(isWhitespace(text)) {
                setErrorText("Value needed.");
            } else if (!isFilepath(text)) {
                setErrorText("Not a valid filepath.");
            } else if (!directoryExistsOnMachine(text)) {
                setErrorText("Directory not found.");
            } else {
                setErrorText("");
            }
        }
    }
    
    public void setErrorText(String message) {
        errorMessage = message;
        errorText.setText(errorMessage);
    }
    
    private boolean isWhitespace(String string) {
        return string.matches("^\\s*$");
    }
    
    private boolean isFilepath(String string) {
        return string.matches("[A-Z]\\:(\\\\[\\w\\d\\ \\^\\&\'\\@\\{\\}\\[\\]\\,\\$\\=\\!\\-\\#\\(\\)\\%\\.\\+\\~\\_]+)*");
    }
    
    private boolean directoryExistsOnMachine(String string) {
        if(!isFilepath(string)) {
            return false;
        } else {
            
            return (new File(doubleBackslashes(string))).exists();
        }
        
    }
    
    private String doubleBackslashes(String string) {
        return string.replaceAll("\\\\", "\\\\\\\\");
    }
    
    public String getValidDirectory() {
        String text = field.getText();
            if(isWhitespace(text)) {
                setErrorText("Value needed.");
                return null;
            } else if (!isFilepath(text)) {
                setErrorText("Not a valid filepath.");
                return null;
            } else if (!directoryExistsOnMachine(text)) {
                setErrorText("Directory not found.");
                return null;
            } else {
                setErrorText("");
                return text;
            }
    }
}
