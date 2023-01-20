package Algorithm;


import java.io.FileNotFoundException;

public class Ajkp {
    FileLoader file = new FileLoader();
    int[] values;
    int[] weights;

    public Ajkp(String fileName) throws FileNotFoundException {

        file.Load(fileName);
    }

    public void sortItems(){
        values = file.getValue();
        weights = file.getWeight();
        int items = file.getItems();
        int temp = 0;
        int temp2 = 0;

        for (int i = 0; i < items-1; i++) {
            for (int j = 0; j < items-i-1; j++) {
                if (((double)values[j] / weights[j]) < ((double)values[j + 1] / weights[j + 1])) {
                    temp = values[j];
                    values[j] = values[j + 1];
                    values[j + 1] = temp;
                    temp2 = weights[j];
                    weights[j] = weights[j + 1];
                    weights[j + 1] = temp2;
                }
            }
        }
    }

    public int[] getValues() {
        return values;
    }

    public int[] getWeights() {
        return weights;
    }

    public Solution calculateLowerBound()
    {
        sortItems();
        int items = file.getItems();
        int maxWeight = file.getMax_weight();
        int weights[] = getWeights();
        int lowerbound[] = new int[items];
        int somaW = 0;
        int c = 0;

        for (int i = 0; i < items; i++) {
           somaW += weights[i];
           while (somaW <= maxWeight)
           {
               c = i+1;
               break;//o c é o primeiro indice a não poder ser colocado na mochila
           }
        }

        for(int j = 0; j < c; j++)
        {
            lowerbound[j] = 1;
        }

        Solution lowerBound = new Solution(lowerbound);
        return lowerBound;
    }

    public int calculateUpperBound(Solution s) {

        sortItems();
        int items = file.getItems();
        int maxWeight = file.getMax_weight();
        int newSolution[] = new int[items];
        int ogsolutionElements = s.countElements();
        int[] weights = getWeights();
        int c = 0;
        int sum = 0;
        int sum2 = 0;
        int w = 0;
        int firstElementIndex = 0;
        int firstElementWeight = 0;
        int sum_all = 0;

        if(ogsolutionElements <= items) {
            for (int i = 0; i < ogsolutionElements; i++)
            {
                newSolution[i] = s.getArray()[i];
                if (s.getArray()[i] == 1) {
                    sum += weights[i];

                    for (int j = ogsolutionElements; j < items; j++) {
                        sum2 += weights[j];
                        while ((sum + sum2) <= maxWeight) {
                            newSolution[j] = 1;
                            c = j+ogsolutionElements;
                            break;
                        }
                    }
                }
            }

            for (int x = 0; x < c; x++)
            {
                if (newSolution[x] == 1)
                {
                    firstElementIndex = x;
                    firstElementWeight = weights[x];
                    break;
                }
            }

            for (int k = firstElementIndex+1; k < c; k++)
            {
                if(newSolution[k] == 1) {
                    sum_all += weights[k];
                }
            }

            w = maxWeight - sum_all - firstElementWeight;
        }

    return w;

    }

    /* public int[] beamSearch(){


    }*/

}
