package BTree;

import java.util.List;

public class BTreeNode<K extends Comparable<K>, V> implements IBTreeNode{

    private int numOfKeys;
    private boolean leaf;
    private List<K> keys;
    private List<V> values;
    private List<IBTreeNode> children;

    public BTreeNode(int numOfKeys, boolean leaf, List<K> keys, List<V> values, List<IBTreeNode> children){
        setNumOfKeys(numOfKeys);
        setLeaf(leaf);
        setKeys(keys);
        setValues(values);
        setChildren(children);
    }

    @Override
    public int getNumOfKeys() {
        return this.numOfKeys;
    }

    @Override
    public void setNumOfKeys(int numOfKeys) {
        this.numOfKeys = numOfKeys;
    }

    @Override
    public boolean isLeaf() {
        return this.leaf;
    }

    @Override
    public void setLeaf(boolean isLeaf) {
        this.leaf = isLeaf;
    }

    @Override
    public List getKeys() {
        return this.keys;
    }

    @Override
    public void setKeys(List keys) {
        this.keys = keys;
    }

    @Override
    public List getValues() {
        return this.values;
    }

    @Override
    public void setValues(List values) {
        this.values = values;
    }

    @Override
    public List<IBTreeNode> getChildren() {
        return this.children;
    }

    @Override
    public void setChildren(List children) {
        this.children = children;
    }
}
