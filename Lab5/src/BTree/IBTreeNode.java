package BTree;

import java.util.List;


public interface IBTreeNode<K extends Comparable<K>, V> {

	/**
	 * @return the numOfKeys return number of keys in this node.
	 */
	int getNumOfKeys();

	/**
	 * @param numOfKeys number of keys in this node
	 */
	void setNumOfKeys(int numOfKeys);

	/**
	 * @return return min number of keys in this node.
	 */
	int getMinNumOfKeys();

	/**
	 * @return if the node is leaf or not.
	 */
	boolean isLeaf();

	/**
	 * @param isLeaf if the node is leaf or not.
	 */
	void setLeaf(boolean isLeaf);

	/**
	 * @return the keys return the list of keys of the given node.
	 */
	List<K> getKeys();

	/**
	 * @param keys the keys to set
	 */
	void setKeys(List<K> keys);

	/**
	 * @return the values return the list of values for the given node.
	 */
	List<V> getValues();

	/**
	 * @param values the values to set
	 */
	void setValues(List<V> values);

	/**
	 * @return the children return the list of children for the given node.
	 */
	List<IBTreeNode<K, V>> getChildren();

	/**
	 * @param children the children to set.
	 */
	void setChildren(List<IBTreeNode<K, V>> children);

	/**
	 * @return true if the node is full, false otherwise.
	 */
	public boolean isfull();

	/**
	 *
	 * @param parent the node that will hold the split result
	 * @param splittedIndex	at what index to add the split result
	 * @return the middle key where the split has happened
	 */
	public K split(IBTreeNode<K,V> parent,int splittedIndex);

	/**
	 *
	 * @param k key
	 * @return index of key in keys if not found then return -1
	 */
	int indexOfKey(Comparable k);

	/**
	 * return parent node
	 */
	BTreeNode getParent();

	void print();


}