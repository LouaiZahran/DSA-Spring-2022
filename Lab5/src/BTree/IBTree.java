package BTree;

public interface IBTree<K extends Comparable<K>, V> {

	/**
	 * Return the minimum degree of the given Btree. 
	 * The minimum degree of the Btree is sent as a parameter t the constructor.
	 * @return the provided minimum degree
	 */
	int getMinimumDegree();
	/**
	 * Return the root of the given Btree.
	 * @return the root of the tree
	 */
	IBTreeNode<K, V> getRoot();
	/**
	 * Insert the given key in the Btree. If the key is already in the Btree, ignore the call of this method.
	 * @param key the key of the node to be inserted
	 * @param value the value associated with the inserted key
	 */
	void insert(K key, V value);
	/**
	 * Search for the given key in the BTree.
	 * @param key the key to be searched for
	 * @return the associated value if the key is found
	 */
	V search(K key);
	/**
	 * Delete the node with the given key from the Btree.
	 * Return true in case of success and false otherwise.
	 * @param key the key of the node to be deleted
	 * @return true if the key is found and the node is successfully deleted, false otherwise
	 */
	boolean delete(K key);
	
}
