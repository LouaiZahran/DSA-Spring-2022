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
        int[] n={1,3,2,4};
        createArray(n);

    }
}
