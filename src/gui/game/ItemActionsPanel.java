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
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        gridBagLayout.rowHeights = new int[11];//new int[getComponentCount() + 1];
        gridBagLayout.rowWeights = new double[11];//new double[getComponentCount() + 1];
        gridBagLayout.rowWeights[9] = Double.MIN_VALUE;

        constraints.anchor = GridBagConstraints.NORTH;
        infoTextArea.setWrapStyleWord(true);
        infoTextArea.setEditable(false);
        infoTextArea.setBackground(new Color(195, 255, 247, 255));

        TitledBorder title = BorderFactory.createTitledBorder("Info");
        infoTextArea.setBorder(title);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 0, 5, 0);

        constraints.gridy = 0;
        add(infoTextArea, constraints);

        constraints.gridy = 10;
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
        itemButtons.put(Tower.class, new ArrayList<>(Arrays.asList(attackButton)));
        itemButtons.put(Worker.class, new ArrayList<>(Arrays.asList(moveButton, attackButton, buildButton)));
        itemButtons.put(Soldier.class, new ArrayList<>(Arrays.asList(moveButton, attackButton)));
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
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        trainInfantryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);

                try {
                    var gameManager = GameManager.getInstance();
                    if (choice == JOptionPane.YES_OPTION) {
                        gameManager.train(gameManager.getGame().getCurrentPlayer().getUniversity(), University.UnitType.INFANTRY);
                    } else {
                        return;
                    }

                    gameManager.getMainFrame().getGamePanel().getMapPanel().onTourPassed();
                } catch (AgeOfEmpiresException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        trainCavalryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);

                try {
                    var gameManager = GameManager.getInstance();
                    if (choice == JOptionPane.YES_OPTION) {
                        gameManager.train(gameManager.getGame().getCurrentPlayer().getUniversity(), University.UnitType.CAVALRY);
                    } else {
                        return;
                    }

                    gameManager.getMainFrame().getGamePanel().getMapPanel().onTourPassed();
                } catch (AgeOfEmpiresException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        trainCatapultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);

                try {
                    var gameManager = GameManager.getInstance();
                    if (choice == JOptionPane.YES_OPTION) {
                        gameManager.train(gameManager.getGame().getCurrentPlayer().getUniversity(), University.UnitType.CATAPULT);
                    } else {
                        return;
                    }

                    gameManager.getMainFrame().getGamePanel().getMapPanel().onTourPassed();
                } catch (AgeOfEmpiresException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        passTheTourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure?", "Pass the Tour", JOptionPane.YES_NO_OPTION);

                var gameManager = GameManager.getInstance();
                if (choice == JOptionPane.YES_OPTION) {
                    gameManager.getGame().getCurrentPlayer().pass();
                } else {
                    return;
                }

                gameManager.getMainFrame().getGamePanel().getMapPanel().onTourPassed();
            }
        });

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

        String info = item.toString() + "\n" + "Symbol: " + item.getSymbol() + "\n" + "x: " + item.getX() + "\n" + "y: " + item.getY() + "\n" + "life points: " + item.getLifePoints();

        if (item instanceof University university) {
            info += "\n" + "Infantry Training Count: " + university.getInfantryTrainingCount() + "\n" + "Cavalry Training Count: " + university.getCavalryTrainingCount() + "\n" + "Catapult Training Count: " + university.getCatapultTrainingCount();
        }

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
            if (item instanceof Soldier) {
                visibleButtons.addAll(itemButtons.get(Soldier.class));
            } else {
                visibleButtons.addAll(itemButtons.get(item.getClass()));
            }

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
