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

      /*  System.out.println("Enter number of threads: ");
        int threads = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter number of seconds: ");
        int seconds = Integer.parseInt(scanner.nextLine());*/
        file.Load(fileName);
        int items = file.getItems();
        Ajkp start = new Ajkp(fileName);

        int[] teste2 = {1,0};
        Solution test = new Solution(teste2);


        System.out.println("Solução parcial: ");
        System.out.println(Arrays.toString(test.getArray()));

        Bounds up = start.calculateUpperBound(test);
        Solution ub_solution = up.getSolution();
        System.out.println("Upperbound valor: " + up.getValue());
        System.out.println("Upperbound solução apartir da solução parcial dada: " + Arrays.toString(ub_solution.getArray()));

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