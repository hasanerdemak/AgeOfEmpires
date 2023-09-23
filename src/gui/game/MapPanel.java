package gui.game;

import entities.Item;
import entities.buildings.concretes.MainBuilding;
import entities.buildings.concretes.Tower;
import entities.buildings.concretes.University;
import entities.humans.abstracts.Human;
import entities.humans.concretes.*;
import exceptions.AgeOfEmpiresException;
import game.GameManager;
import gui.game.selectiondialogs.ItemSelectionDialog;
import interfaces.AttackableInterface;
import utils.GameColors;
import utils.MoveControlUtils;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MapPanel extends JPanel {
    private static final int MAP_ROWS = 50;
    private static final int MAP_COLS = 100;
    private static final int BLOCK_SIZE = 13; // Initial size of the blocks
    private final JPopupMenu tooltipPopup = new JPopupMenu();
    JScrollPane parentScrollPane;
    private int blockSize = BLOCK_SIZE;
    private int xOffset = 0;
    private int yOffset = 0;
    private Item selectedItem;
    private int lastClickedCol;
    private int lastClickedRow;

    public MapPanel() {
        setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));

        initializeMouseListeners();
    }

    private void initializeMouseListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                handleMouseMove(e);
            }
        });

        /*
        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                // Check if the CTRL key is pressed
                if (e.isControlDown()) {
                    parentScrollPane = (JScrollPane) getParent().getParent();

                    int x = e.getX();
                    int y = e.getY();

                    // Adjust the blockSize based on the mouse wheel rotation
                    int rotation = e.getWheelRotation();
                    if (rotation < 0) {
                        // Zoom in (enlarge the blocks)
                        if (blockSize < 100) {
                            blockSize += 5;
                        }
                    } else {
                        // Zoom out (reduce the blocks size)
                        if (blockSize > 10) {
                            blockSize -= 5;
                        }
                    }

                    parentScrollPane.getHorizontalScrollBar().setValue(x);
                    parentScrollPane.getVerticalScrollBar().setValue(y);

                    // Repaint the panel to update the view
                    revalidate(); // Ensures the scrollbars are updated if needed
                    repaint();
                }
            }
        });

         */

    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public int getXOffset() {
        return xOffset;
    }

    public void setXOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    public void setYOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Override
    public Dimension getPreferredSize() {
        // Calculate and return the preferred size based on the map size and blockSize
        // Replace 'mapWidth' and 'mapHeight' with the actual size of your map
        int preferredWidth = blockSize * (MAP_COLS + 2) + 2 * xOffset;
        int preferredHeight = blockSize * (MAP_ROWS + 2) + 2 * yOffset;
        return new Dimension(preferredWidth, preferredHeight);
    }

    private void handleMouseClick(MouseEvent e) {
        int x = e.getX() - getXOffset();
        int y = e.getY() - getYOffset();

        int col = x / getBlockSize();
        int row = y / getBlockSize();
        lastClickedCol = col;
        lastClickedRow = row;
        System.out.println("Tıklanan blok: (" + col + ", " + row + ")");

        GamePanel gamePanel = GameManager.getInstance().getMainFrame().getGamePanel();

        if (selectedItem != null && selectedItem.getCurrentState() != Item.State.IDLE) {
            handleSelectedItemAction(col, row);
        } else {
            handleNoSelectedItemAction(col, row, gamePanel);
        }

        gamePanel.getItemActionsPanel().setItem(selectedItem);
        repaint();
    }

    private void handleMouseMove(MouseEvent e) {
        int x = e.getX() - getXOffset();
        int y = e.getY() - getYOffset();

        int col = x / getBlockSize();
        int row = y / getBlockSize();

        // Check if there is an item at the current block
        ArrayList<Item> items = GameManager.getInstance().getGame().getMap().getAllItemsAtCoordinates(col, row);

        if (items.size() != 0) {
            // Show the tooltip popup with item information
            showTooltip(items, x + getBlockSize() / 2, y + getBlockSize() / 2);
            //showTooltip(item, (int) ((col+0.75) * getBlockSize()), (int) ((row+0.75) * getBlockSize()));
        } else {
            tooltipPopup.setVisible(false);
        }
    }

    private void handleSelectedItemAction(int col, int row) {
        switch (selectedItem.getCurrentState()) {
            case ATTACK -> handleAttackAction(col, row);
            case MOVE -> handleMoveAction(col, row);
        }
    }

    private void handleAttackAction(int col, int row) {
        try {
            AttackableInterface attackableItem = (AttackableInterface) selectedItem;
            GameManager.getInstance().attack(attackableItem, col, row);
            onTourPassed();
        } catch (AgeOfEmpiresException ex) {
            showErrorDialog(ex.getMessage());
        }
    }

    private void handleMoveAction(int col, int row) {
        try {
            Human human = (Human) selectedItem;
            GameManager.getInstance().move(human, col, row);
            onTourPassed();
        } catch (AgeOfEmpiresException ex) {
            showErrorDialog(ex.getMessage());
        }
    }

    private void handleNoSelectedItemAction(int col, int row, GamePanel gamePanel) {
        var items = GameManager.getInstance().getGame().getMap().getAllItemsAtCoordinates(col, row);

        if (items.size() > 1) {
            handleMultipleItemsSelection(col, row, gamePanel, items);
        } else if (items.size() == 1) {
            selectedItem = items.get(0);
        } else {
            selectedItem = null;
        }
    }

    private void handleMultipleItemsSelection(int col, int row, GamePanel gamePanel, ArrayList<Item> items) {
        highlightBlock(getGraphics(), col, row);
        ItemSelectionDialog dialog = new ItemSelectionDialog((Frame) SwingUtilities.getWindowAncestor(gamePanel), items);
        dialog.setVisible(true);

        selectedItem = dialog.getSelectedItem();
        if (selectedItem != null) {
            gamePanel.getItemActionsPanel().setItem(selectedItem);
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showTooltip(ArrayList<Item> items, int x, int y) {
        tooltipPopup.removeAll();
        for (var item : items) {
            tooltipPopup.add(createTooltipTextArea(item), BorderLayout.CENTER);
        }
        if (!tooltipPopup.isShowing()) {
            tooltipPopup.show(this, x, y);
        }
    }

    // Create a text area for tooltip content
    private JTextArea createTooltipTextArea(Item item) {
        JTextArea tooltipText = new JTextArea(item.getItemInfo());

        tooltipText.setWrapStyleWord(true);
        tooltipText.setEditable(false);

        return tooltipText;
    }

    public void onTourPassed() {
        var gamePanel = GameManager.getInstance().getMainFrame().getGamePanel();
        gamePanel.getItemActionsPanel().setItem(null);
        gamePanel.refreshGameStatus();
        if (selectedItem != null) {
            selectedItem.setCurrentState(Item.State.IDLE);
            selectedItem = null;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Color cellColor;
        for (int row = 1; row <= MAP_ROWS; row++) {
            for (int col = 1; col <= MAP_COLS; col++) {
                // Determine the color of the cell based on its contents.
                ArrayList<Item> items = GameManager.getInstance().getGame().getMap().getAllItemsAtCoordinates(col, row); //mapItems[row][col];
                if (items.size() != 0) {
                    if (items.contains(selectedItem)) {
                        cellColor = GameColors.OCCUPIED_HIGHLIGHTED_TILE_COLOR;
                        paintBlockContent(g, cellColor, selectedItem, col, row);
                    } else {
                        var item = items.get(0);
                        switch (item.getOwnerPlayer().getPlayerID()) {
                            case 0 -> cellColor = GameColors.PLAYER1_COLOR;
                            case 1 -> cellColor = GameColors.PLAYER2_COLOR;
                            case 2 -> cellColor = GameColors.PLAYER3_COLOR;
                            case 3 -> cellColor = GameColors.PLAYER4_COLOR;
                            default -> cellColor = GameColors.OCCUPIED_TILE_COLOR;
                        }

                        paintBlockContent(g, cellColor, items.get(0), col, row);
                    }
                } else {
                    cellColor = GameColors.EMPTY_TILE_COLOR;
                    paintBlockContent(g, cellColor, null, col, row);
                }
            }
        }

        // todo There is an error. Highlighted blocks are resetted after these function calls
        if (selectedItem != null) {
            if (selectedItem.getCurrentState() == Item.State.MOVE) {
                paintMovableBlocks((Human) selectedItem);
            } else if (selectedItem.getCurrentState() == Item.State.ATTACK) {
                paintAttackableBlocks((AttackableInterface) selectedItem);
            }
        }
    }

    public void paintMovableBlocks(Human human) {
        if (human == null) return;

        Graphics g = getGraphics();
        int humanX = human.getX();
        int humanY = human.getY();
        int speed = human.getMovementSpeed();
        for (int i = Math.max(1, humanX - speed); i <= Math.min(100, humanX + speed); i++) {
            for (int j = Math.max(1, humanY - speed); j <= Math.min(50, humanY + speed); j++) {
                if (i == humanX && j == humanY) continue;

                try {
                    MoveControlUtils.checkMoveDistance(human, i, j);
                    highlightBlock(g, i, j);
                } catch (AgeOfEmpiresException ex) {
                    // Handle the exception if needed
                }
            }
        }
    }

    public void paintAttackableBlocks(AttackableInterface attackableItem) {

        Graphics g = getGraphics();
        int attackableItemX = attackableItem.getX();
        int attackableItemY = attackableItem.getY();
        int upperLimit = (int) attackableItem.getUpperAttackDistanceLimit();
        boolean isArcher = attackableItem instanceof Archer;
        for (int i = Math.max(1, attackableItemX - upperLimit); i <= Math.min(100, attackableItemX + upperLimit); i++) {
            for (int j = Math.max(1, attackableItemY - upperLimit); j <= Math.min(50, attackableItemY + upperLimit); j++) {
                if (i == attackableItemX && j == attackableItemY) continue;

                if (isArcher) ((Archer) attackableItem).makeAttackAdjustments(i, j);

                try {
                    MoveControlUtils.checkAttackDistance(attackableItem, i, j);
                    highlightBlock(g, i, j);
                } catch (AgeOfEmpiresException ex) {
                    // Handle the exception if needed
                }
            }
        }
    }

    private void paintItemIcon(Graphics g, int x, int y, int width, int height, ImageIcon icon) {
        if (icon == null) return;
        // Get the scaled instance of the icon to fit the block size
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Calculate the position to center the icon
        int iconX = x + (width - scaledIcon.getIconWidth()) / 2;
        int iconY = y + (height - scaledIcon.getIconHeight()) / 2;

        // Draw the scaled icon onto the Graphics object
        scaledIcon.paintIcon(this, g, iconX, iconY);
    }

    public void highlightBlock(Graphics g, int col, int row) {
        if (col < 1 || col > 100 || row < 1 || row > 50) {
            return;
        }

        Item item = GameManager.getInstance().getGame().getMap().getItemAtCoordinates(col, row);
        Color cellColor = item != null ? GameColors.OCCUPIED_HIGHLIGHTED_TILE_COLOR : GameColors.HIGHLIGHTED_TILE_COLOR;

        paintBlockContent(g, cellColor, item, col, row);
    }

    private void paintBlockContent(Graphics g, Color cellColor, Item item, int col, int row) {
        int blockSize = getBlockSize();

        g.setColor(cellColor);
        g.fillRect(col * blockSize + xOffset, row * blockSize + yOffset, blockSize, blockSize);
        g.setColor(GameColors.LINE_TILE_COLOR);
        g.drawRect(col * blockSize + xOffset, row * blockSize + yOffset, blockSize, blockSize);

        if (item != null) {
            ImageIcon myIcon = getIcon(item);
            paintItemIcon(g, col * blockSize + xOffset, row * blockSize + yOffset, blockSize, blockSize, myIcon);
        }
    }

    private ImageIcon getIcon(Item item) {
        if (item.getClass().equals(MainBuilding.class)) {
            return new ImageIcon("images/main-building.png");
        } else if (item.getClass().equals(University.class)) {
            return new ImageIcon("images/university.png");
        } else if (item.getClass().equals(Tower.class)) {
            return new ImageIcon("images/tower.png");
        } else if (item.getClass().equals(Worker.class)) {
            return new ImageIcon("images/worker.png");
        } else if (item.getClass().equals(Archer.class)) {
            return new ImageIcon("images/archer.png");
        } else if (item.getClass().equals(Swordman.class)) {
            return new ImageIcon("images/swordman.png");
        } else if (item.getClass().equals(Spearman.class)) {
            return new ImageIcon("images/spearman.png");
        } else if (item.getClass().equals(Cavalry.class)) {
            return new ImageIcon("images/cavalry.png");
        } else if (item.getClass().equals(Catapult.class)) {
            return new ImageIcon("images/catapult.png");
        }
        return null;
    }

    static class Block {
        public int col;
        public int row;

        public Block(int col, int row) {
            this.col = col;
            this.row = row;
        }
    }

}