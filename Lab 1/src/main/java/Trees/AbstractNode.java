package Trees;

import lombok.Data;

@Data
public abstract class AbstractNode {
    private Object value;
    private AbstractNode left;
    private AbstractNode right;
    AbstractNode(Object value){
        this.value=value;;
    }

    public boolean hasLeft(){
        return left != null;
    }

    public boolean hasRight(){
        return right != null;
    }
}
