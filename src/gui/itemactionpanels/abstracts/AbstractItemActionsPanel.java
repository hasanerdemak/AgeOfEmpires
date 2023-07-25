package gui.itemactionpanels.abstracts;

import entities.Item;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractItemActionsPanel extends JPanel {
    private JLabel infoLabel = new JLabel();
    private GridBagConstraints constraints = new GridBagConstraints();

    public AbstractItemActionsPanel() {
        setLayout(new GridBagLayout());

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(50, 20, 5, 20);

        constraints.gridy = 0;
        add(infoLabel, constraints);

        constraints.insets.top = 5;

    }

    public JLabel getInfoLabel() {
        return infoLabel;
    }

    public void setInfoLabel(JLabel infoLabel) {
        this.infoLabel = infoLabel;
    }

    public GridBagConstraints getConstraints() {
        return constraints;
    }

    public void setConstraints(GridBagConstraints constraints) {
        this.constraints = constraints;
    }

    public void onAddedCustomPanel(Item item) {
        infoLabel.setText(item.print_message());
    }
}
