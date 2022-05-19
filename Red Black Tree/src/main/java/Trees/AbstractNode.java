package Trees;

import lombok.Data;

/**
 * An abstract model of any node
 */
@Data
public abstract class AbstractNode<T> {
    private T value;
    private AbstractNode<T> left;
    private AbstractNode<T> right;
    AbstractNode(T value){
        this.value=value;
    }

    /***
     * Checks for the presence of a left child
     * @return true if a left child exists, false otherwise
     */
    public boolean hasLeft(){
        return left != null;
    }

    /***
     * Checks for the presence of a right child
     * @return true if a right child exists, false otherwise
     */
    public boolean hasRight(){
        return right != null;
    }
}
