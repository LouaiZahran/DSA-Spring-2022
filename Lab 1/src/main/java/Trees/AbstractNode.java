package Trees;

import lombok.Data;

@Data
public abstract class AbstractNode<T> {
    private T value;
    private AbstractNode<T> left;
    private AbstractNode<T> right;
    AbstractNode(T value){
        this.value=value;
    }

    public boolean hasLeft(){
        return left != null;
    }

    public boolean hasRight(){
        return right != null;
    }
}
