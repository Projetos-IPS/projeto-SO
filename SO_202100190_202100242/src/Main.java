import java.io.FileNotFoundException;
import java.util.Scanner;

import Algorithm.Ajkp;
import Algorithm.FileLoader;

public class Main {
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        /** MAIN FINAL - NÃO APAGAR
        Scanner scanner = new Scanner(System.in);
        System.out.println(GREEN + "Enter command >" + RESET);
        String command = scanner.nextLine();

        String[] commandSplice = command.split(" ");
        int test = Integer.parseInt(commandSplice[0]);
        String fileName = commandSplice[1];
        int threads = Integer.parseInt(commandSplice[2]);
        int seconds = Integer.parseInt(commandSplice[3]);
        **/

        FileLoader file = new FileLoader();
        String fileName = "ex08";

        file.Load(fileName);
        //file.printFile();
        Ajkp sort = new Ajkp(fileName, 20);
        sort.printLowerBound();
        sort.printUpperBound();
    }
}