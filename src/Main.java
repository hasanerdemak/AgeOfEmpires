import concretes.Game;
import concretes.Player;
import entities.*;
import exceptions.AgeOfEmpiresException;

import java.util.Scanner;

public class Main {
    static Scanner scanner;
    static Game game;

    public static void main(String[] args) throws AgeOfEmpiresException {
        //startGame();

        Game g = new Game(2); // iki kişilik oyun başlatır
        g.getPlayer(0).getWorker(0).move(1, 2); // İlk oyuncu işçisini hareket ettirir
        g.getPlayer(1).purchase(new Cavalry()); // İkinci oyuncu bir atlı alır
        // İlk oyuncu 1,2 konumuna bir üniversite kurar
        g.getPlayer(0).getWorker(0).build(new University());
        // İkinci oyuncu ilk ürettiği askeri 5,5 konumuna taşır
        g.getPlayer(1).getSoldier(0).move(95, 46);
        // ekranın o anki basılır, Ayrıntılar için MapInterface’e bakın
        g.getMap().print();
        g.save_binary("binary_file"); // oyunun o hali binary olarak kaydedilmiştir.
        g.save_text("text_file"); // oyunun o hali text olarak kaydedilmiştir.

        // oyunun o hali g1 nesnesindedir. Dosyanın binrary olduğunu Boolean değer ile gösterilir
        Game g1 = new Game("binary_file", true);
        // oyunun o hali g2 nesnesindedir. Dosyanın binrary olmadığı Boolean değer ile gösterilir
        Game g2 = new Game("text_file", false);
        // ilk oyuncu universiteden piyade eğitimi yaptırır
        g2.getPlayer(0).getUniversity().trainInfantry();
        // ilk oyuncu iki kez üst üste oynadığı için AgeOfEmpiresException atılmalı
        //g2.getPlayer(0).getUniversity().trainInfantry();
    }

    public static void startGame() throws AgeOfEmpiresException {
        scanner = new Scanner(System.in);

        while (game == null) {
            System.out.print("""
                    1- Start New Game
                    2- Load Game from Binary File
                    3- Load Game from Text File
                    4- Exit
                    Choice:\s""");
            int operation = scanner.nextInt();
            if (operation < 1 || operation > 4) {
                System.out.println("Invalid choice");
                continue;
            }
            switch (operation) {
                case 1 -> {
                    System.out.print("Enter player count: ");
                    int playerCount = scanner.nextInt();
                    game = new Game(playerCount);
                }
                case 2 -> {
                    System.out.print("Enter binary file name: ");
                    String filename = scanner.next();
                    game = new Game(filename, true);
                }
                case 3 -> {
                    System.out.print("Enter text file name: ");
                    String filename = scanner.next();
                    game = new Game(filename, false);
                }
                case 4 -> {
                    return;
                }
            }
        }


        while (!game.isGameOver) {
            var currentPlayer = game.getPlayer(game.getPlayerTurnCounter());
            boolean doesMakeMove = false;
            while (!doesMakeMove) {
                System.out.println("The turn to play is on " + currentPlayer);
                System.out.print("""
                        1- Purchase Human
                        2- Get Soldier
                        3- Get Worker
                        4- Get Tower
                        5- Get University
                        6- Print Main Building Info
                        7- Print Player Info
                        8- Print Map
                        9- Pass
                        10- Save Game
                        Choose move:\s""");
                int operation = scanner.nextInt();
                if (operation < 1 || operation > 10) {
                    System.out.println("Invalid choice");
                    continue;
                }
                switch (operation) {
                    case 1 -> doesMakeMove = purchaseActions(currentPlayer);
                    case 2 -> doesMakeMove = getSoldierAndActions(currentPlayer);
                    case 3 -> doesMakeMove = getWorkerAndActions(currentPlayer);
                    case 4 -> doesMakeMove = getTowerAndActions(currentPlayer);
                    case 5 -> doesMakeMove = trainSoldierInUniversity(currentPlayer);
                    case 6 -> currentPlayer.getMainBuilding().print_message();
                    case 7 -> currentPlayer.printPlayerInfo();
                    case 8 -> game.getMap().print();
                    case 9 -> {
                        doesMakeMove = true;
                        currentPlayer.pass();
                    }
                    case 10 -> saveGameActions();
                }

                System.out.println("----------------------------------------");
            }

        }
    }

    public static boolean purchaseActions(Player player) throws AgeOfEmpiresException {
        System.out.print("""
                1- Worker
                2- Archer
                3- Swordman
                4- Spearman
                5- Cavalry
                6- Catapult
                7- Cancel
                Choose human to purchase:\s""");
        int operation = scanner.nextInt();
        if (operation < 1 || operation > 7) {
            System.out.println("Invalid choice");
            return purchaseActions(player);
        }
        switch (operation) {
            case 1 -> player.purchase(new Worker());
            case 2 -> player.purchase(new Archer());
            case 3 -> player.purchase(new Swordman());
            case 4 -> player.purchase(new Spearman());
            case 5 -> player.purchase(new Cavalry());
            case 6 -> player.purchase(new Catapult());
        }

        return operation != 7;
    }

