package Trees;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedBlackTreeTest {

    RedBlackTree<Integer> tree = new RedBlackTree<>();

    @Test
    void insert() {
        int arr[] = {5, 3, 76, 9, 72, 4, 8};
        for(int i=0; i<arr.length; i++) {
            tree.insert(arr[i]);
            assertTrue(tree.search(arr[i]));
        }
    }

    @Test
    void delete() {
        insert();
        int arr[] = {5, 3, 76, 3, 72, 4, 8};
        for(int i=0; i<arr.length; i++) {
            tree.delete(arr[i]);
            //assertFalse(tree.search(arr[i]));
            tree.traverse();
            System.out.println();
        }
    }
}