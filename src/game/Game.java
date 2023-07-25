package game;

import entities.Resources;
import exceptions.AgeOfEmpiresException;
import interfaces.GameInterface;
import utils.ResourcesUtils;

import java.util.ArrayList;

public class Game implements GameInterface {
    private final ArrayList<Player> players;
    private final Map map;
    private boolean gameOver = false;
    private int playerTurnCounter = 0;

    public Game(int playerCount) throws AgeOfEmpiresException {

        if (playerCount < 2 || playerCount > 4) {
            throw new AgeOfEmpiresException("Invalid player count. Game could not be initialized. There can be 2-4 players");
        }

        map = new Map();

        players = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            players.add(new Player(i, this, true));
        }


    }

    public Game(String filename, boolean isBinary) throws AgeOfEmpiresException {
        map = new Map();

        players = new ArrayList<>();

        GameData gameData;
        if (isBinary) {
            gameData = GameSaveManager.loadGameDataFromBinaryFile(filename);
        } else {
            gameData = GameSaveManager.loadGameDataFromTextFile(filename);
        }
        assert gameData != null;
        GameSaveManager.extractGameDataToGame(gameData, this);

    }

    public static boolean checkPlayerTurn(Player player) throws AgeOfEmpiresException {
        var game = player.getCurrentGame();
        return game.getPlayer(game.getPlayerTurnCounter()).equals(player);
    }

    public int getPlayerCount() {
        return players.size();
    }

    @Override
    public Player getPlayer(int x) throws AgeOfEmpiresException {

        if (x >= 0 && x < getPlayerCount()) {
            return players.get(x);
        } else {
            throw new AgeOfEmpiresException("There is no player with index " + x + ". Index can be 0-" + (getPlayerCount() - 1));
        }

    }

    public void removePlayer(Player player) {
        players.remove(player);
        player.getCurrentGame().getMap().getMapItems().removeAll(player.getPlayerItems());
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    @Override
    public Map getMap() {
        return map;
    }

    @Override
    public void save_text(String filename) throws AgeOfEmpiresException {
        var gameData = GameSaveManager.createGameData(this);
        GameSaveManager.saveGameDataToTextFile(gameData, filename);
    }

    @Override
    public void save_binary(String filename) throws AgeOfEmpiresException {
        var gameData = GameSaveManager.createGameData(this);
        GameSaveManager.saveGameDataToBinaryFile(gameData, filename);
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getPlayerTurnCounter() {
        return playerTurnCounter;
    }

    public void setPlayerTurnCounter(int playerTurnCounter) {
        this.playerTurnCounter = playerTurnCounter;
    }

    public void increasePlayerTurnCounter() {
        playerTurnCounter = (playerTurnCounter + 1) % getPlayerCount();
        // if the tour is over give resources to the players
        if (playerTurnCounter == 0) {
            for (var player : players) {
                ResourcesUtils.increaseResources(player, new Resources(10, 2, 5));
            }
        }

        // Check game status after a move
        if (players.size() == 1) { // If one player is left, (s)he has won the game
            gameOver = true;
            //System.out.println("GAME OVER, " + players.get(0) + " won the game");
            System.out.println("Oyun bitti");
        } else if (players.size() == 0) { // If there are no players left, no one has won the game.
            gameOver = true;
            //System.out.println("GAME OVER, no one won the game");
        }
    }
}
