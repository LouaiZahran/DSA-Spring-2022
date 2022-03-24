package sort;

public class QuickSort implements Sorting {
    @Override
    public void sort(int arr[]){
        quickSort(arr,0,arr.length-1);
    }

    private void quickSort(int[] arr, int low, int high) {
        if(low<high){
            int pivotIndex=partition(arr,low,high);
            quickSort(arr,pivotIndex+1,high);
            quickSort(arr,low,pivotIndex-1);
        }
    }

    private int partition(int[] arr,int low,int high){
        int pivot=arr[high];
        int i=low-1;
        for(int j=low;j<high;j++){
            if(arr[j]<pivot){ //if less than pivot change it to right of this pivot
                i++;
                swap(arr,i,j);
            }
        }
        //put pivot in its place
        swap(arr,i+1,high);
        return i+1;
    }

    private static void swap(int arr[], int a, int b){
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}
