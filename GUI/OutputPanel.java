//Alexander Weaver
//Last update: 5-21-2015 2:29pm
package GUI;

import javax.swing.*;
import java.awt.*;

public class OutputPanel extends JPanel {
    
    public OutputPanel(JComponent parent, int width, int height) {
        this.setMaximumSize(new Dimension(width, height));
        //outputPanel.setBackground(Color.white);
        parent.add(this);
    }
}
