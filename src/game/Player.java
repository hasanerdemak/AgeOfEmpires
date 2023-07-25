package game;

import entities.Item;
import entities.buildings.abstracts.Building;
import entities.buildings.concretes.MainBuilding;
import entities.buildings.concretes.Tower;
import entities.buildings.concretes.University;
import entities.humans.abstracts.Human;
import entities.humans.abstracts.Soldier;
import entities.humans.concretes.*;
import exceptions.AgeOfEmpiresException;
import interfaces.PlayerInterface;
import utils.MoveControlUtils;

import java.util.ArrayList;

public class Player implements PlayerInterface {
    private final ArrayList<Tower> towers;
    private final ArrayList<Worker> workers;
    private final ArrayList<Soldier> soldiers;
    private final IDCountersHolder idCountersHolder;
    public int populationLimit = 20;
    private int playerID;
    private Game currentGame;
    private int wood;
    private int gold;
    private int stone;
    private MainBuilding mainBuilding;
    private University university;

    public Player(int id, Game currentGame, boolean isFirstStart) throws AgeOfEmpiresException {
        this.playerID = id;
        this.currentGame = currentGame;

        this.wood = 100;
        this.gold = 100;
        this.stone = 100;

        towers = new ArrayList<>();
        workers = new ArrayList<>();
        soldiers = new ArrayList<>();

        idCountersHolder = new IDCountersHolder();

        if (isFirstStart) {
            switch (this.playerID) {
                case 0 -> setMainBuilding(new MainBuilding(this, 1, 1));
                case 1 -> setMainBuilding(new MainBuilding(this, 100, 50));
                case 2 -> setMainBuilding(new MainBuilding(this, 1, 50));
                case 3 -> setMainBuilding(new MainBuilding(this, 100, 1));
            }

            assert mainBuilding != null;
            var initialWorker = new Worker(this, mainBuilding.getX(), mainBuilding.getY());
            addWorker(initialWorker, true);
        }
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public ArrayList<Tower> getTowers() {
        return towers;
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    @Override
    public int getWood() {
        return wood;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }

    @Override
    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    @Override
    public int getStone() {
        return stone;
    }

    public void setStone(int stone) {
        this.stone = stone;
    }

    public MainBuilding getMainBuilding() {
        return mainBuilding;
    }

    public void setMainBuilding(MainBuilding mainBuilding) {
        this.mainBuilding = mainBuilding;
        if (mainBuilding != null)
            currentGame.getMap().addMapItem(mainBuilding);
    }

    @Override
    public int getTowerCount() {
        return towers.size();
    }

    @Override
    public int getWorkerCount() {
        return workers.size();
    }

    @Override
    public int getSoldierCount() {
        return soldiers.size();
    }

    @Override
    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) throws AgeOfEmpiresException {
        if (this.university == null || university == null) {
            this.university = university;
            if (university != null)
                currentGame.getMap().addMapItem(university);
        } else {
            throw new AgeOfEmpiresException(this + " already has one University");
        }
    }

    @Override
    public Tower getTower(int index) {
        return towers.get(index);
    }

    @Override
    public Worker getWorker(int index) {
        return workers.get(index);
    }

    @Override
    public Soldier getSoldier(int index) {
        return soldiers.get(index);
    }

    public IDCountersHolder getIdCountersHolder() {
        return idCountersHolder;
    }

    public void addTower(Tower newTower, boolean incrementCounter) {
        if (!towers.contains(newTower)) {
            newTower.setItemID(idCountersHolder.towerIDCounter);
            towers.add(newTower);
            currentGame.getMap().addMapItem(newTower);
            if (incrementCounter) idCountersHolder.towerIDCounter++;
        } else {
            //System.err.println(this + " -> " + newTower + " has been added before.");
        }
    }

    public void addWorker(Worker newWorker, boolean incrementCounter) {
        if (!workers.contains(newWorker)) {
            newWorker.setItemID(idCountersHolder.workerIDCounter);
            workers.add(newWorker);
            currentGame.getMap().addMapItem(newWorker);
            if (incrementCounter) idCountersHolder.workerIDCounter++;
        } else {
            //System.err.println(this + " -> " + newWorker + " has been added before.");
        }
    }

    public void addSoldier(Soldier newSoldier, boolean incrementCounter) {
        if (!soldiers.contains(newSoldier)) {
            newSoldier.setItemID(getSoldierID(newSoldier, incrementCounter));
            soldiers.add(newSoldier);
            currentGame.getMap().addMapItem(newSoldier);
        } else {
            //System.err.println(this + " -> " + newSoldier + " has been added before.");
        }
    }

    public void removeTower(Tower tower) {
        towers.remove(tower);
    }

    public void removeWorker(Worker worker) {
        workers.remove(worker);
    }

    public void removeSoldier(Soldier soldier) {
        soldiers.remove(soldier);
    }

    public int getSoldierID(Soldier soldier, boolean incrementCounter) {
        if (soldier instanceof Archer) {
            if (incrementCounter) return idCountersHolder.archerIDCounter++;
            else return idCountersHolder.archerIDCounter;
        } else if (soldier instanceof Swordman) {
            if (incrementCounter) return idCountersHolder.swordmanIDCounter++;
            else return idCountersHolder.swordmanIDCounter;
        } else if (soldier instanceof Spearman) {
            if (incrementCounter) return idCountersHolder.spearmanIDCounter++;
            else return idCountersHolder.spearmanIDCounter;
        } else if (soldier instanceof Cavalry) {
            if (incrementCounter) return idCountersHolder.cavalryIDCounter++;
            else return idCountersHolder.cavalryIDCounter;
        } else if (soldier instanceof Catapult) {
            if (incrementCounter) return idCountersHolder.catapultIDCounter++;
            else return idCountersHolder.catapultIDCounter;
        }

        return -1;
    }

    @Override
    public void purchase(Item item) throws AgeOfEmpiresException {
        // Check default conditions required to make a move
        MoveControlUtils.checkMoveConditions(this);

        if (item instanceof Building) {
            throw new AgeOfEmpiresException(this + " -> purchase function cannot take Building. Item sent: " + item);
        }
        if (getWorkerCount() + getSoldierCount() >= 20) {
            throw new AgeOfEmpiresException(this + " -> has reached the population limit " + populationLimit);
        }

        if (item instanceof Human) {
            mainBuilding.produceUnit((Human) item);
        } else {
            throw new AgeOfEmpiresException(this + " -> purchase function can only produce Humans. Item sent: " + item);
        }

        // Increase player turn counter after successful move
        this.getCurrentGame().increasePlayerTurnCounter();
    }

    @Override
    public void pass() {
        //System.out.println(this + " passed this tour");
        currentGame.increasePlayerTurnCounter();
    }

    public ArrayList<Item> getPlayerItems() {
        var playerItems = new ArrayList<Item>();
        playerItems.add(mainBuilding);
        if (university != null) playerItems.add(university);
        playerItems.addAll(towers);
        playerItems.addAll(workers);
        playerItems.addAll(soldiers);

        return playerItems;
    }

    public void printPlayerInfo() {
        System.out.println(this + " INFO");
        System.out.println("----------------------------------------");
        System.out.println("RESOURCES");
        System.out.println();
        System.out.println("Wood: " + wood);
        System.out.println("Gold: " + gold);
        System.out.println("Stone: " + stone);
        System.out.println("----------------------------------------");
        System.out.println("BUILDINGS");
        System.out.println();
        System.out.println("Main Building");
        mainBuilding.print_message();
        if (university != null) {
            System.out.println("University");
            university.print_message();
        }
        if (towers.size() != 0) {
            System.out.println("Towers");
            for (Tower tower : towers) {
                tower.print_message();
            }
        }
        if (workers.size() != 0) {
            System.out.println("----------------------------------------");
            System.out.println("WORKERS");
            System.out.println();
            for (Worker worker : workers) {
                worker.print_message();
            }
        }
        if (soldiers.size() != 0) {
            System.out.println("----------------------------------------");
            System.out.println("SOLDIERS");
            System.out.println();
            for (Soldier soldier : soldiers) {
                soldier.print_message();
            }
        }
        System.out.println("----------------------------------------");
        System.out.println("Population: " + (getWorkerCount() + getSoldierCount()) + "/" + populationLimit);

    }

    @Override
    public String toString() {
        return "Player-" + playerID;
    }


    public static class IDCountersHolder {
        public int towerIDCounter = 0;
        public int workerIDCounter = 0;
        public int archerIDCounter = 0;
        public int swordmanIDCounter = 0;
        public int spearmanIDCounter = 0;
        public int cavalryIDCounter = 0;
        public int catapultIDCounter = 0;

    }
}
