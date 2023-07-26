package gui.game;

import entities.Item;
import game.GameManager;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapPanel extends JPanel {
    private static final int MAP_ROWS = 50;
    private static final int MAP_COLS = 100;
    private static final int BLOCK_SIZE = 10; // Initial size of the blocks
    private static final Map<TileKey, Color> TILE_COLORS = new HashMap<>();

    static {
        TILE_COLORS.put(TileKey.EMPTY, Color.WHITE);
        TILE_COLORS.put(TileKey.HIGHLIGHTED, Color.RED);
        TILE_COLORS.put(TileKey.OCCUPIED, Color.GRAY);
        TILE_COLORS.put(TileKey.OCCUPIED_HIGHLIGHTED, Color.GREEN);
        TILE_COLORS.put(TileKey.LINE, Color.BLACK);
    }

    private int blockSize = BLOCK_SIZE; // Current size of the blocks
    private int xOffset = 0;
    private int yOffset = 0;

    // This 2D array will store the items on the map.
    // 'null' represents an empty cell, and non-null values will represent the buildings or humans on the map.
    private ArrayList<Block> highlightedBlocks = new ArrayList<>();

    private Item selectedItem;

    private int lastX;
    private int lastY;
    private boolean isDragging;

    // Set the boundaries to limit the map scrolling
    private int minXOffset = 0;
    private int minYOffset = 0;
    private int maxXOffset;
    private int maxYOffset;

    public MapPanel() {
        setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));

        initializeMouseListeners();
    }

    private void initializeMouseListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resetBlocks();

                int x = e.getX() - getXOffset();
                int y = e.getY() - getYOffset();

                int col = x / getBlockSize();
                int row = y / getBlockSize();

                GamePanel gamePanel = (GamePanel) (getParent().getParent().getParent());
                var items = GameManager.getInstance().getGame().getMap().getAllItemsAtCoordinates(col, row);

                // If there is more than one item, show the selection dialog
                if (items.size() > 1) {
                    highlightBlock(getGraphics(), col, row);
                    ItemSelectionDialog dialog = new ItemSelectionDialog((Frame) SwingUtilities.getWindowAncestor(gamePanel), items);
                    dialog.setVisible(true);

                    selectedItem = dialog.getSelectedItem();
                    if (selectedItem != null) {
                        // Do something with the selected item, like open a panel, perform an action, etc.
                        // For example, if you have an itemActionsPanel in your main application, you can do:
                        gamePanel.getItemActionsPanel().openPanel(selectedItem);
                    }
                } else if (items.size() == 1) {
                    // If there is only one item, handle it directly
                    selectedItem = items.get(0);
                    // Handle the item as needed
                }

                if (selectedItem != null) {
                    highlightNeighbors(col, row);
                    gamePanel.getItemActionsPanel().openPanel(selectedItem);
                } else {
                    gamePanel.getItemActionsPanel().closePanels();
                }


                System.out.println("Tıklanan blok: (" + col + ", " + row + ")");
            }
        });

        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                // Check if the CTRL key is pressed
                if (e.isControlDown()) {
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

                    // Repaint the panel to update the view
                    revalidate(); // Ensures the scrollbars are updated if needed
                    repaint();
                }
            }
        });

        // Calculate the maximum offsets based on the map size and the panel size
        //maxXOffset = MAP_COLS * blockSize - getWidth();
        //maxYOffset = MAP_ROWS * blockSize - getHeight();
        maxXOffset = 100;
        maxYOffset = 100;
        // Add mouse listeners
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastX = e.getX();
                lastY = e.getY();
                isDragging = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDragging = false;

                // Bring the map back to the minimum offset
                if (xOffset < minXOffset) {
                    xOffset = minXOffset;
                } else if (xOffset > maxXOffset) {
                    //xOffset = maxXOffset;
                    xOffset = 0;
                }

                if (yOffset < minYOffset) {
                    yOffset = minYOffset;
                } else if (yOffset > maxYOffset) {
                    //yOffset = maxYOffset;
                    yOffset = 0;
                }

                // Repaint the panel to update the view
                repaint();
            }
        });

        // Add mouse motion listener for dragging
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging) {
                    int dx = e.getX() - lastX;
                    int dy = e.getY() - lastY;

                    // Update the xOffset and yOffset with elastic resistance if the boundaries are exceeded
                    if (xOffset < minXOffset || xOffset > maxXOffset) {
                        dx /= 2; // Apply resistance for dragging
                    }

                    if (yOffset < minYOffset || yOffset > maxYOffset) {
                        dy /= 2; // Apply resistance for dragging
                    }

                    xOffset += dx;
                    yOffset += dy;

                    lastX = e.getX();
                    lastY = e.getY();

                    // Repaint the panel to update the view
                    repaint();
                }
            }
        });
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 1; row <= MAP_ROWS; row++) {
            for (int col = 1; col <= MAP_COLS; col++) {
                // Determine the color of the cell based on its contents.
                Item item = GameManager.getInstance().getGame().getMap().getItemAtCoordinates(col, row); //mapItems[row][col];
                Color cellColor;
                if (item != null) {
                    if (item.equals(selectedItem)) {
                        cellColor = TILE_COLORS.get(TileKey.OCCUPIED_HIGHLIGHTED);
                    } else {
                        cellColor = TILE_COLORS.get(TileKey.OCCUPIED);
                    }
                } else {
                    cellColor = TILE_COLORS.get(TileKey.EMPTY);
                }

                paintBlockContent(g, cellColor, item, col, row);
            }
        }
    }

    // Helper method to draw the symbol for the given item at the specified position.
    private void drawItemSymbol(Graphics g, int x, int y, int width, int height, char symbol) {
        g.setColor(TILE_COLORS.get(TileKey.LINE));
        Font font = new Font("Arial", Font.BOLD, blockSize * 9 / 10);
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

        for (var block : highlightedBlocks) {
            int col = block.col;
            int row = block.row;
            // Determine the color of the cell based on its contents.
            Item item = GameManager.getInstance().getGame().getMap().getItemAtCoordinates(col, row); // mapItems[row][col];
            Color cellColor = item != null ? TILE_COLORS.get(TileKey.OCCUPIED) : TILE_COLORS.get(TileKey.EMPTY);

            paintBlockContent(g, cellColor, item, col, row);
        }

        highlightedBlocks.clear();
    }

    private void highlightNeighbors(int col, int row) {
        var g = getGraphics();

        if (selectedItem == null || selectedItem.getCurrentState() == Item.State.IDLE) {
            highlightBlock(g, col, row);
        } else {
            for (int i = col - 7; i <= col + 7; i++) {
                for (int j = row - 7; j <= row + 7; j++) {
                    highlightBlock(g, i, j);
                }
            }
        }
    }

    private void highlightBlock(Graphics g, int col, int row) {
        if (col < 1 || col > 100 || row < 1 || row > 50) {
            return;
        }

        Item item = GameManager.getInstance().getGame().getMap().getItemAtCoordinates(col, row);
        Color cellColor = item != null ? TILE_COLORS.get(TileKey.OCCUPIED_HIGHLIGHTED) : TILE_COLORS.get(TileKey.HIGHLIGHTED);

        paintBlockContent(g, cellColor, item, col, row);

        highlightedBlocks.add(new Block(col, row));
    }

    private void paintBlockContent(Graphics g, Color cellColor, Item item, int col, int row) {
        int blockSize = getBlockSize();

        g.setColor(cellColor);
        g.fillRect(col * blockSize + xOffset, row * blockSize + yOffset, blockSize, blockSize);
        g.setColor(TILE_COLORS.get(TileKey.LINE));
        g.drawRect(col * blockSize + xOffset, row * blockSize + yOffset, blockSize, blockSize);

        if (item != null) {
            char itemSymbol = item.getSymbol().charAt(0);
            drawItemSymbol(g, col * blockSize + xOffset, row * blockSize + yOffset, blockSize, blockSize, itemSymbol);
        }
    }

    private enum TileKey {
        EMPTY, HIGHLIGHTED, OCCUPIED, OCCUPIED_HIGHLIGHTED, LINE
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