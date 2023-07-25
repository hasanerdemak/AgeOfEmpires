package gui.itemactionpanels;

import gui.itemactionpanels.abstracts.AbstractItemActionsPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainBuildingActionsPanel extends AbstractItemActionsPanel {
    JButton purchaseButton = new JButton("purchase");

    public MainBuildingActionsPanel() {
        super();

        var constraints = getConstraints();

        constraints.gridy = 1;
        add(purchaseButton, constraints);

        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("main building purchase");
            }
        });

    }

}
