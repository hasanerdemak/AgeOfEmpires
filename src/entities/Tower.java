package entities;

import abstracts.AttackableInterface;
import abstracts.TowerInterface;
import concretes.*;
import exceptions.AgeOfEmpiresException;

public class Tower extends Building implements TowerInterface, AttackableInterface {
    private final int lowerAttackDistanceLimit = 1;
    private final int upperAttackDistanceLimit = 7;
    private final int attackPower = 2;

    public Tower() {
        super(50, new Resources(10, 5, 40));
    }

    public Tower(Player ownerPlayer, int x, int y) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, 50, new Resources(10, 5, 40));
    }

    public Tower(Player ownerPlayer, int x, int y, int lifePoints) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, new Resources(10, 5, 40));
    }

    @Override
    public float getLowerAttackDistanceLimit() {
        return lowerAttackDistanceLimit;
    }

    @Override
    public float getUpperAttackDistanceLimit() {
        return upperAttackDistanceLimit;
    }

    @Override
    public int getAttackPower() {
        return attackPower;
    }

    @Override
    public String getSymbol() {
        return "T";
    }

    @Override
    public void attack(int x, int y) throws AgeOfEmpiresException {
        AttackableInterface.super.attack(x, y);
    }

    @Override
    public void defend(int x, int y) {
        AttackableInterface.super.defend(x, y);
    }

    @Override
    public boolean checkIfAlive() {
        if (super.checkIfAlive()) {
            return true;
        }

        getOwnerPlayer().removeTower(this);
        return false;
    }


}
