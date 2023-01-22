package Algorithm;

import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ajkp extends Thread{
    private FileLoader file = new FileLoader();
    public static Base bestSolution;
    public static Solution iterationSolution;
    
    private String filename;
    int[] values, weights;
    Solution lowerBoundSolution;
    int indexLowerBound = 0;

    int indexUpperBound = 0;
    int items, maxWeight, maxValue;
    private double seconds;

    public Ajkp(String filename, double seconds) throws FileNotFoundException {
        //this.filename = filename;
        this.seconds = seconds;
        this.filename = "ex05";
        file.Load(filename);

        this.items = file.getItems();
        this.maxWeight = file.getMax_weight();
        this.values = file.getValue();
        this.weights = file.getWeight();
        this.maxValue = file.getIdeal_value();
    }

    public void Ajkp() throws FileNotFoundException, InterruptedException {
            int count = 1, sumVBest = 0;
            int n = items / 2;

            file.Load(filename);
            sort();
            iterationSolution = lowerBound();
            long timer, start = System.currentTimeMillis();
            long end = System.currentTimeMillis();
            float sec = (end - start) / 1000F;
            iterationSolution.setTime(sec);
            Solution bestSolutionFound = iterationSolution;

            do {
                int sumVTmp = 0;
                Solution tmpSolution = beamSearch(n, iterationSolution);
                tmpSolution.setIterations(count);

                for (int i = 0; i < items; i++) {
                    int[] iterationSolutionBinary = iterationSolution.getSolution();
                    if (iterationSolutionBinary[i] == 1)
                        sumVBest += values[i];

                    int[] tmpSolutionBinary = tmpSolution.getSolution();
                    if (tmpSolutionBinary[i] == 1)
                        sumVTmp += values[i];
                }

                end = System.currentTimeMillis();
                sec = (end - start) / 1000F;
                //System.out.println(sec + " seconds");
                tmpSolution.setTime(sec);


                if (sumVBest < sumVTmp)
                    bestSolutionFound = tmpSolution;
                else
                    System.out.println(count);

                /*iterationSolution.setIterations(count);
                System.out.println(System.currentTimeMillis() - startTime);
                iterationSolution.setTime(System.currentTimeMillis() - startTime);*/

                count++;
                //System.out.println(count);
                timer = System.currentTimeMillis() - start;
            } while (timer < seconds * 1000);

            bestSolution.updateSolution(bestSolutionFound);
            String s = bestSolutionFound.toString();
    }

    @Override
    public void run() {
        try {
            Ajkp();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Solution lowerBound() {
        sort();
        int[] knapsackLowerBound = new int[items];
        int sumV = 0;
        int sumW = 0;
        int sumW2 = 0;

        for (int i = 0; i < items; i++) {
            sumW += weights[i];
            if (sumW <= maxWeight) {
                knapsackLowerBound[i] = 1;
                sumV += values[i];
                sumW2 += weights[i];
            } else {
                indexLowerBound = i + 1;
                break;
            }
        }

        for (int i = indexLowerBound - 1; i < items; i++)
            knapsackLowerBound[i] = -1;

        lowerBoundSolution = new Solution(knapsackLowerBound, sumW2, sumV, 0);
        int level = lowerBoundSolution.getLevel();
        lowerBoundSolution.setLevel(level);

        return lowerBoundSolution;
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

        double ub1, ub2;
        int val = getValues()[indexUpperBound + 1];
        int wei = getWeights()[indexUpperBound + 1];
        int val2 = getValues()[indexUpperBound];
        int wei2 = getWeights()[indexUpperBound];
        int val3 = getValues()[indexUpperBound - 1];
        int wei3 = getWeights()[indexUpperBound - 1];

        ub1 = sumValues + sumValues2 + (w * ((double) val / (double) wei));
        ub2 = sumValues + sumValues2 + (val2 - (wei2 - w) * (double) val3 / (double) wei3);
        int max = Math.max((int) ub1, (int) ub2);

        //return sumV + 1;
        return max;
    }

    public ArrayList<Solution> initialSolution() {
        ArrayList<Solution> solutions = new ArrayList<>();
        int[] newSolution = new int[items];

        for(int i = 0; i < items; i++)
            newSolution[i] = -1;

        solutions.add(new Solution(newSolution, 0, 0, 0));
        return solutions;
    }

    public ArrayList<Solution> getChilds(ArrayList<Solution> solutions) {
        ArrayList<Solution> childs = new ArrayList<>();
        int v = 0;
        int w = 0;

        for(int x = 0; x < childs.size(); x++) {
            for (Solution l : solutions) {
                int index = l.getLevel();
                if (index < items) {
                    int[] new_sol = l.getSolution();
                    new_sol[index] = 0;

                    l.setSolution(new_sol);
                    childs.add(l);
                    new_sol[index] = 1;

                    for (int k = 0; k < items; k++) {
                        if (new_sol[k] == 1) {
                            w += weights[k];
                            v += values[k];

                        }
                    }

                    if (w <= maxWeight) {
                        childs.add(new Solution(new_sol, w, v, index + 1));
                    }

                }

            }
        }

        return childs;
    }

    public ArrayList<Solution> selectSolutions(int n, ArrayList<Solution> list) {
        int size = list.size();
        Random rand = new Random();
        ArrayList<Solution> selectedSolutions = new ArrayList<>();

        if (size > n) {
            for (int i = 0; i < n; i++) {
                int selected = rand.nextInt(size);
                Solution sol = list.get(selected);

                if(!selectedSolutions.contains(sol))
                    selectedSolutions.add(sol);
            }
            return selectedSolutions;
        }
        else
            return list;
    }

    public Solution beamSearch(int n, Solution lb) {
        ArrayList<Solution> start = initialSolution();

        while (!start.isEmpty()) {
            start = getChilds(start);

            for (Solution sol : start) {
                int ub = upperBound(sol);
                int lb_int = lb.getSumValues();

                if (ub >= lb_int) {
                    if (sol.getSumValues() > lb_int)
                        lb = sol;
                }
                else {
                    start.remove(sol);
                }

            }
            start = selectSolutions(n, start);

        }
        return lb;
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


    public int[] getValues() { return values; };

    public int[] getWeights() { return weights; }

    public static class Base {
        private int iterations, bestValue, bestWeight, threadUpdate = 0;
        static int threadNumber;
        private double bestTime = 0;

        int[] finalSolution;
        public Base(int threads) {
            threadNumber = threads;
        }

        public void setFinalSolution(int[] finalSolution) {
            this.finalSolution = finalSolution;
        }

        public int[] getFinalSolution() {
            return finalSolution;
        }

        public synchronized void updateSolution(Solution solution) {
            if (this.getBestValue() < solution.getSumValues()) {
                this.setBestValue(solution.getSumValues());
                this.setFinalSolution(solution.getSolution());
                this.setBestWeight(solution.getSumWeights());
                this.setIterations(solution.getIterations());
                this.setBestTime(solution.getTime());
            }

            threadUpdate++;
            if (threadUpdate == threadNumber)
                notify();
        }

        public synchronized void print() {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
            System.out.println("Final sorted knapsack: " + Arrays.toString(this.getFinalSolution()));
            System.out.println("Final Value: " + this.getBestValue());
            System.out.println("Final Weight: " + this.getBestWeight());
            System.out.println("Best Iteration: " + this.getIterations());
            DecimalFormat formatter = new DecimalFormat("#0.00000");
            System.out.println("Best Time: " + formatter.format(this.getBestTime()) + " seconds");
            System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
        }

        public int getBestValue() { return bestValue; }

        public void setBestValue(int bestValue) { this.bestValue = bestValue; }



        public int getBestWeight() { return bestWeight; }

        public void setBestWeight(int bestWeight) { this.bestWeight = bestWeight; }

        public int getIterations() { return iterations; }

        public void setIterations(int iterations) { this.iterations = iterations; }

        public double getBestTime() { return bestTime; }

        public void setBestTime(double bestTime) { this.bestTime = bestTime; }

        @Override
        public String toString() {
            return "Best Value: " + bestValue + "\nBest Weight: " + bestWeight;
        }
    }
}
