package SearchEngine;

import java.util.List;

public interface ISearchEngine {

	/**
	 * Given a file path that contains one or more Wikipedia 
	 * documents in the same XML format of the provided sample data, 
	 * you are required to index these documents content to be able to w
	 * search through them later.
	 * @param filePath the path to the file to be indexed
	 */
	void indexWebPage(String filePath);
	
	/**
	 * Given a root directory, you are required to index all the files 
	 * in the given directory/sub directories.
	 * The files will be in the same format as described in the previous function, 
	 * you are required to index these documents content to be able 
	 * to search through them later.
	 * @param directoryPath the directory in which the files to be indexed exist
	 */
	void indexDirectory(String directoryPath);
	
	/**
	 * Given a file path that contains one or more Wikipedia documents 
	 * in the same format as described in the previous function, 
	 * you are required to delete these documents IDs from the B-Tree index if they were indexed before
	 * @param filePath the path to the file to be deleted
	 */
	void deleteWebPage(String filePath);

	/**
	 * Given a search query of one word, you are required to return a list 
	 * of SearchResult object that contains the documents IDs that contains 
	 * this search word, along with its rank. The rank of a Wikipedia document 
	 * is the frequency of the given search word in this document. 
	 * Please note that the search words are not case sensitive.
	 * @param word the word to be searched for
	 * @return a list of search results containing IDs and ranks of documents in which word is found
	 */
	List<ISearchResult> searchByWordWithRanking(String word);
	
	/**
	 * Similar to the previous function but the search query will consist of multiple words 
	 * and you are required to return the Wikipedia documents that has this set of word in ANY order. 
	 * Also note that the query search words are not case sensitive. 
	 * The rank of a document is the min frequency of the given words in this document
	 * @param sentence the words to be searched for
	 * @return a list of search results containing IDs and ranks of documents in which words are found
	 */
	List<ISearchResult> searchByMultipleWordWithRanking(String sentence);

    void print();
}
