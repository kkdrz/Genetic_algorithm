package pwr.si.drozd.algorithm;

import lombok.Getter;
import pwr.si.drozd.entity.Data;
import pwr.si.drozd.entity.Individual;

@Getter
public class RandomAlgorithm {

    private Data data;

    public RandomAlgorithm(Data data) {
        this.data = data;
    }

    public Individual findBestIndividual(long duration) {
        Individual bestIndividual = new Individual(data.getUnitsNum());
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() < startTime + duration) {
            Individual randomIndividual = new Individual(data.getUnitsNum());
            if (randomIndividual.calculateCost(data) < bestIndividual.calculateCost(data)) {
                bestIndividual = randomIndividual;
            }
        }
        return bestIndividual;
    }
}
