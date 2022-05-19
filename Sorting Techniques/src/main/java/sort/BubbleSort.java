package sort;

/**
 * O(n^2) Bubble Sort Class
 */
public class BubbleSort implements Sorting {
    @Override
    public void sort(int[] arr){
        int n = arr.length;
        for(int i=0; i<n; i++) {
            boolean notSwapped=true;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    notSwapped=false;
                }
            }
            if (notSwapped)
                break;
        }
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
