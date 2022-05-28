package BTree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BTreeNode<K extends Comparable<K>, V> implements IBTreeNode{

    public int n;
    public K[] key;
    public BTreeNode<K, V>[] child;
    public boolean leaf = true;

    public BTreeNode(int T){
        key = (K[])(new ArrayList<K>(2 * T - 1).toArray());
        child = (BTreeNode<K, V>[])(new ArrayList<BTreeNode<K, V>>(2 * T).toArray());
    }

    public int Find(Comparable<K> k) {
        for (int i = 0; i < this.n; i++) {
            if (this.key[i] == k) {
                return i;
            }
        }
        return -1;
    };

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
    public List getKeys() {
        return Arrays.stream(key).toList();
    }

    @Override
    public void setKeys(List keys) {
        key = (K[])keys.toArray();
    }

    @Override
    public List getValues() {
        return null;
    }

    @Override
    public void setValues(List values) {

    }

    @Override
    public List<IBTreeNode> getChildren() {
        return null;
    }

    @Override
    public void setChildren(List children) {

    }

}