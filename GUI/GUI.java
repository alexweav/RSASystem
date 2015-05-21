//Alexander Weaver
//Last update: 5-15-2015 1:27am
package GUI;

import java.awt.*;
import javax.swing.*;

public class GUI {
    private static final int WINDOW_WIDTH = 600;
    private static final int TAB_HEIGHT = 400;
    private static final int OUTPUT_HEIGHT = 100;
    //Window consists of tabbed panel and an output box aligned vertically, so the window's total height is the sum of the heights of the two components
    private static final int WINDOW_HEIGHT = TAB_HEIGHT + OUTPUT_HEIGHT;
    
    public void init() {
        JFrame window = new JFrame("RSA System");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setLocationRelativeTo(null);
        windowSetup(window);
        window.setVisible(true);
    }
    
    private void windowSetup(JFrame window) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        window.add(container);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setMaximumSize(new Dimension(WINDOW_WIDTH, TAB_HEIGHT));
        JPanel encryptionPanel = new JPanel();
        encryptionPanel.setMaximumSize(new Dimension(WINDOW_WIDTH, TAB_HEIGHT));
        JPanel decryptionPanel = new JPanel();
        decryptionPanel.setMaximumSize(new Dimension(WINDOW_WIDTH, TAB_HEIGHT));
        JPanel generationPanel = new JPanel();
        generationPanel.setMaximumSize(new Dimension(WINDOW_WIDTH, TAB_HEIGHT));
        tabbedPane.addTab("Encryption", encryptionPanel);
        tabbedPane.addTab("Decryption", decryptionPanel);
        tabbedPane.addTab("Key Generation", generationPanel);
        container.add(tabbedPane);
        buildOutputPanel(container);
    }
    
    //builds the output panel onto any specified container component.
    private void buildOutputPanel(JComponent container) {
        JPanel outputPanel = new JPanel();
        outputPanel.setMaximumSize(new Dimension(WINDOW_WIDTH, OUTPUT_HEIGHT));
        //outputPanel.setBackground(Color.white);
        container.add(outputPanel);
    }

}
