//Alexander Weaver
//Last update: 5-21-2015 2:29pm
package GUI;

import javax.swing.*;
import java.awt.*;

public class OutputPanel extends JPanel {
    
    public OutputPanel(JComponent parent, int width, int height) {
        this.setMaximumSize(new Dimension(width, height));
        this.setLayout(new BorderLayout());
        parent.add(this);
        JLabel label = new JLabel("Status:");
        this.add(label, BorderLayout.PAGE_START);
        JEditorPane textArea = new JEditorPane();
        JScrollPane editorScrollPane = new JScrollPane(textArea);
        editorScrollPane.setPreferredSize(new Dimension(500, 60));
        editorScrollPane.setMinimumSize(new Dimension(500, 60));
        this.add(editorScrollPane, BorderLayout.LINE_START);
    }
}
