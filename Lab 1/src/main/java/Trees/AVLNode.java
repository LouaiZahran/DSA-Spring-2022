package Trees;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
class AVLNode extends AbstractNode{

    private int height;
    AVLNode(Object value){
        super(value);
    }

}