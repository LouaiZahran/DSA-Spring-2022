package IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
    public static ArrayList<Object> loadFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        ArrayList<Object> list = new ArrayList<>();
        while(scanner.hasNext())
            list.add(scanner.nextLine());
        scanner.close();
        return list;
    }
}
