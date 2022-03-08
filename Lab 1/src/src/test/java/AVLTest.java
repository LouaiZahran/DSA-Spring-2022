import Trees.AVL;
import Trees.AbstractNode;
import Trees.Tree;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AVLTest {
    private Tree<Integer> tree = new AVL<Integer>();
    private void insert(Integer...integers) {
        for(Integer i:integers)
            tree.insert(i);
    }

    private boolean checkBalanceOfTree(AbstractNode<Integer> current) {

        boolean balancedRight = true, balancedLeft = true;
        int leftHeight = 0, rightHeight = 0;

        if (current.getRight() != null) {
            balancedRight = checkBalanceOfTree(current.getRight());
            rightHeight = getDepth(current.getRight());
        }

        if (current.getLeft() != null) {
            balancedLeft = checkBalanceOfTree(current.getLeft());
            leftHeight = getDepth(current.getLeft());
        }

        return balancedLeft && balancedRight && Math.abs(leftHeight - rightHeight) < 2;
    }

    private int getDepth(AbstractNode<Integer> n) {
        int leftHeight = 0, rightHeight = 0;

        if (n.getRight() != null)
            rightHeight = getDepth(n.getRight());
        if (n.getLeft() != null)
            leftHeight = getDepth(n.getLeft());

        return Math.max(rightHeight, leftHeight)+1;
    }

    private boolean checkOrderingOfTree(AbstractNode<Integer> current) {
        if(current.getLeft() != null) {
            int compLeft=String.valueOf(current.getLeft().getValue()).compareTo(String.valueOf(current.getValue()));
            if(compLeft > 0)
                return false;
            else
                return checkOrderingOfTree(current.getLeft());
        } else  if(current.getRight() != null) {
            int compRight=String.valueOf(current.getRight().getValue()).compareTo(String.valueOf(current.getValue()));
            if(compRight< 0)
                return false;
            else
                return checkOrderingOfTree(current.getRight());
        } else if(current.getLeft() == null && current.getRight() == null)
            return true;

        return true;
    }

    @Test
    @Ignore("Should be fixed")
    public void test() {
        assertTrue(tree.isEmpty());
        insert(16,24,36,19,44,28,61,74,83,64,52,65,86,93,88);
        assertTrue(tree.getMin() == 16);
        assertTrue(tree.getMax() == 93);

        assertTrue(checkBalanceOfTree(tree.getRoot()));
        assertTrue(checkOrderingOfTree(tree.getRoot()));

        tree.delete(88);
        assertTrue(checkBalanceOfTree(tree.getRoot()));
        assertTrue(checkOrderingOfTree(tree.getRoot()));
        assertFalse(tree.search(88));

        tree.delete(19);
        assertTrue(checkBalanceOfTree(tree.getRoot()));
        assertTrue(checkOrderingOfTree(tree.getRoot()));
        assertFalse(tree.search(19));

        tree.delete(16);
        assertTrue(checkBalanceOfTree(tree.getRoot()));
        assertTrue(checkOrderingOfTree(tree.getRoot()));
        assertFalse(tree.search(16));

        tree.delete(28);
        assertTrue(checkBalanceOfTree(tree.getRoot()));
        assertTrue(checkOrderingOfTree(tree.getRoot()));
        assertFalse(tree.search(28));

        tree.delete(24);
        assertTrue(checkBalanceOfTree(tree.getRoot()));
        assertTrue(checkOrderingOfTree(tree.getRoot()));
        assertFalse(tree.search(24));

        tree.delete(36);
        assertTrue(checkBalanceOfTree(tree.getRoot()));
        assertTrue(checkOrderingOfTree(tree.getRoot()));
        assertFalse(tree.search(36));

        tree.delete(52);
        assertTrue(checkBalanceOfTree(tree.getRoot()));
        assertTrue(checkOrderingOfTree(tree.getRoot()));
        assertFalse(tree.search(52));

        tree.delete(93);
        assertTrue(checkBalanceOfTree(tree.getRoot()));
        assertTrue(checkOrderingOfTree(tree.getRoot()));
        assertFalse(tree.search(93));

        tree.delete(86);
        assertTrue(checkBalanceOfTree(tree.getRoot()));
        assertTrue(checkOrderingOfTree(tree.getRoot()));
        assertFalse(tree.search(86));

        tree.delete(83);
        assertTrue(checkBalanceOfTree(tree.getRoot()));
        assertTrue(checkOrderingOfTree(tree.getRoot()));
        assertFalse(tree.search(83));
    }

}