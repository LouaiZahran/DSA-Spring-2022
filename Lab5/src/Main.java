import java.util.ArrayList;
import java.util.List;

import BTree.BTree;
import BTree.BTreeNode;
import BTree.IBTree;
import BTree.IBTreeNode;

public class Main {
    public static void main(String args[]){
        BTree tst = new BTree<Integer,String>(1);
        tst.insert(0, "2");
        tst.insert(2, "2");
        tst.insert(6, "2");
        tst.insert(7, "2");

        tst.insert(3, "2");
        tst.insert(4, "2");

        tst.getRoot().print();

        // var childkeys = new ArrayList<Integer>();
        // var childvalues = new ArrayList<String>();
        // List<IBTreeNode<Integer, String>> childChildren = new ArrayList<IBTreeNode<Integer,String>>();
        // int s = 5, t=0;
        // for(int i=0; i<s ; i++){
        //     childkeys.add(t);childvalues.add(t+"A");
        //     t+=2;
        // }
        // t = 1;
        // for (int i = 0; i < 5; i++) {
        //     var k = new ArrayList<Integer>();
        //     var v = new ArrayList<String>();
        //     k.add(t);v.add(t+"A");
        //     t+=2;
        //     BTreeNode childchild = new BTreeNode<Integer,String>(1, true, k , v,new ArrayList<>());
        //     childChildren.add(childchild);
        // }
        // BTreeNode<Integer,String> n = new BTreeNode<Integer,String>(5, false, childkeys, childvalues, childChildren);

        // var ke = new ArrayList<Integer>();var ve = new ArrayList<String>();
        // ke.add(999);
        // ve.add("parentkey");
        // List<IBTreeNode<Integer, String>> children = new ArrayList<IBTreeNode<Integer,String>>();
        // var k = new ArrayList<Integer>();
        // var v = new ArrayList<String>();
        // k.add(1000);v.add("secondchild");
        // BTreeNode secchild = new BTreeNode<Integer,String>(1, true, k , v,new ArrayList<>());
        // children.add(n);
        // children.add(secchild);
        // BTreeNode<Integer,String> parent = new BTreeNode<Integer,String>(4, false, ke, ve, children);
        // n.split(parent,0);
        // parent.print();
    }
}