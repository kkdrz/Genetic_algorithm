package pwr.si.drozd;

import pwr.si.drozd.algorithm.RandomAlgorithm;
import pwr.si.drozd.entity.Data;
import pwr.si.drozd.algorithm.GeneticAlgorithm;
import pwr.si.drozd.tools.DataReader;
import java.io.FileNotFoundException;


public class App {

    public static void main(String[] args) throws FileNotFoundException {
        DataReader dataReader = new DataReader();
        Data data = dataReader.readData("had20.txt");
        GeneticAlgorithm GA = new GeneticAlgorithm(data);

        GA.initPopulation();


        System.out.println("Random: " + new RandomAlgorithm(data).findBestIndividual(60000).calculateCost(data.getUnitsNum(), data.getFlows(), data.getDistances()));

    }

}
