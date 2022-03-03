package Trees;

import java.util.NoSuchElementException;

public abstract class AbstractTree implements Tree{
    Node root;
    int size = 0;

    @Override
    public boolean search(Object obj){
        return search(root, obj);
    }

    private boolean search(Node node, Object obj){
        if(node == null)
            return false;

        String currentValue = (String)node.getValue();
        String insertedValue = (String)obj;
        int comp = currentValue.compareToIgnoreCase(insertedValue);
        if(comp == 0)
            return true;
        else if(comp > 0)
            return search(node.getLeft(), obj);
        else
            return search(node.getRight(), obj);
    }

    @Override
    public abstract void insert(Object obj) throws IllegalArgumentException;

    @Override
    public abstract void delete(Object obj) throws NoSuchElementException;

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
    public Object getMax(){
        if(root == null)
            return null;
        return getMax(root);
    }

    protected Object getMax(AbstractNode node){
        if(!node.hasRight())
            return node.getValue();
        return getMax(node.getRight());
    }

}
