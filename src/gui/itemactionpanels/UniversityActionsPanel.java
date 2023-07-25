package gui.itemactionpanels;

import entities.Item;
import entities.buildings.concretes.University;
import gui.itemactionpanels.abstracts.AbstractItemActionsPanel;

import javax.swing.*;
import java.awt.*;

public class UniversityActionsPanel extends AbstractItemActionsPanel {
    JButton trainInfantryButton = new JButton("Train Infantry");
    JButton trainCavalryButton = new JButton("Train Cavalry");
    JButton trainCatapultButton = new JButton("Train Catapult");

    public UniversityActionsPanel(){
        super();

        var constraints = getConstraints();

        constraints.gridy = 1;
        add(trainInfantryButton);
        constraints.gridy = 2;
        add(trainCavalryButton);
        constraints.gridy = 3;
        add(trainCatapultButton);
    }

    @Override
    public void onAddedCustomPanel(Item item) {
        var university = (University) item;
        String info = university.print_message() +
                "\n" +
                "Infantry Training Count: " + university.getInfantryTrainingCount() +
                "\n" +
                "Cavalry Training Count: " + university.getCavalryTrainingCount() +
                "\n" +
                "Catapult Training Count: " + university.getCatapultTrainingCount();
        getInfoLabel().setText(info);
    }
}
