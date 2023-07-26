package gui.game;

import game.Player;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;

public class PlayerInfoPanel extends JPanel {
    private Player player;
    private JLabel playerIDLabel;
    private JLabel populationLabel;
    private JLabel woodLabel;
    private JLabel goldLabel;
    private JLabel stoneLabel;

    public PlayerInfoPanel(Player player) {
        setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));

        this.player = player;
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;

        playerIDLabel = new JLabel("Player ID: " + player.getPlayerID());
        populationLabel = new JLabel("Population: " + (player.getWorkerCount() + player.getSoldierCount()) + "/" + player.populationLimit);
        woodLabel = new JLabel("Wood: " + player.getWood() + "  ");
        goldLabel = new JLabel("Gold: " + player.getGold() + "  ");
        stoneLabel = new JLabel("Stone: " + player.getStone() + "  ");

        // Add components to the panel with proper constraints
        add(playerIDLabel, constraints);

        constraints.gridy = 1;
        add(populationLabel, constraints);

        constraints.gridy = 2;
        JPanel rowPanel = createLabelRow(new JLabel[]{woodLabel, goldLabel, stoneLabel});
        add(rowPanel, constraints);
    }

    // Helper method to create a panel with multiple labels in a row
    private JPanel createLabelRow(JLabel[] labels) {
        JPanel rowPanel = new JPanel(new GridLayout(1, labels.length));
        for (JLabel label : labels) {
            rowPanel.add(label);
        }
        return rowPanel;
    }
}
