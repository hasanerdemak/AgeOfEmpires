package entities;

import concretes.Player;
import concretes.Resources;
import exceptions.AgeOfEmpiresException;

public class Archer extends Soldier {
    private final float lowerArrowThrowDistance = 2;
    private final float upperArrowThrowDistance = 5;
    private Weapon currentWeapon = Weapon.BOW;

    public Archer() {
        super(5, new Resources(5, 2, 0),
                2, 2, 1, (float) Math.sqrt(2));
    }

    public Archer(Player ownerPlayer, int x, int y) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, 5, new Resources(5, 2, 0),
                2, 2, 1, (float) Math.sqrt(2));
    }

    public Archer(Player ownerPlayer, int x, int y, int lifePoints) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, new Resources(5, 2, 0),
                2, 2, 1, (float) Math.sqrt(2));
    }

    @Override
    public float getLowerAttackDistanceLimit() {
        if (currentWeapon == Weapon.BOW) {
            return lowerArrowThrowDistance;
        } else {
            return super.getLowerAttackDistanceLimit();
        }
    }

    @Override
    public float getUpperAttackDistanceLimit() {
        if (currentWeapon == Weapon.BOW) {
            return upperArrowThrowDistance;
        } else {
            return super.getUpperAttackDistanceLimit();
        }
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
        chooseWeapon(x, y);
        if (currentWeapon == Weapon.BOW) {
            Item item = getOwnerPlayer().getCurrentGame().getMap().getItemAtCoordinates(x, y);
            if (item != null) {
                if (item instanceof Human) {
                    setAttackPower(2);
                } else if (item instanceof Building) {
                    setAttackPower(1);
                }
            }
        }
    }

    private void chooseWeapon(int x, int y) {
        if (Math.abs(this.getX_WithoutPrinting() - x) == 1 || Math.abs(this.getY_WithoutPrinting() - y) == 1) {
            currentWeapon = Weapon.SWORD;
        } else {
            currentWeapon = Weapon.BOW;
        }
    }

    @Override
    public String getSymbol() {
        return "A";
    }

    private enum Weapon {
        BOW,
        SWORD
    }

}
