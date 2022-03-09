import Trees.AVL;
import Trees.Tree;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class Analysis {
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
    @Test
    public void AnalyseAVL(){
        long startTime=0;
        long endTime=0;
        Tree<String> tree = new AVL<String>();
        ArrayList<Double> insertionAvgTime=new ArrayList<>();
        ArrayList<Double> deletionAvgTime=new ArrayList<>();
        ArrayList<String> arrayToDelete=new ArrayList<>();
        double timeAvg=0;
        double sum=0;
        for(int i=1;i<=1000000;i*=10){
            for (int j=0;j<i;j++){
                String str=getString(40);
                arrayToDelete.add(str);
                startTime=System.nanoTime();
                tree.insert(str);
                endTime=System.nanoTime()-startTime;
                sum+=(double) endTime;
            }
            insertionAvgTime.add(sum/i);
            sum=0;
            for (int j=0;j<i;j++){
                int index=(int) (Math.random()*arrayToDelete.size())-1;
                String deletedString= arrayToDelete.remove(index==-1?0:index);
                startTime=System.nanoTime();
                tree.delete(deletedString);
                endTime=System.nanoTime()-startTime;
                sum+=(double) endTime;
            }
            deletionAvgTime.add(sum/i);
            sum=0;
        }
        System.out.println("Insertion Avg Time");
        for(int i=0 , n = 1 ;i<insertionAvgTime.size() && n<=1000000;i++ ,n*=10){
            System.out.println("at "+"n="+n +" :"+insertionAvgTime.get(i) + "ns");
        }

        System.out.println("Deletion Avg Time");
        for(int i=0 , n = 1 ;i<deletionAvgTime.size() && n<=1000000;i++ ,n*=10){
            System.out.println("at "+"n="+n+" :"+deletionAvgTime.get(i) + "ns");
        }
    }

}
