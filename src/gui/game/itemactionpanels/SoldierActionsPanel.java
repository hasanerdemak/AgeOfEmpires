package gui.game.itemactionpanels;

import entities.Item;
import entities.humans.abstracts.Human;
import entities.humans.abstracts.Soldier;
import entities.humans.concretes.Worker;
import exceptions.AgeOfEmpiresException;
import game.GameManager;
import gui.game.MapPanel;
import gui.game.itemactionpanels.abstracts.AbstractItemActionsPanel;
import utils.MoveControlUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SoldierActionsPanel extends AbstractItemActionsPanel {
    JButton moveButton = new JButton("Move");
    JButton attackButton = new JButton("Attack");

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
                var mapPanel = GameManager.getInstance().getMainFrame().getGamePanel().getMapPanel();
                Graphics g = mapPanel.getGraphics();
                Soldier soldier = (Soldier) getItem();
                int soldierX = soldier.getX();
                int soldierY = soldier.getY();
                soldier.setCurrentState(Item.State.MOVE);

                int speed = soldier.getMovementSpeed();
                for (int i = soldierX - speed; i <= soldierX + speed; i++) {
                    for (int j = soldierY - speed; j <= soldierY + speed; j++) {

                        if (i == soldierX && j == soldierY) continue;

                        try {
                            MoveControlUtils.checkMoveDistance(soldier, i, j);
                            mapPanel.highlightBlock(g, i, j);
                        } catch (AgeOfEmpiresException ex) {

                        }
                    }
                }
            }
        });

        attackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var mapPanel = GameManager.getInstance().getMainFrame().getGamePanel().getMapPanel();
                Graphics g = mapPanel.getGraphics();
                Soldier soldier = (Soldier) getItem();
                int soldierX = soldier.getX();
                int soldierY = soldier.getY();
                soldier.setCurrentState(Item.State.ATTACK);
                // todo Archer'da sorun var yanlarını highlight etmiyor
                int upperLimit = (int) soldier.getUpperAttackDistanceLimit();
                for (int i = soldierX - upperLimit; i <= soldierX + upperLimit; i++) {
                    for (int j = soldierY - upperLimit; j <= soldierY + upperLimit; j++) {

                        if (i == soldierX && j == soldierY) continue;

                        try {
                            MoveControlUtils.checkAttackDistance(soldier, i, j);
                            mapPanel.highlightBlock(g, i, j);
                        } catch (AgeOfEmpiresException ex) {

                        }
                    }
                }
            }
        });

    }

    @Override
    public void checkButtonEnableState() {
        moveButton.setEnabled(isButtonsEnabled());
        attackButton.setEnabled(isButtonsEnabled());
    }

}
