package data;

import sort.Sorting;
import sort.SortingFactory;


/**
 * A class that follows the strategy design pattern for sorters
 */
public class Data {
    /** Sorting Strategy */
    private Sorting sorting;
    /** The array following that strategy */
    private final int[] arr;

    /**
     * Class Constructor
     * @param arr the array following the strategy
     * @param sorting the current assigned strategy
     */
    public Data(int[] arr, Sorting sorting){
        this.arr=arr;
        this.sorting = sorting;
    }

    /**
     * Class Constructor
     * @param arr the array following the strategy
     * @param sorting the name of the strategy to be assigned
     */
    public Data(int[] arr, String sorting){
        this.arr=arr;
        this.sorting = SortingFactory.getSort(sorting);
    }

    /**
     * Changes strategies
     * @param sorting the new strategy to be followed
     */
    public void changeSort(Sorting sorting){
        this.sorting=sorting;
    }

    /**
     * Apply Strategy
     */
    public void sort(){
        sorting.sort(this.arr);
    }
}
