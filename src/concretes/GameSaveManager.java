package concretes;

import entities.*;
import exceptions.AgeOfEmpiresException;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GameSaveManager {

    public static GameData createGameData(Game game) throws AgeOfEmpiresException {
        GameData newGameData = new GameData();
        newGameData.isGameOver = game.isGameOver;

        for (int i = 0; i < game.getPlayerCount(); i++) {
            Player player = game.getPlayer(i);
            PlayerData playerData = createPlayerData(player);
            newGameData.playerDataArrayList.add(playerData);
        }

        return newGameData;
    }

    private static PlayerData createPlayerData(Player player) {
        var idCountersHolder = player.getIdCountersHolder();
        PlayerData playerData = new PlayerData(
                player.getPlayerID(),
                player.getWood(),
                player.getGold(),
                player.getStone(),
                idCountersHolder.towerIDCounter,
                idCountersHolder.workerIDCounter,
                idCountersHolder.archerIDCounter,
                idCountersHolder.swordmanIDCounter,
                idCountersHolder.spearmanIDCounter,
                idCountersHolder.cavalryIDCounter,
                idCountersHolder.catapultIDCounter
        );

        Building mainBuilding = player.getMainBuilding();
        ItemData mainBuildingData = createItemData(mainBuilding);
        playerData.itemDataArrayList.add(mainBuildingData);

        University university = player.getUniversity();
        if (university != null) {
            ItemData universityData = createItemData(university);
            playerData.infantryTrainingCount = university.getInfantryTrainingCount();
            playerData.cavalryTrainingCount = university.getCavalryTrainingCount();
            playerData.catapultTrainingCount = university.getCatapultTrainingCount();
            playerData.itemDataArrayList.add(universityData);
        }

        if (player.getTowerCount() != 0) {
            for (int i = 0; i < player.getTowerCount(); i++) {
                ItemData towerData = createItemData(player.getTower(i));
                playerData.itemDataArrayList.add(towerData);
            }
        }

        if (player.getWorkerCount() != 0) {
            for (int i = 0; i < player.getWorkerCount(); i++) {
                ItemData workerData = createItemData(player.getWorker(i));
                playerData.itemDataArrayList.add(workerData);
            }
        }

        if (player.getSoldierCount() != 0) {
            for (int i = 0; i < player.getSoldierCount(); i++) {
                ItemData soldierData = createItemData(player.getSoldier(i));
                playerData.itemDataArrayList.add(soldierData);
            }
        }

        return playerData;
    }

    private static ItemData createItemData(Item item) {
        return new ItemData(
                item.getClass().getSimpleName(),
                item.getItemID(),
                item.getX(),
                item.getY(),
                item.getLifePoints()
        );
    }

    public static void extractGameDataToGame(GameData gameData, Game game) throws AgeOfEmpiresException {
        game.isGameOver = gameData.isGameOver;
        for (int i = 0; i < gameData.playerDataArrayList.size(); i++) {
            var playerData = gameData.playerDataArrayList.get(i);
            var newPlayer = new Player(i, game, false);
            newPlayer.setWood(playerData.wood);
            newPlayer.setGold(playerData.gold);
            newPlayer.setStone(playerData.stone);

            var idCountersHolder = newPlayer.getIdCountersHolder();
            idCountersHolder.towerIDCounter = playerData.towerIDCounter;
            idCountersHolder.workerIDCounter = playerData.workerIDCounter;
            idCountersHolder.archerIDCounter = playerData.archerIDCounter;
            idCountersHolder.swordmanIDCounter = playerData.swordmanIDCounter;
            idCountersHolder.spearmanIDCounter = playerData.spearmanIDCounter;
            idCountersHolder.cavalryIDCounter = playerData.cavalryIDCounter;
            idCountersHolder.catapultIDCounter = playerData.catapultIDCounter;

            for (var item : playerData.itemDataArrayList) {
                switch (item.itemType) {
                    case "MainBuilding" -> {
                        var mainBuilding = new MainBuilding(
                                newPlayer,
                                item.x,
                                item.y,
                                item.lifePoints
                        );
                        newPlayer.setMainBuilding(mainBuilding);
                    }
                    case "University" -> {
                        var university = new University(
                                newPlayer,
                                item.x,
                                item.y,
                                item.lifePoints
                        );
                        university.setInfantryTrainingCount(playerData.infantryTrainingCount);
                        university.setCavalryTrainingCount(playerData.cavalryTrainingCount);
                        university.setCatapultTrainingCount(playerData.catapultTrainingCount);
                        newPlayer.setUniversity(university);
                    }
                    case "Tower" -> {
                        var tower = new Tower(
                                newPlayer,
                                item.x,
                                item.y,
                                item.lifePoints
                        );
                        newPlayer.addTower(tower, false);
                        tower.setItemID(item.itemID);
                    }
                    case "Worker" -> {
                        var worker = new Worker(
                                newPlayer,
                                item.x,
                                item.y,
                                item.lifePoints
                        );
                        newPlayer.addWorker(worker, false);
                        worker.setItemID(item.itemID);
                    }
                    case "Archer" -> {
                        var archer = new Archer(
                                newPlayer,
                                item.x,
                                item.y,
                                item.lifePoints
                        );
                        newPlayer.addSoldier(archer, false);
                        archer.setItemID(item.itemID);
                    }
                    case "Swordman" -> {
                        var swordman = new Swordman(
                                newPlayer,
                                item.x,
                                item.y,
                                item.lifePoints
                        );
                        newPlayer.addSoldier(swordman, false);
                        swordman.setItemID(item.itemID);
                    }
                    case "Spearman" -> {
                        var spearman = new Spearman(
                                newPlayer,
                                item.x,
                                item.y,
                                item.lifePoints
                        );
                        newPlayer.addSoldier(spearman, false);
                        spearman.setItemID(item.itemID);
                    }
                    case "Cavalry" -> {
                        var cavalry = new Cavalry(
                                newPlayer,
                                item.x,
                                item.y,
                                item.lifePoints
                        );
                        newPlayer.addSoldier(cavalry, false);
                        cavalry.setItemID(item.itemID);
                    }
                    case "Catapult" -> {
                        var catapult = new Catapult(
                                newPlayer,
                                item.x,
                                item.y,
                                item.lifePoints
                        );
                        newPlayer.addSoldier(catapult, false);
                        catapult.setItemID(item.itemID);
                    }
                }
            }

            game.addPlayer(newPlayer);
        }

    }


    public static void saveGameDataToBinaryFile(GameData gameData, String fileName) {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(fileName))) {
            objectOut.writeObject(gameData);
            System.out.println("Game data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving game data: " + e.getMessage());
        }
    }

    public static GameData loadGameDataFromBinaryFile(String fileName) {
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(fileName))) {
            GameData gameData = (GameData) objectIn.readObject();
            System.out.println("Game data loaded successfully.");
            return gameData;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading game data: " + e.getMessage());
            return null;
        }
    }


    public static void saveGameDataToTextFile(GameData gameData, String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            // Write isGameOver
            writer.println("isGameOver: " + gameData.isGameOver);
            writer.println();

            // Write player data
            int playerCount = gameData.playerDataArrayList.size();
            writer.println("playerCount: " + playerCount);
            writer.println();

            for (int i = 0; i < playerCount; i++) {
                writer.println("----------------------------------------");
                PlayerData playerData = gameData.playerDataArrayList.get(i);

                // Write player info
                writer.println("Player " + (i + 1));
                writer.println("playerID: " + playerData.playerID);
                writer.println("wood: " + playerData.wood);
                writer.println("gold: " + playerData.gold);
                writer.println("stone: " + playerData.stone);
                writer.println("towerIDCounter: " + playerData.towerIDCounter);
                writer.println("workerIDCounter: " + playerData.workerIDCounter);
                writer.println("archerIDCounter: " + playerData.archerIDCounter);
                writer.println("swordmanIDCounter: " + playerData.swordmanIDCounter);
                writer.println("spearmanIDCounter: " + playerData.spearmanIDCounter);
                writer.println("cavalryIDCounter: " + playerData.cavalryIDCounter);
                writer.println("catapultIDCounter: " + playerData.catapultIDCounter);
                writer.println("infantryTrainingCount: " + playerData.infantryTrainingCount);
                writer.println("cavalryTrainingCount: " + playerData.cavalryTrainingCount);
                writer.println("catapultTrainingCount: " + playerData.catapultTrainingCount);
                writer.println();

                // Write item data
                int itemCount = playerData.itemDataArrayList.size();
                writer.println("itemCount: " + itemCount);
                writer.println();

                for (int j = 0; j < itemCount; j++) {
                    ItemData itemData = playerData.itemDataArrayList.get(j);

                    writer.println("Item " + (j + 1));
                    writer.println("itemType: " + itemData.itemType);
                    writer.println("itemID: " + itemData.itemID);
                    writer.println("x: " + itemData.x);
                    writer.println("y: " + itemData.y);
                    writer.println("lifePoints: " + itemData.lifePoints);
                    writer.println();
                }
            }

            System.out.println("Game data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving game data: " + e.getMessage());
        }
    }

    public static GameData loadGameDataFromTextFile(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            GameData gameData = new GameData();

            // Read isGameOver
            String isGameOverLine = scanner.nextLine();
            String[] isGameOverParts = isGameOverLine.split(": ");
            gameData.isGameOver = Boolean.parseBoolean(isGameOverParts[1]);
            scanner.nextLine(); // Consume empty line

            // Read player data
            String playerCountLine = scanner.nextLine();
            String[] playerCountParts = playerCountLine.split(": ");
            int playerCount = Integer.parseInt(playerCountParts[1]);
            scanner.nextLine(); // Consume empty line

            for (int i = 0; i < playerCount; i++) {
                scanner.nextLine();
                PlayerData playerData = new PlayerData();

                // Read player info
                scanner.nextLine(); // item header line
                playerData.playerID = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                playerData.wood = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                playerData.gold = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                playerData.stone = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                playerData.towerIDCounter = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                playerData.workerIDCounter = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                playerData.archerIDCounter = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                playerData.swordmanIDCounter = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                playerData.spearmanIDCounter = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                playerData.cavalryIDCounter = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                playerData.catapultIDCounter = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                playerData.infantryTrainingCount = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                playerData.cavalryTrainingCount = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                playerData.catapultTrainingCount = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                scanner.nextLine(); // Consume empty line

                // Read item data
                String itemCountLine = scanner.nextLine();
                String[] itemCountParts = itemCountLine.split(": ");
                int itemCount = Integer.parseInt(itemCountParts[1]);
                scanner.nextLine(); // Consume empty line

                for (int j = 0; j < itemCount; j++) {
                    ItemData itemData = new ItemData();

                    scanner.nextLine(); // item header line
                    itemData.itemType = scanner.nextLine().split(": ")[1];
                    itemData.itemID = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                    itemData.x = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                    itemData.y = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                    itemData.lifePoints = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                    scanner.nextLine(); // Consume empty line

                    playerData.itemDataArrayList.add(itemData);
                }

                gameData.playerDataArrayList.add(playerData);
            }

            System.out.println("Game data loaded successfully.");
            return gameData;
        } catch (IOException e) {
            System.out.println("Error loading game data: " + e.getMessage());
            return null;
        }
    }

}

