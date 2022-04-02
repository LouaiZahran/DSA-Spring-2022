import heap.MaxHeap;
import data.Data;
import lombok.SneakyThrows;
import sort.Sorting;
import sort.SortingFactory;

import java.util.Arrays;

public class Main {
    @SneakyThrows
    public static void main(String[] args){
        MaxHeap heap = new MaxHeap(100);
        int[] arr = {2, 1, 6, 5, 3};
        Sorting sort= SortingFactory.getSort("mergeSort");
        Data data=new Data(arr,sort);
        for (int j : arr) heap.insert(j);
        for(int i=0; i<arr.length; i++)
            System.out.println(heap.extractMax());

        System.out.println(Arrays.toString(arr));
        MaxHeap.sort(arr);                          //Ascending
        System.out.println(Arrays.toString(arr));
        data.sort();
        System.out.println(Arrays.toString(arr));   //Mergesort

        sort=SortingFactory.getSort("InsertionSort");
        data.changeSort(sort);
        MaxHeap.sort(arr);                          //Ascending
        System.out.println(Arrays.toString(arr));
        data.sort();
        System.out.println(Arrays.toString(arr));   //InsertionSort
    }
}
