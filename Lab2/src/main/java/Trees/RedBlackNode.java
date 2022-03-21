package Trees;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

import static java.awt.Color.RED;
import static java.awt.Color.BLACK;

/**
 * Our implementation for the RBTree Node
 * @author Louai Zahran
 * @author AbdelRahman Bahaa
 */
@Setter @Getter
public class RedBlackNode<T> extends AbstractNode<T>{

    private Color color;
    private RedBlackNode parentNode;
    private boolean isNullLeaf;

    RedBlackNode(){
        super(null);
    };

    RedBlackNode(T value) {
        super(value);
        color = RED;
        isNullLeaf = false;
        setLeft(createNullLeaf());
        setRight(createNullLeaf());
    }

    private AbstractNode<T> createNullLeaf(){
        RedBlackNode<T> node = new RedBlackNode<>();
        node.isNullLeaf = true;
        node.color = BLACK;
        return node;
    }

    /**
     * Flips the color of the node from red to black and vice versa
     */
    public void convertColor() {
        setColor(color == RED ? BLACK : RED);
    }

    /***
     * Checks for the presence of a left child
     * @return true if a left child exists, false otherwise
     */
    public boolean isLeft(){
        if(parentNode == null)
            return false;
        return this==parentNode.getLeft();
    }
}
