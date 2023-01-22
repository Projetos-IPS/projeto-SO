import java.io.FileNotFoundException;
import java.util.Scanner;

import Algorithm.Ajkp;

public class Main {
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        /** MAIN FINAL - NÃO APAGAR
        Scanner scanner = new Scanner(System.in);
        System.out.println(GREEN + "Enter command >" + RESET);
        String command = scanner.nextLine();**/

        String command = "1 ex08 3 5";


        String[] commandSplice = command.split(" ");
        int test = Integer.parseInt(commandSplice[0]);
        String filename = commandSplice[1];
        int threads = Integer.parseInt(commandSplice[2]);
        int seconds = Integer.parseInt(commandSplice[3]);

        Ajkp.Base bestSolution = new Ajkp.Base(threads);
        Ajkp.bestResult = bestSolution;

     //   Ajkp sorted = new Ajkp(filename);
     //  sorted.printSort();

        for (int i = 0; i < threads; i++) {
            Ajkp algorithm = new Ajkp(filename, seconds);
            algorithm.start();
        }

        System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
        System.out.println("Test Number: " + test);
        System.out.println("Test File: " + filename + ".txt");
        bestSolution.print();
    }
}