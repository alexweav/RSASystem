//Alexander Weaver
//Last update: 5-24-2015 2:35pm
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OutputPanel extends JPanel implements ActionListener {
    
    //tracks the text in the status pane
    private String statusText = "";
    
    //the text box in the status pane
    private JEditorPane textArea;
    
    //constructor, sequentially builds the output panel
    public OutputPanel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new BorderLayout());
        JLabel label = new JLabel("Status:");
        this.add(label, BorderLayout.PAGE_START);
        textArea = new JEditorPane();
        JScrollPane editorScrollPane = new JScrollPane(textArea);
        textArea.setText(statusText);
        textArea.setEditable(false);
        editorScrollPane.setPreferredSize(new Dimension(450, 60));
        editorScrollPane.setMinimumSize(new Dimension(450, 60));
        this.add(editorScrollPane, BorderLayout.CENTER);
        JButton clipboardButton = new JButton("Copy Status");
        clipboardButton.setActionCommand("copyText");
        clipboardButton.addActionListener(this);
        this.add(clipboardButton, BorderLayout.LINE_END);
    }
    
    //actions method for interactible items
    @Override
    public void actionPerformed(ActionEvent e) {
        if("copyText".equals(e.getActionCommand())) {   //copies status pane to clipboard
            System.out.println("copy");
            StringSelection ss = new StringSelection(statusText);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(ss, null);
        }
    }
    
    //sets the text in the status box to the given string
    public void setStatusText(String str) {
        statusText = str;
        textArea.setText(statusText);
    }
    
    //returns the text in the status box
    public String getStatusText() {
        return statusText;
    }
}
