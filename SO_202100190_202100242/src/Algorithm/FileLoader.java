package Algorithm;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileLoader {
    private int items;
    private int max_weight;
    private int[] values;
    private int[] weights;

    public FileLoader() {
        this.items = 0;
        this.max_weight = 0;
    }

    public void Load(String filename) throws FileNotFoundException {
        String txt = ".txt";
        String path = "knap_tests_extended/" + filename + txt;
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        String fileContent;

        int[] valuesX = new int[1];
        int[] weightsX = new int[1];
        int count = 0;
        int count2 = 0;

        while (scanner.hasNext()) {
            fileContent = scanner.next();

            if (count == 0) {
                setItems(Integer.parseInt(fileContent));
                valuesX = new int[getItems()];
                weightsX = new int[getItems()];
            }

            if (count == 1) {
                setMax_weight(Integer.parseInt(fileContent));
                count++;
            } else if (count == 2) {
                valuesX[count2] = Integer.parseInt(fileContent);
                count2++;

                if (count2 == getItems()) {
                    setValues(valuesX);
                    count2 = 0;
                    count++;
                }
            } else if (count == 3) {
                weightsX[count2] = Integer.parseInt(fileContent);
                count2++;

                if (count2 == getItems()) {
                    setWeights(weightsX);
                    count2 = 0;
                    count++;
                }
            }

            if (count == 0)
                count++;
        }
    }

    public void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + "|");
    }

    public int getItems() { return items; }

    public void setItems(int items) { this.items = items; }

    public int getMax_weight() { return max_weight; }

    public void setMax_weight(int max_weight) { this.max_weight = max_weight; }

    public int[] getValues() { return values; }

    public void setValues(int[] values) { this.values = values; }

    public int[] getWeights() { return weights; }

    public void setWeights(int[] weights) { this.weights = weights; }
}