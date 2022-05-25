package BTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static BTree.BTree.subList;

/*
a node with 2 keys b & c along with their values:

|         |---|         |---|         |
| child a | b | child c | d | child e |
|         |---|         |---|         |

*/
public class BTreeNode<K extends Comparable<K>, V> implements IBTreeNode{

    private IBTreeNode parent;
    private int numOfKeys;  //max no of keys
    private boolean leaf;
    private List<K> keys;
    private List<V> values;
    private List<IBTreeNode<K,V>> children;
    // public BTreeNode(int numOfKeys,boolean leaf){
    //     setNumOfKeys(numOfKeys);
    //     setLeaf(leaf);
    // }
    public BTreeNode(int numOfKeys, boolean leaf, List<K> keys, List<V> values, List<IBTreeNode<K, V>> children,IBTreeNode<K,V> Parent){
        setNumOfKeys(numOfKeys);
        setLeaf(leaf);
        setKeys(keys);
        setValues(values);
        setChildren(children);
//        if(children.size()!=numOfKeys+1){
//            for(int i=children.size();i<numOfKeys+1;i++){
//                this.children.add(null);
//            }
//        }
        this.parent = Parent;
    }

    @Override
    public int indexOfKey(Comparable k) {
        for (int i = 0; i < this.keys.size(); i++) {
            if (this.keys.get(i).compareTo((K) k)== 0) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getMinNumOfKeys() {
        return (int) Math.ceil( this.numOfKeys/2f);
    }


    @Override
    public boolean isfull(){
        return this.keys.size() == this.numOfKeys;
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
        int i=0;
        for (Comparable key : keys) {
            System.out.print(key+":"+values.get(i));
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

    @Override
    public IBTreeNode<K,V> getParent() {
        return parent;
    }

    @Override
    public void setParent(IBTreeNode parent) {
        this.parent = parent;
    }

    @Override
    public IBTreeNode<K,V> split(Stack history){
        return null;
//        int med = (this.keys.size()-1)/2;
//        K key = this.keys.get(med);
//        V val = this.values.get(med);
//        IBTreeNode<K,V> newParent =  this.getParent()==null ? new BTreeNode<K,V>(numOfKeys,false,new ArrayList<K>(),new ArrayList<V>(),new ArrayList<IBTreeNode<K, V>>(),null):this.parent;
//        IBTreeNode<K,V> LeftChild =  new BTreeNode<K,V>(numOfKeys, leaf, subList(keys,0, med), subList(values,0, med), complete(subList(children,0,med+1),numOfKeys+1),newParent);
//        IBTreeNode<K,V> RightChild = new BTreeNode<K,V>(numOfKeys, leaf, subList(keys,med+1,keys.size()), subList(values,med+1,values.size()), complete(subList(children,med+1,children.size()),numOfKeys+1),newParent);
//        for (IBTreeNode child:LeftChild.getChildren()) {
//            if(child!=null){
//                child.setParent(LeftChild);
//            }
//        }
//        for (IBTreeNode child:RightChild.getChildren()) {
//            if(child!=null){
//                child.setParent(RightChild);
//            }
//        }
////        this.getChildren().set(0,LeftChild);
////        this.getChildren().set(1,RightChild);
////        LeftChild.getChildren().forEach((child)->{if(child!=null){child.setParent(LeftChild);}});
////        RightChild.getChildren().forEach((child)->{if(child!=null){child.setParent(RightChild);}});
//
//        if(parent==null){
//            newParent.getChildren().set(0,LeftChild);
//            newParent.getChildren().set(1,RightChild);
//            newParent.getKeys().add(key);
//            newParent.getValues().add(val);
//            return newParent;
//        }
//        int mergeindex = (int) history.pop();
//        if(!parent.isfull()){
//            parent.getChildren().set(mergeindex, LeftChild);
//            parent.getKeys().add(mergeindex, key);
//            parent.getValues().add(mergeindex, val);
//            parent.getChildren().add(mergeindex+1, RightChild);
//            assert (parent.getChildren().get(parent.getChildren().size()-1)==null);
//            parent.getChildren().remove(parent.getChildren().size()-1);
//            return null;
//        }
//        else{
//            IBTreeNode possiblyNewRoot = parent.split(history);
//            if(mergeindex > parent.getKeys().size()){
//                mergeindex -= parent.getKeys().size()+1;
//            }
//            parent.getChildren().set(mergeindex, LeftChild);
//            parent.getKeys().add(mergeindex, key);
//            parent.getValues().add(mergeindex, val);
//            parent.getChildren().add(mergeindex+1, RightChild);
//            assert (parent.getChildren().get(parent.getChildren().size()-1)==null);
//            parent.getChildren().remove(parent.getChildren().size()-1);
//            return possiblyNewRoot;
//        }
    }
}