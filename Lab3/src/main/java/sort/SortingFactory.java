package sort;

public class SortingFactory {
    public static Sorting getSort(String sortingType){
        if(sortingType==null)
            return null;
        if(sortingType.equalsIgnoreCase("BUBLESORT")){
            return new BubbleSort();

        } else if(sortingType.equalsIgnoreCase("INSERTIONSORT")){
            return new InsertionSort();

        } else if(sortingType.equalsIgnoreCase("MERGESORT")){
            return new MergeSort();
        }else if(sortingType.equalsIgnoreCase("QUICKSORT")){
            return new QuickSort();

        } else if(sortingType.equalsIgnoreCase("SELECTIONSORT")){
            return new SelectionSort();
        }
        return null;//not found in the factory
    }
}
