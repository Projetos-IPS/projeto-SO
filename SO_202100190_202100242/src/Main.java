import java.io.FileNotFoundException;
import java.util.Scanner;

import Algorithm.Ajkp;
import Algorithm.FileLoader;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        /*Scanner scanner = new Scanner(System.in);

        System.out.println("Enter file name:");
        String fileName = scanner.nextLine();

        System.out.println("Enter number of threads");
        int threads = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter number of seconds");
        int seconds = Integer.parseInt(scanner.nextLine());
         */

        FileLoader file = new FileLoader();
        String fileName = "ex05";
        
        file.Load(fileName);
        file.printFile();

      /*  System.out.println("Enter number of threads: ");
        int threads = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter number of seconds: ");
        int seconds = Integer.parseInt(scanner.nextLine());*/
    }
}