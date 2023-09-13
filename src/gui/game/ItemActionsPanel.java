package gui.game;

import entities.Item;
import entities.buildings.concretes.MainBuilding;
import entities.buildings.concretes.Tower;
import entities.buildings.concretes.University;
import entities.humans.abstracts.Human;
import entities.humans.abstracts.Soldier;
import entities.humans.concretes.Worker;
import exceptions.AgeOfEmpiresException;
import game.GameManager;
import gui.game.selectiondialogs.BuildSelectionDialog;
import gui.game.selectiondialogs.PurchaseSelectionDialog;
import interfaces.AttackableInterface;
import utils.MoveControlUtils;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class ItemActionsPanel extends JPanel {
    JButton purchaseButton = new JButton("purchase");
    JButton trainInfantryButton = new JButton("Train Infantry");
    JButton trainCavalryButton = new JButton("Train Cavalry");
    JButton trainCatapultButton = new JButton("Train Catapult");
    JButton moveButton = new JButton("Move");
    JButton attackButton = new JButton("Attack");
    JButton buildButton = new JButton("Build");
    HashMap<Class<?>, ArrayList<JButton>> itemButtons = new HashMap<>();
    private JTextArea infoTextArea = new JTextArea();
    private GridBagConstraints constraints = new GridBagConstraints();
    private boolean isButtonsEnabled;
    private Item item;
    private ArrayList<JButton> buttons = new ArrayList<>();
    private ArrayList<JButton> visibleButtons = new ArrayList<>();

    public ItemActionsPanel() {
        setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0}; // component + 1
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE}; // component + 1

        constraints.anchor = GridBagConstraints.NORTH;
        //infoTextArea.setLineWrap(true);
        infoTextArea.setWrapStyleWord(true);
        infoTextArea.setEditable(false);
        infoTextArea.setBackground(new Color(195, 255, 247, 255));

        TitledBorder title;
        title = BorderFactory.createTitledBorder("Info");
        infoTextArea.setBorder(title);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 0, 5, 0);

        constraints.gridy = 0;
        add(infoTextArea, constraints);

        buttons.add(purchaseButton);
        buttons.add(trainInfantryButton);
        buttons.add(trainCavalryButton);
        buttons.add(trainCatapultButton);
        buttons.add(moveButton);
        buttons.add(attackButton);
        buttons.add(buildButton);
        for (var button : buttons) {
            constraints.gridy++;
            add(button, constraints);
        }


        var layout = (GridBagLayout) getLayout();
        layout.rowHeights = new int[getComponentCount() + 1];
        layout.rowWeights = new double[getComponentCount() + 1];
        layout.rowWeights[getComponentCount()] = Double.MIN_VALUE;

        initializeItemButtonsHashMap();
        initializeButtonsActionListeners();

        onPanelVisible();
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
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    private void initializeItemButtonsHashMap() {
        ArrayList<JButton> buttonsForMainBuilding = new ArrayList<>();
        buttonsForMainBuilding.add(purchaseButton);
        itemButtons.put(MainBuilding.class, buttonsForMainBuilding);

        ArrayList<JButton> buttonsForUniversity = new ArrayList<>();
        buttonsForUniversity.add(trainInfantryButton);
        buttonsForUniversity.add(trainCavalryButton);
        buttonsForUniversity.add(trainCatapultButton);
        itemButtons.put(University.class, buttonsForUniversity);

        ArrayList<JButton> buttonsForTower = new ArrayList<>();
        buttonsForTower.add(attackButton);
        itemButtons.put(Tower.class, buttonsForTower);

        ArrayList<JButton> buttonsForWorker = new ArrayList<>();
        buttonsForWorker.add(moveButton);
        buttonsForWorker.add(attackButton);
        buttonsForWorker.add(buildButton);
        itemButtons.put(Worker.class, buttonsForWorker);

        ArrayList<JButton> buttonsForSoldier = new ArrayList<>();
        buttonsForSoldier.add(moveButton);
        buttonsForSoldier.add(attackButton);
        itemButtons.put(Soldier.class, buttonsForSoldier);
    }

    private void initializeButtonsActionListeners() {
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var dialog = new PurchaseSelectionDialog((Frame) SwingUtilities.getWindowAncestor(ItemActionsPanel.this));
                dialog.setVisible(true);

                String selectedItemName = dialog.getSelectedItemName();

                try {
                    var gameManager = GameManager.getInstance();
                    gameManager.purchase(gameManager.getGame().getCurrentPlayer(), selectedItemName);
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

        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Human human = (Human) getItem();
                human.setCurrentState(Item.State.MOVE);

                var mapPanel = GameManager.getInstance().getMainFrame().getGamePanel().getMapPanel();
                Graphics g = mapPanel.getGraphics();
                int humanX = human.getX();
                int humanY = human.getY();
                int speed = human.getMovementSpeed();
                for (int i = humanX - speed; i <= humanX + speed; i++) {
                    for (int j = humanY - speed; j <= humanY + speed; j++) {

                        if (i == humanX && j == humanY) continue;

                        try {
                            MoveControlUtils.checkMoveDistance(human, i, j);
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
                AttackableInterface attackableItem = (AttackableInterface) getItem();
                attackableItem.setCurrentState(Item.State.ATTACK);

                var mapPanel = GameManager.getInstance().getMainFrame().getGamePanel().getMapPanel();
                Graphics g = mapPanel.getGraphics();
                int attackableItemX = attackableItem.getX();
                int attackableItemY = attackableItem.getY();
                // todo upperLimit doğru mu kontrol et (doğru bloklar boyanıyor mu)
                int upperLimit = (int) attackableItem.getUpperAttackDistanceLimit();
                for (int i = attackableItemX - upperLimit; i <= attackableItemX + upperLimit; i++) {
                    for (int j = attackableItemY - upperLimit; j <= attackableItemY + upperLimit; j++) {

                        if (i == attackableItemX && j == attackableItemY) continue;

                        try {
                            MoveControlUtils.checkAttackDistance(attackableItem, i, j);
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

                var dialog = new BuildSelectionDialog((Frame) SwingUtilities.getWindowAncestor(ItemActionsPanel.this));
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

    public void onPanelVisible() {
        setVisibleButtons();

        var item = getItem();
        if (item == null) {
            infoTextArea.setVisible(false);
            return;
        }

        boolean isButtonsEnabled = GameManager.getInstance().getGame().getCurrentPlayer().equals(item.getOwnerPlayer());
        setButtonsEnabled(isButtonsEnabled);

        String info = item +
                "\n" +
                "Symbol: " + item.getSymbol() +
                "\n" +
                "x: " + item.getX() +
                "\n" +
                "y: " + item.getY() +
                "\n" +
                "life points: " + item.getLifePoints();

        if (item.getClass().equals(University.class)) {
            var university = (University) item;
            info += "\n" +
                    "Infantry Training Count: " + university.getInfantryTrainingCount() +
                    "\n" +
                    "Cavalry Training Count: " + university.getCavalryTrainingCount() +
                    "\n" +
                    "Catapult Training Count: " + university.getCatapultTrainingCount();
        }

        infoTextArea.setText(info);
        infoTextArea.setVisible(true);
    }

    private void setVisibleButtons() {
        // remove buttons
        for (var button : buttons) {
            button.setVisible(false);
            remove(button);
        }

        // set visible buttons
        visibleButtons.clear();
        if (getItem() != null) {
            if (getItem() instanceof Soldier) {
                visibleButtons.addAll(itemButtons.get(Soldier.class));
            } else {
                visibleButtons.addAll(itemButtons.get(getItem().getClass()));
            }

            // add visible buttons
            constraints.gridy = 1;
            for (var button : visibleButtons) {
                button.setVisible(true);
                add(button, constraints);
                constraints.gridy++;
            }
        }

        revalidate();
        repaint();
    }

}
