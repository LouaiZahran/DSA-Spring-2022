package heap;

import javax.naming.SizeLimitExceededException;

public class MaxHeap {
    private int heap[];
    private int maxSize;
    private int size;

    public MaxHeap(int n){
        heap = new int[n];
        maxSize = n;
        size = 0;
    }

    public MaxHeap(int arr[]){
        int n = arr.length;
        heap = new int[2*n];
        maxSize = 2*n;
        size = n;

        for(int i=0; i<n; i++)
            heap[i] = arr[i];

        for(int i=0; i<n; i++)
            maxHeapify(i);
    }

    public static void sort(int arr[]){
        MaxHeap heap = new MaxHeap(arr);
        for(int i=0; i<arr.length; i++)
            arr[i] = heap.extractMax();
    }

    private void swap(int a, int b){
        int tmp = heap[a];
        heap[a] = heap[b];
        heap[b] = tmp;
    }

    private void maxHeapify(int current){
        if(current >= size/2) //if in leaf level
            return;
        int leftChild = current*2 + 1;
        int rightChild = current*2 + 2;
        if(leftChild < size && heap[current] < heap[leftChild]){
            swap(current, leftChild);
            maxHeapify(leftChild);
        }else if(rightChild < size && heap[current] < heap[rightChild]){
            swap(current, rightChild);
            maxHeapify(rightChild);
        }
    }

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

    public int getMax() throws IndexOutOfBoundsException{
        if(size == 0)
            throw new IndexOutOfBoundsException();
        return heap[0];
    }

    public int extractMax() throws IndexOutOfBoundsException{
        int max = getMax();
        size--;
        if(size != 0) {
            heap[0] = heap[size];
            maxHeapify(0);
        }
        return max;
    }

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