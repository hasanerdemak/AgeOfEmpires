package gui.game;

import entities.Item;
import entities.buildings.concretes.MainBuilding;
import entities.buildings.concretes.Tower;
import entities.buildings.concretes.University;
import entities.humans.abstracts.Human;
import entities.humans.abstracts.Soldier;
import entities.humans.concretes.Cavalry;
import entities.humans.concretes.Worker;
import exceptions.AgeOfEmpiresException;
import game.GameManager;
import gui.game.selectiondialogs.BuildSelectionDialog;
import gui.game.selectiondialogs.PurchaseSelectionDialog;
import interfaces.AttackableInterface;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ItemActionsPanel extends JPanel {

    JButton purchaseButton = new JButton("purchase");
    JButton trainInfantryButton = new JButton("Train Infantry");
    JButton trainCavalryButton = new JButton("Train Cavalry");
    JButton trainCatapultButton = new JButton("Train Catapult");
    JButton moveButton = new JButton("Move");
    JButton attackButton = new JButton("Attack");
    JButton buildButton = new JButton("Build");
    JButton cancelButton = new JButton("Cancel");
    JButton passTheTourButton = new JButton("Pass The Tour");
    private JTextArea infoTextArea = new JTextArea();
    private GridBagConstraints constraints = new GridBagConstraints();
    private HashMap<Class<?>, ArrayList<JButton>> itemButtons = new HashMap<>();
    private Item item;
    private ArrayList<JButton> buttons = new ArrayList<>();
    private ArrayList<JButton> visibleButtons = new ArrayList<>();
    private boolean isButtonsEnabled;

    public ItemActionsPanel() {
        initializeLayout();
        initializeButtons();
        initializeItemButtonsHashMap();
        initializeButtonsActionListeners();
        onPanelVisible();
    }

    private void initializeLayout() {
        setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowHeights = new int[12];//new int[getComponentCount() + 1];
        gridBagLayout.rowWeights = new double[12];//new double[getComponentCount() + 1];
        gridBagLayout.rowWeights[10] = Double.MIN_VALUE;

        constraints.anchor = GridBagConstraints.NORTH;
        infoTextArea.setWrapStyleWord(true);
        infoTextArea.setEditable(false);
        infoTextArea.setBackground(new Color(195, 255, 247, 255));

        TitledBorder title = BorderFactory.createTitledBorder("Info");
        infoTextArea.setBorder(title);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);

        constraints.gridy = 0;
        add(infoTextArea, constraints);

        constraints.gridy = 11;
        add(passTheTourButton, constraints);
    }

    private void initializeButtons() {
        buttons.add(purchaseButton);
        buttons.add(trainInfantryButton);
        buttons.add(trainCavalryButton);
        buttons.add(trainCatapultButton);
        buttons.add(moveButton);
        buttons.add(attackButton);
        buttons.add(buildButton);
        buttons.add(cancelButton);

        constraints.gridy = 1;
        for (var button : buttons) {
            add(button, constraints);
            button.setVisible(false);
            constraints.gridy++;
        }
    }

    private void initializeItemButtonsHashMap() {
        itemButtons.put(MainBuilding.class, new ArrayList<>(Arrays.asList(purchaseButton)));
        itemButtons.put(University.class, new ArrayList<>(Arrays.asList(trainInfantryButton, trainCavalryButton, trainCatapultButton)));
        itemButtons.put(Tower.class, new ArrayList<>(Arrays.asList(attackButton, cancelButton)));
        itemButtons.put(Worker.class, new ArrayList<>(Arrays.asList(moveButton, attackButton, buildButton, cancelButton)));
        itemButtons.put(Soldier.class, new ArrayList<>(Arrays.asList(moveButton, attackButton, cancelButton)));
    }

    private void initializeButtonsActionListeners() {
        purchaseButton.addActionListener(e -> performPurchaseAction());
        trainInfantryButton.addActionListener(e -> performTrainAction(University.UnitType.INFANTRY));
        trainCavalryButton.addActionListener(e -> performTrainAction(University.UnitType.CAVALRY));
        trainCatapultButton.addActionListener(e -> performTrainAction(University.UnitType.CATAPULT));
        moveButton.addActionListener(e -> performMoveAction());
        attackButton.addActionListener(e -> performAttackAction());
        buildButton.addActionListener(e -> performBuildAction());
        cancelButton.addActionListener(e -> performCancelAction());
        passTheTourButton.addActionListener(e -> performPassTheTourAction());
    }

    private ArrayList<JButton> getCurrentButtons(){
        ArrayList<JButton> currentButtons;
        if (item instanceof Soldier) {
            currentButtons = itemButtons.get(Soldier.class);
        } else {
            currentButtons = itemButtons.get(item.getClass());
        }

        return currentButtons;
    }

    private void performPurchaseAction() {
        var dialog = new PurchaseSelectionDialog((Frame) SwingUtilities.getWindowAncestor(ItemActionsPanel.this));
        dialog.setVisible(true);

        String selectedItemName = dialog.getSelectedItemName();

        try {
            var gameManager = GameManager.getInstance();
            gameManager.purchase(gameManager.getGame().getCurrentPlayer(), selectedItemName);
            gameManager.getMainFrame().getGamePanel().getMapPanel().onTourPassed();
        } catch (AgeOfEmpiresException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performTrainAction(University.UnitType unitType) {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);

        try {
            var gameManager = GameManager.getInstance();
            if (choice == JOptionPane.YES_OPTION) {
                gameManager.train(gameManager.getGame().getCurrentPlayer().getUniversity(), unitType);
            } else {
                return;
            }

            gameManager.getMainFrame().getGamePanel().getMapPanel().onTourPassed();
        } catch (AgeOfEmpiresException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performMoveAction() {
        Human human = (Human) getItem();
        human.setCurrentState(Item.State.MOVE);

        var mapPanel = GameManager.getInstance().getMainFrame().getGamePanel().getMapPanel();
        mapPanel.paintMovableBlocks(human);

        for (var button: getCurrentButtons()) {
            button.setEnabled(false);
        }
        cancelButton.setEnabled(true);

    }

    private void performAttackAction() {
        AttackableInterface attackableItem = (AttackableInterface) getItem();
        attackableItem.setCurrentState(Item.State.ATTACK);

        var mapPanel = GameManager.getInstance().getMainFrame().getGamePanel().getMapPanel();
        mapPanel.paintAttackableBlocks(attackableItem);

        for (var button: getCurrentButtons()) {
            button.setEnabled(false);
        }
        cancelButton.setEnabled(true);

    }

    private void performBuildAction() {
        Worker worker = (Worker) getItem();
        worker.setCurrentState(Item.State.BUILD);

        var dialog = new BuildSelectionDialog((Frame) SwingUtilities.getWindowAncestor(ItemActionsPanel.this));
        dialog.setVisible(true);

        String selectedBuildingName = dialog.getSelectedBuildingName();

        if (selectedBuildingName != null){
            try {
                var gameManager = GameManager.getInstance();
                gameManager.build(worker, selectedBuildingName);
                worker.setCurrentState(Item.State.IDLE);
                gameManager.getMainFrame().getGamePanel().getMapPanel().onTourPassed();
            } catch (AgeOfEmpiresException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void performCancelAction() {
        var item = getItem();
        item.setCurrentState(Item.State.IDLE);

        for (var button: getCurrentButtons()) {
            button.setEnabled(true);
        }
        cancelButton.setEnabled(false);

        GameManager.getInstance().getMainFrame().getGamePanel().getMapPanel().repaint();
    }

    private void performPassTheTourAction() {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure?", "Pass the Tour", JOptionPane.YES_NO_OPTION);

        var gameManager = GameManager.getInstance();
        if (choice == JOptionPane.YES_OPTION) {
            gameManager.getGame().getCurrentPlayer().pass();
        } else {
            return;
        }

        gameManager.getMainFrame().getGamePanel().getMapPanel().onTourPassed();
    }

    public JTextArea getInfoTextArea() {
        return infoTextArea;
    }

    public void setInfoTextArea(JTextArea infoTextArea) {
        this.infoTextArea = infoTextArea;
    }

    public GridBagConstraints getConstraints() {
        return constraints;
    }

    public void setConstraints(GridBagConstraints constraints) {
        this.constraints = constraints;
    }

    public boolean isButtonsEnabled() {
        return isButtonsEnabled;
    }

    public void setButtonsEnabled(boolean enabled) {
        isButtonsEnabled = enabled;

        for (var button : visibleButtons) {
            button.setEnabled(enabled);
        }
        cancelButton.setEnabled(false);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
        onPanelVisible();
    }

    private void onPanelVisible() {
        updateVisibleButtons();

        if (item == null) {
            infoTextArea.setVisible(false);
            return;
        }

        boolean isButtonsEnabled = GameManager.getInstance().getGame().getCurrentPlayer().equals(item.getOwnerPlayer());
        setButtonsEnabled(isButtonsEnabled);

        String info = item.getItemInfo();

        infoTextArea.setText(info);
        infoTextArea.setVisible(true);
    }

    private void updateVisibleButtons() {

        for (var button : buttons) {
            button.setVisible(false);
            remove(button);
        }

        visibleButtons.clear();
        if (item != null) {
            visibleButtons.addAll(getCurrentButtons());

            constraints.gridy = 1;
            for (var button : visibleButtons) {
                add(button, constraints);
                button.setVisible(true);
                constraints.gridy++;
            }
        }

        revalidate();
        repaint();
    }

}
