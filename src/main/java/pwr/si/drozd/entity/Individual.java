package pwr.si.drozd.entity;


import pwr.si.drozd.tools.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@lombok.Data
public class Individual {

    private int[] units;

    public Individual(int unitsNum) {
        units = new int[unitsNum];
        randomUnits();
    }

    public Individual(int[] units) {
        this.units = Arrays.copyOf(units, units.length);
    }

    public Individual(Individual individual) {
        units = Arrays.copyOf(individual.getUnits(), individual.getUnits().length);
    }

    public Individual crossover(Individual partner) {
        int position = ThreadLocalRandom.current().nextInt(1, units.length - 1);
        Individual child = new Individual(this);
        ArrayList<Integer> childUnits = new ArrayList<>();

        for (int i = 0; i < child.getUnits().length; i++) {
            if (i < position && !childUnits.contains(units[i])) {
                childUnits.add(units[i]);
            } else if (!childUnits.contains(partner.getUnits()[i])) {
                childUnits.add(partner.getUnits()[i]);
            }
        }

        for (int i = 0; childUnits.size() < child.getUnits().length && i < child.getUnits().length; i++) {
            if (!childUnits.contains(i)) childUnits.add(i);
        }

        for (int i = 0; i < childUnits.size(); i++) {
            child.getUnits()[i] = childUnits.get(i);
        }

        return child;
    }

    public void mutate() {
        Random random = new Random();
        int pos1 = random.nextInt();
        int pos2 = random.nextInt();
        while (pos2 == pos1) pos2 = random.nextInt();

        int temp = units[pos1];
        units[pos1] = units[pos2];
        units[pos2] = temp;
    }

    private void randomUnits() {
        for (int i = 0; i < units.length; i++) {
            units[i] = i;
        }
        Utils.shuffleArray(units);
    }

    public int calculateCost(int unitsNum, int[][] flows, int[][] distances) {
        int cost = 0;

        for (int i = 0; i < unitsNum; i++) {
            for (int j = 0; j < unitsNum; j++) {
                if (flows[i][j] > 0) {
                    cost += flows[i][j] * distances[getLocationOf(i)][getLocationOf(j)];
                }
            }
        }
        return cost;
    }

    public int calculateCost(Data data) {
        return calculateCost(data.getUnitsNum(), data.getFlows(), data.getDistances());
    }

    public int getLocationOf(int unitNumber) {
        for (int i = 0; i < units.length; i++) {
            if (units[i] == unitNumber) return i;
        }
        return -1;
    }
}
