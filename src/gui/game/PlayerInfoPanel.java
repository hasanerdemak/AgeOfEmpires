package gui.game;

import game.Player;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerInfoPanel extends JPanel {
    Color originalColor;
    Color highlightedColor;
    private Color currentColor;
    private Player player;
    private JLabel playerIDLabel;
    private JLabel populationLabel;
    private JLabel woodLabel;
    private JLabel goldLabel;
    private JLabel stoneLabel;

    private Timer colorTransitionTimer;
    private int transitionDuration = 1000; // Duration of each color transition in milliseconds
    private long transitionStartTime;
    private boolean isHighlighted = false;
    private boolean isTransitionReversed = false;


    public PlayerInfoPanel(Player player, Color playerColor) {
        this.player = player;
        originalColor = new Color(214, 217, 223);
        highlightedColor = playerColor;
        currentColor = originalColor;

        setBorder(new SoftBevelBorder(BevelBorder.LOWERED, playerColor, playerColor, playerColor, playerColor));

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

        colorTransitionTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBackgroundColor();
            }
        });
        colorTransitionTimer.setRepeats(true);
        colorTransitionTimer.setCoalesce(true);
    }

    public Player getPlayer() {
        return player;
    }

    // Helper method to create a panel with multiple labels in a row
    private JPanel createLabelRow(JLabel[] labels) {
        JPanel rowPanel = new JPanel(new GridLayout(1, labels.length));
        for (JLabel label : labels) {
            rowPanel.add(label);
        }
        return rowPanel;
    }

    public void refreshLabels() {
        populationLabel.setText("Population: " + (player.getWorkerCount() + player.getSoldierCount()) + "/" + player.populationLimit);
        woodLabel.setText("Wood: " + player.getWood() + "  ");
        goldLabel.setText("Gold: " + player.getGold() + "  ");
        stoneLabel.setText("Stone: " + player.getStone() + "  ");
    }

    public void highlightPanel() {
        if (!isHighlighted) {
            isHighlighted = true;
            colorTransitionTimer.start();
            transitionStartTime = System.currentTimeMillis();
        }
    }

    public void resetPanelHighlighting() {
        if (isHighlighted) {
            isHighlighted = false;
            colorTransitionTimer.stop();
            setBorderAndBackgroundColor(originalColor);
            isTransitionReversed = false;
        }
    }

    private void updateBackgroundColor() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - transitionStartTime;
        float progress = (float) elapsedTime / transitionDuration;

        if (progress >= 1.0f) {
            // Transition is complete, switch colors
            if (currentColor.equals(originalColor)) {
                currentColor = highlightedColor;
            } else {
                currentColor = originalColor;
            }
            setBorderAndBackgroundColor(currentColor);
            transitionStartTime = currentTime;
            isTransitionReversed = !isTransitionReversed;
        } else {
            if (!isTransitionReversed){
                Color newColor = interpolateColor(originalColor, highlightedColor, progress);
                setBorderAndBackgroundColor(newColor);
            }
            else {
                Color newColor = interpolateColor(highlightedColor, originalColor, progress);
                setBorderAndBackgroundColor(newColor);
            }

        }
    }

    private void setBorderAndBackgroundColor(Color color) {
        setBackground(color);
        for (var jComponent : getComponents()) {
            jComponent.setBackground(color);
        }
    }

    private Color interpolateColor(Color startColor, Color endColor, float progress) {
        int r = (int) (startColor.getRed() + progress * (endColor.getRed() - startColor.getRed()));
        int g = (int) (startColor.getGreen() + progress * (endColor.getGreen() - startColor.getGreen()));
        int b = (int) (startColor.getBlue() + progress * (endColor.getBlue() - startColor.getBlue()));
        return new Color(r, g, b);
    }
}
