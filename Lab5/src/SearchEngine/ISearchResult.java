package SearchEngine;

public interface ISearchResult {

	/**
	 * Return the document ID which is an attribute provided with each Wikipedia document, please check the sample data files for more info.
	 * @return document ID
	 */
	String getId();
	void setId(String id);
	/**
	 * Return the frequency of the word in the given document.
	 * @return document frequency
	 */
	int getRank();
	void setRank(int rank);
}
