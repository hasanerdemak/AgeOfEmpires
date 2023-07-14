package abstracts;

import concretes.Game;
import concretes.MoveUtils;
import entities.Item;
import entities.Spearman;
import exceptions.AgeOfEmpiresException;

public interface AttackableInterface extends ItemInterface {
    default void attack(int x, int y) throws AgeOfEmpiresException {
        // Check default conditions required to make a move
        MoveUtils.checkMoveConditions(getOwnerPlayer());
        MoveUtils.checkCoordinates(this, x, y, "attack");
        MoveUtils.checkAttackDistance(this, x, y);

        // Check if there is an item
        Item item = MoveUtils.getTargetItemAtCoordinates(this, x, y);
        // Check if the item is his own item
        if (MoveUtils.isEnemyItem(this, item)) {
            handleAttack(item);
        } else {
            throw new AgeOfEmpiresException(this + " cannot attack his own item: " + item + " at coordinates x: " + x + ", y: " + y);
        }

        // Increase player turn counter after successful move
        getOwnerPlayer().getCurrentGame().increasePlayerTurnCounter();
    }

    default void defend(int x, int y) {
        try {
            if (Game.checkPlayerTurn(getOwnerPlayer())) {
                throw new AgeOfEmpiresException(getOwnerPlayer() + " cannot defend himself without being attacked.");
            }
            // Check distance
            MoveUtils.checkAttackDistance(this, x, y);
            // Check if there is an item
            Item item = MoveUtils.getTargetItemAtCoordinates(this, x, y);
            // Check if the item is his own item
            if (MoveUtils.isEnemyItem(this, item)) {
                item.damage(getAttackPower());
            }
        } catch (AgeOfEmpiresException e) {
            //System.out.println(e.getMessage());
        }
    }

    default void handleAttack(Item targetItem) {
        // Check if the attacked item can attack
        if (targetItem instanceof AttackableInterface) {
            // if priority is on the attacker item
            if (this instanceof Spearman && !(targetItem instanceof Spearman)) {
                targetItem.damage(getAttackPower());
                if (targetItem.checkIfAlive()) {
                    ((AttackableInterface) targetItem).defend(getX(), getY());
                    this.checkIfAlive();
                }
            }
            // else if priority is on the defender item
            else if (!(this instanceof Spearman) && targetItem instanceof Spearman) {
                ((Spearman) targetItem).defend(getX(), getY());
                if (this.checkIfAlive()) {
                    targetItem.damage(getAttackPower());
                    targetItem.checkIfAlive();
                }
            } else {
                targetItem.damage(getAttackPower());
                ((AttackableInterface) targetItem).defend(getX(), getY());
                targetItem.checkIfAlive();
                this.checkIfAlive();
            }
        } else {
            targetItem.damage(getAttackPower());
            targetItem.checkIfAlive();
        }
    }

    float getLowerAttackDistanceLimit();

    float getUpperAttackDistanceLimit();

    int getAttackPower();

}