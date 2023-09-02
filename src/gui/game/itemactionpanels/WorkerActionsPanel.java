package gui.game.itemactionpanels;

import entities.Item;
import entities.humans.concretes.Worker;
import exceptions.AgeOfEmpiresException;
import game.GameManager;
import gui.game.itemactionpanels.abstracts.AbstractItemActionsPanel;
import utils.MoveControlUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorkerActionsPanel extends AbstractItemActionsPanel {

    JButton moveButton = new JButton("Move");
    JButton attackButton = new JButton("Attack");
    JButton buildButton = new JButton("Build");

    public WorkerActionsPanel() {
        super();

        var constraints = getConstraints();

        constraints.gridy = 1;
        add(moveButton, constraints);
        constraints.gridy = 2;
        add(attackButton, constraints);
        constraints.gridy = 3;
        add(buildButton, constraints);


        var layout = (GridBagLayout) getLayout();
        layout.rowHeights = new int[getComponentCount() + 1];
        layout.rowWeights = new double[getComponentCount() + 1];
        layout.rowWeights[getComponentCount()] = Double.MIN_VALUE;

        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Worker worker = (Worker) getItem();
                worker.setCurrentState(Item.State.MOVE);

                // todo mapPanel'e eriş. Highlight et
                var mapPanel = GameManager.getInstance().getMainFrame().getGamePanel().getMapPanel();
                Graphics g = mapPanel.getGraphics();
                int workerX = worker.getX();
                int workerY = worker.getY();
                int speed = worker.getMovementSpeed();
                for (int i = workerX - speed; i <= workerX + speed; i++) {
                    for (int j = workerY - speed; j <= workerY + speed; j++) {

                        if (i == workerX && j == workerY) continue;

                        try {
                            MoveControlUtils.checkMoveDistance(worker, i, j);
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
        buildButton.setEnabled(isButtonsEnabled());
    }

}
