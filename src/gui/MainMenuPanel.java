package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainMenuPanel extends JPanel {
    private Image backgroundImage;
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton exitButton;

    public MainMenuPanel() {
        setLayout(new GridBagLayout());

        // Load the background image from a file (adjust the path according to your image's location)
        try {
            backgroundImage = ImageIO.read(new File("background2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        newGameButton = new JButton("New Game");
        loadGameButton = new JButton("Load Game");
        exitButton = new JButton("Exit");

        // Customize button appearance
        Font buttonFont = new Font("Arial", Font.BOLD, 30);
        newGameButton.setFont(buttonFont);
        loadGameButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);

        // Set button foreground (text) color and background color
        Color buttonTextColor = Color.WHITE;
        Color buttonBackgroundColor = new Color(45, 45, 100); // Adjust the RGB values for the desired color
        newGameButton.setForeground(buttonTextColor);
        newGameButton.setBackground(buttonBackgroundColor);
        loadGameButton.setForeground(buttonTextColor);
        loadGameButton.setBackground(buttonBackgroundColor);
        exitButton.setForeground(buttonTextColor);
        exitButton.setBackground(buttonBackgroundColor);

        // Create constraints for button positioning
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 20, 5, 20); // Adjust the insets as needed for spacing

        // Add buttons to the panel
        constraints.gridy = 0;
        add(newGameButton, constraints);
        constraints.gridy = 1;
        add(loadGameButton, constraints);
        constraints.gridy = 2;
        add(exitButton, constraints);

        // Add ActionListeners to the buttons
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add code here to handle the "New Game" button action
                // For example, you can start a new game or open a new game setup window.
            }
        });

        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add code here to handle the "Load Game" button action
                // For example, you can open a dialog to select a saved game file to load.
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add code here to handle the "Exit" button action
                // For example, you can close the application or display a confirmation dialog.
                int response = JOptionPane.showConfirmDialog(MainMenuPanel.this,
                        "Are you sure you want to exit the game?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0); // Terminate the application when "Yes" is selected.
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}