    public static boolean getSoldierAndActions(Player player) throws AgeOfEmpiresException {
        System.out.println("-----------------------------------");
        System.out.println("Current Soldiers owned by " + player);
        if (player.getSoldierCount() != 0) {
            for (int i = 0; i < player.getSoldierCount(); i++) {
                System.out.print(i + ": ");
                player.getSoldier(i).print_message();
            }
        } else {
            System.out.println("Player does not have any Soldier");
            return false;
        }

        System.out.println("-----------------------------------");

        System.out.print("Get soldier: ");
        int soldierIndex = scanner.nextInt();
        Soldier soldier = player.getSoldier(soldierIndex);

        int operation = 3;
        while (operation == 3) {
            System.out.print("""
                    1- Move
                    2- Attack
                    3- Print info
                    4- Cancel
                    Choose soldier action:\s""");
            operation = scanner.nextInt();
            if (operation < 1 || operation > 4) {
                System.out.println("Invalid choice");
                continue;
            }

            int x = 0, y = 0;
            if (operation == 1 || operation == 2) {
                System.out.print("Enter x coordinate: ");
                x = scanner.nextInt();

                System.out.print("Enter y coordinate: ");
                y = scanner.nextInt();
            }

            switch (operation) {
                case 1 -> soldier.move(x, y);
                case 2 -> soldier.attack(x, y);
                case 3 -> soldier.print_message();
            }
        }

        return operation != 4;
    }

    public static boolean getWorkerAndActions(Player player) throws AgeOfEmpiresException {
        System.out.println("-----------------------------------");
        System.out.println("Current Workers owned by " + player);
        if (player.getWorkerCount() != 0) {
            for (int i = 0; i < player.getWorkerCount(); i++) {
                System.out.print(i + ": ");
                player.getWorker(i).print_message();
            }
        } else {
            System.out.println("Player does not have any Worker");
            return false;
        }
        System.out.println("-----------------------------------");
        System.out.print("Get worker: ");
        int workerIndex = scanner.nextInt();
        Worker worker = player.getWorker(workerIndex);

        int operation = 4;
        while (operation == 4) {
            System.out.print("""
                    1- Move
                    2- Attack
                    3- Build
                    4- Print info
                    5- Cancel
                    Choose worker action:\s""");
            operation = scanner.nextInt();
            if (operation < 1 || operation > 5) {
                System.out.println("Invalid choice");
                continue;
            }

            int x = 0, y = 0;
            if (operation == 1 || operation == 2) {
                System.out.print("Enter x coordinate: ");
                x = scanner.nextInt();
                //System.out.println();
                System.out.print("Enter y coordinate: ");
                y = scanner.nextInt();
            }

            switch (operation) {
                case 1 -> worker.move(x, y);
                case 2 -> worker.attack(x, y);
                case 3 -> {
                    System.out.print("""
                            1- University
                            2- Tower
                            Choose building:\s""");
                    int buildingIndex = scanner.nextInt();
                    Building b = (buildingIndex == 1) ? new University() : new Tower();
                    worker.build(b);
                }
                case 4 -> worker.print_message();
            }
        }

        return operation != 5;
    }

    public static boolean getTowerAndActions(Player player) throws AgeOfEmpiresException {
        System.out.println("-----------------------------------");
        System.out.println("Current Towers owned by " + player);
        if (player.getTowerCount() != 0) {
            for (int i = 0; i < player.getTowerCount(); i++) {
                System.out.print(i + ": ");
                player.getTower(i).print_message();
            }
            System.out.println("-----------------------------------");
        } else {
            System.out.println("Player does not have any Tower");
            return false;
        }

        System.out.print("Get tower: ");
        int towerIndex = scanner.nextInt();
        Tower tower = player.getTower(towerIndex);

        int operation = 2;
        while (operation == 2) {
            System.out.print("""
                    1- Attack
                    2- Print info
                    3- Cancel
                    Choose tower action:\s""");
            operation = scanner.nextInt();
            if (operation < 1 || operation > 3) {
                System.out.println("Invalid choice");
                continue;
            }

            int x = 0, y = 0;
            if (operation == 1) {
                System.out.print("Enter x coordinate:");
                x = scanner.nextInt();
                System.out.print("Enter y coordinate:");
                y = scanner.nextInt();
            }

            switch (operation) {
                case 1 -> tower.attack(x, y);
                case 2 -> tower.print_message();
            }
        }

        return operation != 3;
    }

    public static boolean trainSoldierInUniversity(Player player) throws AgeOfEmpiresException {
        University university = player.getUniversity();
        if (university == null) {
            System.out.println("Player does not have University");
            return false;
        }
        int operation = 4;
        while (operation == 4) {
            System.out.print("""
                    1- Infantry (Archer, Swordman, Spearman)
                    2- Cavalry
                    3- Catapult
                    4- Print info
                    5- Cancel
                    Choose soldier type to train:\s""");
            operation = scanner.nextInt();
            if (operation < 1 || operation > 5) {
                System.out.println("Invalid choice");
                continue;
            }

            switch (operation) {
                case 1 -> university.trainInfantry();
                case 2 -> university.trainCavalry();
                case 3 -> university.trainCatapult();
                case 4 -> university.print_message();
            }
        }

        return operation != 5;
    }

    public static boolean saveGameActions() throws AgeOfEmpiresException {
        while (true) {
            System.out.print("""
                    1- Save Game to a Binary File
                    2- Save Game to a Text File
                    3- Cancel
                    Choose soldier type to train:\s""");
            int operation = scanner.nextInt();
            if (operation < 1 || operation > 3) {
                System.out.println("Invalid choice");
                continue;
            }

            switch (operation) {
                case 1 -> {
                    System.out.print("Enter binary file name: ");
                    String filename = scanner.next();
                    game.save_binary(filename);
                    return true;
                }
                case 2 -> {
                    System.out.print("Enter text file name: ");
                    String filename = scanner.next();
                    game.save_text(filename);
                    return true;
                }
                case 3 -> {
                    return false;
                }
            }
        }
    }
}