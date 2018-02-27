package pwr.si.drozd.tools;


import pwr.si.drozd.entity.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataReader {

    private static Scanner sc = new Scanner(System.in);

    public static Data readData(File file) throws FileNotFoundException {
        int rows;
        int[][] distances;
        int[][] flows;
        try {
            sc = new Scanner(file);
            rows = sc.nextInt();

            distances = readMatrix(rows);
            flows = readMatrix(rows);

            return new Data(rows, distances, flows);
        } catch (FileNotFoundException e) {
            throw e;
        } finally {
            sc.close();
        }
    }

    private static int[][] readMatrix(int rows) {
        int[][] matrix = new int[rows][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        return matrix;
    }
}
