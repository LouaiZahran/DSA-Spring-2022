import Trees.AVL;
import Trees.Tree;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class Analysis {
    String getString(int n) {
        String stringFactory = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        String ret = "";
        for (int i = 0; i < n; i++) {
            int index = (int) (Math.random() * stringFactory.length());
            ret += (char) stringFactory.charAt(index);
        }
        return ret;
    }

    @Test
    public void AnalyseAVL() {
        long startTime = 0;
        long endTime = 0;
        Tree<String> tree = new AVL<String>();
        ArrayList<String> stringArrayList = new ArrayList<>();
        ArrayList<Double> insertionAvgTimeArr = new ArrayList<>();
        ArrayList<Double> deletionAvgTimeArr = new ArrayList<>();

        for (int i = 10; i <= 1000000; i *= 10) {
            double sumDeletion = 0;
            double sumInsertion = 0;
            for (int k = 0; k < 10; k++) {
                stringArrayList.clear();
                for (int j = 0; j < i; j++) {
                    stringArrayList.add(getString(40));
                }
                startTime = System.currentTimeMillis();
                for (int j = 0; j < i; j++) {
                    tree.insert(stringArrayList.get(j));
                }
                endTime = System.currentTimeMillis() - startTime;
                sumInsertion += (double) endTime;

                startTime = System.currentTimeMillis();
                for (int j = 0; j < i; j++) {
                    tree.delete(stringArrayList.get(j));
                }
                endTime = System.currentTimeMillis() - startTime;
                sumDeletion += (double) endTime;
            }
            insertionAvgTimeArr.add(sumInsertion/10);
            deletionAvgTimeArr.add(sumDeletion/10);
        }
        System.out.println("Insertion Avg Time");
        for (int i = 0, n = 10; i < insertionAvgTimeArr.size() && n <= 1000000; i++, n *= 10) {
            System.out.println("at " + "n=" + n + " :" + insertionAvgTimeArr.get(i) + "ms");
        }

        System.out.println("Deletion Avg Time");
        for (int i = 0, n = 10; i < deletionAvgTimeArr.size() && n <= 1000000; i++, n *= 10) {
            System.out.println("at " + "n=" + n + " :" + deletionAvgTimeArr.get(i) + "ms");
        }
    }

}
