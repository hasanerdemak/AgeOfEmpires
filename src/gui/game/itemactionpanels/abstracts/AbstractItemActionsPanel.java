package gui.game.itemactionpanels.abstracts;

import entities.Item;
import game.GameManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public abstract class AbstractItemActionsPanel extends JPanel {
    private JTextArea infoTextArea = new JTextArea();
    private GridBagConstraints constraints = new GridBagConstraints();
    private boolean isButtonsEnabled;

    public AbstractItemActionsPanel() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0}; // component + 1
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE}; // component + 1

        constraints.anchor = GridBagConstraints.NORTH;
        //infoTextArea.setLineWrap(true);
        infoTextArea.setWrapStyleWord(true);
        infoTextArea.setEditable(false);
        infoTextArea.setBackground(new Color(195, 255, 247, 255));

        TitledBorder title;
        title = BorderFactory.createTitledBorder("Info");
        infoTextArea.setBorder(title);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 0, 5, 0);

        constraints.gridy = 0;
        add(infoTextArea, constraints);

    }

    public JTextArea getInfoTextArea() {
        return infoTextArea;
    }

    public void setInfoTextArea(JTextArea infoTextArea) {
        this.infoTextArea = infoTextArea;
    }

    public GridBagConstraints getConstraints() {
        return constraints;
    }

    public void setConstraints(GridBagConstraints constraints) {
        this.constraints = constraints;
    }

    public boolean isButtonsEnabled() {
        return isButtonsEnabled;
    }

    public void onPanelVisible(Item item) {
        String info = item +
                "\n" +
                "Symbol: " + item.getSymbol() +
                "\n" +
                "x: " + item.getX() +
                "\n" +
                "y: " + item.getY() +
                "\n" +
                "life points: " + item.getLifePoints();
        infoTextArea.setText(info);

        boolean isButtonsEnabled = GameManager.getInstance().getGame().getCurrentPlayer().equals(item.getOwnerPlayer());
        setButtonsEnabled(isButtonsEnabled);
        checkButtonEnableState();
    }

    public abstract void checkButtonEnableState();

    // Boolean değişkeni güncellemek için setter metodu
    public void setButtonsEnabled(boolean enabled) {
        isButtonsEnabled = enabled;
        checkButtonEnableState(); // Duruma göre butonların enable durumunu güncelle
    }
}
