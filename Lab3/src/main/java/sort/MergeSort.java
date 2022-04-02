package sort;

/**
 * O(nlogn) Merge Sort Class
 */
public class MergeSort  implements Sorting {
    @Override
    public void sort(int[] arr){
        sort(arr, 0, arr.length-1);
    }

    /**
     * A recursive method to sort the array
     * @param arr the array to be sorted
     * @param l the first index to start sorting at
     * @param r the last index to finish sorting at
     */
    private void sort(int[] arr, int l, int r){
        if(l >= r)
            return;

        int m = (l+r)/2;
        sort(arr, l, m);
        sort(arr, m+1, r);
        merge(arr, l, m, r);
    }

    /**
     * The method responsible for merging 2 sorted arrays into one in O(n)
     * @param arr the array to be merged into
     * @param l the first index to start merging at
     * @param m the midpoint between the first and second merged arrays
     * @param r the last index to finish merging at
     */
    private void merge(int[] arr, int l, int m, int r){
        int ptr1 = 0, ptr2 = 0;
        int[] leftArr = new int[m-l+1];
        int[] rightArr = new int[r-m];

        System.arraycopy(arr, l, leftArr, 0, leftArr.length);

        for(int i=0; i<rightArr.length; i++)
            rightArr[i] = arr[m + 1 + i];

        for(int i=l; i<=r; i++){
            if(ptr1 == leftArr.length)
                arr[i] = rightArr[ptr2++];
            else if(ptr2 == rightArr.length)
                arr[i] = leftArr[ptr1++];
            else if(leftArr[ptr1] < rightArr[ptr2])
                arr[i] = leftArr[ptr1++];
            else
                arr[i] = rightArr[ptr2++];
        }
    }
}
