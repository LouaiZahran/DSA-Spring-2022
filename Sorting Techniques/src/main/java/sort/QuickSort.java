package sort;

/**
 * O(nlogn) Quick Sort Class
 */
public class QuickSort implements Sorting {
    @Override
    public void sort(int[] arr){
        quickSort(arr,0,arr.length-1);
    }

    /**
     * A recursive method to sort the array
     * @param arr the array to be sorted
     * @param low the first index to start sorting at
     * @param high the last index to finish sorting at
     */
    private void quickSort(int[] arr, int low, int high) {
        if(low<high){
            int pivotIndex=partition(arr,low,high);
            quickSort(arr,pivotIndex+1,high);
            quickSort(arr,low,pivotIndex-1);
        }
    }

    /**
     * Partitions the array while maintaining that all the elements less than the pivot are on its left
     * @param arr the array to be partitioned
     * @param low the first index to start partitioning at
     * @param high the last index to finish partitioning at
     * @return the new position of the pivot
     */
    private int partition(int[] arr,int low,int high){
        int pivot=arr[high];
        int i=low-1;
        for(int j=low;j<high;j++){
            if(arr[j]<pivot){ //if less than pivot change it to left of this pivot
                i++;
                swap(arr,i,j);
            }
        }
        //put pivot in its place
        swap(arr,i+1,high);
        return i+1;
    }

    /**
     * A simple method that swaps 2 elements in an array
     * @param arr the array to be swapped into
     * @param a the index of the first element to be swapped
     * @param b the index of the second element to be swapped
     */
    private static void swap(int[] arr, int a, int b){
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}
