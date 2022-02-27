package Dictionary;

import AVL.*;
import IO.FileReader;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Dictionary {
    Tree tree = new AVL();

    public void load(String fileName) throws FileNotFoundException {
        tree.clear();
        insert(FileReader.loadFile(fileName));
    }

    private void insert(ArrayList<Object> list){
        for(Object obj: list)
            insert(obj);
    }

    public void insert(Object obj) throws IllegalArgumentException{
        tree.insert(obj);
    }

    public ArrayList<Boolean> lookup(ArrayList<Object> list){
        ArrayList<Boolean> ret = new ArrayList<>();
        for(Object obj: list)
            ret.add(lookup(obj));
        return ret;
    }

    public boolean lookup(Object obj){
        return tree.search(obj);
    }

    public ArrayList<Boolean> delete(ArrayList<Object> list){
        ArrayList<Boolean> ret = new ArrayList<>();
        for(Object obj: list)
            ret.add(delete(obj));
        return ret;
    }

    public boolean delete(Object obj){
        try{
            tree.delete(obj);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    public int getSize(){
        return tree.getSize();
    }
}
