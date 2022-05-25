import java.util.*;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import BTree.BTree;
import SearchEngine.Doc;
import SearchEngine.ISearchEngine;
import SearchEngine.ISearchResult;
import SearchEngine.InputReader;
import SearchEngine.SearchEngine;

public class Main {
    public static void main(String args[]) {
        BTree<Integer, Integer> t = new BTree(2);
        HashMap<Integer, Integer> input = new HashMap<>();
        for (int i = 0; i < 1000000; i++) {
            Integer k = (int) (Math.random() * 90000);
            Integer v = (int) (Math.random() * 90000);
            input.put(k,v);
            t.insert(k, v);
        }
        BiConsumer<Integer, Integer> fun = (x, y) -> {
            var found = t.search(x);
            System.out.println("->"+ x + " : found: "+ found +" expected: "+y );
            if(!found.equals(y)){
                throw new RuntimeException("INCORRECT");
            };
        };
        input.forEach(fun);
//        t.getRoot().print();

//        ISearchEngine engine = new SearchEngine();
//        List<Doc> docs;
//        try {
//            docs = InputReader.read("Wikipedia Data Sample/wiki_00");
//            for(Doc doc: docs) {
//                System.out.println("Current DocumentID :" + doc.getId());
//                System.out.println("URL: " + doc.getUrl());
//                System.out.println("Title: " + doc.getTitle());
//                System.out.println("Content: " + Arrays.toString(doc.getContent()));
//            }
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        engine.indexWebPage("Wikipedia Data Sample/wiki_00");
//        engine.print();
//        List<ISearchResult> results = engine.searchByWordWithRanking("Beach");
//        for(ISearchResult result: results)
//            System.out.println(result.getId() + " " + result.getRank());
    }
}