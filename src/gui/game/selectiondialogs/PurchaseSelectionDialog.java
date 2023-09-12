package gui.game.selectiondialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PurchaseSelectionDialog extends JDialog {

    private String selectedItemName;

    public PurchaseSelectionDialog(Frame parent) {
        super(parent, "Select Human", true);

        ButtonGroup buttonGroup = new ButtonGroup();
        JPanel panel = new JPanel(new GridLayout(0, 1));

        String[] itemNames = new String[]{"Worker", "Swordman", "Spearman", "Archer", "Cavalry", "Catapult"};
        for (String itemName : itemNames) {
            JRadioButton radioButton = new JRadioButton(itemName);
            radioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedItemName = itemName;
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

    public String getSelectedItemName() {
        return selectedItemName;
    }
}
