package gui;

import entities.Item;
import game.GameManager;
import gui.itemactionpanels.MainBuildingActionsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapPanel extends JPanel {
    private static final int MAP_ROWS = 50;
    private static final int MAP_COLS = 100;
    private static final Map<TileKey, Color> TILE_COLORS = new HashMap<>();

    static {
        TILE_COLORS.put(TileKey.EMPTY, Color.WHITE);
        TILE_COLORS.put(TileKey.HIGHLIGHTED, Color.RED);
        TILE_COLORS.put(TileKey.OCCUPIED, Color.GRAY);
        TILE_COLORS.put(TileKey.OCCUPIED_HIGHLIGHTED, Color.GREEN);
        TILE_COLORS.put(TileKey.LINE, Color.BLACK);
    }

    // This 2D array will store the items on the map.
    // 'null' represents an empty cell, and non-null values will represent the buildings or humans on the map.
    private ArrayList<Block> highlightedBlocks;

    private Item selectedItem;

    public MapPanel() {
        highlightedBlocks = new ArrayList<>();
        //setSize(BLOCK_SIZE*MAP_COLS, BLOCK_SIZE*MAP_ROWS);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resetBlocks();

                int x = e.getX() - getXOffset();
                int y = e.getY() - getYOffset();

                int col = x / getBlockWidth();
                int row = y / getBlockHeight();

                selectedItem = GameManager.getInstance().getGame().getMap().getItemAtCoordinates(col, row);
                if (selectedItem != null) {
                    highlightNeighbors(col, row);
                    GamePanel gamePanel = (GamePanel) getParent();
                    gamePanel.getItemActionsPanel().openPanel(selectedItem);
                }

                System.out.println("Tıklanan blok: (" + col + ", " + row + ")");
            }
        });
    }

    public int getBlockWidth() {
        return getWidth() / MAP_COLS;
    }

    public int getBlockHeight() {
        return getHeight() / MAP_ROWS;
    }

    public int getXOffset() {
        return ((getWidth() % MAP_COLS) / 2) - getBlockWidth();
    }

    public int getYOffset() {
        return ((getHeight() % MAP_ROWS) / 2) - getBlockHeight();
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    // Method to update the mapItems array when a building or human is placed on the map.
    public void updateMapItem(int row, int col) {
        repaint(); // Redraw the map to reflect the changes.
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int blockWidth = getBlockWidth();
        int blockHeight = getBlockHeight();

        int xOffset = getXOffset();
        int yOffset = getYOffset();

        for (int row = 1; row <= MAP_ROWS; row++) {
            for (int col = 1; col <= MAP_COLS; col++) {
                // Determine the color of the cell based on its contents.
                Item item = GameManager.getInstance().getGame().getMap().getItemAtCoordinates(col, row); //mapItems[row][col];
                Color cellColor = item != null ? TILE_COLORS.get(TileKey.OCCUPIED) : TILE_COLORS.get(TileKey.EMPTY);

                g.setColor(cellColor);
                // Add the xOffset and yOffset to center the blocks
                g.fillRect(col * blockWidth + xOffset, row * blockHeight + yOffset, blockWidth, blockHeight);
                g.setColor(TILE_COLORS.get(TileKey.LINE));
                g.drawRect(col * blockWidth + xOffset, row * blockHeight + yOffset, blockWidth, blockHeight);

                // If the cell contains an item, draw its representation.
                if (item != null) {
                    char itemSymbol = item.getSymbol().charAt(0);
                    drawItemSymbol(g, col * blockWidth + xOffset, row * blockHeight + yOffset, blockWidth, blockHeight, itemSymbol);
                }
            }
        }
    }

    // Helper method to draw the symbol for the given item at the specified position.
    private void drawItemSymbol(Graphics g, int x, int y, int width, int height, char symbol) {
        g.setColor(TILE_COLORS.get(TileKey.LINE));
        Font font = new Font("Arial", Font.BOLD, 10);
        g.setFont(font);
        FontMetrics fontMetrics = g.getFontMetrics();
        int symbolWidth = fontMetrics.charWidth(symbol);
        int symbolHeight = fontMetrics.getAscent();
        int symbolX = x + (width - symbolWidth) / 2;
        int symbolY = y + (height + symbolHeight) / 2;
        g.drawString(String.valueOf(symbol), symbolX, symbolY);
    }

    private void resetBlocks() {
        var g = getGraphics();

        int blockWidth = getBlockWidth();
        int blockHeight = getBlockHeight();

        int xOffset = getXOffset();
        int yOffset = getYOffset();

        for (var block : highlightedBlocks) {
            int col = block.col;
            int row = block.row;
            // Determine the color of the cell based on its contents.
            Item item = GameManager.getInstance().getGame().getMap().getItemAtCoordinates(col, row); // mapItems[row][col];
            Color cellColor = item != null ? TILE_COLORS.get(TileKey.OCCUPIED) : TILE_COLORS.get(TileKey.EMPTY);

            g.setColor(cellColor);
            // Add the xOffset and yOffset to center the blocks
            g.fillRect(col * blockWidth + xOffset, row * blockHeight + yOffset, blockWidth, blockHeight);
            g.setColor(TILE_COLORS.get(TileKey.LINE));
            g.drawRect(col * blockWidth + xOffset, row * blockHeight + yOffset, blockWidth, blockHeight);

            // If the cell contains an item, draw its representation.
            if (item != null) {
                char itemSymbol = item.getSymbol().charAt(0);
                drawItemSymbol(g, col * blockWidth + xOffset, row * blockHeight + yOffset, blockWidth, blockHeight, itemSymbol);
            }
        }

        highlightedBlocks.clear();
    }

    private void highlightNeighbors(int col, int row) {
        var g = getGraphics();
        int xOffset = getXOffset();
        int yOffset = getYOffset();
        int blockWidth = getBlockWidth();
        int blockHeight = getBlockHeight();

        if (selectedItem == null || selectedItem.getCurrentState() == Item.State.IDLE) {
            highlightBlock(col, row, xOffset, yOffset, blockWidth, blockHeight, g);
        } else {
            for (int i = col - 7; i <= col + 7; i++) {
                for (int j = row - 7; j <= row + 7; j++) {
                    highlightBlock(i, j, xOffset, yOffset, blockWidth, blockHeight, g);
                }
            }
        }
    }

    private void highlightBlock(int col, int row, int xOffset, int yOffset, int blockWidth, int blockHeight, Graphics g) {
        if (col < 1 || col > 100 || row < 1 || row > 50) {
            return;
        }

        Item item = GameManager.getInstance().getGame().getMap().getItemAtCoordinates(col, row);
        Color cellColor = item != null ? TILE_COLORS.get(TileKey.OCCUPIED_HIGHLIGHTED) : TILE_COLORS.get(TileKey.HIGHLIGHTED);

        g.setColor(cellColor);
        g.fillRect(col * blockWidth + xOffset, row * blockHeight + yOffset, blockWidth, blockHeight);
        g.setColor(TILE_COLORS.get(TileKey.LINE));
        g.drawRect(col * blockWidth + xOffset, row * blockHeight + yOffset, blockWidth, blockHeight);

        if (item != null) {
            char itemSymbol = item.getSymbol().charAt(0);
            drawItemSymbol(g, col * blockWidth + xOffset, row * blockHeight + yOffset, blockWidth, blockHeight, itemSymbol);
        }

        highlightedBlocks.add(new Block(col, row));
    }

    private enum TileKey {
        EMPTY, HIGHLIGHTED, OCCUPIED, OCCUPIED_HIGHLIGHTED, LINE
    }

}

class Block {
    public int col;
    public int row;

    public Block(int col, int row) {
        this.col = col;
        this.row = row;
    }
}