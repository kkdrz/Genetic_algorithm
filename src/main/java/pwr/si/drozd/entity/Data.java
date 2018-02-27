package pwr.si.drozd.entity;

import lombok.Getter;

@Getter
public class Data {

    private int instances;
    private int[][] distances;
    private int[][] flows;

    public Data(int instances, int[][] distances, int[][] flows) {
        this.instances = instances;
        this.distances = distances;
        this.flows = flows;
    }

}

