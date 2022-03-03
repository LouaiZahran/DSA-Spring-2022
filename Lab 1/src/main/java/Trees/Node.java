package Trees;

import lombok.Data;

@Data
public abstract class AbstractNode {
    private Object value;
    private Node left;
    private Node right;
    private int height;
    Node(Object value){
        this.value=value;
    }

    public boolean hasLeft(){
        return left != null;
    }

    public boolean hasRight(){
        return right != null;
    }
}