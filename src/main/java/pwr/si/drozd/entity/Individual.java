package pwr.si.drozd.entity;


import pwr.si.drozd.tools.Utils;

import java.util.Arrays;
import java.util.Random;

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

    public Individual crossover(Individual partner) {
        return null;
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
