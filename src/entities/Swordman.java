package entities;

import concretes.Player;
import concretes.Resources;
import exceptions.AgeOfEmpiresException;

public class Swordman extends Soldier {
    public Swordman() {
        super(5, new Resources(2, 2, 0),
                2, 3, 1, (float) Math.sqrt(2));
    }

    public Swordman(Player ownerPlayer, int x, int y) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, 5, new Resources(2, 2, 0),
                2, 3, 1, (float) Math.sqrt(2));
    }

    public Swordman(Player ownerPlayer, int x, int y, int lifePoints) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, new Resources(2, 2, 0),
                2, 3, 1, (float) Math.sqrt(2));
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
    }

    @Override
    public String getSymbol() {
        return "K";
    }
}
