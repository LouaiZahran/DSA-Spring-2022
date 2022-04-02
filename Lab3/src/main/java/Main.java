import heap.MaxHeap;
import data.Data;
import lombok.SneakyThrows;

import java.util.Scanner;

public class Main {
    static void print(int[] arr,String method){
        System.out.println(method);
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i]+" ");
        }
        System.out.print("\n");
    }
    static void testSort(int[] clone,String string){

        Data data=new Data(clone,string);
        data.sort();
    }
    static void testHeap(int[] clone){
        MaxHeap.sort(clone);
    }
    @SneakyThrows
    public static void main(String[] args){
        Scanner scan=new Scanner(System.in);
        int n=scan.nextInt();
        int[] arr = new int[n];
        for(int i=0;i<n;i++){
            arr[i]=scan.nextInt();
        }
        int[] clone =new int[n];
        System.arraycopy(arr,0,clone,0,n);
        testSort(clone,"mergeSort");
        print(clone,"merge sort");

        System.arraycopy(arr,0,clone,0,n);
        testSort(clone,"InsertionSort");
        print(clone,"insertion sort");

        System.arraycopy(arr,0,clone,0,n);
        testSort(clone,"BubbleSort");
        print(clone,"bubble sort");

        System.arraycopy(arr,0,clone,0,n);
        testSort(clone,"QuickSort");
        print(clone,"quick sort");

        System.arraycopy(arr,0,clone,0,n);
        testSort(clone,"SelectionSort");
        print(clone,"selection sort");

        System.arraycopy(arr,0,clone,0,n);
        testHeap(clone);
        print(clone,"heap sort");

    }
}
