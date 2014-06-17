package view;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Mikhail Tsvik (tsvik@me.com)
 * Date: 14.06.14
 */

public class MainFrame extends JFrame {

    private JFrame frame;
    private JList sidebar;
    private JList content;


    public MainFrame() {
        super("VK Reader");
        frameProperties();
        sidebar();
        content();
    }


    private void frameProperties() {
        frame = new JFrame();
        frame.getContentPane().setLayout(new BorderLayout(0, 0));
        frame.setMinimumSize(new Dimension(350, 350));
        frame.setMaximumSize(new Dimension(600, Integer.MAX_VALUE));
        frame.setBounds(60, 60, 500, 650);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void sidebar() {
        sidebar = new JList();
        sidebar.setBackground(Color.GRAY);
        sidebar.setPreferredSize(new Dimension(70, 0));
        frame.getContentPane().add(sidebar, BorderLayout.WEST);
    }

    private void content() {
        content = new JList();
        content.setPreferredSize(new Dimension(100, 0));
        frame.getContentPane().add(content, BorderLayout.CENTER);
    }
}
