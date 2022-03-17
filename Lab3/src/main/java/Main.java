import heap.MaxHeap;
import lombok.SneakyThrows;
import sort.MergeSort;

import java.util.Arrays;

public class Main {
    @SneakyThrows
    public static void main(String args[]){
        MaxHeap heap = new MaxHeap(100);
        int arr[] = {2, 1, 6, 5, 3};
        for(int i=0; i<arr.length; i++)
            heap.insert(arr[i]);
        for(int i=0; i<arr.length; i++)
            System.out.println(heap.extractMax());

        System.out.println(Arrays.toString(arr));
        MaxHeap.sort(arr);      //Descending
        System.out.println(Arrays.toString(arr));
        MergeSort.sort(arr);    //Ascending
        System.out.println(Arrays.toString(arr));
    }
}
