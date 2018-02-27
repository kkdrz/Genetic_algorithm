package pwr.si.drozd.tools;

import org.junit.Test;
import pwr.si.drozd.entity.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import static org.junit.Assert.*;

public class DataReaderTest {

    static final int had12instances = 12;

    static final int[][] had12distances = {
            {0, 1, 2, 2, 3, 4, 4, 5, 3, 5, 6, 7},
            {1, 0, 1, 1, 2, 3, 3, 4, 2, 4, 5, 6},
            {2, 1, 0, 2, 1, 2, 2, 3, 1, 3, 4, 5},
            {2, 1, 2, 0, 1, 2, 2, 3, 3, 3, 4, 5},
            {3, 2, 1, 1, 0, 1, 1, 2, 2, 2, 3, 4},
            {4, 3, 2, 2, 1, 0, 2, 3, 3, 1, 2, 3},
            {4, 3, 2, 2, 1, 2, 0, 1, 3, 1, 2, 3},
            {5, 4, 3, 3, 2, 3, 1, 0, 4, 2, 1, 2},
            {3, 2, 1, 3, 2, 3, 3, 4, 0, 4, 5, 6},
            {5, 4, 3, 3, 2, 1, 1, 2, 4, 0, 1, 2},
            {6, 5, 4, 4, 3, 2, 2, 1, 5, 1, 0, 1},
            {7, 6, 5, 5, 4, 3, 3, 2, 6, 2, 1, 0}
    };

    static final int[][] had12flows = {
            {0, 3, 4, 6, 8, 5, 6, 6, 5, 1, 4, 6},
            {3, 0, 6, 3, 7, 9, 9, 2, 2, 7, 4, 7},
            {4, 6, 0, 2, 6, 4, 4, 4, 2, 6, 3, 6},
            {6, 3, 2, 0, 5, 5, 3, 3, 9, 4, 3, 6},
            {8, 7, 6, 5, 0, 4, 3, 4, 5, 7, 6, 7},
            {5, 9, 4, 5, 4, 0, 8, 5, 5, 5, 7, 5},
            {6, 9, 4, 3, 3, 8, 0, 6, 8, 4, 6, 7},
            {6, 2, 4, 3, 4, 5, 6, 0, 1, 5, 5, 3},
            {5, 2, 2, 9, 5, 5, 8, 1, 0, 4, 5, 2},
            {1, 7, 6, 4, 7, 5, 4, 5, 4, 0, 7, 7},
            {4, 4, 3, 3, 6, 7, 6, 5, 5, 7, 0, 9},
            {6, 7, 6, 6, 7, 5, 7, 3, 2, 7, 9, 0}
    };

    @Test
    public void When_FileExists_Expect_CorrectData() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("data/had12.txt");
        assertNotNull("File meant to be tested doesn't exist", url);
        File file = new File(url.getFile());

        Data result = DataReader.readData(file);
        int[][] resultDistances = result.getDistances();
        int[][] resultFlows = result.getFlows();
        int resultInstances = result.getInstances();

        assertEquals("Number of instances does not match.", had12instances, resultInstances);
        for(int i=0; i < had12instances; i++) {
            assertArrayEquals("Distances matrix does not match.", resultDistances[i], had12distances[i]);
            assertArrayEquals("Flows matrix does not match.", resultFlows[i], had12flows[i]);
        }
    }



    @Test(expected = FileNotFoundException.class)
    public void When_FileNotExists_Expect_Exception() throws Exception {
        File file = new File("blabla");

        Data result = DataReader.readData(file);
    }

}