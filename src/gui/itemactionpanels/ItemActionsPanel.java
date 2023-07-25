package gui.itemactionpanels;

import entities.Item;
import entities.buildings.concretes.MainBuilding;
import entities.buildings.concretes.Tower;
import entities.buildings.concretes.University;
import entities.humans.abstracts.Soldier;
import entities.humans.concretes.Worker;

import javax.swing.*;
import java.awt.*;

public class ItemActionsPanel extends JPanel {
    MainBuildingActionsPanel mainBuildingActionsPanel = new MainBuildingActionsPanel();
    UniversityActionsPanel universityActionsPanel = new UniversityActionsPanel();
    TowerActionsPanel towerActionsPanel = new TowerActionsPanel();
    WorkerActionsPanel workerActionsPanel = new WorkerActionsPanel();
    SoldierActionsPanel soldierActionsPanel = new SoldierActionsPanel();

    public ItemActionsPanel() {
    }

    public void openPanel(Item item) {

        if (item instanceof MainBuilding) {
            mainBuildingActionsPanel.onAddedCustomPanel(item);
            add(mainBuildingActionsPanel);
        } else if (item instanceof University) {
            universityActionsPanel.onAddedCustomPanel(item);
            add(universityActionsPanel);
        } else if (item instanceof Tower) {
            towerActionsPanel.onAddedCustomPanel(item);
            add(towerActionsPanel);
        } else if (item instanceof Worker) {
            workerActionsPanel.onAddedCustomPanel(item);
            add(workerActionsPanel);
        } else if (item instanceof Soldier) {
            soldierActionsPanel.onAddedCustomPanel(item);
            add(soldierActionsPanel);
        }
    }

}
