package sort;

public class SelectionSort implements Sorting
{
    @Override
    public void sort(int arr[]){
        for(int i=0;i<arr.length-1;i++){
            //find min value index
            int minValueIndex=i;
            for(int j=i+1;j<arr.length;j++){
                if(arr[j]<arr[i]){
                    minValueIndex=j;
                }
            }
            if(minValueIndex!=i){
                swap(arr,minValueIndex,i);
            }
        }
    }
    private void swap(int arr[], int a, int b){
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}
