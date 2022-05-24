package BTree;

import java.security.AllPermission;
import java.util.ArrayList;
import java.util.List;

/*
a node with 2 keys b & c along with their values:

|         |---|         |---|         |
| child a | b | child c | d | child e |
|         |---|         |---|         |

*/
public class BTreeNode<K extends Comparable<K>, V> implements IBTreeNode{

    private int numOfKeys;  //max no of keys ?
    private boolean leaf;
    private List<K> keys;
    private List<V> values;
    private List<IBTreeNode<K,V>> children;
    // public BTreeNode(int numOfKeys,boolean leaf){
    //     setNumOfKeys(numOfKeys);
    //     setLeaf(leaf);
    // }
    public BTreeNode(int numOfKeys, boolean leaf, List<K> keys, List<V> values, List<IBTreeNode<K, V>> childChildren){
        setNumOfKeys(numOfKeys);
        setLeaf(leaf);
        setKeys(keys);
        setValues(values);
        setChildren(childChildren);
        if(childChildren!=null || childChildren.get(0)!=null){
            for(int i=0;i<numOfKeys+1;i++){
                this.children.add(null);
            }
        }
    }
    @Override
    public boolean isfull(){
        return this.keys.size() == this.numOfKeys;
    }

    static private<l> ArrayList<l> subList(List<l> a,int from,int to){
        var returned = new ArrayList<l>();
        for(int i = from;i<to;i++){
            returned.add(a.get(i));
        }
        return returned;
    }
    @Override
    public K split(IBTreeNode parent,int splittedIndex){
        int med = (this.keys.size()-1)/2;
        BTreeNode<K,V> LeftChild = new BTreeNode<K,V>(numOfKeys, leaf, subList(keys,0, med), subList(values,0, med), subList(children,0,med+1));
        parent.getChildren().set(splittedIndex, LeftChild);
        parent.getKeys().add(splittedIndex, this.keys.get(med));
        parent.getValues().add(splittedIndex, this.values.get(med));
        BTreeNode<K,V> RightChild = new BTreeNode<K,V>(numOfKeys, leaf, subList(keys,med+1,keys.size()), subList(values,med+1,values.size()), subList(children,med+1,children.size()));
        parent.getChildren().add(splittedIndex+1, RightChild);
        return keys.get(med);
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
    public List<K> getKeys() {
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
    public List<IBTreeNode<K,V>> getChildren() {
        return this.children;
    }

    @Override
    public void setChildren(List children) {
        this.children = children;
    }
    @Override
    public void print(){
        int i = 0;
        for (Comparable key : keys) {
            System.out.print(key+"");
            System.out.print(" | ");
            i++;
        }
        System.out.println();
        for (IBTreeNode<K,V> ibTreeNode : children) {
            if(ibTreeNode!=null){
                ibTreeNode.print();
            }
        }
    }
}