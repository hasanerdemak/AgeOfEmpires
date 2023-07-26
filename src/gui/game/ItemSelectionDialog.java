package gui.game;

import entities.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ItemSelectionDialog extends JDialog {
    private Item selectedItem;

    public ItemSelectionDialog(Frame parent, List<Item> items) {
        super(parent, "Select Item", true);

        ButtonGroup buttonGroup = new ButtonGroup();
        JPanel panel = new JPanel(new GridLayout(0, 1));

        for (Item item : items) {
            JRadioButton radioButton = new JRadioButton(item.getClass().getSimpleName() + "-" + item.getItemID());
            radioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedItem = item;
                }
            });
            buttonGroup.add(radioButton);
            panel.add(radioButton);
        }

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(okButton);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    public Item getSelectedItem() {
        return selectedItem;
    }
}

