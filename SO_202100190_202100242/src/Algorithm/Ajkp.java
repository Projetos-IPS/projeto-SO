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

    public LowerBound calculateLowerBound()
    {
        sortItems();
        int items = file.getItems();
        int maxWeight = file.getMax_weight();
        int weights[] = getWeights();
        int values[] = getValues();
        int lowerbound[] = new int[items];
        int soma_final = 0;
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
            if(lowerbound[j] == 1)
            {
                soma_final += values[j];
            }
        }

        Solution lb_solution = new Solution(lowerbound);
        LowerBound lb = new LowerBound(lb_solution, soma_final);
        return lb;
    }

    public int calculateUpperBound(Solution s) {

        sortItems();
        int items = file.getItems();
        int maxWeight = file.getMax_weight();
        int newSolution[] = new int[items];
        int ogsolutionElements = s.countElements();
        int[] weights = getWeights();
        int[] values = getValues();
        int c = 0;
        int sum = 0;
        int sum2 = 0;
        int w = 0;
        int firstElementIndex = 0;
        int firstElementWeight = 0;
        int sum_all = 0;
        int sum_values = 0;
        int sum_values2 = 0;

      //  if(ogsolutionElements <= items) {
            for (int i = 0; i < ogsolutionElements; i++)
            {
                newSolution[i] = s.getArray()[i];
                if (s.getArray()[i] == 1) {
                    sum += weights[i];
                    sum_values += values[i];

                    for (int j = ogsolutionElements; j < items; j++) {
                        sum2 += weights[j];
                        while ((sum + sum2) <= maxWeight) {
                            newSolution[j] = 1;
                            c = j+1;
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
                    sum_values2 += values[k];
                }
            }

            w = maxWeight - sum_all - firstElementWeight;

      //  cálculo do upperbound

        double ub1 = 0.0;
        double ub2 = 0.0;
        int ub1_int = 0;
        int ub2_int = 0;
        int val = getValues()[c+1];
        int wei = getWeights()[c+1];
        int val2 = getValues()[c];
        int wei2 = getWeights()[c];
        int val3 = getValues()[c-1];
        int wei3 = getWeights()[c-1];
        int max = 0;

        ub1 = sum_values + sum_values2 + (double)(10*((double)val/(double)wei));
        ub2 = sum_values + sum_values2 + (double)(val2-(wei2-10)*(double)val3/(double)wei3);

        ub1_int = (int) ub1;
        ub2_int = (int) ub2;

        if(ub1_int > ub2_int)
            max = ub1_int;
        else
            max = ub2_int;

    return max;
    }

    /* public int[] beamSearch(){


    }*/

}
