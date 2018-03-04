package pwr.si.drozd;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import pwr.si.drozd.entity.Data;
import pwr.si.drozd.algorithm.GeneticAlgorithm;
import pwr.si.drozd.tools.DataReader;
import java.io.FileNotFoundException;


public class App extends Application {

    public static int POPULATION_SIZE = 100;
    public static int GENERATIONS = 100;
    public static double MUTATION_RATE = 0.2;
    public static double CROSSING_RATE = 0.01;

    static XYChart.Series bestSeries = new XYChart.Series();
    static XYChart.Series avgSeries= new XYChart.Series();
    static XYChart.Series worstSeries= new XYChart.Series();


    public static void main(String[] args) throws FileNotFoundException {
        bestSeries.setName("Best");
        avgSeries.setName("Average");
        worstSeries.setName("Worst");

        DataReader dataReader = new DataReader();
        Data data = dataReader.readData("had12.txt");
        GeneticAlgorithm GA = new GeneticAlgorithm(data);

        GA.initPopulation();
        GA.evaluate();
        int currentGeneration = 0;
        while(currentGeneration++ < GENERATIONS) {
            GA.selection();
            GA.reproduction();
            GA.mutation();
            GA.evaluate();
            populateDataToChart(data, GA, currentGeneration);

            System.out.println(GA.getBestIndividual().calculateCost(data));
        }

        System.out.println(GA.getBestIndividual().calculateCost(data));
        launch(args);
    }

    private static void populateDataToChart(Data data, GeneticAlgorithm GA, int currentGeneration) {
        bestSeries.getData().add(new XYChart.Data(currentGeneration, GA.getBestIndividual().calculateCost(data)));
        worstSeries.getData().add(new XYChart.Data(currentGeneration, GA.getWorstIndividual().calculateCost(data)));
        avgSeries.getData().add(new XYChart.Data(currentGeneration, GA.getAverageCost()));
    }


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Genetic algorithm");

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(1000.0);
        yAxis.setUpperBound(3000.0);

        xAxis.setLabel("Generation");
        final LineChart<Number,Number> lineChart =
                new LineChart<>(xAxis,yAxis);

        lineChart.setTitle("Genetic Algorithm");

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().addAll(bestSeries, worstSeries, avgSeries);

        stage.setScene(scene);
        stage.show();
    }
}
