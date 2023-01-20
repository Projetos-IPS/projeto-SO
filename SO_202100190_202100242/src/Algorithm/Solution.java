package Algorithm;

public class Solution {
   //guarda cada soluçao
    int items[];
    int sumValues, sumWeights, level;

    public Solution(int items[]){
        this.items = items;
    }

    public int countElements(){
        return items.length;
    }

    public int[] getArray() {
        return items;
    }


}
