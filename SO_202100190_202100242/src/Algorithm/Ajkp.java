package Algorithm;

import java.io.FileNotFoundException;

public class Ajkp extends Thread {
    private FileLoader file = new FileLoader();
    private String filename;
    int[] values;
    int[] weights;
    private double seconds;

    //int[] values = file.getValue();
    //int[] weights = file.getWeight();

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

    public Solution calculateLowerBound() {
        initialSolution();

        int items = file.getItems();
        int maxWeight = file.getMax_weight();
        int weights[] = getWeights();

        int finalValues[] = new int[items];
        int finalWeights[] = new int[items];

        int somaW = 0;
        int c = 0;

        for (int i = 0; i < items; i++) {
            somaW += weights[i];

            while (somaW < maxWeight) {
                c = i+1;
                break; //o c é o primeiro indice a não poder ser colocado na mochila
            }
        }

        for(int j = 0; j < c; j++) {
            finalWeights[j] = 1;
            finalValues[j] = 1;
        }

        Solution lowerBound = new Solution(finalValues, finalWeights);

        return lowerBound;
    }


    /*public int[] beamSearch(int[] lowerBound) {
        int[] bestSolution = lowerBound;



        return bestSolution;
    }
     */

    public void initialSolution() {
        int items = file.getItems();
        values = file.getValue();
        weights = file.getWeight();

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

    public int[] getValues() { return values; }

    public int[] getWeights() { return weights; }
}
