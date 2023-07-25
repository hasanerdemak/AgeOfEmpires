package gui.itemactionpanels;

import entities.Item;
import entities.buildings.concretes.Tower;
import gui.itemactionpanels.abstracts.AbstractItemActionsPanel;

import javax.swing.*;

public class TowerActionsPanel extends AbstractItemActionsPanel {
    JButton attackButton = new JButton("Attack!");

    public TowerActionsPanel(){
        super();

        var constraints = getConstraints();
    }

}
