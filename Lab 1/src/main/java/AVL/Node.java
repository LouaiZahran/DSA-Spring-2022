package AVL;

import lombok.Data;

@Data
class Node{
    private String value;
    private Node left;
    private Node right;
    private int height;
    Node(String value){
        this.value=value;
    }
}