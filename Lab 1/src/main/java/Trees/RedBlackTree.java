package Trees;

import java.util.NoSuchElementException;

//import colors of java to be used in JavaFx

import static java.awt.Color.RED;
import static java.awt.Color.BLACK;

public class RedBlackTree extends AbstractTree {

    RedBlackNode root;

    public void insert(Object obj) throws IllegalArgumentException{
        AbstractNode node=new RedBlackNode(obj);
        root = insert(root, node);
    }

    private AbstractNode insert(AbstractNode node,AbstractNode newNode) throws IllegalArgumentException{
        if(node == null) {
            this.size++;
            return newNode;
        }

        String currentValue = (String)node.getValue();
        String insertedValue = (String)newNode.getValue();
        int comp = currentValue.compareToIgnoreCase(insertedValue);

        if(comp > 0){ //new string is lower than current
            node.setLeft(insert(node.getLeft(), newNode));
        }else if(comp < 0){
            node.setRight(insert(node.getRight(), newNode));
        }else{        //duplicated element
            throw new IllegalArgumentException("Duplicated");
        }
    }


    public void delete(Object obj) throws NoSuchElementException {
        root = delete(root, obj);
        this.size--;
    }

    private AbstractNode delete(AbstractNode node, Object obj) throws NoSuchElementException{
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
                node.setLeft(delete(node.getLeft(),node.getValue()));
            }

        }

    }

}
