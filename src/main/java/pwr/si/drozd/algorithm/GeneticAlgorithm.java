package pwr.si.drozd.algorithm;

import pwr.si.drozd.App;
import pwr.si.drozd.entity.Data;
import pwr.si.drozd.entity.Individual;

import java.util.ArrayList;
import java.util.Random;

@lombok.Data
public class GeneticAlgorithm {

    private Data data;

    private Individual[] population;
    private ArrayList<Individual> selectionPool;
    private Individual bestIndividual;

    public GeneticAlgorithm(Data data) {
        this.data = data;
    }

    public void initPopulation() {
        population = new Individual[App.POPULATION_SIZE];
        for (int i = 0; i < population.length; i++) {
            population[i] = new Individual(data.getUnitsNum());
        }
    }

    public Individual evaluate() {
        if (bestIndividual == null) bestIndividual = population[0];

        for (Individual individual : population) {
            if (bestIndividual.calculateCost(data) > individual.calculateCost(data)) {
                bestIndividual = new Individual(individual);
            }
        }
        return bestIndividual;
    }

    public void selection() {
        selectionPool = new ArrayList<>();
        double min = 1.0 / getWorstIndividual().calculateCost(data);
        double max = 1.0 / bestIndividual.getCost();
        for (int i = 0; i < population.length; i++) {
            double current = (1.0 / population[i].calculateCost(data));
            double normalizedCost = (current - min) / (max - min);
            int n = (int) (normalizedCost * 100.0);
            for (int j = 0; j < n; j++) {
                selectionPool.add(population[i]);
            }
        }
    }

    public void reproduction() {
        Random random = new Random();
        Individual[] newPopulation = new Individual[population.length];

        for (int i = 0; i < population.length; i++) {
            Individual parent1 = selectionPool.get(random.nextInt(selectionPool.size() - 1));
            Individual parent2 = selectionPool.get(random.nextInt(selectionPool.size() - 1));

            Individual child = parent1.crossover(parent2);
            newPopulation[i] = child;
        }

        population = newPopulation;
    }

    public void mutation() {
        for (int i = 0; i < population.length; i++) {
            population[i].mutate();
        }
    }

    public Individual getWorstIndividual() {
        Individual worst = population[0];
        for (Individual individual : population) {
            if (worst.calculateCost(data) < individual.calculateCost(data)) {
                worst = new Individual(individual);
            }
        }
        return worst;
    }

    public int getAverageCost() {
        int avg = 0;
        for (Individual i : population) {
            avg += i.calculateCost(data);
        }
        return avg / population.length;
    }
}
