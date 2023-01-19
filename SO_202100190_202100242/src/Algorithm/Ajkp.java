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
    

    /* public int[] beamSearch(){


    }*/

}
