package Algorithm;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Ajkp extends Thread {
    private FileLoader file = new FileLoader();
    private String filename;
    int[] values, weights;
    Solution lowerBoundSolution, upperBoundSolution;
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
    }

    public void Ajkp() {
        sort();

        /**
        int[] b = {1, 0, 1, 1, 1, 0, 0, 0};
        Solution initialSolution = new Solution(b);
        ArrayList<Solution> a = new ArrayList<>();
        a.add(initialSolution);**/

        int count = 0;
        double startTime, endTime;
        int[] finalKnapsack = new int[items];

        seconds = System.currentTimeMillis() + seconds * 1000;
        startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() < seconds) {
            lowerBound();
            Solution bestSolution = lowerBoundSolution;

            int sumValuesBest = 0;
            int sumValuesTmp = 0;

            int n = items / 2;
            Solution tmpSolution = beamSearch(n, bestSolution);

            for (int i = 0; i < items; i++) {
                int[] bestSolutionBinary = bestSolution.getSolution();
                if (bestSolutionBinary[i] == 1)
                    sumValuesBest += values[i];
            }

            for (int i = 0; i < items; i++) {
                int[] tmpSolutionBinary = tmpSolution.getSolution();
                if (tmpSolutionBinary[i] == 1)
                    sumValuesTmp += values[i];
            }

            if (sumValuesBest > sumValuesTmp) {
                bestSolution = tmpSolution;
            }
            count++;
        }
    }

    public int lowerBound() {
        sort();
        int[] knapsackLowerBound = new int[items];
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

        for (int i = indexLowerBound - 1; i < items; i++)
            knapsackLowerBound[i] = -1;

        lowerBoundSolution = new Solution(knapsackLowerBound);

        return sumV;
    }

    public int upperBound(Solution partialSolution) {
        sort();
        int[] knapsackUpperBound = partialSolution.getSolution();
        int sumV = 0;
        int sumValues = 0;
        int sumValues2 = 0;
        int sumW = 0;
        int w = 0;
        int firstIndex = 0;
        int sumWeights = 0;

        for (int i = 0; i < partialSolution.countElements(); i++)
            if (partialSolution.getSolution()[i] == 1) {
                sumW += weights[i];
                sumV += values[i];
                sumValues += values[i];
            }

        for (int i = 0; i < items; i++)
            if (knapsackUpperBound[i] == -1) {
                sumW += weights[i];
                if (sumW <= maxWeight) {
                    knapsackUpperBound[i] = 1;
                    sumV += values[i];
                    sumValues2 += values[i];
                    if (i == items - 1)
                        indexUpperBound = i;
                } else {
                    indexUpperBound = i;
                    break;
                }
            }
        if (indexUpperBound == items - 1)
            indexUpperBound--;

        for (int i = indexUpperBound; i < items; i++)
            knapsackUpperBound[i] = -1;

        for (int i = 0; i < items; i++)
            if (knapsackUpperBound[i] == 1) {
                firstIndex = i;
                break;
            }
        for (int i = 0; i < items; i++) {
            if (knapsackUpperBound[i] == 1 && i != firstIndex) {
                sumWeights += weights[i];
            }
        }

        w = maxWeight - sumWeights - weights[firstIndex];
        upperBoundSolution = new Solution(knapsackUpperBound);

        double ub1, ub2;
        int val = getValues()[indexUpperBound + 1];
        int wei = getWeights()[indexUpperBound + 1];
        int val2 = getValues()[indexUpperBound];
        int wei2 = getWeights()[indexUpperBound];
        int val3 = getValues()[indexUpperBound - 1];
        int wei3 = getWeights()[indexUpperBound - 1];

        ub1 = sumValues + sumValues2 + (w * ((double)val / (double)wei));
        ub2 = sumValues + sumValues2 + (val2 - (wei2 - w) * (double) val3 / (double) wei3);
        int max = Math.max((int)ub1, (int)ub2);

        //return sumV + 1;
        return max;
    }

    public ArrayList<Solution> getChilds(ArrayList<Solution> list) {
        ArrayList<Solution> childs = new ArrayList<>();

        for (Solution l : list) {

        }
        return list;
    }

    public void selectSolutions(int n, List<Solution> list) {
        //n = items / 2;


    }

    public Solution beamSearch(int n, Solution lowerBoundSolution) {
        Solution bestSolution = lowerBoundSolution;




        

        return bestSolution;
    }

    public ArrayList<Solution> initialSolution() {
        ArrayList<Solution> solutions = new ArrayList<>();
        int[] newSolution = new int[items];

        for(int i = 0; i < items; i++)
            newSolution[i] = -1;

        solutions.add(new Solution(newSolution));

        return solutions;
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
        int[] lowerBoundS = lowerBoundSolution.getSolution();
        System.out.println("\n▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
        System.out.print("KnapSack LowerBound:\n[");
        for (int i = 0; i < items; i++) {
            if (i != items-1)
                System.out.print(lowerBoundS[i] + ", ");
            else
                System.out.println(lowerBoundS[i] + "]");
        }
        System.out.println("Index LowerBound: " + indexLowerBound);
        System.out.println("LowerBound Value: " + lowerBound);
        System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
    }

    public void printUpperBound() {
        int[] arr = {1, 0, -1, -1, -1, -1};
        Solution arrS = new Solution(arr);
        int upperBound = upperBound(arrS);
        int[] upperBoundS = upperBoundSolution.getSolution();
        System.out.println("\n▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
        System.out.print("KnapSack UpperBound:\n[");
        for (int i = 0; i < items; i++) {
            if (i != items-1)
                System.out.print(upperBoundS[i] + ", ");
            else
                System.out.println(upperBoundS[i] + "]");
        }
        System.out.println("Index UpperBound: " + indexUpperBound);
        System.out.println("UpperBound Value: " + upperBound);
        System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
    }

    public int[] getValues() { return values; };

    public int[] getWeights() { return weights; }
}
