package Trees;

//import colors of java to be used in JavaFx

import java.awt.*;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

import static java.awt.Color.RED;
import static java.awt.Color.BLACK;

public class RedBlackTree<T> extends AbstractTree<T> {


    @Override
    public void delete(T obj) throws NoSuchElementException {
        root = delete((RedBlackNode<T>) root, obj);
    }

    /**
     * Main delete method of red black tree.
     */
    public RedBlackNode<T> delete(RedBlackNode<T> root, T data) {
        AtomicReference<RedBlackNode<T>> rootReference = new AtomicReference<>();
        delete(root, data, rootReference);
        if(rootReference.get() == null) {
            return root;
        } else {
            return rootReference.get();
        }
    }

    public void insert(T obj) throws IllegalArgumentException{
        AbstractNode node = new RedBlackNode(obj);
        root = insert(root, node);
        fixTree((RedBlackNode) node);
    }

    private AbstractNode insert(AbstractNode node,AbstractNode newNode) throws IllegalArgumentException{
        if(node == null) {
            this.size++;
            return newNode;
        }

        String currentValue = String.valueOf(node.getValue());
        String insertedValue = String.valueOf(newNode.getValue());
        int comp = currentValue.compareToIgnoreCase(insertedValue);

        if(comp > 0){ //new string is lower than current
            node.setLeft(insert(node.getLeft(), newNode));
            ((RedBlackNode)node.getLeft()).setParentNode((RedBlackNode) node);
        }else if(comp < 0){
            node.setRight(insert(node.getRight(), newNode));
            ((RedBlackNode)node.getRight()).setParentNode((RedBlackNode) node);
        }else{        //duplicated element
            throw new IllegalArgumentException("Duplicated");
        }
        return node;
    }

    /**
     * Using atomicreference because java does not provide mutable wrapper. Its like
     * double pointer in C.
     */
    private void delete(RedBlackNode<T> node, T data, AtomicReference<RedBlackNode<T>> rootRef) {
        if(node == null || node.isNullLeaf()) {
            return;
        }
        if(node.getValue() == data) {
            //if node to be deleted has 0 or 1 null children then we have
            //deleteOneChild use case as discussed in video.
            if(((RedBlackNode<T>)(node.getLeft())).isNullLeaf() || ((RedBlackNode<T>)(node.getRight())).isNullLeaf()) {
                deleteOneChild(node, rootRef);
            } else {
                //otherwise look for the inorder successor in right subtree.
                //replace inorder successor data at root data.
                //then delete inorder successor which should have 0 or 1 not null child.
                RedBlackNode<T> inorderSuccessor = findSmallest((RedBlackNode<T>) node.getRight());
                node.setValue(inorderSuccessor.getValue());
                delete((RedBlackNode<T>) node.getRight(), inorderSuccessor.getValue(), rootRef);
            }
        }
        //search for the node to be deleted.
        if((int)node.getValue() < (int)data) {
            delete((RedBlackNode<T>) node.getRight(), data, rootRef);
        } else {
            delete((RedBlackNode<T>) node.getLeft(), data, rootRef);
        }
    }

    private RedBlackNode<T> findSmallest(RedBlackNode<T> subtreeRoot) {
        RedBlackNode<T> prev = null;
        while(subtreeRoot != null && !subtreeRoot.isNullLeaf()) {
            prev = subtreeRoot;
            subtreeRoot = (RedBlackNode<T>) subtreeRoot.getLeft();
        }
        return prev != null ? prev : subtreeRoot;
    }

    /**
     * Assumption that node to be deleted has either 0 or 1 non leaf child
     */
    private void deleteOneChild(RedBlackNode<T> nodeToBeDelete, AtomicReference<RedBlackNode<T>> rootRef) {
        RedBlackNode<T> child = (RedBlackNode<T>) (((RedBlackNode<T>)(nodeToBeDelete.getRight())).isNullLeaf() ? nodeToBeDelete.getLeft() : nodeToBeDelete.getRight());
        //replace node with either one not null child if it exists or null child.
        replaceNode(nodeToBeDelete, child, rootRef);
        //if the node to be deleted is BLACK. See if it has one red child.
        if(nodeToBeDelete.getColor() == Color.BLACK) {
            //if it has one red child then change color of that child to be Black.
            if(child.getColor() == Color.RED) {
                child.setColor(Color.BLACK);
            } else {
                //otherwise we have double black situation.
                deleteCase1(child, rootRef);
            }
        }
    }


    /**
     * If double black node becomes root then we are done. Turning it into
     * single black node just reduces one black in every path.
     */
    private void deleteCase1(RedBlackNode<T> doubleBlackNode, AtomicReference<RedBlackNode<T>> rootRef) {
        if(doubleBlackNode.getParentNode() == null) {
            rootRef.set(doubleBlackNode);
            return;
        }
        deleteCase2(doubleBlackNode, rootRef);
    }