class GameData implements Serializable {
    public boolean isGameOver;
    public ArrayList<PlayerData> playerDataArrayList = new ArrayList<>();
}

class PlayerData implements Serializable {
    public int playerID;
    public int wood;
    public int gold;
    public int stone;
    public ArrayList<ItemData> itemDataArrayList = new ArrayList<>();

    public int towerIDCounter;
    public int workerIDCounter;
    public int archerIDCounter;
    public int swordmanIDCounter;
    public int spearmanIDCounter;
    public int cavalryIDCounter;
    public int catapultIDCounter;

    public int infantryTrainingCount;
    public int cavalryTrainingCount;
    public int catapultTrainingCount;

    public PlayerData() {
        this.playerID = 0;
        this.wood = 0;
        this.gold = 0;
        this.stone = 0;
        this.towerIDCounter = 0;
        this.workerIDCounter = 0;
        this.archerIDCounter = 0;
        this.swordmanIDCounter = 0;
        this.spearmanIDCounter = 0;
        this.cavalryIDCounter = 0;
        this.catapultIDCounter = 0;
    }

    public PlayerData(int playerID, int wood, int gold, int stone,
                      int towerIDCounter, int workerIDCounter,
                      int archerIDCounter, int swordmanIDCounter, int spearmanIDCounter,
                      int cavalryIDCounter, int catapultIDCounter) {
        this.playerID = playerID;
        this.wood = wood;
        this.gold = gold;
        this.stone = stone;
        this.towerIDCounter = towerIDCounter;
        this.workerIDCounter = workerIDCounter;
        this.archerIDCounter = archerIDCounter;
        this.swordmanIDCounter = swordmanIDCounter;
        this.spearmanIDCounter = spearmanIDCounter;
        this.cavalryIDCounter = cavalryIDCounter;
        this.catapultIDCounter = catapultIDCounter;
    }

}

class ItemData implements Serializable {
    public String itemType;
    public int itemID;
    public int x;
    public int y;
    public int lifePoints;

    public ItemData() {
        this.itemType = null;
        this.itemID = 0;
        this.x = 0;
        this.y = 0;
        this.lifePoints = 0;
    }

    public ItemData(String itemType, int itemID, int x, int y, int lifePoints) {
        this.itemType = itemType;
        this.itemID = itemID;
        this.x = x;
        this.y = y;
        this.lifePoints = lifePoints;
    }
}
