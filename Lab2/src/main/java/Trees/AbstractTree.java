package Trees;

import java.util.NoSuchElementException;

/**
 * An abstract model of any BST
 * @param <T> wildcard to support any datatype
 */
public abstract class AbstractTree<T> implements Tree<T>{

    AbstractNode<T> root;
    int size = 0;

    @Override
    public AbstractNode<T> search(T obj){
        return search(root, obj);
    }

    private AbstractNode<T> search(AbstractNode node, T obj){
        if(node == null)
            return null;

        String currentValue = String.valueOf(node.getValue());
        String insertedValue = String.valueOf(obj);
        int comp = currentValue.compareToIgnoreCase(insertedValue);
        if(comp == 0)
            return node;
        else if(comp > 0)
            return search(node.getLeft(), obj);
        else
            return search(node.getRight(), obj);
    }

    @Override
    public boolean contains(T obj) {
        if(search(obj) == null)
            return false;
        return true;
    }

    @Override
    public abstract void insert(T obj) throws IllegalArgumentException;

    @Override
    public abstract void delete(T obj) throws NoSuchElementException;

    @Override
    public void clear(){
        root = null;
    }

    @Override
    public void traverse() {
        traverseInOrder(root);
    }

    private void traverseInOrder(AbstractNode node) {
        if (node == null)
            return;
        traverseInOrder(node.getLeft());
        System.out.println(node.getValue());
        traverseInOrder(node.getRight());
    }
    public int getSize(){
        return this.size;
    }


    @Override
    public T getMax(){
        if(root == null)
            return null;
        return getMax(root);
    }

    /**
     * Internally traversing the tree to get the maximum element
     * @param node the current maximum
     * @return the maximum value across the BST
     */
    protected T getMax(AbstractNode<T> node){
        if(!node.hasRight())
            return node.getValue();
        return getMax(node.getRight());
    }
    @Override
    public T getMin(){
        if(root == null)
            return null;
        return getMin(root);
    }

    /**
     * Internally traversing the tree to get the minimum element
     * @param node the current minimum
     * @return the minimum value across the BST
     */
    protected T getMin(AbstractNode<T> node){
        if(!node.hasLeft())
            return node.getValue();
        return getMin(node.getLeft());
    }

    @Override
    public AbstractNode<T> getRoot(){
        return root;
    }

    @Override
    public boolean isEmpty(){
        return size == 0;
    }

}
