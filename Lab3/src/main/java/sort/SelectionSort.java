package sort;

/**
 * O(n^2) Selection Sort Class
 */
public class SelectionSort implements Sorting
{
    @Override
    public void sort(int[] arr){
        for(int i=0;i<arr.length-1;i++){
            //find min value index
            int minValueIndex=i;
            for(int j=i+1;j<arr.length;j++){
                if(arr[j]<arr[minValueIndex]){
                    minValueIndex=j;
                }
            }
            if(minValueIndex!=i){
                swap(arr,minValueIndex,i);
            }
        }
    }

    /**
     * A simple method that swaps 2 elements in an array
     * @param arr the array to be swapped into
     * @param a the index of the first element to be swapped
     * @param b the index of the second element to be swapped
     */
    private void swap(int[] arr, int a, int b){
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}
