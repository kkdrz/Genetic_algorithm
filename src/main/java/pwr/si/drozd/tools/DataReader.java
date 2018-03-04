package pwr.si.drozd.tools;


import pwr.si.drozd.entity.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class DataReader {

    private static Scanner sc = new Scanner(System.in);

    public Data readData(String dataFileName) throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("data/" + dataFileName);
        assert url != null;
        File file = new File(url.getFile());

        int rows;
        int[][] distances;
        int[][] flows;
        int best;
        try {
            sc = new Scanner(file);
            rows = sc.nextInt();

            distances = readMatrix(rows);
            flows = readMatrix(rows);

            best = sc.nextInt();
            return new Data(rows, distances, flows, best);
        } catch (FileNotFoundException e) {
            throw e;
        } finally {
            sc.close();
        }
    }

    private int[][] readMatrix(int rows) {
        int[][] matrix = new int[rows][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        return matrix;
    }
}
