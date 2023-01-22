package Algorithm;

public class Solution {
    private int[] solution;
    private int sumValues, sumWeights, level, iterations;
    private double time;


    public Solution(int[] solution, int sumWeights, int sumValues, int level) {
        this.solution = solution;
        this.sumWeights = sumWeights;
        this.sumValues = sumValues;
        this.level = level;

    }

    public void setSolution(int sol[]) { this.solution = sol; }

    public int getSumValues() { return sumValues; }

    public int getSumWeights() { return sumWeights; }

    public int getLevel() {
        for(int i = 0; i < solution.length; i++)
            if(solution[i] == -1) {
                level = i;
                break;
            }
        return level;
    }

    public void setLevel(int level) { this.level = level; }

    public int countElements() { return solution.length; }

    public int[] getSolution() { return solution; }

    public int getIterations() { return iterations; }

    public void setIterations(int iterations) { this.iterations = iterations; }

    public double getTime() { return time; }

    public void setTime(double time) { this.time = time; }
}
