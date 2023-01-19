package Algorithm;

import java.io.FileNotFoundException;

public class Ajkp extends Thread {
    private FileLoader file = new FileLoader();
    private String filename;
    private double seconds;

    int[] values = file.getValue();
    int[] weights = file.getWeight();

    public Ajkp(String filename, double seconds) throws FileNotFoundException {
        this.filename = filename;
        this.seconds = seconds;
        file.Load(filename);
    }

    public void Ajkp() {
        int count = 0;
        double startTime, endTime;

        seconds = System.currentTimeMillis() + seconds * 1000;
        startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() < seconds) {
            //initialSolution();

        }
    }


    public int[] BeamSearch(int[] lowerBound) {
        int[] bestSolution = lowerBound;



        return bestSolution;
    }

    /*public int[] LowerBound() {
        int[] lowerBoundSolution;



        return lowerBoundSolution;
    }*/

    public void initialSolution(int[] values, int[] weights) {
        int items = file.getItems();
        int tempV = 0;
        int tempW = 0;

        for (int i = 0; i < items-1; i++) {
            for (int j = 0; j < items-i-1; j++) {
                if (((double)values[j] / weights[j]) < ((double)values[j + 1] / weights[j + 1])) {
                    tempV = values[j];
                    values[j] = values[j + 1];
                    values[j + 1] = tempV;
                    tempW = weights[j];
                    weights[j] = weights[j + 1];
                    weights[j + 1] = tempW;
                }
            }
        }
    }
}
