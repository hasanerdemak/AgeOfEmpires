package entities.humans.concretes;

import entities.Item;
import entities.Resources;
import entities.buildings.abstracts.Building;
import entities.buildings.concretes.University;
import entities.humans.abstracts.Human;
import entities.humans.abstracts.Soldier;
import exceptions.AgeOfEmpiresException;
import game.Player;
import utils.DistanceUtils;

public class Archer extends Soldier {
    private final float lowerArrowThrowDistance = 2;
    private final float upperArrowThrowDistance = 5;
    private Weapon currentWeapon = Weapon.BOW;

    public Archer() {
        super(5, new Resources(5, 2, 0), 2, 2, 1, (float) Math.sqrt(2));
    }

    public Archer(Player ownerPlayer, int x, int y) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, 5, new Resources(5, 2, 0), 2, 2, 1, (float) Math.sqrt(2));
    }

    public Archer(Player ownerPlayer, int x, int y, int lifePoints) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, new Resources(5, 2, 0), 2, 2, 1, (float) Math.sqrt(2));
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

    public Weapon getCurrentWeapon() {
        return currentWeapon;
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
        if (DistanceUtils.getEuclideanDistanceBetweenCoordinates(this.getX(), this.getY(), x, y) <= Math.sqrt(2)) {
            currentWeapon = Weapon.SWORD;
        } else {
            currentWeapon = Weapon.BOW;
        }
    }

    @Override
    public String getSymbol() {
        return "O";
    }

    public enum Weapon {
        BOW, SWORD
    }

}
