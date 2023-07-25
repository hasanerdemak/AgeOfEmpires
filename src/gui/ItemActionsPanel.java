package gui;

import javax.swing.*;
import java.awt.*;

public class ItemActionsPanel extends JPanel {
    public ItemActionsPanel() {
        //setLayout(new GridBagLayout());
        //setPreferredSize(new Dimension(200, getFrameHeight()));
        setBackground(Color.BLUE);
    }

    private int getFrameHeight() {
        Container parent = this.getParent();
        if (parent instanceof JFrame) {
            return ((JFrame) parent).getHeight();
        }
        // Handle the case where the parent is not a JFrame (e.g., if panel is used in a different container)
        return -1; // Return a default value or handle the case as needed
    }
}
