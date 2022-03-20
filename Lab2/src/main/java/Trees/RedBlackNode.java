package Trees;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

import static java.awt.Color.RED;
import static java.awt.Color.BLACK;

@Setter @Getter
public class RedBlackNode<T> extends AbstractNode<T>{

    private Color color = RED;
    private RedBlackNode parentNode;
    private boolean isNullLeaf;

    RedBlackNode(){
        super(null);
    };

    RedBlackNode(T value) {
        super(value);
        isNullLeaf = false;
        setLeft(createNullLeaf());
        setRight(createNullLeaf());
    }

    private AbstractNode<T> createNullLeaf(){
        RedBlackNode<T> node = new RedBlackNode<>();
        isNullLeaf = true;
        color = BLACK;
        return node;
    }

    public void convertColor() {
        setColor(color == RED ? BLACK : RED);
    }
    public boolean isLeft(){
        if(parentNode == null)
            return false;
        return this==parentNode.getLeft();
    }
}
