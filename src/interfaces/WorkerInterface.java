package interfaces;

import entities.buildings.abstracts.Building;
import exceptions.AgeOfEmpiresException;

public interface WorkerInterface {

    void build(Building b) throws AgeOfEmpiresException;

}
