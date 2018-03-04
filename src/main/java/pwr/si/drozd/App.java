package pwr.si.drozd;

import pwr.si.drozd.algorithm.RandomAlgorithm;
import pwr.si.drozd.entity.Data;
import pwr.si.drozd.algorithm.GeneticAlgorithm;
import pwr.si.drozd.entity.Individual;
import pwr.si.drozd.tools.DataReader;
import java.io.FileNotFoundException;


public class App {

    public static void main(String[] args) throws FileNotFoundException {
        DataReader dataReader = new DataReader();
        Data data = dataReader.readData("had20.txt");
        GeneticAlgorithm GA = new GeneticAlgorithm(data);

        //GA.initPopulation();

       // System.out.println("Random: " + new RandomAlgorithm(data).findBestIndividual(60000).calculateCost(data.getUnitsNum(), data.getFlows(), data.getDistances()));

        int[] test = {0,2,1,4,3,6,5,8,7};
        Individual a = new Individual(test);

        int[] test2 = {1,4,3,0,1,8,5,7,6};
        Individual b = new Individual(test2);

        Individual c = a.crossover(b);
    }

}
