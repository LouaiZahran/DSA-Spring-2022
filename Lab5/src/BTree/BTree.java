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
    private List<IBTreeNode<K,V>> complete(ArrayList<IBTreeNode<K,V>> list, int i) {
        for (int j = list.size(); j < i; j++) {
            list.add(null);
        }
        return list;
    }
    static <l> ArrayList<l> subList(List<l> a, int from, int to){
        var returned = new ArrayList<l>();
        for(int i = from ; i < to ; i++){
            returned.add(a.get(i));
        }
        return returned;
    }
    private IBTreeNode<K,V> insert(IBTreeNode leaf, Comparable key, Object value,IBTreeNode L,IBTreeNode R,Stack<Integer> history){
        if(leaf==null){
            IBTreeNode newRoot = new BTreeNode<K,V>(minimumDegree*2-1,false,new ArrayList<K>(),new ArrayList<V>(),new ArrayList<IBTreeNode<K, V>>(),null);
            newRoot.getKeys().add(key);
            newRoot.getValues().add(value);
            newRoot.getChildren().set(0,L);
            newRoot.getChildren().set(1,R);
            L.setParent(newRoot);
            R.setParent(newRoot);
            return newRoot;
        }
        int i = history.pop();
        Boolean was_full =leaf.isfull();
        leaf.getKeys().add(i,key);
        leaf.getValues().add(i,value);
        leaf.getChildren().remove(i);
        leaf.getChildren().add(i, L);
        leaf.getChildren().add(i+1, R);
        if(L!=null){
            L.setParent(leaf);
        }
        if(R!=null){
            R.setParent(leaf);
        }
        if(was_full) {
            //split
            int med = (leaf.getKeys().size()) / 2;
            K mid_key = (K) leaf.getKeys().get(med);
            V val = (V) leaf.getValues().get(med);
            IBTreeNode<K, V> LeftChild = new BTreeNode<K, V>(leaf.getNumOfKeys(), leaf.isLeaf(), subList(leaf.getKeys(), 0, med), subList(leaf.getValues(), 0, med), complete(subList(leaf.getChildren(), 0, med + 1), leaf.getNumOfKeys() + 1), null);
            IBTreeNode<K, V> RightChild = new BTreeNode<K, V>(leaf.getNumOfKeys(), leaf.isLeaf(), subList(leaf.getKeys(), med + 1, leaf.getKeys().size()), subList(leaf.getValues(), med + 1, leaf.getValues().size()), complete(subList(leaf.getChildren(), med + 1, leaf.getChildren().size()), leaf.getNumOfKeys() + 1), null);
            for (IBTreeNode child : LeftChild.getChildren()) {
                if (child != null) {
                    child.setParent(LeftChild);
                }
            }
            for (IBTreeNode child : RightChild.getChildren()) {
                if (child != null) {
                    child.setParent(RightChild);
                }
            }
            return insert(leaf.getParent(), mid_key, val, LeftChild, RightChild, history);
        }else{
            return null;
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
        IBTreeNode<K,V> R = insert(leaf,key,value,null,null,insertionH);
        if(R!=null){
            root = R;
        }
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
    public Object search( Comparable key){
        IBTreeNode<K,V> current = root;
        while (current!=null) {
            int key_index=0;
            while (key_index < current.getKeys().size() && (key.compareTo((K) (current.getKeys().get(key_index))) >= 0)) {
                if (key.compareTo((K) (current.getKeys().get(key_index))) == 0) {
                    return current.getValues().get(key_index);
                }
                key_index++;
            }
            current = ((IBTreeNode) (current.getChildren().get(key_index)));
        }
        return null;
    }

    @Override
    public boolean delete(Comparable key) {
        delete(root,key);
        return false;
    }
    private void borrowFromLeftSibling(IBTreeNode parent,IBTreeNode node,IBTreeNode leftSibling, Comparable oldParentKey, Comparable newParentKey ){

        int oldParentIndex=parent.indexOfKey(oldParentKey);//get index in parent key to be demoted
        node.getKeys().add(0,oldParentKey); //add from left
        parent.getKeys().remove(oldParentIndex);  //remove old parent
        parent.getKeys().add(oldParentIndex,newParentKey); //add new parent
        leftSibling.getKeys().remove(newParentKey);     //remove new parent from his old node
    }
    private void borrowFromRightSibling(IBTreeNode parent,IBTreeNode node,IBTreeNode rightSibling, Comparable oldParentKey, Comparable newParentKey ){
        int oldParentIndex = parent.indexOfKey(oldParentKey); // get index in parent key to be demoted
        node.getKeys().add(node.getKeys().size()-1,oldParentKey);  // add from right
        parent.getKeys().remove(oldParentIndex); //remove old parent
        parent.getKeys().add(oldParentIndex,newParentKey);//add new parent
        rightSibling.getKeys().remove(oldParentKey); //remove new parent from his old node
    }
    private void mergeSiblings(IBTreeNode rightSibling, IBTreeNode parent,IBTreeNode node,Comparable key,int indexInParent) {
        node.getKeys().remove(key);
        node.getKeys().add(parent);
        node.getKeys().addAll(rightSibling.getKeys());

        parent.getKeys().remove(indexInParent); //remove parent
        parent.getChildren().remove(indexInParent+1); //remove right sibling
        parent.getChildren().remove(indexInParent); //remove node from children

        parent.getChildren().add(indexInParent,node);  //add new node
    }
    private boolean deleteLeaf(IBTreeNode node,Comparable key){

        if(node.getKeys().size()>node.getMinNumOfKeys()){//node leaf and has more than min keys
            node.getKeys().remove(key);
            return true;
        }
        else{ // borrow from right sibling or left sibling or merge
                //less than smallest key in parent then has no left sibling
                IBTreeNode parent=node.getParent();
                int indexInParent=0;
                boolean canBorrowFromRightSibling=true;
                boolean canBorrowFromLeftSibling=true;
                for(;indexInParent<parent.getKeys().size();indexInParent++){
                    if(key.compareTo(parent.getKeys().get(indexInParent)) > 0
                            &&  key.compareTo(parent.getKeys().get(indexInParent+1)) < 0 ){
                        break;
                    }
                    if(key.compareTo(parent.getKeys().get(0))<0) //its is the most left node
                    {
                        canBorrowFromLeftSibling=false;
                        break;
                    }
                    if(key.compareTo(parent.getKeys().get(parent.getKeys().size()-1))>0) {//its the most right node
                        canBorrowFromRightSibling=false;
                        break;
                    }
                }
                boolean hasLeftSibling = canBorrowFromLeftSibling & indexInParent >=0 & indexInParent <parent.getKeys().size();
                boolean hasRightSibling = canBorrowFromRightSibling & indexInParent >0 & indexInParent <=parent.getKeys().size();

                IBTreeNode leftSibling=hasLeftSibling?(IBTreeNode)(parent.getChildren().get(indexInParent)) :null;
                IBTreeNode rightSibling=hasRightSibling?(IBTreeNode)(parent.getChildren().get(indexInParent+2)):null;

                canBorrowFromRightSibling &= hasRightSibling && rightSibling.getKeys().size() > rightSibling.getMinNumOfKeys();
                canBorrowFromLeftSibling  &= hasLeftSibling && leftSibling.getKeys().size() > leftSibling.getMinNumOfKeys();

                if(canBorrowFromLeftSibling){
                    Comparable oldParentKey= (Comparable) parent.getKeys().get(indexInParent);
                    Comparable  newParentKey = (Comparable) leftSibling.getKeys().get(leftSibling.getKeys().size()-1);

                    borrowFromLeftSibling(parent,node,leftSibling,oldParentKey,newParentKey);
                    node.getKeys().remove(key); // remove the desired node
                    return  true;
                }else if(canBorrowFromRightSibling){
                    Comparable oldParentKey = (Comparable) parent.getKeys().get(indexInParent+1);
                    Comparable newParentKey = (Comparable) rightSibling.getKeys().get(0);

                    borrowFromRightSibling(parent,node,rightSibling,oldParentKey,newParentKey);
                    node.getKeys().remove(key); //remove the desired node
                    return  true;
                }
                else if(hasLeftSibling) {
                    node.getKeys().remove(key);
                    mergeSiblings(leftSibling,parent, node,key,indexInParent);
                } else if (hasRightSibling) {
                    node.getKeys().remove(key);
                    mergeSiblings(node,parent,rightSibling,key,indexInParent);
                }
        }
        return false;
    }


    private boolean delete(IBTreeNode node, Comparable key){
        if(node ==null)
            return false;
        int index=node.indexOfKey(key);
        if(index != -1)//found in this node
        {
            if(node.isLeaf()){
                deleteLeaf(node,key);
            }
        }
        return false;
    }


}