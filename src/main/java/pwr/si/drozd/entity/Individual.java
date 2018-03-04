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
        Random random = new Random();
        for (int i = 0; i < units.length; i++) {
            units[i] = i;
        }
        Utils.shuffleArray(units);
    }

    public int getIndex(int unitNumber) {
        for (int i = 0; i < units.length; i++) {
            if (units[i] == unitNumber) return i;
        }
        return -1;
    }
}
