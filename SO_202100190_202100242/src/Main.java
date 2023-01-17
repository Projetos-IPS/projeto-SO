import java.io.*;
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

        file.Load(fileName);
        int[] teste = file.getWeights();
        int[] teste2 = file.getValues();

      /*  System.out.println("Enter number of threads: ");
        int threads = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter number of seconds: ");
        int seconds = Integer.parseInt(scanner.nextLine());*/
/*
        ajkp start = new ajkp(fileName);

        float[] teste = start.beamSearch();
*/

        for (int i = 0; i < teste.length; i++)
        {
            System.out.println("v: " + teste2[i]);
            System.out.println("w: " + teste[i]);

        }

    }
}