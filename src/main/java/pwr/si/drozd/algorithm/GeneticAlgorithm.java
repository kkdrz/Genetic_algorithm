package pwr.si.drozd.algorithm;

import pwr.si.drozd.entity.Data;
import pwr.si.drozd.entity.Individual;

@lombok.Data
public class GeneticAlgorithm {
    private static int POP_SIZE = 100;

    private int unitsNum;
    private int[][] distances;
    private int[][] flows;

    public static Data DATA;

    private Individual[] population;

    public GeneticAlgorithm(Data data) {
        unitsNum = data.getUnitsNum();
        distances = data.getDistances();
        flows = data.getFlows();
    }

    public void initPopulation() {
        population = new Individual[POP_SIZE];
        for (int i = 0; i < population.length; i++) {
            population[i] = new Individual(unitsNum);
        }
    }

}
