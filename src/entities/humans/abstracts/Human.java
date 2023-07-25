package entities.humans.abstracts;

import entities.Item;
import interfaces.AttackableInterface;
import interfaces.HumanInterface;
import utils.MoveControlUtils;
import game.Player;
import entities.Resources;
import exceptions.AgeOfEmpiresException;

public abstract class Human extends Item implements HumanInterface, AttackableInterface {
    private int movementSpeed;
    private int attackPower;
    private float lowerAttackDistanceLimit;
    private float upperAttackDistanceLimit;

    public Human(int lifePoints, Resources cost, int movementSpeed, int attackPower, float lowerAttackDistanceLimit, float upperAttackDistanceLimit) {
        super(lifePoints, cost);
        this.movementSpeed = movementSpeed;
        this.attackPower = attackPower;
        this.lowerAttackDistanceLimit = lowerAttackDistanceLimit;
        this.upperAttackDistanceLimit = upperAttackDistanceLimit;
    }

    public Human(Player ownerPlayer, int x, int y, int lifePoints, Resources cost, int movementSpeed, int attackPower, float lowerAttackDistanceLimit, float upperAttackDistanceLimit) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, cost);
        this.movementSpeed = movementSpeed;
        this.attackPower = attackPower;
        this.lowerAttackDistanceLimit = lowerAttackDistanceLimit;
        this.upperAttackDistanceLimit = upperAttackDistanceLimit;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    @Override
    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    @Override
    public float getLowerAttackDistanceLimit() {
        return lowerAttackDistanceLimit;
    }

    public void setLowerAttackDistanceLimit(int lowerAttackDistanceLimit) {
        this.lowerAttackDistanceLimit = lowerAttackDistanceLimit;
    }

    @Override
    public float getUpperAttackDistanceLimit() {
        return upperAttackDistanceLimit;
    }

    public void setUpperAttackDistanceLimit(int upperAttackDistanceLimit) {
        this.upperAttackDistanceLimit = upperAttackDistanceLimit;
    }

    @Override
    public void attack(int x, int y) throws AgeOfEmpiresException {
        AttackableInterface.super.attack(x, y);
    }

    @Override
    public void move(int x, int y) throws AgeOfEmpiresException {
        // Check default conditions required to make a move
        MoveControlUtils.checkMoveConditions(getOwnerPlayer());
        MoveControlUtils.checkCoordinates(this, x, y, "move to");
        MoveControlUtils.checkMoveDistance(this, x, y);
        MoveControlUtils.checkEmptyCoordinates(this, x, y);

        // Go to x,y
        setX(x);
        setY(y);

        // Increase player turn counter after successful move
        getOwnerPlayer().getCurrentGame().increasePlayerTurnCounter();
    }


}
