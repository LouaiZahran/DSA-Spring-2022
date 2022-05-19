import Dictionary.Dictionary;
import IO.FileReader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The main class
 */
public class Main {

    /**
     * Driver code
     * @param args unused
     */
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to our dictionary!");

        while(true){
            String command = scanner.nextLine();

            if(command.equals("load")){
                try {
                    dictionary.load("dictionary.txt");
                    System.out.println("Dictionary is loaded successfully!");
                } catch (FileNotFoundException e) {
                    System.out.println("dictionary.txt is not found");
                }

            }else if(command.equals("size")){
                System.out.println(dictionary.getSize());

            }else if(command.equals("insert")){
                String str = scanner.nextLine();
                try{
                    dictionary.insert(str);
                }catch (IllegalArgumentException e){
                    System.out.println("ERROR: Word already in the dictionary!");
                }

            }else if(command.equals("lookup")){
                String str = scanner.nextLine();
                System.out.println(dictionary.lookup(str)?"YES":"NO");

            }else if(command.equals("remove")){
                String str = scanner.nextLine();
                if(dictionary.delete(str))
                    System.out.println("The word is deleted successfully.");
                else
                    System.out.println("ERROR: Word is not in the dictionary.");

            }else if(command.equals("lookup -b")){
                ArrayList<Object> words;
                try{
                    words = FileReader.loadFile("queries.txt");
                    ArrayList<Boolean> result = dictionary.lookup(words);
                    for(int i=0; i<words.size(); i++){
                        System.out.println(words.get(i) + ": " + (result.get(i)?"YES":"NO"));
                    }
                }catch(Exception e){
                    System.out.println("ERROR: queries.txt is not found");
                }

            }else if(command.equals("remove -b")){
                ArrayList<Object> words;
                try{
                    words = FileReader.loadFile("deletions.txt");
                    ArrayList<Boolean> result = dictionary.delete(words);
                    for(int i=0; i<words.size(); i++){
                        System.out.println(words.get(i) + ": " + (result.get(i)?"Deleted":"ERROR: not found"));
                    }
                }catch(Exception e){
                    System.out.println("ERROR: deletions.txt is not found");
                }

            }else{
                System.out.println("Command is not supported");
            }
        }
    }
}
