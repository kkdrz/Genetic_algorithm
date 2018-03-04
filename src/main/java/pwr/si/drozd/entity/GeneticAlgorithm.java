package pwr.si.drozd.entity;

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

    public int calculateCost(Individual individual) {
        int cost = 0;

        for (int i = 0; i < unitsNum; i++) {
            for (int j = 0; j < unitsNum; j++) {
                if (flows[i][j] > 0) {
                    cost += flows[i][j] * distances[individual.getIndex(i)][individual.getIndex(j)];
                }
            }
        }
        return cost;
    }

}
