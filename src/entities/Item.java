package entities;

import entities.buildings.concretes.University;
import entities.humans.concretes.Archer;
import entities.humans.concretes.Catapult;
import entities.humans.concretes.Cavalry;
import entities.humans.concretes.Spearman;
import exceptions.AgeOfEmpiresException;
import game.Player;
import interfaces.AttackableInterface;
import interfaces.ItemInterface;

public abstract class Item implements ItemInterface {

    private int itemID;
    private Player ownerPlayer;
    private int x;
    private int y;
    private int lifePoints;
    private Resources cost;
    private State currentState = State.IDLE;

    public Item(int lifePoints, Resources cost) {
        this.lifePoints = lifePoints;
        this.cost = cost;
    }

    public Item(Player ownerPlayer, int x, int y, int lifePoints, Resources cost) throws AgeOfEmpiresException {
        this.ownerPlayer = ownerPlayer;
        setX(x);
        setY(y);
        setLifePoints(lifePoints);
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
        return x;
    }

    public void setX(int x) throws AgeOfEmpiresException {
        if (x < 1 || x > 100) {
            throw new AgeOfEmpiresException("x must be in range 1-100");
        }
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) throws AgeOfEmpiresException {
        if (y < 1 || y > 50) {
            throw new AgeOfEmpiresException("y must be in range 1-50");
        }
        this.y = y;
    }

    @Override
    public int getLifePoints() {
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

    public State getCurrentState() {
        return currentState;
    }

    @Override
    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public void damage(int attackPower) {
        this.lifePoints -= attackPower;
    }

    @Override
    public boolean checkIfAlive() {
        boolean isAlive = getLifePoints() > 0;
        if (!isAlive) {
            ownerPlayer.getCurrentGame().getMap().getMapItems().remove(this);
        }
        return isAlive;
    }

    @Override
    public String print_message() {
        return this + "\t->\tSymbol: " + getSymbol() + "\tx: " + getX() + "\ty: " + getY() + "\tlife points: " + getLifePoints();
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

    public String getItemInfo() {
        StringBuilder info = new StringBuilder(this + "\n" +
                //"Symbol: " + getSymbol() + "\n" +
                "X: " + getX() + "   " +
                "Y: " + getY() + "\n" +
                "Life points: " + getLifePoints());

        if (this instanceof AttackableInterface attackableItem) {
            if (attackableItem instanceof Archer) {
                info.append("\nAttack power:\n")
                        .append("  - With Bow:\n")
                        .append("    -> 2 (to humans)\n")
                        .append("    -> 1 (to buildings)\n")
                        .append("  - With Sword: 2");
            } else if (attackableItem instanceof Spearman) {
                info.append("\nAttack power:\n")
                        .append("    -> 10 (to cavalry)\n")
                        .append("    -> 2 (to other items)");
            } else if (attackableItem instanceof Cavalry) {
                info.append("\nAttack power:\n")
                        .append("    -> 5 (to cavalry and buildings)\n")
                        .append("    -> 10 (to other items)");
            } else if (attackableItem instanceof Catapult) {
                info.append("\nAttack power:\n")
                        .append("    -> 30 (to buildings)\n")
                        .append("    -> ∞ (to humans)");
            } else {
                info.append("\nAttack power: ").append(attackableItem.getAttackPower());
            }
        }

        if (this instanceof University university) {
            info.append("\nInfantry Training Count: ")
                    .append(university.getInfantryTrainingCount())
                    .append("\nCavalry Training Count: ")
                    .append(university.getCavalryTrainingCount())
                    .append("\nCatapult Training Count: ")
                    .append(university.getCatapultTrainingCount());
        }

        return info.toString();
    }

    public enum State {
        IDLE,
        ATTACK,
        MOVE,
        BUILD
    }

}
