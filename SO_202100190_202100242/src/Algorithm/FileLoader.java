package Algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Formatter;

public class FileLoader {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    private int items;
    private int max_weight;
    private int[] value;
    private int[] weight;
    private int ideal_value;

    public FileLoader() {
        this.items = 0;
        this.max_weight = 0;
        this.ideal_value = 0;
    }

    public void Load(String filename) throws FileNotFoundException {
        String path = "knap_tests/" + filename + ".txt";
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        String fileContent;
        int count = 0;
        int countIndex = 0;
        int countingLines = 0;

        while (scanner.hasNext()) {
            fileContent = scanner.next();

            if (count == 2) {
                if (countingLines % 2 == 0) {
                    value[countIndex] = Integer.parseInt(fileContent);
                    countingLines++;
                } else {
                    weight[countIndex] = Integer.parseInt(fileContent);
                    countIndex++;
                    countingLines++;

                    if (countIndex == items) {
                        count++;
                    }
                }
            }

            if (count == 3) {
                setIdeal_value(Integer.parseInt(fileContent));
                ideal_value = getIdeal_value();
            }

            if (count == 1) {
                setMax_weight(Integer.parseInt(fileContent));
                max_weight = getMax_weight();
                count++;
                countingLines++;
            }

            if (count == 0) {
                setItems(Integer.parseInt(fileContent));
                items = getItems();
                value = new int[getItems()];
                weight = new int[getItems()];
                count++;
                countingLines++;
            }
        }
    }

    public void printFile() {
        System.out.println(PURPLE + "\n▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒" + RESET);
        System.out.println(PURPLE + "Items: "  + RESET + getItems());
        System.out.println(PURPLE + "Max Weight: " + RESET + getMax_weight());
        System.out.println(PURPLE + "Ideal Value: " + RESET + getIdeal_value());
        System.out.println(PURPLE + "\nValue | Weight" + RESET);

        for (int i = 0; i < getItems(); i++)
            System.out.println("|" + String.format("%03d", value[i]) + "|   " + "|" + String.format("%03d", weight[i]) + "|");

        System.out.println(PURPLE + "▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒" + RESET);
    }

    public int getItems() { return items; }

    public void setItems(int items) { this.items = items; }

    public int getMax_weight() { return max_weight; }

    public void setMax_weight(int max_weight) { this.max_weight = max_weight; }

    public int[] getValue() { return value; }

    public int[] getWeight() { return weight; }

    public int getIdeal_value() { return ideal_value; }

    public void setIdeal_value(int ideal_value) { this.ideal_value = ideal_value; }
}
