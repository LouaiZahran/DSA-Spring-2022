package BTree;

import java.util.ArrayList;

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

    @Override
    public void insert(Comparable key, Object value) {
        IBTreeNode current = root,next;
        if(current==null){
            // public BTreeNode(int numOfKeys, boolean leaf, List<K> keys, List<V> values, List<IBTreeNode<K, V>> childChildren)
            current = new BTreeNode<K,V>(2*this.minimumDegree-1,true,new ArrayList<K>(),new ArrayList<V>(),new ArrayList<IBTreeNode<K,V>>());
            current.getKeys().add(key);
            current.getChildren().add(0,null);
            current.getChildren().add(1,null);
            current.getValues().add(value);
            this.root = current;
            return;
        }
        while(!current.isLeaf()){
            System.out.println("hi");
            int key_index=-1;
            boolean found = false;
            while(key_index+2<current.getNumOfKeys() && (found = key.compareTo((K) (current.getKeys().get(key_index+1)))<=0)){
                key_index++;
                if(found){
                    current.getValues().set(key_index,value);
                    return;
                    // throw new RuntimeException("Key already exists");
                }
            }
            if(key_index<0){
                key_index=0;
            }
            System.out.println(current.getKeys().size());
            next = ((IBTreeNode)(current.getChildren().get(key_index)));
            if(next == null){
                current.setLeaf(false);
                current.getChildren().set(key_index,new BTreeNode<K,V>(2*minimumDegree-1,true,new ArrayList<K>(),new ArrayList<V>(),new ArrayList<IBTreeNode<K,V>>()));
                next = ((IBTreeNode)(current.getChildren().get(key_index)));
            }
            if(!next.isfull()){
                System.out.println("hi");
                current = next;
            }
            else{
                K med =(K)(next.split(current, key_index));
                if(key.compareTo(med)<0){
                    current = (IBTreeNode)current.getChildren().get(key_index);
                }
                else if(key.compareTo(med)>0){
                    current = (IBTreeNode)current.getChildren().get(key_index+1);
                }
                else{
                    assert (key.compareTo((K)current.getKeys().get(key_index))==0);
                    current.getValues().set(key_index,value);
                    return;
                }
            }
        }
        if(current.getKeys().size()==0){
            current.getKeys().add(key);
            current.getChildren().add(null);
            current.getChildren().add(null);
            current.getValues().add(value);
            return;
        }
        int i = 0;
        while(i<current.getKeys().size()&&key.compareTo((K)current.getKeys().get(i))<0){
            i++;
        }
        if (key.compareTo((K)current.getKeys().get(i))!=0){
            if(!current.isfull()){
                current.getKeys().add(i,key);
                current.getChildren().add(i,null);
                current.getChildren().add(i+1,null);
                current.getValues().add(i,value);
            }
            else{
                if(key.compareTo((K)current.getKeys().get(i))<0){
                    current.setLeaf(false);
                    current.getChildren().set(i,new BTreeNode<K,V>(2*minimumDegree-1,true,new ArrayList<K>(),new ArrayList<V>(),new ArrayList<IBTreeNode<K,V>>()));
                }
                else if(key.compareTo((K)current.getKeys().get(i))>0){
                    current.setLeaf(false);
                    current.getChildren().set(i+1,new BTreeNode<K,V>(2*minimumDegree-1,true,new ArrayList<K>(),new ArrayList<V>(),new ArrayList<IBTreeNode<K,V>>()));
                }
                else{
                    assert (key.compareTo((K)current.getKeys().get(i))==0);
                    current.getValues().set(i,value);
                    return;
                }
            }
        }
        else{
            current.getValues().set(i,value);
        }

    }

    @Override
    public Object search(Comparable key) {
        return search(root, key);
    }

    private Object search(IBTreeNode node, Comparable key){
        if(node == null)
            return null;

        if(key.compareTo(node.getKeys().get(0)) == -1)
            return search((IBTreeNode) node.getChildren().get(0), key);

        int length = node.getNumOfKeys();
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