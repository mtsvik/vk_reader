package view;

import model.Item;
import org.json.JSONException;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.io.IOException;

/**
 * Author: Mikhail Tsvik (tsvik@me.com)
 * Date: 24.09.14
 */

public class ContentListRenderer implements ListCellRenderer {

    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
    private Item item;

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        item = (Item) value;

        try {
            String out = "<html>";
            String postText = "<br>" + item.get_text() + "<br>";
            String posterName = item.getPosterName();
            String time = item.getPostTime();

            out += posterName;
            out += postText;
            out += time;
            out += "</html>";
            renderer.setText(out);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        renderer.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(224, 224, 224)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        return renderer;
    }


}
