package Algorithm;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Ajkp extends Thread {
    private FileLoader file = new FileLoader();
    private String filename;
    int[] values, weights, knapsackLowerBound;
    int[] knapsackUpperBound = {1,0,-1,-1,-1,-1,-1,-1};
    int indexLowerBound = 0;
    int indexUpperBound = 0;
    int items, maxWeight;
    private double seconds;

    public Ajkp(String filename, double seconds) throws FileNotFoundException {
        this.filename = filename;
        this.seconds = seconds;
        file.Load(filename);

        this.items = file.getItems();
        this.maxWeight = file.getMax_weight();
        this.values = file.getValue();
        this.weights = file.getWeight();
        this.knapsackLowerBound = new int[items];
    }

    public void Ajkp() {
        int[] b = {1, 0, 1, 1, 1, 0, 0, 0};
        Solution initialSolution = new Solution(b);
        ArrayList<Solution> a = new ArrayList<>();
        a.add(initialSolution);






        int count = 0;
        double startTime, endTime;
        int[] finalKnapsack = new int[items];

        seconds = System.currentTimeMillis() + seconds * 1000;
        startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() < seconds) {
            //finalKnapsack = lowerBound();

        }
    }

    public int lowerBound() {
        sort();
        int sumV = 0;
        int sumW = 0;

        for (int i = 0; i < items; i++) {
            sumW += weights[i];
            if (sumW <= maxWeight) {
                knapsackLowerBound[i] = 1;
                sumV += values[i];
            } else {
                indexLowerBound = i + 1;
                break;
            }
        }

        for (int i = indexLowerBound; i < items; i++)
            knapsackLowerBound[i] = -1;

        return sumV;
    }

    public int upperBound() {
        sort();
        int sumV = 0;
        int sumW = 0;

        for (int i = 0; i < items; i++)
            if (knapsackUpperBound[i] == 1) {
                sumW += weights[i];
                sumV += values[i];
            }

        for (int i = 0; i < items; i++)
            if (knapsackUpperBound[i] == -1) {
                sumW += weights[i];
                if (sumW <= maxWeight) {
                    knapsackUpperBound[i] = 1;
                    sumV += values[i];
                } else {
                    indexUpperBound = i + 1;
                    break;
                }
            }

        for (int i = indexUpperBound; i < items; i++)
            knapsackUpperBound[i] = -1;

        return sumV + 1;
    }

    public void selectSolutions(int n, List<Solution> list) {
        //n = items / 2;

    }

    public Solution beamSearch(int n, int indexLowerBound) {
        //Solution bestSolution = lowerBound();
        Solution initialSolution = new Solution(knapsackUpperBound);



        

        return initialSolution;
    }

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
        int lowerBound = lowerBound();
        System.out.println("\n▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
        System.out.print("KnapSack LowerBound:\n[");
        for (int i = 0; i < items; i++) {
            if (i != items-1)
                System.out.print(knapsackLowerBound[i] + ", ");
            else
                System.out.println(knapsackLowerBound[i] + "]");
        }
        System.out.println("Index LowerBound: " + indexLowerBound);
        System.out.println("LowerBound Value: " + lowerBound);
        System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
    }

    public void printUpperBound() {
        int upperBound = upperBound();
        System.out.println("\n▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
        System.out.print("KnapSack UpperBound:\n[");
        for (int i = 0; i < items; i++) {
            if (i != items-1)
                System.out.print(knapsackUpperBound[i] + ", ");
            else
                System.out.println(knapsackUpperBound[i] + "]");
        }
        System.out.println("Index UpperBound: " + indexUpperBound);
        System.out.println("UpperBound Value: " + upperBound);
        System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
    }
}