    /**
     * If sibling is red and parent and sibling's children are black then rotate it
     * so that sibling becomes black. Double black node is still double black so we need
     * further processing.
     */

    private RedBlackNode<T> findSiblingNode(RedBlackNode<T> node) {
        RedBlackNode<T> parent = node.getParentNode();
        if(node.isLeft()) {
            return ((RedBlackNode<T>)parent.getRight()).isNullLeaf() ? null : (RedBlackNode<T>)parent.getRight();
        } else {
            return ((RedBlackNode<T>)parent.getLeft()).isNullLeaf() ? null : (RedBlackNode<T>)parent.getLeft();
        }
    }

    private void deleteCase2(RedBlackNode<T> doubleBlackNode, AtomicReference<RedBlackNode<T>> rootRef) {
        RedBlackNode<T> siblingNode = findSiblingNode(doubleBlackNode);
        assert siblingNode != null;
        if(siblingNode.getColor() == Color.RED) {
            if(siblingNode.isLeft()) {
                rightRotate(siblingNode, true);
            } else {
                leftRotate(siblingNode, true);
            }
            if(siblingNode.getParentNode() == null) {
                rootRef.set(siblingNode);
            }
        }
        deleteCase3(doubleBlackNode, rootRef);
    }

    /**
     * If sibling, sibling's children and parent are all black then turn sibling into red.
     * This reduces black node for both the paths from parent. Now parent is new double black
     * node which needs further processing by going back to case1.
     */
    private void deleteCase3(RedBlackNode<T> doubleBlackNode, AtomicReference<RedBlackNode<T>> rootRef) {

        RedBlackNode<T> siblingNode = findSiblingNode(doubleBlackNode);

        if(doubleBlackNode.getParentNode().getColor() == Color.BLACK
                && siblingNode.getColor() == Color.BLACK
                && ((RedBlackNode<T>)siblingNode.getLeft()).getColor() == Color.BLACK
                && ((RedBlackNode<T>)siblingNode.getRight()).getColor() == Color.BLACK) {
            siblingNode.setColor(Color.RED);
            deleteCase1(doubleBlackNode.getParentNode(), rootRef);
        } else {
            deleteCase4(doubleBlackNode, rootRef);
        }
    }

    /**
     * If sibling color is black, parent color is red and sibling's children color is black then swap color b/w sibling
     * and parent. This increases one black node on double black node path but does not affect black node count on
     * sibling path. We are done if we hit this situation.
     */
    private void deleteCase4(RedBlackNode<T> doubleBlackNode, AtomicReference<RedBlackNode<T>> rootRef) {
        RedBlackNode<T> siblingNode = findSiblingNode(doubleBlackNode);
        if(doubleBlackNode.getParentNode().getColor() == Color.RED
                && siblingNode.getColor() == Color.BLACK
                && ((RedBlackNode<T>)siblingNode.getLeft()).getColor() == Color.BLACK
                && ((RedBlackNode<T>)siblingNode.getRight()).getColor() == Color.BLACK) {
            siblingNode.setColor(Color.RED);
            doubleBlackNode.getParentNode().setColor(Color.BLACK);
        } else {
            deleteCase5(doubleBlackNode, rootRef);
        }
    }

    /**
     * If sibling is black, double black node is left child of its parent, siblings right child is black
     * and sibling's left child is red then do a right rotation at siblings left child and swap colors.
     * This converts it to delete case6. It will also have a mirror case.
     */
    private void deleteCase5(RedBlackNode<T> doubleBlackNode, AtomicReference<RedBlackNode<T>> rootRef) {
        RedBlackNode<T> siblingNode = findSiblingNode(doubleBlackNode);
        if(siblingNode.getColor() == Color.BLACK) {
            if (doubleBlackNode.isLeft()
                    && ((RedBlackNode<T>)siblingNode.getRight()).getColor() == Color.BLACK
                    && ((RedBlackNode<T>)siblingNode.getLeft()).getColor() == Color.RED) {
                rightRotate((RedBlackNode<T>) siblingNode.getLeft(), true);
            } else if (!doubleBlackNode.isLeft()
                    && ((RedBlackNode<T>)siblingNode.getLeft()).getColor() == Color.BLACK
                    && ((RedBlackNode<T>)siblingNode.getRight()).getColor() == Color.RED) {
                leftRotate((RedBlackNode<T>) siblingNode.getRight(), true);
            }
        }
        deleteCase6(doubleBlackNode, rootRef);
    }

