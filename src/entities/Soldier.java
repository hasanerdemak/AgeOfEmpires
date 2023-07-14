package entities;

import concretes.Player;
import concretes.Resources;
import exceptions.AgeOfEmpiresException;

public abstract class Soldier extends Human {

    public Soldier() {
        super(0, null, 0, 0, 0, 0);
    }

    public Soldier(int lifePoints, Resources cost, int movementSpeed, int attackPower, float lowerAttackDistanceLimit, float upperAttackDistanceLimit) {
        super(lifePoints, cost, movementSpeed, attackPower, lowerAttackDistanceLimit, upperAttackDistanceLimit);
    }

    public Soldier(Player ownerPlayer, int x, int y, int lifePoints, Resources cost, int movementSpeed, int attackPower, float lowerAttackDistanceLimit, float upperAttackDistanceLimit) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, cost, movementSpeed, attackPower, lowerAttackDistanceLimit, upperAttackDistanceLimit);
    }

    @Override
    public int getLifePoints() {
        var life = super.getLifePoints() + getTrainingCount();
        System.out.println(life);
        return life;
    }

    @Override
    public int getAttackPower() {
        return super.getAttackPower() + getTrainingCount();
    }

    public abstract int getTrainingCount();

    public abstract void makeAttackAdjustments(int x, int y);

    @Override
    public void attack(int x, int y) throws AgeOfEmpiresException {
        makeAttackAdjustments(x, y);
        super.attack(x, y);
    }

    @Override
    public void defend(int x, int y) {
        makeAttackAdjustments(x, y);
        super.defend(x, y);
    }

    @Override
    public boolean checkIfAlive() {
        if (super.checkIfAlive()) {
            return true;
        }

        getOwnerPlayer().removeSoldier(this);
        return false;
    }
}
