package heap;

import javax.naming.SizeLimitExceededException;

/**
 * Our implementation for the max heap class
 * @author Louai Zahran
 * @author Abdelrahman Bahaa
 */
public class MaxHeap {
    /** The heap represented as an array */
    private final int[] heap;
    /** The maximum size for this heap */
    private final int maxSize;
    /** The current size for this heap */
    private int size;

    /**
     * Class Constructor
     * @param n the maximum size of the heap
     */
    public MaxHeap(int n){
        heap = new int[n];
        maxSize = n;
        size = 0;
    }

    /**
     * Class Constructor
     * @param arr the elements to build the heap with in O(n)
     */
    public MaxHeap(int[] arr){
        int n = arr.length;
        heap = arr;
        maxSize = n;
        size = n;

        for(int i=n/2-1;i>=0; i--)
            maxHeapify(i);
    }

    /**
     * O(nlogn) Heap Sort
     * @param arr the array to be sorted in-place
     */
    public static void sort(int[] arr){
        if(arr.length==0)
            return;
        MaxHeap heap = new MaxHeap(arr);
        while (heap.size!=0){
            heap.extractMax();
        }
    }

    /**
     * A simple method that swaps 2 elements in the heap
     * @param a the index of the first element to be swapped
     * @param b the index of the second element to be swapped
     */
    private void swap(int a, int b){
        int tmp = heap[a];
        heap[a] = heap[b];
        heap[b] = tmp;
    }

    /**
     * Responsible for maintaining the heap property
     * @param current the root of the subtree from which the heap property should be maintained
     */
    private void maxHeapify(int current){
        if(current >= size/2) //if in leaf level
            return;
        int leftChild = current*2 + 1;
        int rightChild = current*2 + 2;
        int largest=current;
        if(leftChild < size && heap[largest] < heap[leftChild]){
            largest=leftChild;
        }
        if(rightChild < size && heap[largest] < heap[rightChild]){
            largest=rightChild;
        }
        if(largest!=current) {
            swap(largest,current);
            maxHeapify(largest);
        }

    }

    /**
     * Inserts an element its position while maintaining the heap property
     * @param n the element to be inserted
     * @throws SizeLimitExceededException if the insertion will make heapSize > maxSize
     */
    public void insert(int n) throws SizeLimitExceededException{
        if(size == maxSize)
            throw new SizeLimitExceededException();
        heap[size] = n;
        int current = size++;
        int parent = (current - 1)/2;
        while(current > 0 && heap[current] > heap[parent]){
            swap(current, parent);
            current = parent;
            parent = (current - 1)/2;
        }
    }

    /**
     * Gets the maximum element without deleting it
     * @return the maximum element - the root
     * @throws IndexOutOfBoundsException if the heap is empty
     */
    public int getMax() throws IndexOutOfBoundsException{
        if(size == 0)
            throw new IndexOutOfBoundsException();
        return heap[0];
    }

    /**
     * A method that extracts and removes the maximum element in the heap
     * @return the maximum element - the root
     * @throws IndexOutOfBoundsException if the heap is empty
     */
    public int extractMax() throws IndexOutOfBoundsException{
        int max = getMax();
        size--;
        if(size != 0) {
            swap(0,size);
            maxHeapify(0);
        }
        return max;
    }

    /**
     * A method that prints the heap contents as levels, each level on a line
     */
    private void print(){
        int pw2 = 1;
        for(int i=0; i<size; i++) {
            System.out.print(heap[i]);
            System.out.print(" ");
            if (i == size-1 || i == pw2 * 2 - 2){
                System.out.println();
                pw2 *= 2;
            }
        }
        System.out.println();
    }
}
