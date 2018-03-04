package pwr.si.drozd.algorithm;

import pwr.si.drozd.entity.Data;
import pwr.si.drozd.entity.Individual;

@lombok.Data
public class GeneticAlgorithm {
    private static int POP_SIZE = 100;

    private Data data;

    private Individual[] population;
    private Individual bestIndividual;

    public GeneticAlgorithm(Data data) {
        this.data = data;
    }

    public void initPopulation() {
        population = new Individual[POP_SIZE];
        for (int i = 0; i < population.length; i++) {
            population[i] = new Individual(data.getUnitsNum());
        }
    }

    private Individual getBestIndividualOfCurrentPopulation() {
        Individual best = population[0];
        for (Individual individual : population) {
            if (best.calculateCost(data) > individual.calculateCost(data)) {
                best = new Individual(individual);
            }
        }
        return best;
    }
}
