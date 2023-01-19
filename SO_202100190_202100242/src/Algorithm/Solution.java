package Algorithm;

public class Solution {
    int weights[];
    int values[];
    int sumValues, sumWeights, level;

    public Solution(int values[], int weights[]) {
            this.weights = weights;
            this.values = values;
    }

        public int[] getWeights() { return weights; }

        public int[] getValues() { return values; }
}
