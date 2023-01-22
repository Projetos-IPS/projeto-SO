package Algorithm;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class Ajkp extends Thread{
    private FileLoader file = new FileLoader();
    public static Base bestResult;
    public static Solution personalBestResult;
    
    private String filename;
    int[] values, weights;
    Solution lowerBoundSolution;
    int indexLowerBound = 0;
    int indexUpperBound = 0;
    int items, maxWeight;
    private int threads;
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

    public void Ajkp() throws FileNotFoundException, InterruptedException {
        int count = 0, threadUpdate = 0;
        double startTime;

        file.Load(filename);

        sort();

        startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() < startTime + seconds * 1000) {
            personalBestResult = lowerBound();

            int sumValuesBest = 0;
            int sumValuesTmp = 0;

            int n = items / 2;
            Solution tmpSolution = beamSearch(n, personalBestResult);

            for (int i = 0; i < items; i++) {
                int[] personalBestResultBinary = personalBestResult.getSolution();
                if (personalBestResultBinary[i] == 1)
                    sumValuesBest += values[i];
            }

            for (int i = 0; i < items; i++) {
                int[] tmpSolutionBinary = tmpSolution.getSolution();
                if (tmpSolutionBinary[i] == 1)
                    sumValuesTmp += values[i];
            }

            if (sumValuesBest > sumValuesTmp)
                personalBestResult = tmpSolution;

            if (personalBestResult.getSumValues() > bestResult.getFinalValue()) {
                personalBestResult.setIterations(count);
                personalBestResult.setTime(System.currentTimeMillis() - startTime);
            }

            count++;
        }

        bestResult.updateSolution(personalBestResult);
        //return personalBestResult.getSumValues();
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

        solutions.add(new Solution(newSolution, 0,0,0));
        return solutions;
    }

    public ArrayList<Solution> getChilds(ArrayList<Solution> solutions) {
        ArrayList<Solution> childs = new ArrayList<>();

        for (Solution l : solutions) {
            int index = l.getLevel();
            if (index < l.getLevel()) {
                int[] new_sol = l.getSolution();
                new_sol[index] = 0;

                l.setSolution(new_sol);
                childs.add(l);
                new_sol[index] = 1;

                int w = 0;
                int v = 0;

                for (int i = 0; i < new_sol.length; i++)
                    if (new_sol[i] == 1) {
                        w += getWeights()[i];
                        v += getValues()[i];
                    }

                if (w <= maxWeight)
                    childs.add(new Solution(new_sol, w, v, index + 1));

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

        while (start.isEmpty() == false) {
            start = getChilds(start);

            for (Solution sol : start) {
                int ub = upperBound(sol);
                int lb_int = lb.getSumValues();

                if (ub >= lb_int)
                    if (sol.getSumValues()> lb_int)
                        lb = sol;
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

    /**public void printLowerBound() {
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
    }**/

    public int[] getValues() { return values; };

    public int[] getWeights() { return weights; }

    public static class Base {
        private int iterations, finalValue, finalWeight, threadUpdate = 0;
        static int threadNumber;
        private double bestTime = 0;

        public Base(int threads) {
            threadNumber = threads;
        }

        public synchronized void updateSolution(Solution solution) {
            if (this.getFinalValue() < solution.getSumValues()) {
                this.setFinalValue(solution.getSumValues());
                this.setFinalWeight(solution.getSumWeights());
                this.setIterations(solution.getIterations());
                this.setBestTime(solution.getTime());
            }

            threadUpdate++;
            if (threadUpdate == threadNumber)
                notify();
        }

        /**public synchronized void print() {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
         
            //System.out.println("Number of Items: " + file.getItems());
            System.out.println("Total Runtime: " );
            System.out.println("Number of Threads: " + threadNumber);

            System.out.println("Best Solution: " );
            System.out.println("Best Solution Weight: " );
            System.out.println("Time To Best Solution: " );
            System.out.println("Number of Iterations to Best Solution: " );
            System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
        }**/

        public synchronized void print() {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Final Value: " + this.getFinalValue());
            System.out.println("Final Weight: " + this.getFinalWeight());
            System.out.println("Best Iteration: " + this.getIterations());
            System.out.println("Best Time: " + this.getBestTime() / 1000);
        }

        public int getFinalValue() { return finalValue; }

        public void setFinalValue(int finalValue) { this.finalValue = finalValue; }

        public int getFinalWeight() { return finalWeight; }

        public void setFinalWeight(int finalWeight) { this.finalWeight = finalWeight; }

        public int getIterations() { return iterations; }

        public void setIterations(int iterations) { this.iterations = iterations; }

        public double getBestTime() { return bestTime; }

        public void setBestTime(double bestTime) { this.bestTime = bestTime; }

        @Override
        public String toString() {
            return "Final Value: " + finalValue + "\nFinal Weight: " + finalWeight;
        }
    }
}
