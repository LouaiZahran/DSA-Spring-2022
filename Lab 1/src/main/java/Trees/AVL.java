package Trees;

import java.util.NoSuchElementException;

public class AVL extends AbstractTree{

    AVLNode root;

    //TODO: implement this method
    public int getHeight(){
        return 0;
    }

    private void calculateNewHeight(AVLNode node){
        if (node == null)
            return;
        AVLNode left = (AVLNode) node.getLeft();
        AVLNode right = (AVLNode) node.getRight();
        int leftHeight = node.hasLeft()?  left.getHeight():-1;
        int rightHeight = node.hasRight()? right.getHeight():-1;
        node.setHeight(1 + Math.max(leftHeight, rightHeight));
    }


    private int getBalanceFactor(AbstractNode node){
        if (node == null)
            return 0;
        return (node.hasLeft()?((AVLNode)(node.getLeft())).getHeight():-1) - (node.hasRight()?((AVLNode)(node.getRight())).getHeight():-1);
    }


    public void insert(Object obj) throws IllegalArgumentException{
        root = insert(root, obj);
    }

    private AVLNode insert(AbstractNode node, Object obj) throws IllegalArgumentException{
        if(node == null) {
            this.size++;
            return new AVLNode(obj);
        }

        String currentValue = (String)node.getValue();
        String insertedValue = (String)obj;
        int comp = currentValue.compareToIgnoreCase(insertedValue);

        if(comp > 0){ //new string is lower than current
            node.setLeft(insert(node.getLeft(), obj));
        }else if(comp < 0){
            node.setRight(insert(node.getRight(), obj));
        }else{        //duplicated element
            throw new IllegalArgumentException("Duplicated");
        }

        calculateNewHeight((AVLNode) node);
        return rotate(node);
    }


    public void delete(Object obj) throws NoSuchElementException {
        root = delete(root, obj);
        this.size--;
    }

    private AVLNode delete(AVLNode node, Object obj) throws NoSuchElementException{
        if(node == null)
            throw new NoSuchElementException("String " + obj +" is not found");

        String currentValue = (String)node.getValue();
        String deletedValue = (String)obj;
        int comp = currentValue.compareToIgnoreCase(deletedValue);

        if(comp > 0){ //deleted string is lower than current
            node.setLeft(delete((AVLNode) node.getLeft(), obj));
        }else if(comp < 0){
            node.setRight(delete((AVLNode) node.getRight(), obj));
        }else{//Element found: delete action
              //one or no child
            if(node.getRight() == null)
                return (AVLNode) node.getLeft();
            else if(node.getLeft()==null)
                return (AVLNode) node.getRight();
            else{
                node.setValue(getMax(node.getLeft()));
                node.setLeft(delete((AVLNode) node.getLeft(),node.getValue()));
            }
            calculateNewHeight(node);
            return rotate(node);
        }

        calculateNewHeight(node);
        return rotate(node);
    }

    private AVLNode rotate(AbstractNode nodeToRotate){
        int balanceFactor=getBalanceFactor(nodeToRotate);
        if (balanceFactor >1) //left
        {
            if (getBalanceFactor(nodeToRotate.getLeft()) < 0) { //right
                nodeToRotate.setLeft(rotateLeft(nodeToRotate));
            }
            return rotateRight(nodeToRotate);
        }else if (balanceFactor <-1) //right
        {
            if (getBalanceFactor(nodeToRotate.getLeft()) > 0) { //left
                nodeToRotate.setLeft(rotateRight(nodeToRotate));
            }
            return rotateLeft(nodeToRotate);
        }
        return (AVLNode) nodeToRotate;
    }
    private AVLNode rotateLeft(AbstractNode node){
        AVLNode rightNode = (AVLNode) node.getRight();
        AVLNode tempSubTree = (AVLNode) rightNode.getLeft();

        rightNode.setLeft(node);
        node.setRight(tempSubTree);

        calculateNewHeight((AVLNode) node);
        calculateNewHeight(rightNode);

        return rightNode;

    }
    private AVLNode rotateRight(AbstractNode node){
        AVLNode leftNode = (AVLNode) node.getLeft();
        AVLNode tempSubTree = (AVLNode) leftNode.getRight();

        leftNode.setRight(node);
        node.setLeft(tempSubTree);

        calculateNewHeight((AVLNode) node);
        calculateNewHeight(leftNode);
        return leftNode;
    }

}
