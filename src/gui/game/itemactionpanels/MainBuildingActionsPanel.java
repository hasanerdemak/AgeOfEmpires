package gui.game.itemactionpanels;

import exceptions.AgeOfEmpiresException;
import game.GameManager;
import gui.game.ItemSelectionDialog;
import gui.game.PurchaseSelectionDialog;
import gui.game.itemactionpanels.abstracts.AbstractItemActionsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainBuildingActionsPanel extends AbstractItemActionsPanel {
    JButton purchaseButton = new JButton("purchase");

    public MainBuildingActionsPanel() {
        super();

        var constraints = getConstraints();

        constraints.gridy = 1;
        add(purchaseButton, constraints);

        var layout = (GridBagLayout) getLayout();
        layout.rowHeights = new int[getComponentCount()+1];
        layout.rowWeights = new double[getComponentCount()+1];
        layout.rowWeights[getComponentCount()] = Double.MIN_VALUE;

        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var dialog = new PurchaseSelectionDialog((Frame) SwingUtilities.getWindowAncestor(MainBuildingActionsPanel.this));
                dialog.setVisible(true);

                String selectedItemName = dialog.getSelectedItemName();

                try {
                    var gameManager = GameManager.getInstance();
                    gameManager.purchase(gameManager.getGame().getCurrentPlayer(), selectedItemName);
                } catch (AgeOfEmpiresException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

    @Override
    public void checkButtonEnableState() {
        purchaseButton.setEnabled(isButtonsEnabled());
    }

}
