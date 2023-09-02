package gui.game.itemactionpanels;

import entities.Item;
import entities.buildings.concretes.MainBuilding;
import entities.buildings.concretes.Tower;
import entities.buildings.concretes.University;
import entities.humans.abstracts.Soldier;
import entities.humans.concretes.Worker;
import game.GameManager;
import gui.game.itemactionpanels.abstracts.AbstractItemActionsPanel;

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
    AbstractItemActionsPanel emptyPanel = new AbstractItemActionsPanel() {
        @Override
        public void checkButtonEnableState() {

        }
    };
    private AbstractItemActionsPanel activePanel;
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

        emptyPanel.remove(emptyPanel.getInfoTextArea());
        activePanel = emptyPanel;
    }

    public AbstractItemActionsPanel getActivePanel() {
        return activePanel;
    }

    public void setActivePanel(AbstractItemActionsPanel activePanel) {
        this.activePanel = activePanel;
    }

    public void openPanel(Item item) {
        if (item instanceof MainBuilding) {
            cardLayout.show(this, mainBuildingActionsPanel.getClass().getSimpleName());
            mainBuildingActionsPanel.onPanelVisible(item);
            activePanel = mainBuildingActionsPanel;
        } else if (item instanceof University) {
            cardLayout.show(this, universityActionsPanel.getClass().getSimpleName());
            universityActionsPanel.onPanelVisible(item);
            activePanel = universityActionsPanel;
        } else if (item instanceof Tower) {
            cardLayout.show(this, towerActionsPanel.getClass().getSimpleName());
            towerActionsPanel.onPanelVisible(item);
            activePanel = towerActionsPanel;
        } else if (item instanceof Worker) {
            cardLayout.show(this, workerActionsPanel.getClass().getSimpleName());
            workerActionsPanel.onPanelVisible(item);
            activePanel = workerActionsPanel;
        } else if (item instanceof Soldier) {
            cardLayout.show(this, soldierActionsPanel.getClass().getSimpleName());
            soldierActionsPanel.onPanelVisible(item);
            activePanel = soldierActionsPanel;
        }

        activePanel.setItem(item);
    }

    public void closePanels() {
        cardLayout.show(this, emptyPanel.getClass().getSimpleName());
        activePanel = emptyPanel;
    }

}
