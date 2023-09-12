package gui.game.itemactionpanels;

import entities.Item;
import entities.humans.concretes.Worker;
import exceptions.AgeOfEmpiresException;
import game.GameManager;
import gui.game.itemactionpanels.abstracts.AbstractItemActionsPanel;
import gui.game.selectiondialogs.BuildSelectionDialog;
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

        initializeButtonActionListeners();

    }

    private void initializeButtonActionListeners() {
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

        attackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Worker worker = (Worker) getItem();
                worker.setCurrentState(Item.State.ATTACK);

                var mapPanel = GameManager.getInstance().getMainFrame().getGamePanel().getMapPanel();
                Graphics g = mapPanel.getGraphics();
                int workerX = worker.getX();
                int workerY = worker.getY();
                // todo upperLimit doğru mu kontrol et (doğru bloklar boyanıyor mu)
                int upperLimit = (int) worker.getUpperAttackDistanceLimit();
                for (int i = workerX - upperLimit; i <= workerX + upperLimit; i++) {
                    for (int j = workerY - upperLimit; j <= workerY + upperLimit; j++) {

                        if (i == workerX && j == workerY) continue;

                        try {
                            MoveControlUtils.checkAttackDistance(worker, i, j);
                            mapPanel.highlightBlock(g, i, j);
                        } catch (AgeOfEmpiresException ex) {

                        }
                    }
                }
            }
        });


        buildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Worker worker = (Worker) getItem();
                worker.setCurrentState(Item.State.BUILD);

                var dialog = new BuildSelectionDialog((Frame) SwingUtilities.getWindowAncestor(WorkerActionsPanel.this));
                dialog.setVisible(true);

                String selectedBuildingName = dialog.getSelectedBuildingName();

                try {
                    var gameManager = GameManager.getInstance();
                    gameManager.build(worker, selectedBuildingName);
                    gameManager.getMainFrame().getGamePanel().getMapPanel().onTourPassed();
                } catch (AgeOfEmpiresException ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
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
