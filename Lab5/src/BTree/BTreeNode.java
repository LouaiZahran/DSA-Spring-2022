package BTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BTreeNode<K extends Comparable<K>, V> implements IBTreeNode<K, V>{

    public int n;
    public List<K> key;
    public List<V> value;
    public List<IBTreeNode<K, V>> child;
    public boolean leaf = true;

    public BTreeNode(int T){
        key = new ArrayList<>();
        value = new ArrayList<>();
        child = new ArrayList<>();
        for(int i=0; i < 2 * T - 1; i++)
            key.add(null);

        for(int i=0; i < 2 * T - 1; i++)
            value.add(null);

        for(int i=0; i < 2 * T; i++)
            child.add(null);

    }

    public int Find(Comparable<K> k) {
        for (int i = 0; i < this.n; i++) {
            if (this.key.get(i) == k) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getNumOfKeys() {
        return this.n;
    }

    @Override
    public void setNumOfKeys(int numOfKeys) {
        this.n = numOfKeys;
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
    public List<K> getKeys() {
        return key;
    }

    @Override
    public void setKeys(List<K> keys) {
        key = keys;
    }

    @Override
    public List<V> getValues() {
        return value;
    }

    @Override
    public void setValues(List<V> values) {
        value = values;
    }

    @Override
    public List<IBTreeNode<K, V>> getChildren() {
        return child;
    }

    @Override
    public void setChildren(List<IBTreeNode<K, V>> children) {
        child = children;
    }

}