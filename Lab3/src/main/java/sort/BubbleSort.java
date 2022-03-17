package sort;

public class BubbleSort {
    public static void sort(int arr[]){
        int n = arr.length;
        for(int i=0; i<n; i++)
            for(int j=i; j<n-1; j++)
                if(arr[j] > arr[j+1])
                    swap(arr, j, j+1);
    }

    private static void swap(int arr[], int a, int b){
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}
