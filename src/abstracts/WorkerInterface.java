package abstracts;

import entities.Building;
import exceptions.AgeOfEmpiresException;

public interface WorkerInterface {

    void build(Building b) throws AgeOfEmpiresException;

}
