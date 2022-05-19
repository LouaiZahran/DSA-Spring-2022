package IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * File IO
 */
public class FileReader {
    /**
     * Reads the content of the specified file
     * @param fileName The name of the files containing words to be converted to a list
     * @return The words written in the file as a list
     * @throws FileNotFoundException if the file is not found
     */
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
