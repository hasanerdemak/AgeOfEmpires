package entities;

import abstracts.ItemInterface;
import concretes.Player;
import concretes.Resources;
import exceptions.AgeOfEmpiresException;

public abstract class Item implements ItemInterface {
    private int itemID;
    private Player ownerPlayer;
    private int x;
    private int y;
    private int lifePoints;
    private Resources cost;

    public Item(int lifePoints, Resources cost) {
        this.lifePoints = lifePoints;
        this.cost = cost;
    }

    public Item(Player ownerPlayer, int x, int y, int lifePoints, Resources cost) throws AgeOfEmpiresException {
        this.ownerPlayer = ownerPlayer;
        setX(x);
        setY(y);
        this.lifePoints = lifePoints;
        this.cost = cost;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    @Override
    public Player getOwnerPlayer() {
        return ownerPlayer;
    }

    public void setOwnerPlayer(Player ownerPlayer) {
        this.ownerPlayer = ownerPlayer;
    }

    @Override
    public int getX() {
        System.out.println(x);
        return x;
    }

    @Override
    public int getY() {
        System.out.println(y);
        return y;
    }

    public void setX(int x) throws AgeOfEmpiresException {
        if (x < 1 || x > 100) {
            throw new AgeOfEmpiresException("x must be in range 1-100");
        }
        this.x = x;
    }

    public void setY(int y) throws AgeOfEmpiresException {
        if (y < 1 || y > 50) {
            throw new AgeOfEmpiresException("y must be in range 1-50");
        }
        this.y = y;
    }

    @Override
    public int getLifePoints() {
        System.out.println(lifePoints);
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public Resources getCost() {
        return cost;
    }

    public void setCost(Resources cost) {
        this.cost = cost;
    }

    public void damage(int attackPower) {
        this.lifePoints -= attackPower;
    }

    @Override
    public boolean checkIfAlive() {
        if (getLifePoints() <= 0) {
            ownerPlayer.getCurrentGame().getMap().getMapItems().remove(this);
            return false;
        }
        return true;
    }

    @Override
    public String print_message() {
        String message = this + "\t->\tSymbol: " + getSymbol() + "\tx: " + getX() + "\ty: " + getY() + "\tlife points: " + getLifePoints();
        System.out.println(message);
        return message;
    }

    @Override
    public String toString() {
        String className = getClass().getSimpleName();
        if (getOwnerPlayer() == null) {
            return className;
        } else {
            return className + "-" + getItemID() + " (" + getOwnerPlayer() + ")";
        }
    }
}
