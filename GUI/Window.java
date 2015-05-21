//Alexander Weaver
//Last update: 5-21-2015 4:59pm
package GUI;

import java.awt.*;
import javax.swing.*;

public class Window extends JFrame {
    private static final int WINDOW_WIDTH = 600;
    private static final int TAB_HEIGHT = 400;
    private static final int OUTPUT_HEIGHT = 100;
    //Window consists of tabbed panel and an output box aligned vertically, so the window's total height is the sum of the heights of the two components
    private static final int WINDOW_HEIGHT = TAB_HEIGHT + OUTPUT_HEIGHT;
    
    public Window() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        this.add(container);
        ControlsPanel controlsPanel = new ControlsPanel((JComponent) container, WINDOW_WIDTH, TAB_HEIGHT);
        OutputPanel outputPanel = new OutputPanel((JComponent)container, WINDOW_WIDTH, OUTPUT_HEIGHT);
        this.setVisible(true);
    }
}