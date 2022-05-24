import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;;
import SearchEngine.Doc;
import SearchEngine.ISearchEngine;
import SearchEngine.ISearchResult;
import SearchEngine.InputReader;
import SearchEngine.SearchEngine;

public class Main {
    public static void main(String args[]){
        ISearchEngine engine = new SearchEngine();
        try {
            List<Doc> docs = InputReader.read("Wikipedia Data Sample/wiki_00");
            for(Doc doc: docs) {
                System.out.println("Current DocumentID :" + doc.getId());
                System.out.println("URL: " + doc.getUrl());
                System.out.println("Title: " + doc.getTitle());
                System.out.println("Content: " + Arrays.toString(doc.getContent()));
            }
            engine.indexWebPage("Wikipedia Data Sample/wiki_00");
            List<ISearchResult> results = engine.searchByWordWithRanking("Beach");
            for(ISearchResult result: results)
                System.out.println(result.getId() + " " + result.getRank());
        }
        catch (Exception e){
            System.out.println("File Not Found!");
        }
    }
}