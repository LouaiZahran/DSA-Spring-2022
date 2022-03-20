package Trees;

import java.util.NoSuchElementException;

public abstract class AbstractTree<T> implements Tree<T>{

    AbstractNode<T> root;
    int size = 0;

    @Override
    public boolean search(T obj){
        return search(root, obj);
    }

    private boolean search(AbstractNode node, T obj){
        if(node == null)
            return false;

        String currentValue = String.valueOf(node.getValue());
        String insertedValue = String.valueOf(obj);
        int comp = currentValue.compareToIgnoreCase(insertedValue);
        if(comp == 0)
            return true;
        else if(comp > 0)
            return search(node.getLeft(), obj);
        else
            return search(node.getRight(), obj);
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
        return root == null;
    }

}
