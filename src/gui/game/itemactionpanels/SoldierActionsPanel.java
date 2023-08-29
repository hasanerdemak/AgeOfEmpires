package gui.game.itemactionpanels;

import entities.Item;
import entities.humans.abstracts.Human;
import entities.humans.abstracts.Soldier;
import exceptions.AgeOfEmpiresException;
import game.GameManager;
import gui.game.MapPanel;
import gui.game.itemactionpanels.abstracts.AbstractItemActionsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SoldierActionsPanel extends AbstractItemActionsPanel {
    JButton moveButton = new JButton("Move");
    JButton attackButton = new JButton("Attack");
    Soldier soldier;

    public SoldierActionsPanel() {
        super();

        var constraints = getConstraints();

        constraints.gridy = 1;
        add(moveButton, constraints);
        constraints.gridy = 2;
        add(attackButton, constraints);

        var layout = (GridBagLayout) getLayout();
        layout.rowHeights = new int[getComponentCount() + 1];
        layout.rowWeights = new double[getComponentCount() + 1];
        layout.rowWeights[getComponentCount()] = Double.MIN_VALUE;


        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soldier.setCurrentState(Item.State.MOVE);

                        // todo mapPanel'e eriş. Highlight et

                int x = 0, y = 0;

                try {
                    var gameManager = GameManager.getInstance();
                    gameManager.move(soldier, x, y);
                } catch (AgeOfEmpiresException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

    @Override
    public void checkButtonEnableState() {
        moveButton.setEnabled(isButtonsEnabled());
        attackButton.setEnabled(isButtonsEnabled());
    }

    @Override
    public void onPanelVisible(Item item) {
        super.onPanelVisible(item);

        soldier = (Soldier) item;
    }
}
