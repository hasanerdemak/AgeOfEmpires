package interfaces;

import entities.Item;
import entities.buildings.concretes.Tower;
import entities.buildings.concretes.University;
import entities.humans.abstracts.Soldier;
import entities.humans.concretes.Worker;
import exceptions.AgeOfEmpiresException;

public interface PlayerInterface {

    int getWood();

    int getGold();

    int getStone();


    int getTowerCount();

    int getWorkerCount();

    int getSoldierCount();


    void pass();


    Soldier getSoldier(int index);

    Worker getWorker(int index);

    void purchase(Item item) throws AgeOfEmpiresException;

    University getUniversity();

    Tower getTower(int index);

}
