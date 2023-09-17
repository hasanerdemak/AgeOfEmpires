package gui.game;

import exceptions.AgeOfEmpiresException;
import game.Game;
import game.GameManager;
import utils.GameColors;

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
        Game game = new Game(playerCount);
        GameManager.getInstance().setGame(game);
        Color[] playerColors = {GameColors.PLAYER1_COLOR, GameColors.PLAYER2_COLOR, GameColors.PLAYER3_COLOR, GameColors.PLAYER4_COLOR};
        for (int i = 0; i < playerCount; i++) {
            playerInfoPanels[i] = new PlayerInfoPanel(game.getPlayer(i), playerColors[i]);
            playerInfoPanels[i].setPreferredSize(new Dimension(800 / playerCount, 100));
        }

        setLayout(new BorderLayout());

        mapPanel.setPreferredSize(new Dimension(1000, 500));
        itemActionsPanel.setPreferredSize(new Dimension(200, 600));

        JScrollPane scrollPane = new JScrollPane(mapPanel);

        add(scrollPane, BorderLayout.CENTER);
        //add(mapPanel, BorderLayout.CENTER);
        add(itemActionsPanel, BorderLayout.EAST);
        add(createPanelRow(playerInfoPanels), BorderLayout.SOUTH);

        refreshGameStatus();
    }

    public MapPanel getMapPanel() {
        return mapPanel;
    }

    public ItemActionsPanel getItemActionsPanel() {
        return itemActionsPanel;
    }

    private JPanel createPanelRow(JPanel[] panels) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (JPanel panel : panels) {
            rowPanel.add(panel);
        }
        return rowPanel;
    }

    public void refreshGameStatus() {
        for (var playerInfoPanel : playerInfoPanels) {
            playerInfoPanel.refreshLabels();

            try {
                if (GameManager.getInstance().getGame().checkPlayerTurn(playerInfoPanel.getPlayer())) {
                    playerInfoPanel.highlightPanel();
                } else {
                    playerInfoPanel.resetPanelHighlighting();
                }
            } catch (AgeOfEmpiresException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
