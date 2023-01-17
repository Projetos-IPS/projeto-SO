import java.io.*;
import Algorithm.FileLoader;
public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        FileLoader file = new FileLoader();
        file.Load(args[0]);

        System.out.println(file.getItems());

    }
}