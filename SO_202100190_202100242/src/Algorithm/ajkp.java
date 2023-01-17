package Algorithm;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;

public class ajkp {
    FileLoader file = new FileLoader();
    public ajkp(String fileName) throws FileNotFoundException {

        file.Load(fileName);
    }

    public static void reverse(float[] array) {

        // Length of the array
        int n = array.length;

        // Swapping the first half elements with last half
        // elements
        for (int i = 0; i < n / 2; i++) {

            // Storing the first half elements temporarily
            float temp = array[i];

            // Assigning the first half to the last half
            array[i] = array[n - i - 1];

            // Assigning the last half to the first half
            array[n - i - 1] = temp;
        }
    }

    public float[] beamSearch(){

        int[] values = file.getValues();
        int[] weights = file.getWeights();
        float[] results = new float[file.getItems()];

        for (int i = 0; i < values.length; i++)
        {
            for(int j = 0; j < weights.length; j++)
            {
                results[i] = (values[i] / weights[i]);
            }
        }

        Arrays.sort(results);
        reverse(results);

        return results;
    }

}
