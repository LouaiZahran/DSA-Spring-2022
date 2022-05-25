import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import BTree.BTree;
import SearchEngine.Doc;
import SearchEngine.ISearchEngine;
import SearchEngine.ISearchResult;
import SearchEngine.InputReader;
import SearchEngine.SearchEngine;

public class Main {
    public static void main(String args[]){
        BTree<Integer,String> t = new BTree(2);
        Integer c = 0;

        for (int i = 0; i < 6; i++) {
            t.insert((int)(Math.random()*100),"s");
        }
        t.getRoot().print(c);
        System.out.println("COUNT: "+c);
        for (int i = 0; i < 100; i++) {
            t.insert((int)(Math.random()*10000),"a");
        }
        t.getRoot().print(c);

        ISearchEngine engine = new SearchEngine();
        List<Doc> docs;
        try {
            docs = InputReader.read("Wikipedia Data Sample/wiki_00");
            for(Doc doc: docs) {
                System.out.println("Current DocumentID :" + doc.getId());
                System.out.println("URL: " + doc.getUrl());
                System.out.println("Title: " + doc.getTitle());
                System.out.println("Content: " + Arrays.toString(doc.getContent()));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        engine.indexWebPage("Wikipedia Data Sample/wiki_00");
        engine.print(8);
        List<ISearchResult> results = engine.searchByWordWithRanking("Beach");
        for(ISearchResult result: results)
            System.out.println(result.getId() + " " + result.getRank());
    }
}