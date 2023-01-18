package Algorithm;


import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class ajkp {
    FileLoader file = new FileLoader();
    public ajkp(String fileName) throws FileNotFoundException {

        file.Load(fileName);
    }
    
    public int[] initialSolution(){
        int[] values = file.getValue();
        int[] weights = file.getWeight();
        int items = file.getItems();
        int temp = 0;

        for (int i = 0; i < (items-1) ; i++)
        {
            for(int j = 0; j < items-i-1; j++)
            {
                if((values[j]/weights[j])<(values[j+1]/weights[j+1]))
                {
                    temp = values[j];
                    values[j] = values[j+1];
                    values[j+1] = temp;
                }
            }
        }

        return values;
    }
   /* public int[] beamSearch(){


    }*/

}
