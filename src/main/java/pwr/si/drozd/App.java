package pwr.si.drozd;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import pwr.si.drozd.algorithm.RandomAlgorithm;
import pwr.si.drozd.entity.Data;
import pwr.si.drozd.algorithm.GeneticAlgorithm;
import pwr.si.drozd.entity.Individual;
import pwr.si.drozd.tools.DataReader;
import java.io.FileNotFoundException;


public class App extends Application {

    public static int POPULATION_SIZE = 1000;
    public static int GENERATIONS = 200;
    public static double MUTATION_RATE = 0.9;
    public static double CROSSING_RATE = 0.01;
    public static long RANDOM_DURATION = 5000;

    static XYChart.Series randomSeries = new XYChart.Series();
    static XYChart.Series bestSeries = new XYChart.Series();
    static XYChart.Series bestPossibleSeries = new XYChart.Series();
    static XYChart.Series avgSeries= new XYChart.Series();
    static XYChart.Series worstSeries= new XYChart.Series();


    public static void main(String[] args) throws FileNotFoundException {
        DataReader dataReader = new DataReader();
        Data data = dataReader.readData("had14.txt");
        GeneticAlgorithm GA = new GeneticAlgorithm(data);

        GA.initPopulation();
        GA.evaluate();
        int currentGeneration = 0;
        while(currentGeneration++ < GENERATIONS && GA.getBestIndividual().getCost() > data.getBestCost()) {
            GA.selection();
            GA.reproduction();
            GA.mutation();
            GA.evaluate();
            populateDataToChart(data, GA, currentGeneration);
            System.out.println(GA.getBestIndividual().calculateCost(data));
        }

        RandomAlgorithm RA = new RandomAlgorithm(data);
        Individual randomBest = RA.findBestIndividual(RANDOM_DURATION);
        for (int i = 0; i < GENERATIONS; i++) {

        }

        populateConstValuesToChart(data, randomBest);

        System.out.println("Random best: " + randomBest.calculateCost(data));
        System.out.println(GA.getBestIndividual().calculateCost(data));
        launch(args);
    }

    private static void populateConstValuesToChart(Data data, Individual randomBest) {
        bestPossibleSeries.getData().add(new XYChart.Data(0, data.getBestCost()));
        bestPossibleSeries.getData().add(new XYChart.Data(GENERATIONS, data.getBestCost()));

        randomSeries.getData().add(new XYChart.Data(0, randomBest.calculateCost(data)));
        randomSeries.getData().add(new XYChart.Data(GENERATIONS, randomBest.calculateCost(data)));
    }

    private static void populateDataToChart(Data data, GeneticAlgorithm GA, int currentGeneration) {
        bestSeries.getData().add(new XYChart.Data(currentGeneration, GA.getBestIndividual().calculateCost(data)));
        worstSeries.getData().add(new XYChart.Data(currentGeneration, GA.getWorstIndividual().calculateCost(data)));
        avgSeries.getData().add(new XYChart.Data(currentGeneration, GA.getAverageCost()));

    }


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Genetic algorithm");
        bestSeries.setName("Best");
        avgSeries.setName("Average");
        worstSeries.setName("Worst");
        randomSeries.setName("RandomAlgorithm");
        bestPossibleSeries.setName("Best Possible");

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(((XYChart.Data<Number, Number>)bestPossibleSeries.getData().get(0)).getYValue().doubleValue() - 300.0);
        yAxis.setUpperBound(((XYChart.Data<Number, Number>)worstSeries.getData().get(0)).getYValue().doubleValue() + 300.0);

        xAxis.setLabel("Generation");
        final LineChart<Number,Number> lineChart =
                new LineChart<>(xAxis,yAxis);

        lineChart.setTitle("Genetic Algorithm");

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().addAll(bestSeries, worstSeries, avgSeries, randomSeries, bestPossibleSeries);

        stage.setScene(scene);
        stage.show();
    }
}
