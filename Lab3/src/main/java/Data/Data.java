package Data;

import sort.Sorting;
import sort.SortingFactory;

public class Data {
    private Sorting sorting;
    private int[] arr;
    public Data(int[] arr,Sorting sorting){
        this.arr=arr;
        this.sorting = sorting;
    }
    public Data(int[] arr,String sorting){
        this.arr=arr;
        this.sorting = SortingFactory.getSort(sorting);
    }
    public void changeSort(Sorting sorting){
        this.sorting=sorting;
    }

    public void sort(){
        sorting.sort(this.arr);
    }
}
