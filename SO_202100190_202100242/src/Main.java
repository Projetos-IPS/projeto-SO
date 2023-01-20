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

        int[] teste2 = {1,0,1,1,1};
        Solution test = new Solution(teste2);
        System.out.println("Teste: ");
        System.out.println(Arrays.toString(test.getArray()));
        Solution lb = start.calculateLowerBound();

       int teste = start.calculateUpperBound(test);
       System.out.println(teste);
        System.out.println("Lowerbound: ");
        System.out.println(Arrays.toString(lb.getArray()));

        start.sortItems();
        System.out.println("Mochila depois de organizada: ");
        System.out.println(Arrays.toString(start.getValues()));
        System.out.println(Arrays.toString(start.getWeights()));

    }
}