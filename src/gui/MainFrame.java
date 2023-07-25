package gui;

import exceptions.AgeOfEmpiresException;

import javax.swing.*;

public class MainFrame extends JFrame {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private MainMenuPanel mainMenuPanel;
    private GamePanel gamePanel;

    public MainFrame() throws AgeOfEmpiresException {
        setTitle("Age of Empires - Turn-Based Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainMenuPanel = new MainMenuPanel();
        gamePanel = new GamePanel(2);

        //setLayout(new BorderLayout());
        //add(mainMenuPanel);

        add(gamePanel);

        //add(mapPanel, BorderLayout.CENTER);
        //add(playerSetupPanel, BorderLayout.SOUTH);

    }

}

