package Algorithm;

import java.io.FileNotFoundException;

public class Ajkp extends Thread {
    private FileLoader file = new FileLoader();
    private String filename;
    int[] values;
    int[] weights;
    int items;
    int maxWeight;
    private double seconds;

    public Ajkp(String filename, double seconds) throws FileNotFoundException {
        this.filename = filename;
        this.seconds = seconds;
        file.Load(filename);

        this.items = file.getItems();
        this.maxWeight = file.getMax_weight();
        this.values = file.getValue();
        this.weights = file.getWeight();
    }

    public void Ajkp() {
        int count = 0;
        double startTime, endTime;
        int[] finalKnapsack = new int[items];

        seconds = System.currentTimeMillis() + seconds * 1000;
        startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() < seconds) {
            finalKnapsack = lowerBound();

        }
    }

    public int[] lowerBound() {
        sort();

        int knapsack[] = new int[items];

        int sumW = 0;

        for (int i = 0; i < items; i++) {
            sumW += weights[i];

            if (sumW <= maxWeight) {
                knapsack[i] = 1;
            } else {
                knapsack[i] = 0;
            }
        }

        return knapsack;
    }

    public int[] upperBound() {
        sort();

        int c = 0;
        int sumW = 0;
        int knapsack[] = new int[items];
        knapsack[0] = 1;
        knapsack[1] = 0;

        for (int i = 0; i < items; i++) {
            if (i == 0) {
                knapsack[0] = 1;
                sumW += weights[i];
            }
            else if (i == 1)
                knapsack[1] = 0;
            else {
                sumW += weights[i];
                if (sumW > maxWeight) {
                    c = i;
                    break;
                }
            }

        }
    }

    /*public Solution beamSearch() {
        //Solution bestSolution = lowerBound();



        //return bestSolution;
    }*/

    public void sort() {
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

    public void printSort() {
        sort();

        System.out.println("\n▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
        System.out.println("\nValue | Weight");

        for (int i = 0; i < file.getItems(); i++)
            System.out.println("|" + String.format("%03d", values[i]) + "|   " + "|" + String.format("%03d", weights[i]) + "|");

        System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
    }

    public void printLowerBound() {
        int[] knapsack = lowerBound();

        System.out.println("\n▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
        System.out.print("KnapSack LowerBound:\n[");
        for (int i = 0; i < items; i++) {
            if (i != items-1)
                System.out.print(knapsack[i] + ", ");
            else
                System.out.println(knapsack[i] + "]");
        }
        System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
    }
}
