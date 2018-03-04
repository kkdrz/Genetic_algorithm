package pwr.si.drozd.entity;

import lombok.Getter;

@Getter
public class Data {

    private int unitsNum;
    private int bestCost;
    private int[][] distances;
    private int[][] flows;

    public Data(int unitsNum, int[][] distances, int[][] flows, int bestCost) {
        this.unitsNum = unitsNum;
        this.distances = distances;
        this.flows = flows;
        this.bestCost = bestCost;
    }

    public int getBestCost() {
        return bestCost;
    }
}

