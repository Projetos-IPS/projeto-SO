package Algorithm;
import java.util.Scanner;
import java.io.*;

public class AJKPBS {
    public AJKPBS() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        FileLoader file = new FileLoader();

        System.out.println("Document name: ");
        String fileName = scanner.nextLine();


        file.Load(fileName);



    }
    public int[] BeamSearch()
    {
        //usa o solution
    }
}
