package com.company;

import lombok.Data;

import java.awt.event.WindowStateListener;
import java.util.Locale;

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

public class AVL {
    Node root;

    private void getNewHeight(Node node){
        if (node== null)
            return ;
        node.setHeight(1 + Math.max(getHeight(node.getLeft()),getHeight(node.getRight())));
    }
    String getMax(Node node){
        if(node.getRight() ==null)
            return node.getValue();
        return getMax(node.getRight());
    }
    int getHeight(Node node) {
        if (node == null)
            return 0;
        return node.getHeight();
    }
    int getBalanceFactor(Node node){
        if (node == null)
            return 0;
        return getHeight(node.getLeft()) - getHeight(node.getRight());
    }
    public AVL insert(String value){
        root = insert(root , value);
        return this;
    }
    private Node insert(Node node , String value){
        if(node == null)
            return new Node(value);
        //compare to ch1-ch2
        else if(node.getValue().toLowerCase().compareTo(value.toLowerCase())>0){ //new string is lower than current
            node.setLeft(insert(node.getLeft(),value));
        }
        else if(node.getValue().toLowerCase().compareTo(value.toLowerCase())<0){
            node.setRight(insert(node.getRight(),value));
        }else{//duplicated
            return node;
        }
        getNewHeight(node);
        return rotate(node);
    }
    public AVL delete(String value){
        root = insert(root , value);
        return this;
    }
    private Node delete(Node node , String value){
        if(node == null)
            return null;
            //compare to ch1-ch2
        else if(node.getValue().toLowerCase().compareTo(value.toLowerCase())>0){ //new string is lower than current
            node.setLeft(delete(node.getLeft(),value));
        }
        else if(node.getValue().toLowerCase().compareTo(value.toLowerCase())<0){
            node.setRight(delete(node.getRight(),value));
        }else{//delete action
            //one or no child
            if(node.getRight()==null)
                return node.getLeft();
            else if(node.getLeft()==null)
                return node.getRight();

            node.setValue(getMax(node.getLeft()));
            node.setLeft(delete(node.getLeft(),node.getValue()));
        }
        getNewHeight(node);
        return rotate(node);
    }
    private Node rotate(Node nodeToRotate){
        int balanceFactor=getBalanceFactor(nodeToRotate);
        if (balanceFactor >1) //left
        {
            if (getBalanceFactor(nodeToRotate.getLeft()) < 0) { //right
                nodeToRotate.setLeft(rotateLeft(nodeToRotate));
            }
            return rotateRight(nodeToRotate);
        }else if (balanceFactor <-1) //right
        {
            if (getBalanceFactor(nodeToRotate.getLeft()) > 0) { //left
                nodeToRotate.setLeft(rotateRight(nodeToRotate));
            }
            return rotateLeft(nodeToRotate);
        }
        return nodeToRotate;
    }
    private Node rotateLeft(Node node){
        Node rightNode = node.getRight();
        Node tempSubTree = rightNode.getLeft();

        rightNode.setLeft(node);
        node.setRight(tempSubTree);

        getNewHeight(node);
        getNewHeight(rightNode);

        return rightNode;

    }
    private Node rotateRight(Node node){
        Node leftNode = node.getLeft();
        Node tempSubTree = leftNode.getRight();

        leftNode.setRight(node);
        node.setLeft(tempSubTree);

        getNewHeight(node);
        getNewHeight(leftNode);
        return leftNode;
    }


    public void traverse() {
        traverseInOrder(root);
    }

    private void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.getLeft());
            System.out.println(node.getValue());
            traverseInOrder(node.getRight());
        }
    }


}
