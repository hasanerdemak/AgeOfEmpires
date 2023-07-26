package gui.game.itemactionpanels;

import entities.Item;
import entities.buildings.concretes.MainBuilding;
import entities.buildings.concretes.Tower;
import entities.buildings.concretes.University;
import entities.humans.abstracts.Soldier;
import entities.humans.concretes.Worker;
import game.GameManager;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;

public class ItemActionsPanel extends JPanel {
    MainBuildingActionsPanel mainBuildingActionsPanel = new MainBuildingActionsPanel();
    UniversityActionsPanel universityActionsPanel = new UniversityActionsPanel();
    TowerActionsPanel towerActionsPanel = new TowerActionsPanel();
    WorkerActionsPanel workerActionsPanel = new WorkerActionsPanel();
    SoldierActionsPanel soldierActionsPanel = new SoldierActionsPanel();
    JPanel emptyPanel = new JPanel();
    private CardLayout cardLayout;

    public ItemActionsPanel() {
        setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        add(emptyPanel, emptyPanel.getClass().getSimpleName());
        add(mainBuildingActionsPanel, mainBuildingActionsPanel.getClass().getSimpleName());
        add(universityActionsPanel, universityActionsPanel.getClass().getSimpleName());
        add(towerActionsPanel, towerActionsPanel.getClass().getSimpleName());
        add(workerActionsPanel, workerActionsPanel.getClass().getSimpleName());
        add(soldierActionsPanel, soldierActionsPanel.getClass().getSimpleName());
    }

    public void openPanel(Item item) {
        if (item instanceof MainBuilding) {
            cardLayout.show(this, mainBuildingActionsPanel.getClass().getSimpleName());
            mainBuildingActionsPanel.onPanelVisible(item);
        } else if (item instanceof University) {
            cardLayout.show(this, universityActionsPanel.getClass().getSimpleName());
            universityActionsPanel.onPanelVisible(item);
        } else if (item instanceof Tower) {
            cardLayout.show(this, towerActionsPanel.getClass().getSimpleName());
            towerActionsPanel.onPanelVisible(item);
        } else if (item instanceof Worker) {
            cardLayout.show(this, workerActionsPanel.getClass().getSimpleName());
            workerActionsPanel.onPanelVisible(item);
        } else if (item instanceof Soldier) {
            cardLayout.show(this, soldierActionsPanel.getClass().getSimpleName());
            soldierActionsPanel.onPanelVisible(item);
        }
    }

    public void closePanels() {
        cardLayout.show(this, emptyPanel.getClass().getSimpleName());
    }

}
