package Dictionary;

import Trees.*;
import IO.FileReader;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * The class implementing the functionality of a dictionary
 */
public class Dictionary {
    Tree tree = new AVL();

    /**
     * Clears the dictionary and insert all the words inserted in the specified file
     * @param fileName The name of the files containing words to be added to the dictionary
     * @throws FileNotFoundException if the file is not found
     */
    public void load(String fileName) throws FileNotFoundException {
        tree.clear();
        insert(FileReader.loadFile(fileName));
    }

    /**
     * Adds a list of words one by one
     * @param list the words to be inserted
     */
    private void insert(ArrayList<Object> list){
        for(Object obj: list)
            insert(obj);
    }

    /**
     * Adds a single word into the dictionary
     * @param obj the word to be added
     * @throws IllegalArgumentException if the word is already in the dictionary
     */
    public void insert(Object obj) throws IllegalArgumentException{
        tree.insert(obj);
    }

    /**
     * Searches for a list of words
     * @param list the words to be searched for
     * @return a list indicating whether each element was found or not
     */
    public ArrayList<Boolean> lookup(ArrayList<Object> list){
        ArrayList<Boolean> ret = new ArrayList<>();
        for(Object obj: list)
            ret.add(lookup(obj));
        return ret;
    }

    /**
     * Searches for a single word
     * @param obj the word to be searched for
     * @return whether the word was found or not
     */
    public boolean lookup(Object obj){
        return tree.search(obj);
    }

    /**
     * Removes a list of words from the dictionary
     * @param list the words to be removed
     * @return a list indicating whether each element was successfully removed or not
     */
    public ArrayList<Boolean> delete(ArrayList<Object> list){
        ArrayList<Boolean> ret = new ArrayList<>();
        for(Object obj: list)
            ret.add(delete(obj));
        return ret;
    }

    /**
     * Removes a word from the dictionary
     * @param obj the word to be removed
     * @return whether the word was removed successfully or not
     */
    public boolean delete(Object obj){
        try{
            tree.delete(obj);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    /**
     * Gets the number of words in the dictionary
     * @return the size of the dictionary
     */
    public int getSize(){
        return tree.getSize();
    }
}
