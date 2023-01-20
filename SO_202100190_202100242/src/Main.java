import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import Algorithm.*;
public class Main {

    // versao base é mostrar as threads no main e no run fazer o algoritmo
    //o ponto 3 é na classe main?

    public static void main(String[] args) throws FileNotFoundException {

        FileLoader file = new FileLoader();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Document name: ");
        String fileName = scanner.nextLine();

        System.out.println("Enter number of threads: ");
        int threads = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter number of seconds: ");
        int seconds = Integer.parseInt(scanner.nextLine());

        /*ChildThread listThreads [] = new ChildThread[threads];
        for(int i = 0; i < threads; i++)
        {
            listThreads[i] = new ChildThread();
            listThreads[i].start();
        }*/


        file.Load(fileName);
        int items = file.getItems();
        Ajkp start = new Ajkp(fileName);

        int[] teste2 = {1,0};
        Solution test = new Solution(teste2);


        System.out.println("Solução parcial: ");
        System.out.println(Arrays.toString(test.getArray()));

        int up = start.calculateUpperBound(test);
        System.out.println("Upperbound valor: " + up);

        Bounds lb = start.calculateLowerBound();
        Solution lb_solution = lb.getSolution();
        System.out.println("Lowerbound valor: " + lb.getValue());
        System.out.println("Lowerbound solução: " + Arrays.toString(lb_solution.getArray()));

        start.sortItems();
        System.out.println("Mochila depois de organizada: ");
        System.out.println(Arrays.toString(start.getValues()));
        System.out.println(Arrays.toString(start.getWeights()));

    }
}