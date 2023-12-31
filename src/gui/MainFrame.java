package gui;

import exceptions.AgeOfEmpiresException;
import gui.game.GamePanel;
import gui.mainmenu.MainMenuPanel;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class MainFrame extends JFrame {
    public static final int WIDTH = 1445;
    public static final int HEIGHT = 783;
    private MainMenuPanel mainMenuPanel;
    private GamePanel gamePanel;

    public MainFrame() throws AgeOfEmpiresException {
        setTitle("Age of Empires - Turn-Based Game");
        setSize(WIDTH, HEIGHT);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        mainMenuPanel = new MainMenuPanel();
        gamePanel = new GamePanel(4);

        //setLayout(new BorderLayout());
        //add(mainMenuPanel);

        add(gamePanel);

        //add(mapPanel, BorderLayout.CENTER);
        //add(playerSetupPanel, BorderLayout.SOUTH);

    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
}

