package SearchEngine;

import BTree.BTree;
import BTree.IBTree;

import java.io.File;
import java.util.*;

public class SearchEngine implements ISearchEngine{

    private final int t = 5;

    Set<String> insertedDocuments;
    IBTree<String, IBTree<String, Integer>> engine; //BTree of BTrees (documents) of (word, frequency) key-value pairs

    public SearchEngine(){
        insertedDocuments = new HashSet<>();
        engine = new BTree<String, IBTree<String, Integer>>(t);
    }

    @Override
    public void indexWebPage(String filePath) {
        List<Doc> docs;
        try {
            docs = InputReader.read(filePath);
        }catch (Exception e){
            System.out.println("File not found!");
            return;
        }

        for(Doc doc: docs){
            IBTree<String, Integer> indexedDocument = new BTree<String, Integer>(t);
            HashMap<String, Integer> freq = new HashMap<>();
            for(String word: doc.getContent()){
                int currentFreq = freq.getOrDefault(word, 0);
                freq.put(word, currentFreq + 1);
            }

            for(Map.Entry<String, Integer> entry: freq.entrySet()){
                indexedDocument.insert(entry.getKey(), entry.getValue());
            }

            engine.insert(doc.getId(), indexedDocument);
            insertedDocuments.add(doc.getId());
        }
    }

    @Override
    public void indexDirectory(String directoryPath) {
        File path = new File(directoryPath);
        File[] files = path.listFiles();
        if(files == null){
            System.out.println("No files found in the specified directory!");
            return;
        }

        for(File file: files)
            indexWebPage(file.getAbsolutePath());
    }

    @Override
    public void deleteWebPage(String filePath) {
        List<Doc> docs;
        try {
            docs = InputReader.read(filePath);
        }catch (Exception e){
            System.out.println("File not found!");
            return;
        }

        for(Doc doc: docs) {
            engine.delete(doc.getId());
            insertedDocuments.remove(doc.getId());
        }
    }

    @Override
    public List<ISearchResult> searchByWordWithRanking(String word) {
        List<ISearchResult> list = new ArrayList<>();
        for(String documentId: insertedDocuments){
            IBTree<String, Integer> document = engine.search(documentId);
            int frequency = document.search(word);
            if(frequency != 0)
                list.add(new SearchResult(documentId, frequency));
        }
        return list;
    }

    @Override
    public List<ISearchResult> searchByMultipleWordWithRanking(String sentence) {
        List<ISearchResult> list = new ArrayList<>();
        String[] words = sentence.split("[\r\n\t\f]+|\n+");
        for(String documentId: insertedDocuments){
            int minimumFrequency = Integer.MAX_VALUE;
            IBTree<String, Integer> document = engine.search(documentId);
            for(String word: words){
                int frequency = document.search(word);
                minimumFrequency = Math.min(minimumFrequency, frequency);
            }
            if(minimumFrequency != 0)
                list.add(new SearchResult(documentId, minimumFrequency));
        }
        return list;
    }
    @Override
    public void print(int count){
        engine.getRoot().print(count);
        insertedDocuments.forEach((string)->{
            System.out.println(string);
        });
    }
}
