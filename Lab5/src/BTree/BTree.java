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
            current = new BTreeNode<K,V>(2*this.minimumDegree-1,true,new ArrayList<K>(),new ArrayList<V>(),new ArrayList<IBTreeNode<K,V>>());
            current.getKeys().add(key);
            current.getValues().add(value);
            this.root = current;
            return;
        }
        while(!current.isLeaf()){
            int key_index=-1;
            boolean found = false;
            while(key_index+2<current.getNumOfKeys() && (found = key.compareTo((K) (current.getKeys().get(key_index+1)))>=0)){
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

        int i = 0;
        while(i<current.getKeys().size()&&key.compareTo((K)current.getKeys().get(i))>0){
            i++;
        }
        if(current.getKeys().size()==i){
            if(!current.isfull()){
                current.getKeys().add(i,key);
                current.getValues().add(i,value);
                return;
            }
            else{
                BTreeNode newChild = new BTreeNode<K,V>(2*minimumDegree-1,true,new ArrayList<K>(),new ArrayList<V>(),new ArrayList<IBTreeNode<K,V>>());

                newChild.getKeys().add(key);
                newChild.getValues().add(value);
                current.getChildren().set(i,newChild);
                current.setLeaf(false);
                return;
            }
        }
        if (key.compareTo((K)current.getKeys().get(i))!=0){
            if(!current.isfull()){
                current.getKeys().add(i,key);
                current.getValues().add(i,value);
            }
            else{
                BTreeNode newChild = new BTreeNode<K,V>(2*minimumDegree-1,true,new ArrayList<K>(),new ArrayList<V>(),new ArrayList<IBTreeNode<K,V>>());
                newChild.getKeys().add(key);
                newChild.getValues().add(value);
                if(key.compareTo((K)current.getKeys().get(i))<0){
                    current.setLeaf(false);
                    current.getChildren().set(i,newChild);
                }
                else if(key.compareTo((K)current.getKeys().get(i))>0){
                    current.setLeaf(false);
                    current.getChildren().set(i+1,newChild);
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
        delete(root,key);
        return false;
    }
    private void borrowFromLeftSibling(IBTreeNode parent,IBTreeNode node,IBTreeNode leftSibling, Comparable oldParentKey, Comparable newParentKey ,){

        int oldParentIndex=parent.indexOfKey(oldParentKey);//get index in parent key to be demoted
        node.getKeys().add(0,oldParentKey); //add from left
        parent.getKeys().remove(oldParentKey);  //remove old parent
        parent.getKeys().add(parent.getKeys().size()-1,newParentKey); //add new parent
        leftSibling.getKeys().remove(newParentKey);     //remove new parent from his old node
    }
    private boolean delete(IBTreeNode node, Comparable key){
        if(node ==null)
            return false;
        int index=node.indexOfKey(key);
        if(index != -1)//found in this node
        {
            if(node.isLeaf()){
                if(node.getKeys().size()>node.getMinNumOfKeys()){//node leaf and has more than min keys
                   node.getKeys().remove(key);
                   return true;
                }
                else{ // borrow from right sibling or left sibling or merge
                    //less than smallest key in parent then has no left sibling
                    IBTreeNode parent=node.getParent();
                    if(key.compareTo(parent.getKeys().get(0))<0){

                        IBTreeNode rightSibling=(IBTreeNode)(parent.getChildren().get(1));
                        Comparable oldParentKey= (Comparable) parent.getKeys().get(0);
                        Comparable newParentKey = (Comparable) rightSibling.getKeys().get(0);


                        int oldParentIndex = parent.indexOfKey(oldParentKey); // get index in parent key to be demoted
                        node.getKeys().add(node.getKeys().size()-1,oldParentKey);  // add from right
                        parent.getKeys().remove(oldParentKey); //remove old parent
                        parent.getKeys().add(0,newParentKey);//add new parent
                        rightSibling.getKeys().remove(oldParentKey); //remove new parent from his old node
                        node.getKeys().remove(key); //remove the desired node
                        return true;
                    }
                    //more than greatest key in parent then has no right sibling
                    else if(key.compareTo(parent.getKeys().get(parent.getKeys().size()-1))>0){
                        IBTreeNode leftSibling=(IBTreeNode)(parent.getChildren().get(parent.getKeys().size()-1));
                        Comparable oldParentKey= (Comparable) parent.getKeys().get(parent.getKeys().size()-1);
                        Comparable newParentKey = (Comparable) leftSibling.getKeys().get(leftSibling.getKeys().size()-1);

                        int oldParentIndex=parent.indexOfKey(oldParentKey);//get index in parent key to be demoted
                        node.getKeys().add(0,oldParentKey); //add from left
                        parent.getKeys().remove(oldParentKey);  //remove old parent
                        parent.getKeys().add(parent.getKeys().size()-1,newParentKey); //add new parent
                        leftSibling.getKeys().remove(newParentKey);     //remove new parent from his old node
                        node.getKeys().remove(key); // remove the desired node
                        return true;

                    }//has left and right siblings
                    else{
                        int indexInParent=0;
                        for(;indexInParent<parent.getKeys().size();indexInParent++){
                            if(key.compareTo(parent.getKeys().get(indexInParent)) > 0
                            &&  key.compareTo(parent.getKeys().get(indexInParent+1)) < 0 ){
                                break;
                            }
                        }
                        IBTreeNode leftSibling=(IBTreeNode)(parent.getChildren().get(indexInParent));
                        IBTreeNode rightSibling=(IBTreeNode)(parent.getChildren().get(indexInParent+1));
                        //left sibling
                        if(leftSibling.getKeys().size()<node.getMinNumOfKeys())
                        {
                            Comparable oldParentKey= (Comparable) parent.getKeys().get(indexInParent);
                            Comparable  newParentKey = (Comparable) leftSibling.getKeys().get(leftSibling.getKeys().size()-1);
                            
                            int oldParentIndex=parent.indexOfKey(oldParentKey);//get index in parent key to be demoted
                            node.getKeys().add(0,oldParentKey); //add from left
                            parent.getKeys().remove(oldParentKey);  //remove old parent
                            parent.getKeys().add(indexInParent,newParentKey); //add new parent
                            leftSibling.getKeys().remove(newParentKey);     //remove new parent from his old node
                            node.getKeys().remove(key); // remove the desired node
                            return  true;
                        }//right sibling
                        else if(rightSibling.getKeys().size()<node.getMinNumOfKeys()){
                            Comparable oldParentKey = (Comparable) parent.getKeys().get(indexInParent+1);
                            Comparable newParentKey = (Comparable) rightSibling.getKeys().get(0);
                            int oldParentIndex = parent.indexOfKey(oldParentKey); // get index in parent key to be demoted
                            node.getKeys().add(node.getKeys().size()-1,oldParentKey);  // add from right
                            parent.getKeys().remove(oldParentKey); //remove old parent
                            parent.getKeys().add(indexInParent,newParentKey);//add new parent
                            rightSibling.getKeys().remove(oldParentKey); //remove new parent from his old node
                            node.getKeys().remove(key); //remove the desired node
                            return  true;
                        }
                    }
                }
            }
        }
    }

}