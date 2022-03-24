package sort;

public class MergeSort  implements Sorting {
    @Override
    public void sort(int arr[]){
        sort(arr, 0, arr.length-1);
    }

    private void sort(int arr[], int l, int r){
        if(l >= r)
            return;

        int m = (l+r)/2;
        sort(arr, l, m);
        sort(arr, m+1, r);
        merge(arr, l, m, r);
    }

    private void merge(int arr[], int l, int m, int r){
        int ptr1 = 0, ptr2 = 0;
        int leftArr[] = new int[m-l+1];
        int rightArr[] = new int[r-m];

        for(int i=0; i<leftArr.length; i++)
            leftArr[i] = arr[l + i];
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
