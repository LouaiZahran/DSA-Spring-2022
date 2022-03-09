import Trees.RedBlackTree;
import Trees.Tree;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

public class RedBlackTest {
    public void createArray(int[] instructions) {
        Tree<Integer> tree=new RedBlackTree<>();
        for(int i=0;i<instructions.length;i++){
            tree.insert(instructions[i]);
        }
        tree.traverse();
    }

    @Test
    @Ignore("Should be fixed")
    public void test() {
        int[] n={4,14,10,2,5,3,8,19,7,20,12,1,9,15,13,11,18,6,16,17};
        createArray(n);
    }
}
