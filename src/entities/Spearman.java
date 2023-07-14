package entities;

import concretes.Player;
import concretes.Resources;
import exceptions.AgeOfEmpiresException;

public class Spearman extends Soldier {
    public Spearman() {
        super(5, new Resources(2, 3, 0),
                2, 2, 1, (float) Math.sqrt(2));
    }

    public Spearman(Player ownerPlayer, int x, int y) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, 5, new Resources(2, 3, 0),
                2, 2, 1, (float) Math.sqrt(2));
    }

    public Spearman(Player ownerPlayer, int x, int y, int lifePoints) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, new Resources(2, 3, 0),
                2, 2, 1, (float) Math.sqrt(2));
    }

    @Override
    public int getTrainingCount() {
        University university = getOwnerPlayer().getUniversity();
        if (university != null) {
            return university.getInfantryTrainingCount();
        }
        return 0;
    }

    @Override
    public void makeAttackAdjustments(int x, int y) {
        Item item = getOwnerPlayer().getCurrentGame().getMap().getItemAtCoordinates(x, y);
        if (item instanceof Cavalry) {
            setAttackPower(10);
        } else {
            setAttackPower(2);
        }
    }

    @Override
    public String getSymbol() {
        return "S";
    }
}
