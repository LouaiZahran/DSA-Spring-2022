package Trees;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * A class to analyze th RBTree performance
 */
public class AnalysisRBTree {
    String getString(int n){
        String stringFactory="ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        String ret="";
        for(int i=0;i<n;i++){
            int index=(int) (Math.random()*stringFactory.length());
            ret+=(char)stringFactory.charAt(index);
        }
        return ret;
    }

    /**
     * A method that computes the average time of operations on different number of nodes
     */
    @Test
    public void AnalyseRB(){
        long startTime=0;
        long endTime=0;
        Tree<String> tree = new RedBlackTree<>();
        ArrayList<Double> insertionAvgTime=new ArrayList<>();
        ArrayList<Double> deletionAvgTime=new ArrayList<>();
        ArrayList<String> arrayToDelete=new ArrayList<>();
        double sum=0;
        for(int i=1;i<=1000000;i*=10){
            sum = 0;
            for (int j=0;j<i;j++){
                String str=getString(40);
                arrayToDelete.add(str);
                startTime=System.nanoTime();
                tree.insert(str);
                endTime=System.nanoTime()-startTime;
                sum+=(double)endTime/1000000; //Convert to millis
            }
            insertionAvgTime.add(sum);
            sum=0;
            for (int j=0;j<i;j++){
                int index=(int) (Math.random()*arrayToDelete.size())-1;
                String deletedString= arrayToDelete.remove(index==-1?0:index);
                startTime=System.nanoTime();
                tree.delete(deletedString);
                endTime=System.nanoTime()-startTime;
                sum+=(double) endTime/1000000; //Convert to millis
            }
            deletionAvgTime.add(sum);
            sum=0;
        }
        System.out.println("Insertion Avg Time");
        for(int i=0 , n = 1 ;i<insertionAvgTime.size() && n<=1000000;i++ ,n*=10){
            System.out.println("at "+"n="+n +" :"+insertionAvgTime.get(i) + "ms");
        }

        System.out.println("Deletion Avg Time");
        for(int i=0 , n = 1 ;i<deletionAvgTime.size() && n<=1000000;i++ ,n*=10){
            System.out.println("at "+"n="+n+" :"+deletionAvgTime.get(i) + "ms");
        }
    }

}