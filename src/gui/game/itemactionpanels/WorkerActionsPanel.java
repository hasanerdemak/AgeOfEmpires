package gui.game.itemactionpanels;

import gui.game.itemactionpanels.abstracts.AbstractItemActionsPanel;

import javax.swing.*;
import java.awt.*;

public class WorkerActionsPanel extends AbstractItemActionsPanel {

    JButton moveButton = new JButton("Move");
    JButton attackButton = new JButton("Attack");
    JButton buildButton = new JButton("Build");

    public WorkerActionsPanel(){
        super();

        var constraints = getConstraints();

        constraints.gridy = 1;
        add(moveButton, constraints);
        constraints.gridy = 2;
        add(attackButton, constraints);
        constraints.gridy = 3;
        add(buildButton, constraints);


        var layout = (GridBagLayout) getLayout();
        layout.rowHeights = new int[getComponentCount()+1];
        layout.rowWeights = new double[getComponentCount()+1];
        layout.rowWeights[getComponentCount()] = Double.MIN_VALUE;

    }

    @Override
    public void checkButtonEnableState() {
        moveButton.setEnabled(isButtonsEnabled());
        attackButton.setEnabled(isButtonsEnabled());
        buildButton.setEnabled(isButtonsEnabled());
    }

}
