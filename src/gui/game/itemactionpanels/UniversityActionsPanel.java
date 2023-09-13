package gui.game.itemactionpanels;

import entities.Item;
import entities.buildings.concretes.University;
import gui.game.itemactionpanels.abstracts.AbstractItemActionsPanel;

import javax.swing.*;
import java.awt.*;

public class UniversityActionsPanel extends AbstractItemActionsPanel {
    JButton trainInfantryButton = new JButton("Train Infantry");
    JButton trainCavalryButton = new JButton("Train Cavalry");
    JButton trainCatapultButton = new JButton("Train Catapult");

    public UniversityActionsPanel() {
        super();

        var constraints = getConstraints();

        constraints.gridy = 1;
        add(trainInfantryButton, constraints);
        constraints.gridy = 2;
        add(trainCavalryButton, constraints);
        constraints.gridy = 3;
        add(trainCatapultButton, constraints);

        var layout = (GridBagLayout) getLayout();
        layout.rowHeights = new int[getComponentCount() + 1];
        layout.rowWeights = new double[getComponentCount() + 1];
        layout.rowWeights[getComponentCount()] = Double.MIN_VALUE;
    }

    @Override
    public void onPanelVisible(Item item) {
        super.onPanelVisible(item);
        var university = (University) item;

        String info = item +
                "\n" +
                "Symbol: " + item.getSymbol() +
                "\n" +
                "x: " + item.getX() +
                "\n" +
                "y: " + item.getY() +
                "\n" +
                "life points: " + item.getLifePoints() +
                "\n" +
                "Infantry Training Count: " + university.getInfantryTrainingCount() +
                "\n" +
                "Cavalry Training Count: " + university.getCavalryTrainingCount() +
                "\n" +
                "Catapult Training Count: " + university.getCatapultTrainingCount();
        getInfoTextArea().setText(info);
    }

    @Override
    public void checkButtonEnableState() {
        trainInfantryButton.setEnabled(isButtonsEnabled());
        trainCavalryButton.setEnabled(isButtonsEnabled());
        trainCatapultButton.setEnabled(isButtonsEnabled());
    }
}
