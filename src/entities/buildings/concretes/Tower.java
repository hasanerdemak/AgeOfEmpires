package entities.buildings.concretes;

import constants.BuildingConstants.TowerConstants;
import entities.buildings.abstracts.Building;
import exceptions.AgeOfEmpiresException;
import game.Player;
import interfaces.AttackableInterface;
import interfaces.TowerInterface;

public class Tower extends Building implements TowerInterface, AttackableInterface {

    public Tower() {
        super(TowerConstants.LIFE_POINTS, TowerConstants.COST);
    }

    public Tower(Player ownerPlayer, int x, int y) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, TowerConstants.LIFE_POINTS, TowerConstants.COST);
    }

    public Tower(Player ownerPlayer, int x, int y, int lifePoints) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, TowerConstants.COST);
    }

    @Override
    public float getLowerAttackDistanceLimit() {
        return TowerConstants.LOWER_ATTACK_DISTANCE_LIMIT;
    }

    @Override
    public float getUpperAttackDistanceLimit() {
        return TowerConstants.UPPER_ATTACK_DISTANCE_LIMIT;
    }

    @Override
    public int getAttackPower() {
        return TowerConstants.ATTACK_POWER;
    }

    @Override
    public String getSymbol() {
        return TowerConstants.SYMBOL;
    }

    @Override
    public void attack(int x, int y) throws AgeOfEmpiresException {
        AttackableInterface.super.attack(x, y);
        setCurrentState(State.IDLE);
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
