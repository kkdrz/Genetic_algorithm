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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class App extends Application {

    public static int POPULATION_SIZE = 100;
    public static int GENERATIONS = 20;
    public static double MUTATION_RATE = 0.1;
    public static double CROSSING_RATE = 0.01;
    public static long RANDOM_DURATION = 1000;

    public static int REPEATS = 10;

    static List<Integer> randomTries = new ArrayList<>(REPEATS);
    static List<Map<Integer, Integer>> bestTries = new ArrayList<>(REPEATS);
    static List<Map<Integer, Integer>> worstTries = new ArrayList<>(REPEATS);
    static List<Map<Integer, Integer>> avgTries = new ArrayList<>(REPEATS);

    static XYChart.Series randomSeries = new XYChart.Series();
    static XYChart.Series bestSeries = new XYChart.Series();
    static XYChart.Series bestPossibleSeries = new XYChart.Series();
    static XYChart.Series avgSeries = new XYChart.Series();
    static XYChart.Series worstSeries = new XYChart.Series();


    public static void main(String[] args) throws FileNotFoundException {
        DataReader dataReader = new DataReader();
        Data data = dataReader.readData("had14.txt");
        initTriesLists();

        for (int i = 0; i < REPEATS; i++) {
            GeneticAlgorithm GA = new GeneticAlgorithm(data);

            GA.initPopulation();
            GA.evaluate();
            int currentGeneration = -1;
            while (currentGeneration++ < GENERATIONS && GA.getBestIndividual().getCost() > data.getBestCost()) {
                GA.selection();
                GA.reproduction();
                GA.mutation();
                GA.evaluate();
                bestTries.get(i).put(currentGeneration, GA.getBestIndividual().calculateCost(data));
                worstTries.get(i).put(currentGeneration, GA.getWorstIndividual().calculateCost(data));
                avgTries.get(i).put(currentGeneration, GA.getAverageCost());
                System.out.println(GA.getBestIndividual().calculateCost(data));
            }

            RandomAlgorithm RA = new RandomAlgorithm(data);
            Individual randomBest = RA.findBestIndividual(RANDOM_DURATION);
            randomTries.add(randomBest.calculateCost(data));
        }

        populateChartData(data);

        launch(args);
    }

    private static void populateChartData(Data data) {
        bestSeries = calculateAvgOfRepeats(bestTries);
        worstSeries = calculateAvgOfRepeats(worstTries);
        avgSeries = calculateAvgOfRepeats(avgTries);
        randomSeries = calculateAvgOfRepeatsList(randomTries);
        populateConstValuesToChart(data);
    }

    private static void initTriesLists() {
        initTriesList(bestTries);
        initTriesList(worstTries);
        initTriesList(avgTries);
    }

    private static void initTriesList(List<Map<Integer, Integer>> tries) {
        for (int i = 0; i < REPEATS; i++) {
            tries.add(new HashMap<Integer, Integer>());
        }
    }

    private static XYChart.Series calculateAvgOfRepeatsList(List<Integer> tries) {
        XYChart.Series series = new XYChart.Series();
        int sum = 0;
        for (Integer i : tries) {
            sum += i;
        }
        int avg = sum / tries.size();
        series.getData().add(new XYChart.Data(0, avg));
        series.getData().add(new XYChart.Data(GENERATIONS, avg));
        return series;
    }

    private static XYChart.Series calculateAvgOfRepeats(List<Map<Integer, Integer>> tries) {
        XYChart.Series series = new XYChart.Series();
        for (int j = 0; j < GENERATIONS; j++) {
            int avgCost = 0;
            for (int i = 0; i < REPEATS; i++) {
                avgCost += tries.get(i).get(j);
            }
            series.getData().add(new XYChart.Data(j, avgCost / REPEATS));
        }
        return series;
    }

    private static void populateConstValuesToChart(Data data) {
        bestPossibleSeries.getData().add(new XYChart.Data(0, data.getBestCost()));
        bestPossibleSeries.getData().add(new XYChart.Data(GENERATIONS, data.getBestCost()));
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
        yAxis.setLowerBound(((XYChart.Data<Number, Number>) bestPossibleSeries.getData().get(0)).getYValue().doubleValue() - 300.0);
        yAxis.setUpperBound(((XYChart.Data<Number, Number>) worstSeries.getData().get(0)).getYValue().doubleValue() + 300.0);

        xAxis.setLabel("Generation");
        final LineChart<Number, Number> lineChart =
                new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Genetic Algorithm");

        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().addAll(bestSeries, worstSeries, avgSeries, randomSeries, bestPossibleSeries);

        stage.setScene(scene);
        stage.show();
    }
}
