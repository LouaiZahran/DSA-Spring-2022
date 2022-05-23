package BTree;

public class BTree<K extends Comparable<K>, V> implements IBTree{

    private int minimumDegree;
    private IBTreeNode<K, V> root;

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
        while(!current.isLeaf()){
            int key_index=-1;
            boolean found = false;
            while(key_index+2<current.getNumOfKeys()&& (found = key.compareTo((K) (current.getKeys().get(key_index+1)))>=0)){
                if(found){
                    current.getValues().set(key_index+1,value);
                    return;
                    // throw new RuntimeException("Key already exists");
                }
                key_index++;
            }
            next = ((IBTreeNode)(current.getChildren().get(key_index)));
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
        while(key.compareTo((K)current.getKeys().get(i))<0){
            i++;
        }
        if (key.compareTo((K)current.getKeys().get(i))!=0){
            current.getKeys().add(i,key);
            current.getValues().add(i,value);
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