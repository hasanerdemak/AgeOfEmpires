import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScrollPaneWithCtrlZoom {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("ScrollPane with CTRL+Mouse Wheel Zoom");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(800, 600)); // Adjust as needed

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        frame.add(scrollPane);

        scrollPane.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.isControlDown()) {
                    // Calculate the new view position based on the mouse wheel movement
                    int notches = e.getWheelRotation();
                    JViewport viewport = scrollPane.getViewport();
                    Point viewPosition = viewport.getViewPosition();
                    Dimension extentSize = viewport.getExtentSize();

                    int centerX = e.getX() - viewPosition.x;
                    int centerY = e.getY() - viewPosition.y;

                    int newX = viewPosition.x - centerX * notches;
                    int newY = viewPosition.y - centerY * notches;

                    // Ensure that the new view position stays within the content bounds
                    int maxX = contentPanel.getWidth() - extentSize.width;
                    int maxY = contentPanel.getHeight() - extentSize.height;

                    newX = Math.min(maxX, Math.max(0, newX));
                    newY = Math.min(maxY, Math.max(0, newY));

                    viewport.setViewPosition(new Point(newX, newY));
                }
            }
        });

        frame.setVisible(true);
    }
}
