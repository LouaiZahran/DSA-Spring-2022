package Trees;

import org.w3c.dom.Node;

import java.util.NoSuchElementException;

//import colors of java to be used in JavaFx

import static java.awt.Color.RED;
import static java.awt.Color.BLACK;

public class RedBlackTree<T> extends AbstractTree<T> {


    public void insert(T obj) throws IllegalArgumentException{
        AbstractNode node = new RedBlackNode(obj);
        root = insert(root, node);
        fixTree((RedBlackNode) node);
    }

    private AbstractNode insert(AbstractNode node,AbstractNode newNode) throws IllegalArgumentException{
        if(node == null) {
            this.size++;
            return newNode;
        }

        String currentValue = String.valueOf(node.getValue());
        String insertedValue = String.valueOf(newNode.getValue());
        int comp = currentValue.compareToIgnoreCase(insertedValue);

        if(comp > 0){ //new string is lower than current
            node.setLeft(insert(node.getLeft(), newNode));
            ((RedBlackNode)node.getLeft()).setParentNode((RedBlackNode) node);
        }else if(comp < 0){
            node.setRight(insert(node.getRight(), newNode));
            ((RedBlackNode)node.getRight()).setParentNode((RedBlackNode) node);
        }else{        //duplicated element
            throw new IllegalArgumentException("Duplicated");
        }
        return node;
    }


    public void delete(T obj) throws NoSuchElementException {
        root = delete(root, obj);
        this.size--;
    }

    private AbstractNode delete(AbstractNode node, T obj) throws NoSuchElementException{
        if(node == null)
            throw new NoSuchElementException("String " + obj +" is not found");

        String currentValue = (String)node.getValue();
        String deletedValue = (String)obj;
        int comp = currentValue.compareToIgnoreCase(deletedValue);

        if(comp > 0){ //deleted string is lower than current
            node.setLeft(delete(node.getLeft(), obj));
        }else if(comp < 0){
            node.setRight(delete(node.getRight(), obj));
        }else{//Element found: delete action
            //one or no child
            if(node.getRight() == null)
                return node.getLeft();
            else if(node.getLeft()==null)
                return node.getRight();
            else{
                node.setValue(getMax(node.getLeft()));
                node.setLeft(delete(node.getLeft(),(T)node.getValue()));
            }

        }
  
        //TODO: fix return value
        return null;
    }
    private void fixTree(RedBlackNode node){
        RedBlackNode parent = node.getParentNode();
        if( parent!=root && parent!=null && parent.getColor() == RED){
            RedBlackNode grandParent =parent.getParentNode();
            RedBlackNode uncle = parent.isLeft()? (RedBlackNode)grandParent.getRight() : (RedBlackNode)grandParent.getLeft();
            if(uncle != null && uncle.getColor() == RED){
                recolor(parent,uncle,grandParent);
            }else if(parent.isLeft()) { //left heavy or left right "according to color"
                fixLeft(node,parent,grandParent);
            }else{
                fixRight(node,parent,grandParent);
            }
        }

        ((RedBlackNode)root).setColor(BLACK);
    }
    private void recolor(RedBlackNode parent,RedBlackNode uncle,RedBlackNode grandParent){
        parent.convertColor();
        uncle.convertColor();
        grandParent.convertColor();
        fixTree(grandParent);
    }
    private void fixLeft(RedBlackNode node,RedBlackNode parent,RedBlackNode grandParent){
        if(!node.isLeft()){//change left right to left left
            rotateLeft(parent);
            grandParent.convertColor();
            node.convertColor();
        }else {
            parent.convertColor();
            grandParent.convertColor();
        }
        rotateRight(grandParent);
        fixTree(node.isLeft()? parent :grandParent); //was a left left or left right
    }
    private void fixRight(RedBlackNode node,RedBlackNode parent,RedBlackNode grandParent){
        if(node.isLeft()){//change right left to right right
            rotateRight(parent);
            grandParent.convertColor();
            node.convertColor();
        }else {
            parent.convertColor();
            grandParent.convertColor();
        }
        rotateLeft(grandParent);
        fixTree(!node.isLeft()? parent :grandParent); //was a right right or  right left
    }
    private void rotateLeft(RedBlackNode node){
        RedBlackNode rightNode =(RedBlackNode) node.getRight();
        if(rightNode==null)
            return;
        RedBlackNode tempNode = (RedBlackNode) rightNode.getLeft();

        rightNode.setLeft(node);
        node.setRight(tempNode);

        if(tempNode!=null){
            tempNode.setParentNode(node);
        }
        rightNode.setParentNode(node.getParentNode());
        updateChildrenOfParentNode(node,rightNode);
        node.setParentNode(rightNode);
    }
    private void updateChildrenOfParentNode(RedBlackNode node, RedBlackNode tempNode){
        if(node.getParentNode()==null)
            root = tempNode;
        else if(node.isLeft())
            node.getParentNode().setLeft(tempNode);
        else
            node.getParentNode().setRight(tempNode);
    }
    private void rotateRight(RedBlackNode node){
        RedBlackNode leftNode =(RedBlackNode) node.getLeft();
        if(leftNode==null)
            return;
        RedBlackNode tempNode = (RedBlackNode) leftNode.getRight();

        leftNode.setRight(node);
        node.setLeft(tempNode);

        if(tempNode!=null){
            tempNode.setParentNode(node);
        }
        leftNode.setParentNode(node.getParentNode());
        updateChildrenOfParentNode(node,leftNode);
        node.setParentNode(leftNode);
    }

}
