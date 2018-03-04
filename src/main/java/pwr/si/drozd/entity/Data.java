package pwr.si.drozd.entity;

import lombok.Getter;

@Getter
public class Data {

    private int unitsNum;
    private int[][] distances;
    private int[][] flows;

    public Data(int unitsNum, int[][] distances, int[][] flows) {
        this.unitsNum = unitsNum;
        this.distances = distances;
        this.flows = flows;
    }

}

