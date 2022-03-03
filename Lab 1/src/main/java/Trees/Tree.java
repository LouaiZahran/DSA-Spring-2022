package Trees;

import java.util.NoSuchElementException;

/**
 * An interface that any BST should follow
 */
public interface Tree<T> {
    /**
     * Search the tree for the specified key
     * @param obj the key to be searched
     * @return a boolean value that is true if the key is found and false otherwise
     */
    boolean search(T obj);

    /**
     * Inserts a node holding the value of the key to the tree
     * @param obj the value to be inserted
     * @throws IllegalArgumentException if the key is already in the tree
     */
    void insert(T obj) throws IllegalArgumentException;

    /**
     * Removes the node that holds the specified value
     * @param obj the value to be removed
     * @throws NoSuchElementException if the key is not found in the tree
     */
    void delete(T obj) throws NoSuchElementException;

    /**
     * Removes all nodes from the tree
     */
    void clear();

    /**
     * Prints an in-order traversal of the tree starting from its root
     */
    void traverse();

    /**
     * Returns the number of nodes in the tree
     * @return the size of the tree
     */
    int getSize();

    /**
     * Traverses the tree and returns the maximum value it holds
     * @return the value of the maximum key in the tree
     */
    T getMax();
}
