package Trees;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
class AVLNode<T> extends AbstractNode<T>{

    private int height;
    AVLNode(T value){
        super(value);
    }

}