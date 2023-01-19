package Algorithm;

public class Solution {
   //guarda cada soluçao
    int weights[];
    int values[];
    int sumValues, sumWeights, level;

    public Solution(int values[], int weights[]){
        this.weights = weights;
        this.values = values;
    }

    public int[] getWeights() {
        return weights;
    }

    public int[] getValues() {
        return values;
    }
}
