package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerSetupPanel extends JPanel {
    private JLabel playerLabel;
    private JTextField playerNameField;
    private JLabel resourcesLabel;
    private JTextField woodField;
    private JTextField goldField;
    private JTextField stoneField;
    private JButton startGameButton;

    public PlayerSetupPanel() {
        setLayout(new GridLayout(3, 2));

        playerLabel = new JLabel("Player Name:");
        playerNameField = new JTextField();

        resourcesLabel = new JLabel("Starting Resources:");
        woodField = new JTextField();
        goldField = new JTextField();
        stoneField = new JTextField();

        startGameButton = new JButton("Start Game");

        // Add components to the panel
        add(playerLabel);
        add(playerNameField);
        add(resourcesLabel);
        add(woodField);
        add(goldField);
        add(stoneField);
        add(startGameButton);

        // Add ActionListener to the startGameButton
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = playerNameField.getText();
                int startingWood = Integer.parseInt(woodField.getText());
                int startingGold = Integer.parseInt(goldField.getText());
                int startingStone = Integer.parseInt(stoneField.getText());

                // Validate the input values and pass them to the game controller to start the game.
                if (playerName.isEmpty() || startingWood < 0 || startingGold < 0 || startingStone < 0) {
                    JOptionPane.showMessageDialog(PlayerSetupPanel.this, "Invalid input! Please enter valid values.");
                } else {
                    // Pass the player setup information to the game controller to start the game.
                    //GameController.startGame(playerName, startingWood, startingGold, startingStone);
                }
            }
        });
    }
}

