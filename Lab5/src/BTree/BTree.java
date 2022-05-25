package BTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BTree<K extends Comparable<K>, V> implements IBTree{

    private int minimumDegree;
    private IBTreeNode<K, V> root;


    public BTree(int minimumDegree) {
        this.minimumDegree = minimumDegree;
    }

    @Override
    public int getMinimumDegree() {
        return minimumDegree;
    }

    @Override
    public IBTreeNode getRoot() {
        return root;
    }

    private void insert(IBTreeNode leaf, Comparable key, Object value,Stack<Integer> history){
//        int i = 0;
//        while(i<current.getKeys().size()&&key.compareTo((K)current.getKeys().get(i))>0){
//            i++;
//        }
        int i = history.pop();
        Boolean was_full =leaf.isfull();
        leaf.getKeys().add(i,key);
        leaf.getValues().add(i,value);

        if(was_full){
            IBTreeNode newRoot_perhaps = leaf.split(history);
            if(newRoot_perhaps!=null){
                root = newRoot_perhaps;
            }
        }
    }
    private IBTreeNode<K,V> getLeafNode(Comparable key, Object value, Stack<Integer> insertionHistory){
        IBTreeNode current = root;
        if(current==null){
            BTreeNode<K,V> newroot = new BTreeNode<K, V>(2*minimumDegree-1,true,new ArrayList<K>(),new ArrayList<V>(),new ArrayList<IBTreeNode<K, V>>(),null);
            newroot.getKeys().add((K)key);
            newroot.getValues().add(value);
            this.root = newroot;
            return null;
        }
        while(!current.isLeaf()){
            int key_index=0;
            while(key_index<current.getKeys().size() && (key.compareTo((K) (current.getKeys().get(key_index)))>=0)){
                if(key.compareTo((K) (current.getKeys().get(key_index)))==0){
                    current.getValues().set(key_index,value);
                    return null;
                }
                key_index++;
            }
            insertionHistory.push(key_index);
            current = ((IBTreeNode)(current.getChildren().get(key_index)));
        }
        int key_index=0;
        while(key_index<current.getKeys().size() && (key.compareTo((K) (current.getKeys().get(key_index)))>=0)){
            if(key.compareTo((K) (current.getKeys().get(key_index)))==0){
                current.getValues().set(key_index,value);
                return null;
            }
            key_index++;
        }
        insertionHistory.push(key_index);
        return current;
    }
    @Override
    public void insert(Comparable key, Object value) {
        Stack<Integer> insertionH = new Stack<>();
        IBTreeNode<K,V> leaf = getLeafNode(key,value,insertionH);
        if(leaf==null){return;}
        insert(leaf,key,value,insertionH);
    }
//    @Override
//    public void insert(Comparable key, Object value) {
//        IBTreeNode current = root,next;
//        if(current==null){
//            current = new BTreeNode<K,V>(2*this.minimumDegree-1,true,new ArrayList<K>(),new ArrayList<V>(),new ArrayList<IBTreeNode<K,V>>());
//            current.getKeys().add(key);
//            current.getValues().add(value);
//            this.root = current;
//            return;
//        }
//        while(!current.isLeaf()){
//            int key_index=-1;
//            while(key_index+2<current.getKeys().size() && (key.compareTo((K) (current.getKeys().get(key_index+1)))>=0)){
//                key_index++;
//                if(key.compareTo((K) (current.getKeys().get(key_index)))==0){
//                    current.getValues().set(key_index,value);
//                    return;
//                    // throw new RuntimeException("Key already exists");
//                }
//            }
//            if(key_index<0){
//                key_index=0;
//            }
//            next = ((IBTreeNode)(current.getChildren().get(key_index)));
//            if(next == null){
//                current.setLeaf(false);
//                current.getChildren().set(key_index,new BTreeNode<K,V>(2*minimumDegree-1,true,new ArrayList<K>(),new ArrayList<V>(),new ArrayList<IBTreeNode<K,V>>()));
//                next = ((IBTreeNode)(current.getChildren().get(key_index)));
//            }
//            if(!next.isfull()){
//                current = next;
//            }
//            else{
//                K med =(K)(next.split(current, key_index));
//                if(key.compareTo(med)<0){
//                    current = (IBTreeNode)current.getChildren().get(key_index);
//                }
//                else if(key.compareTo(med)>0){
//                    current = (IBTreeNode)current.getChildren().get(key_index+1);
//                }
//                else{
//                    assert (key.compareTo((K)current.getKeys().get(key_index))==0);
//                    current.getValues().set(key_index,value);
//                    return;
//                }
//            }
//        }
//
//        int i = 0;
//        while(i<current.getKeys().size()&&key.compareTo((K)current.getKeys().get(i))>0){
//            i++;
//        }
//        if(current.getKeys().size()==i){
//            if(!current.isfull()){
//                current.getKeys().add(key);
//                current.getValues().add(value);
//                return;
//            }
//            else{
//                BTreeNode newChild = new BTreeNode<K,V>(2*minimumDegree-1,true,new ArrayList<K>(),new ArrayList<V>(),new ArrayList<IBTreeNode<K,V>>());
//                newChild.getKeys().add(key);
//                newChild.getValues().add(value);
//                current.getChildren().set(i,newChild);
//                current.setLeaf(false);
//                return;
//            }
//        }
//        else if (key.compareTo((K)current.getKeys().get(i))!=0){
//            if(!current.isfull()){
//                current.getKeys().add(i,key);
//                current.getValues().add(i,value);
//                return;
//            }
//            else{
//                BTreeNode newChild = new BTreeNode<K,V>(2*minimumDegree-1,true,new ArrayList<K>(),new ArrayList<V>(),new ArrayList<IBTreeNode<K,V>>());
//                newChild.getKeys().add(key);
//                newChild.getValues().add(value);
//                if(key.compareTo((K)current.getKeys().get(i))<0){
//                    current.setLeaf(false);
//                    current.getChildren().set(i,newChild);
//                }
//                else if(key.compareTo((K)current.getKeys().get(i))>0){
//                    current.setLeaf(false);
//                    current.getChildren().set(i+1,newChild);
//                }
//            }
//        }
//        else{
//            current.getValues().set(i,value);
//        }
//    }

    @Override
    public Object search(Comparable key) {
        return search(root, key);
    }

    private Object search(IBTreeNode node, Comparable key){
        if(node == null)
            return null;

        if(key.compareTo(node.getKeys().get(0)) == -1)
            return search((IBTreeNode) node.getChildren().get(0), key);

        int length = node.getKeys().size();
        if(key.compareTo(node.getKeys().get(length-1))==1)
            return search((IBTreeNode) node.getChildren().get(length),key);

        for(int i=0; i<length - 1; i++){
            K currentKey = (K) node.getKeys().get(i);
            K nextKey = (K) node.getKeys().get(i+1);
            if(key.compareTo(currentKey) == 0){
                return node.getValues().get(i);
            }
            if(key.compareTo(currentKey) == 1 && key.compareTo(nextKey) == -1)
                return search((IBTreeNode) node.getChildren().get(i+1), key);
        }
        if(key.compareTo(node.getKeys().get(length - 1)) == 0)
            return node.getValues().get(length - 1);
        else
            return search((IBTreeNode) node.getChildren().get(length), key);
    }

    @Override
    public boolean delete(Comparable key) {
        return false;
    }

}