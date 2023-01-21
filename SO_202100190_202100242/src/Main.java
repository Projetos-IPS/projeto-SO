import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Algorithm.Ajkp;
import Algorithm.FileLoader;
import Algorithm.Solution;

public class Main {
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter file name:");
        String fileName = scanner.nextLine();

        System.out.println("Enter number of threads");
        int threads = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter number of seconds");
        int seconds = Integer.parseInt(scanner.nextLine());

        for(int i = 0; i<threads; i++){
            Ajkp ajr = new Ajkp(fileName, seconds);
            ajr.start();
        }

    }
}