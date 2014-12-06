package view;

import model.Item;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Mikhail Tsvik (tsvik@me.com)
 * Date: 14.06.14
 */

public class MainFrame extends JFrame {

    private JFrame frame;
    private JList sidebar;
    private JList<Item> content;


    public MainFrame(Item[] items) {
        frameProperties();
        sidebar();
        content(items);
        frame.setVisible(true);
    }


    private void frameProperties() {
        frame = new JFrame("VK Reader");
        frame.getContentPane().setLayout(new BorderLayout(0, 0));
        frame.setMinimumSize(new Dimension(500, 550));
        frame.setMaximumSize(new Dimension(700, Integer.MAX_VALUE));
        frame.setBounds(60, 60, 550, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void sidebar() {
        sidebar = new JList();
        frame.getContentPane().add(sidebar, BorderLayout.WEST);
        sidebar.setBackground(new Color(39,39,39));
        sidebar.setPreferredSize(new Dimension(70, 0));

    }

    private void content(Item[] items) {
        content = new JList<Item>(items);
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        ListCellRenderer renderer = new ContentListRenderer();
        content.setCellRenderer(renderer);
        frame.getContentPane().add(scrollPane);

    }
}
