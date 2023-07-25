package gui;

import exceptions.AgeOfEmpiresException;
import game.Game;
import game.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private MapPanel mapPanel;
    private ItemActionsPanel itemActionsPanel;
    private PlayerInfoPanel[] playerInfoPanels;

    public GamePanel(int playerCount) throws AgeOfEmpiresException {
        mapPanel = new MapPanel();
        itemActionsPanel = new ItemActionsPanel();
        playerInfoPanels = new PlayerInfoPanel[playerCount];
        Game game = new Game(2);
        for (int i = 0; i < playerCount; i++) {
            playerInfoPanels[i] = new PlayerInfoPanel(new Player(i, game, true));
            playerInfoPanels[i].setPreferredSize(new Dimension(800 / playerCount, 100));
        }

        setLayout(new BorderLayout());

        mapPanel.setPreferredSize(new Dimension(1000, 600));
        itemActionsPanel.setPreferredSize(new Dimension(200, 600));

        add(mapPanel, BorderLayout.CENTER);
        add(itemActionsPanel, BorderLayout.EAST);
        add(createPanelRow(playerInfoPanels), BorderLayout.SOUTH);
    }

    private JPanel createPanelRow(JPanel[] panels) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (JPanel panel : panels) {
            rowPanel.add(panel);
        }
        return rowPanel;
    }
}
