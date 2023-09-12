package gui.game.selectiondialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuildSelectionDialog extends JDialog {
    private String selectedBuildingName;

    public BuildSelectionDialog(Frame parent) {
        super(parent, "Select Building", true);

        ButtonGroup buttonGroup = new ButtonGroup();
        JPanel panel = new JPanel(new GridLayout(0, 1));

        String[] itemNames = new String[]{"University", "Tower"};
        for (String itemName : itemNames) {
            JRadioButton radioButton = new JRadioButton(itemName);
            radioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedBuildingName = itemName;
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

    public String getSelectedBuildingName() {
        return selectedBuildingName;
    }
}
