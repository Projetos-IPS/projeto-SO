package Algorithm;

public class Solution {
    int[] solution;
    int sumValues, sumWeights, level;

    public Solution(int[] solution, int sumWeights, int sumValues, int level) {
        this.solution = solution;
        this.sumWeights = sumWeights;
        this.sumValues = sumValues;
        this.level = level;
    }

    public void setSolution(int sol[]) {
        this.solution = sol;
    }

    public int getSumValues() {
        return sumValues;
    }

    public int getSumWeights() {
        return sumWeights;
    }

    public int getLevel(){
        for(int i = 0; i < solution.length; i++)
        {
            if(solution[i] == -1)
            {
                level = i;
                break;
            }
        }
        return level;
    }

    public int countElements() { return solution.length; }

    public int[] getSolution() { return solution; }
}