    /**
     * If sibling is black, double black node is left child of its parent, sibling left child is black and sibling's right child is
     * red, sibling takes its parent color, parent color becomes black, sibling's right child becomes black and then do
     * left rotation at sibling without any further change in color. This removes double black and we are done. This
     * also has a mirror condition.
     */
    private void deleteCase6(RedBlackNode<T> doubleBlackNode, AtomicReference<RedBlackNode<T>> rootRef) {
        RedBlackNode<T> siblingNode = findSiblingNode(doubleBlackNode);
        siblingNode.setColor(siblingNode.getParentNode().getColor());
        siblingNode.getParentNode().setColor(Color.BLACK);
        if(doubleBlackNode.isLeft()){
            ((RedBlackNode<T>)siblingNode.getRight()).setColor(Color.BLACK);
            leftRotate(siblingNode, false);
        } else {
            ((RedBlackNode<T>)siblingNode.getLeft()).setColor(Color.BLACK);
            rightRotate(siblingNode, false);
        }
        if(siblingNode.getParentNode() == null) {
            rootRef.set(siblingNode);
        }
    }

    private void replaceNode(RedBlackNode<T> node, RedBlackNode<T> child, AtomicReference<RedBlackNode<T>> rootRef) {
        child.setParentNode(node.getParentNode());
        if(node.getParentNode() == null) {
            rootRef.set(child);
        }
        else {
            if(node.isLeft()) {
                node.getParentNode().setLeft(child);
            } else {
                node.getParentNode().setRight(child);
            }
        }
    }

    private void updateChildrenOfParentNode(RedBlackNode node, RedBlackNode tempNode){
        if(node.getParentNode()==null)
            root = tempNode;
        else if(node.isLeft())
            node.getParentNode().setLeft(tempNode);
        else
            node.getParentNode().setRight(tempNode);
    }

    private void fixTree(RedBlackNode node) {
        RedBlackNode parent = node.getParentNode();
        if (parent != root && parent != null && parent.getColor() == RED) {
            RedBlackNode grandParent = parent.getParentNode();
            RedBlackNode uncle = parent.isLeft() ? (RedBlackNode) grandParent.getRight() : (RedBlackNode) grandParent.getLeft();
            if (uncle != null && uncle.getColor() == RED) {
                recolor(parent, uncle, grandParent);
            } else if (parent.isLeft()) { //left heavy or left right "according to color"
                fixLeft(node, parent, grandParent);
            } else {
                fixRight(node, parent, grandParent);
            }
        }
    }

    private void recolor(RedBlackNode parent,RedBlackNode uncle,RedBlackNode grandParent){
        parent.convertColor();
        uncle.convertColor();
        grandParent.convertColor();
        fixTree(grandParent);
    }
    private void fixLeft(RedBlackNode node,RedBlackNode parent,RedBlackNode grandParent){
        if(!node.isLeft()){//change left right to left left
            leftRotate(parent, false);
            grandParent.convertColor();
            node.convertColor();
        }else {
            parent.convertColor();
            grandParent.convertColor();
        }
        rightRotate(grandParent, false);
        fixTree(node.isLeft()? parent :grandParent); //was a left left or left right
    }
    private void fixRight(RedBlackNode node,RedBlackNode parent,RedBlackNode grandParent){
        if(node.isLeft()){//change right left to right right
            rightRotate(parent, false);
            grandParent.convertColor();
            node.convertColor();
        }else {
            parent.convertColor();
            grandParent.convertColor();
        }
        leftRotate(grandParent, false);
        fixTree(!node.isLeft()? parent :grandParent); //was a right right or  right left
    }

    private void rightRotate(RedBlackNode<T> root, boolean changeColor) {
        RedBlackNode<T> parent = root.getParentNode();
        root.setParentNode(parent.getParentNode());
        if(parent.getParentNode() != null) {
            if(parent.getParentNode().getRight() == parent) {
                parent.getParentNode().setRight(root);
            } else {
                parent.getParentNode().setLeft(root);
            }
        }
        RedBlackNode<T> right = (RedBlackNode<T>) root.getRight();
        root.setRight(parent);
        parent.setParentNode(root);
        parent.setLeft(right);
        if(right != null) {
            right.setParentNode(parent);
        }
        if(changeColor) {
            root.setColor(Color.BLACK);
            parent.setColor(Color.RED);
        }
    }

    private void leftRotate(RedBlackNode<T> root, boolean changeColor) {
        RedBlackNode<T> parent = root.getParentNode();
        root.setParentNode(parent.getParentNode());
        if(parent.getParentNode() != null) {
            if(parent.getParentNode().getRight() == parent) {
                parent.getParentNode().setRight(root);
            } else {
                parent.getParentNode().setLeft(root);
            }
        }
        RedBlackNode<T> left = (RedBlackNode<T>) root.getLeft();
        root.setLeft(parent);
        parent.setParentNode(root);
        parent.setRight(left);
        if(left != null) {
            left.setParentNode(parent);
        }
        if(changeColor) {
            root.setColor(Color.BLACK);
            parent.setColor(Color.RED);
        }
    }

}
