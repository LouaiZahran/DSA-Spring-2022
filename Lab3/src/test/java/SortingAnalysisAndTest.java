import Data.Data;
import org.junit.Test;
import sort.QuickSort;
import sort.Sorting;
import sort.SortingFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortingAnalysisAndTest {
    int[] createArray(int n){
        int[] arr=new int[n];
        for(int i=0;i<arr.length;i++){
            arr[i]=(int)(Math.random()*n);
        }
        return arr;
    }
    void testAscending(int[]arr,String method){
        int[] clone =new int[arr.length];
        System.arraycopy(arr,0,clone,0,arr.length);
        Arrays.sort(clone);
        for(int i=0;i<arr.length;i++){
            assertEquals(clone[i],arr[i],"Array not Sorted "+method);
        }
    }
    long testSort(int[] clone,String string){

        Data data=new Data(clone,string);
        long startTime=System.nanoTime();
        data.sort();
        return System.nanoTime()-startTime;
    }
    @Test
    public void run(){
        ArrayList<Long> mergeSortTime=new ArrayList<>();
        ArrayList<Long> insertionSortTime=new ArrayList<>();
        ArrayList<Long> quickSortTime=new ArrayList<>();
        ArrayList<Long> bubleSortTime=new ArrayList<>();
        ArrayList<Long> selectionSortTime=new ArrayList<>();
        double sum=0;
        for(int i=0;i<=10000;i=i*10) {
            int[] arr = createArray(i);
            int[] clone =new int[i];
            System.arraycopy(arr,0,clone,0,i);
            mergeSortTime.add(testSort(clone,"mergeSort"));
            testAscending(clone,"merge sort");

            System.arraycopy(arr,0,clone,0,i);
            insertionSortTime.add(testSort(clone,"InsertionSort"));
            testAscending(clone,"insertion sort");

            System.arraycopy(arr,0,clone,0,i);
            bubleSortTime.add(testSort(clone,"BubleSort"));
            testAscending(clone,"bubble sort");

            System.arraycopy(arr,0,clone,0,i);
            quickSortTime.add(testSort(clone,"QuickSort"));
            testAscending(clone,"quick sort");

            System.arraycopy(arr,0,clone,0,i);
            selectionSortTime.add(testSort(clone,"SelectionSort"));
            testAscending(clone,"selection sort");

            if (i == 0)
                i++;
        }
        System.out.println("Merge Sort Time");
        for(int i=0 , n = 0 ;i<mergeSortTime.size();i++ ,n*=10){
            System.out.println("at "+"n="+n+" :"+mergeSortTime.get(i) + "ns");
            if (n == 0)
                n++;
        }
        System.out.println("insertion sort Time");
        for(int i=0 , n = 0 ;i<insertionSortTime.size();i++ ,n*=10){
            System.out.println("at "+"n="+n+" :"+insertionSortTime.get(i) + "ns");
            if (n == 0)
                n++;
        }System.out.println("Quick sort Time");
        for(int i = 0, n = 0; i< quickSortTime.size(); i++ ,n*=10){
            System.out.println("at "+"n="+n+" :"+quickSortTime.get(i) + "ns");
            if (n == 0)
                n++;
        }System.out.println("Selection sort Time");
        for(int i=0 , n = 0 ;i<selectionSortTime.size();i++ ,n*=10){
            System.out.println("at "+"n="+n+" :"+selectionSortTime.get(i) + "ns");
            if (n == 0)
                n++;
        }System.out.println("bubble sort Time");
        for(int i=0 , n = 0 ;i<bubleSortTime.size();i++ ,n*=10){
            System.out.println("at "+"n="+n+" :"+bubleSortTime.get(i) + "ns");
            if (n == 0)
                n++;
        }

    }


}
