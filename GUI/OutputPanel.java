//Alexander Weaver
//Last update: 5-23-2015 6:32pm
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OutputPanel extends JPanel implements ActionListener {
    
    private String statusText = "";
    private JEditorPane textArea;
    
    public OutputPanel(JComponent parent, int width, int height) {
        this.setMaximumSize(new Dimension(width, height));
        this.setLayout(new BorderLayout());
        parent.add(this);
        JLabel label = new JLabel("Status:");
        this.add(label, BorderLayout.PAGE_START);
        textArea = new JEditorPane();
        JScrollPane editorScrollPane = new JScrollPane(textArea);
        textArea.setText(statusText);
        editorScrollPane.setPreferredSize(new Dimension(450, 60));
        editorScrollPane.setMinimumSize(new Dimension(450, 60));
        this.add(editorScrollPane, BorderLayout.CENTER);
        JButton clipboardButton = new JButton("Copy Status");
        clipboardButton.setActionCommand("copyText");
        clipboardButton.addActionListener(this);
        this.add(clipboardButton, BorderLayout.LINE_END);
    }
    
    public void actionPerformed(ActionEvent e) {
        if("copyText".equals(e.getActionCommand())) {
            System.out.println("copy");
            StringSelection ss = new StringSelection(statusText);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(ss, null);
        }
    }
    
    public void setStatusText(String str) {
        statusText = str;
        textArea.setText(statusText);
    }
    
    public String getStatusText() {
        return statusText;
    }
}
