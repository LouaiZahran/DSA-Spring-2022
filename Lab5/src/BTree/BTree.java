package BTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BTree<K extends Comparable<K>, V> implements IBTree{

    private final int minimumDegree;
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
        for(int i = from ; i < to && i<a.size() ; i++){
            returned.add(a.get(i));
        }
        return returned;
    }
    private IBTreeNode<K,V> insert(IBTreeNode leaf, Comparable key, Object value,IBTreeNode L,IBTreeNode R,Stack<Integer> history){
        if(leaf==null){
            IBTreeNode newRoot = new BTreeNode<K,V>(minimumDegree*2-1,false,new ArrayList<K>(),new ArrayList<V>(),new ArrayList<IBTreeNode<K, V>>(),null);
            newRoot.getKeys().add(key);
            newRoot.getValues().add(value);
            newRoot.getChildren().add(0,L);
            newRoot.getChildren().add(1,R);
            L.setParent(newRoot);
            R.setParent(newRoot);
            return newRoot;
        }
        int i = history.pop();
        Boolean was_full =leaf.isfull();
        leaf.getKeys().add(i,key);
        leaf.getValues().add(i,value);
        if(L!=null) {
            leaf.getChildren().remove(i);
            leaf.getChildren().add(i, L);
            leaf.getChildren().add(i + 1, R);
            L.setParent(leaf);
            R.setParent(leaf);
        }
        if(was_full) {
            //split
            int med = (leaf.getKeys().size()) / 2;
            K mid_key = (K) leaf.getKeys().get(med);
            V val = (V) leaf.getValues().get(med);
            IBTreeNode<K, V> LeftChild = new BTreeNode<K, V>(leaf.getNumOfKeys(), leaf.isLeaf(), subList(leaf.getKeys(), 0, med), subList(leaf.getValues(), 0, med), subList(leaf.getChildren(), 0, med + 1), null);
            IBTreeNode<K, V> RightChild = new BTreeNode<K, V>(leaf.getNumOfKeys(), leaf.isLeaf(), subList(leaf.getKeys(), med + 1, leaf.getKeys().size()), subList(leaf.getValues(), med + 1, leaf.getValues().size()), subList(leaf.getChildren(), med + 1, leaf.getChildren().size()), null);
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
            while(key_index<current.getKeys().size() && (key.compareTo(current.getKeys().get(key_index))>=0)){
                if(key.compareTo(current.getKeys().get(key_index))==0){
                    current.getValues().set(key_index,value);
                    return null;
                }
                key_index++;
            }
            insertionHistory.push(key_index);
            current = ((IBTreeNode)(current.getChildren().get(key_index)));
        }
        int key_index=0;
        while(key_index<current.getKeys().size() && (key.compareTo(current.getKeys().get(key_index))>=0)){
            if(key.compareTo(current.getKeys().get(key_index))==0){
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

    @Override
    public Object search( Comparable key){
        IBTreeNode<K,V> current = root;
        while (current!=null) {
            int key_index=0;
            while (key_index < current.getKeys().size() && (key.compareTo(current.getKeys().get(key_index)) >= 0)) {
                if (key.compareTo(current.getKeys().get(key_index)) == 0) {
                    return current.getValues().get(key_index);
                }
                key_index++;
            }
            if(key_index>=current.getChildren().size()){
                return null;
            }
            current = current.getChildren().get(key_index);
        }
        return null;
    }
    private IBTreeNode searchNode(Comparable key){
        IBTreeNode<K,V> current = root;
        while (current!=null) {
            int key_index=0;
            while (key_index < current.getKeys().size() && key.compareTo(current.getKeys().get(key_index))>= 0) {
                if (key.compareTo(current.getKeys().get(key_index)) == 0) {
                    return current;
                }
                key_index++;
            }
            current = key_index<current.getChildren().size()  ? current.getChildren().get(key_index) : null;
        }
        return current;
    }
    @Override
    public boolean delete(Comparable key) {
        IBTreeNode node = searchNode(key);
        if(node==null)
            return false;
        return delete(node,key);
    }
    private void borrowFromLeftSibling(IBTreeNode parent,IBTreeNode node,IBTreeNode leftSibling, Comparable oldParentKey, Comparable newParentKey ){
        int oldParentIndex=parent.indexOfKey(oldParentKey);//get index in parent key to be demoted
        node.getValues().add(0,parent.getValues().get(oldParentIndex));
        node.getKeys().add(0,oldParentKey); //add from left
        parent.getKeys().remove(oldParentIndex);  //remove old parent
        parent.getValues().remove(oldParentIndex);
        parent.getKeys().add(oldParentIndex,newParentKey); //add new parent
        parent.getValues().add(oldParentIndex,leftSibling.getValues().get(leftSibling.getValues().size()-1));
        leftSibling.getKeys().remove(newParentKey);     //remove new parent from his old node
        leftSibling.getValues().remove(leftSibling.getValues().size()-1);
    }
    private void borrowFromRightSibling(IBTreeNode parent,IBTreeNode node,IBTreeNode rightSibling, Comparable oldParentKey, Comparable newParentKey ){
        int oldParentIndex = parent.indexOfKey(oldParentKey); // get index in parent key to be demoted
        node.getKeys().add(node.getKeys().size()-1,oldParentKey);  // add from right
        node.getValues().add(node.getValues().size()-1,oldParentKey);
        parent.getKeys().remove(oldParentIndex); //remove old parent
        parent.getValues().remove(oldParentIndex);
        parent.getKeys().add(oldParentIndex,newParentKey);//add new parent
        parent.getValues().add(oldParentIndex,rightSibling.getValues().get(0));
        rightSibling.getKeys().remove(oldParentKey); //remove new parent from his old node
        rightSibling.getValues().remove(0);
    }
    private void mergeSiblings(IBTreeNode node, IBTreeNode parent,IBTreeNode rightSibling,int indexInParent) {
        node.getKeys().add(parent.getKeys().get(indexInParent));
        node.getValues().add(parent.getValues().get(indexInParent));
        node.getKeys().addAll(rightSibling.getKeys());
        node.getValues().addAll(rightSibling.getValues());
        if(parent != this.root)
            delete(parent,(Comparable) parent.getKeys().get(indexInParent));//remove parent
        else{
            parent.getKeys().remove(indexInParent);//in root
            parent.getValues().remove(indexInParent);
            if(parent.getKeys().size()==0)
            {
                this.root=node;
            }
        }
        parent.getChildren().remove(indexInParent + 1); //remove right sibling
        parent.getChildren().remove(indexInParent); //remove node old children
        parent.getKeys().remove(indexInParent);
        parent.getValues().remove(indexInParent);
        if(node != this.root) {//add new node
            parent.getChildren().add(indexInParent, node);
        }
    }
    private boolean deleteLeaf(IBTreeNode node,Comparable key){
        boolean isDeleted=false;

        if(node.getKeys().size()> node.getMinNumOfKeys()){//node leaf and has more than min keys
            int index=node.indexOfKey(key);
            node.getValues().remove(index);
            isDeleted |=node.getKeys().remove(key);
        }
        else{ // borrow from right sibling or left sibling or merge
            //less than smallest key in parent then has no left sibling
            IBTreeNode parent= node.getParent();
            int indexInParent=0;
            boolean hasLeftSibling =true;
            boolean hasRightSibling =true;
            for (;indexInParent<parent.getChildren().size();indexInParent++){
                if(node==parent.getChildren().get(indexInParent)) {
                    break;
                }
            }
            if(indexInParent==parent.getChildren().size()-1){
                hasRightSibling=false;
            }
            if(indexInParent==0)
            {
                hasLeftSibling=false;
            }
            IBTreeNode leftSibling=hasLeftSibling?(IBTreeNode)(parent.getChildren().get(indexInParent-1)) :null;
            IBTreeNode rightSibling=hasRightSibling?(IBTreeNode)(parent.getChildren().get(indexInParent+1)):null;
            boolean canBorrowFromRightSibling=true;
            boolean canBorrowFromLeftSibling=true;
            canBorrowFromRightSibling &= hasRightSibling && rightSibling.getKeys().size() > rightSibling.getMinNumOfKeys();
            canBorrowFromLeftSibling  &= hasLeftSibling && leftSibling.getKeys().size() > leftSibling.getMinNumOfKeys();

            if(canBorrowFromLeftSibling){
                Comparable oldParentKey= (Comparable) parent.getKeys().get(indexInParent-1);
                Comparable  newParentKey = (Comparable) leftSibling.getKeys().get(leftSibling.getKeys().size()-1);

                borrowFromLeftSibling(parent, node,leftSibling,oldParentKey,newParentKey);
                node.getValues().remove(node.indexOfKey(key));
                isDeleted |=node.getKeys().remove(key); // remove the desired node
            }else if(canBorrowFromRightSibling){
                Comparable oldParentKey = (Comparable) parent.getKeys().get(indexInParent);
                Comparable newParentKey = (Comparable) rightSibling.getKeys().get(0);

                borrowFromRightSibling(parent, node,rightSibling,oldParentKey,newParentKey);
                node.getValues().remove(node.indexOfKey(key));
                isDeleted |= node.getKeys().remove(key); //remove the desired node
            }
            else if(hasLeftSibling) {
                node.getValues().remove(node.indexOfKey(key));
                isDeleted |= node.getKeys().remove(key);
                mergeSiblings(leftSibling,parent, node,indexInParent-1);
            } else if (hasRightSibling) {
                node.getValues().remove(node.indexOfKey(key));
                isDeleted |=  node.getKeys().remove(key);
                mergeSiblings(node,parent,rightSibling,indexInParent);
            }
        }
        return isDeleted;
    }
    private void switchKeys(IBTreeNode node,int nodeKeyindex , IBTreeNode node2,int node2KeyIndex)
    {
        Comparable tempKey=(Comparable) node.getKeys().get(nodeKeyindex); //temp = node
        V tempValue = (V) node.getValues().get(nodeKeyindex);
        node.getKeys().remove(nodeKeyindex);
        node.getValues().remove(nodeKeyindex);
        node.getKeys().add(nodeKeyindex,node2.getKeys().get(node2KeyIndex));//node = node2
        node.getValues().add(nodeKeyindex,node2.getValues().get(node2KeyIndex));
        node2.getValues().remove(node2KeyIndex);
        node2.getKeys().remove(node2KeyIndex);
        node2.getValues().add(node2KeyIndex,tempValue);
        node2.getKeys().add(node2KeyIndex,tempKey); //node2 = temp
    }
    private boolean deleteNonLeaf(IBTreeNode node, Comparable key) {
        int nodeIndex=node.indexOfKey(key);
        boolean isDeleted=false;
        IBTreeNode succ =getSuccessor(node,nodeIndex);
        IBTreeNode pred=getPredecessor(node,nodeIndex);
        if(pred.getKeys().size() > node.getMinNumOfKeys()){
            switchKeys(node,nodeIndex,pred,pred.getKeys().size()-1);
            pred.getValues().remove(pred.getKeys().size()-1);
            pred.getKeys().remove(pred.getKeys().size()-1);
            isDeleted=true;
        }else if ( succ!=null&&succ.getKeys().size() > node.getMinNumOfKeys()){
            switchKeys(node,nodeIndex,succ,0);
            succ.getValues().remove(0);
            succ.getKeys().remove(0);
            isDeleted=true;
        }else
        {
            switchKeys(node,nodeIndex,pred,pred.getKeys().size()-1);
            return  deleteLeaf(pred,(Comparable) pred.getKeys().get(pred.getKeys().size()-1));
        }
        return isDeleted;
    }
    private IBTreeNode getPredecssorHelper(IBTreeNode node){
        //traverse most right
        if(node.isLeaf()){
            return node;
        }else{
            return getPredecssorHelper((IBTreeNode)(node.getChildren().get(node.getChildren().size()-1)));
        }
    }
    private IBTreeNode getPredecessor(IBTreeNode node,int nodeIndex){
        //get left node
        return getPredecssorHelper((IBTreeNode) (node.getChildren().get(nodeIndex)));
    }
    private IBTreeNode getSuccessorHelper(IBTreeNode node){
        //traverse to most left
        if(node.isLeaf() ){
            return node;
        }
        else{
            return getSuccessorHelper((IBTreeNode)(node.getChildren().get(0)));
        }
    }
    private IBTreeNode getSuccessor(IBTreeNode node,int nodeIndex){
        //get right node
       /* if(nodeIndex >=node.getChildren().size()-1)
            return null;*/
        return getSuccessorHelper((IBTreeNode) (node.getChildren().get(nodeIndex+1)));
    }


    private boolean delete(IBTreeNode node, Comparable key){
        if(node ==null)
            return false;
        if(node.isLeaf() && node == this.root) //root
        {
            int i = node.indexOfKey(key);
            node.getValues().remove(i);
            return node.getKeys().remove(key);
        }
        else if(node.isLeaf()){
            return deleteLeaf(node,key);
        }else{
            return deleteNonLeaf(node,key);
        }
    }
}