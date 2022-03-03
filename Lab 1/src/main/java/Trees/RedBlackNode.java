package Trees;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

import static java.awt.Color.RED;
import static java.awt.Color.BLACK;

@Setter @Getter
public class RedBlackNode extends AbstractNode{

    private Color color= RED;
    private RedBlackNode parentNode;
    RedBlackNode(Object value) {
        super(value);
    }

    public void convertColor() {
        setColor(color == RED ? BLACK : RED);
    }
    public boolean isLeft(){
        return this==parentNode.getLeft();
    }
}
