package pwr.si.drozd;

import pwr.si.drozd.entity.Data;
import pwr.si.drozd.entity.GeneticAlgorithm;
import pwr.si.drozd.tools.DataReader;
import java.io.FileNotFoundException;


public class App {

    public static void main(String[] args) throws FileNotFoundException {
        DataReader dataReader = new DataReader();
        Data data = dataReader.readData("had20.txt");
        GeneticAlgorithm GA = new GeneticAlgorithm(data);

        GA.initPopulation();

        for(int i = 0; i < GA.getPopulation().length-1;i++) {
            System.out.println(GA.calculateCost(GA.getPopulation()[i]));
        }

    }

}
