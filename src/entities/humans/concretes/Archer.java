package entities.humans.concretes;

import constants.HumanConstants.ArcherConstants;
import entities.Item;
import entities.buildings.abstracts.Building;
import entities.buildings.concretes.University;
import entities.humans.abstracts.Human;
import entities.humans.abstracts.Soldier;
import exceptions.AgeOfEmpiresException;
import game.Player;
import utils.DistanceUtils;

public class Archer extends Soldier {
    private Weapon currentWeapon = Weapon.BOW;

    public Archer() {
        super(ArcherConstants.LIFE_POINTS, ArcherConstants.COST, ArcherConstants.MOVEMENT_SPEED,
                ArcherConstants.SWORD_ATTACK_POWER, ArcherConstants.SWORD_LOWER_ATTACK_DISTANCE_LIMIT, ArcherConstants.SWORD_UPPER_ATTACK_DISTANCE_LIMIT);
    }

    public Archer(Player ownerPlayer, int x, int y) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, ArcherConstants.LIFE_POINTS, ArcherConstants.COST, ArcherConstants.MOVEMENT_SPEED,
                ArcherConstants.SWORD_ATTACK_POWER, ArcherConstants.SWORD_LOWER_ATTACK_DISTANCE_LIMIT, ArcherConstants.SWORD_UPPER_ATTACK_DISTANCE_LIMIT);
    }

    public Archer(Player ownerPlayer, int x, int y, int lifePoints) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, ArcherConstants.COST, ArcherConstants.MOVEMENT_SPEED,
                ArcherConstants.SWORD_ATTACK_POWER, ArcherConstants.SWORD_LOWER_ATTACK_DISTANCE_LIMIT, ArcherConstants.SWORD_UPPER_ATTACK_DISTANCE_LIMIT);
    }

    @Override
    public float getLowerAttackDistanceLimit() {
        if (currentWeapon == Weapon.BOW) {
            return ArcherConstants.BOW_LOWER_ATTACK_DISTANCE_LIMIT;
        } else {
            return super.getLowerAttackDistanceLimit();
        }
    }

    @Override
    public float getUpperAttackDistanceLimit() {
        if (currentWeapon == Weapon.BOW) {
            return ArcherConstants.BOW_UPPER_ATTACK_DISTANCE_LIMIT;
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
                    setAttackPower(ArcherConstants.BOW_ATTACK_POWER_TO_HUMAN);
                } else if (item instanceof Building) {
                    setAttackPower(ArcherConstants.BOW_ATTACK_POWER_TO_BUILDING);
                }
            }
        }
    }

    private void chooseWeapon(int x, int y) {
        if (DistanceUtils.getEuclideanDistanceBetweenCoordinates(this.getX(), this.getY(), x, y) <= ArcherConstants.SWORD_UPPER_ATTACK_DISTANCE_LIMIT) {
            currentWeapon = Weapon.SWORD;
        } else {
            currentWeapon = Weapon.BOW;
        }
    }

    @Override
    public String getSymbol() {
        return ArcherConstants.SYMBOL;
    }

    public enum Weapon {
        BOW, SWORD
    }

}
