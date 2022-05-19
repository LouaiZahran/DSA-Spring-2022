package sort;

/**
 * A factory for creating any class implementing the sorting interface
 */
public class SortingFactory {
    /**
     * A method that creates instances of sorters
     * @param sortingType the name of the desired sorting class
     * @return an instance of the class whose name is sortingType if exists, or null otherwise
     */
    public static Sorting getSort(String sortingType){
        if(sortingType==null)
            return null;
        if(sortingType.equalsIgnoreCase("BUBBLESORT")){
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
        return null;    //not found in the factory
    }
}
