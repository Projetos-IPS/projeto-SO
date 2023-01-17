import java.io.FileNotFoundException;
import java.util.Scanner;

import Algorithm.Ajkp;
import Algorithm.FileLoader;
import Algorithm.FileLoader2;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter file name:");
        String fileName = scanner.nextLine();

        /*System.out.println("Enter number of threads");
        int threads = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter number of seconds");
        int seconds = Integer.parseInt(scanner.nextLine());*/



        FileLoader2 file = new FileLoader2();
        file.Load(fileName);

        //System.out.println(file.getItems());
        //System.out.println(file.getMax_weight());
        file.printFile();


        //Ajkp a = new Ajkp();
        //a.start();
    }
}