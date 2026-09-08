package lab.task;

import java.util.Arrays;
/**
 * implementation of heapsort for arrays of numbers.
 */
public class HeapSort {
    /**
     * swaps two elements in arr on index1 and index2.
     * @param arr an array
     * @param index1 first index
     * @param index2 second index
     */
    private static void swap(int[] arr, int index1, int index2) {
        int tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }

    /**
     * sifts down arr[index] until arr doesn't satisfy the binary heap condition.
     * @param arr an array
     * @param index element to sift
     * @param end where to stop sifting
     */
    private static void siftDown(int[] arr, int index, int end) {
        int curr = index;
        while (curr * 2 + 1 < end) {
            int next1 = curr * 2 + 1;
            int next2 = next1 + 1 < end ? next1 + 1 : next1;
            if (arr[curr] > arr[next1] && arr[curr] > arr[next2]) return;

            int smaller = arr[next1] > arr[next2] ? next1 : next2;
            swap(arr, curr, smaller);
            curr = smaller;
        }
    }

    /**
     * turns an array (can't be null) into a binary heap.
     * @param arr an array
     */
    private static void arrayToHeap(int[] arr) {
        if (arr.length < 2) {
            return;
        }
        int middle = arr.length / 2;
        while (middle > -1) {
            siftDown(arr, middle, arr.length);
            middle--;
        }
    }

    /**
     * the in-place heapsort algorythm itself.
     * @param arr an array (can be null and algorythm does nothing in that case)
     */
    public static void sort(int[] arr) {
        if (arr == null) {
            return;
        }
        arrayToHeap(arr);
        for (int end = arr.length; end > 0; end--) {
            swap(arr, 0, end - 1);
            siftDown(arr, 0, end - 1);
        }
    }

    /**
     * main method for java command and demonstration.
     * @param args arguments(there's none)
     */
    public static void main(String[] args) {
        int[] arr = new int[] {1, 2, 7, 9, 8, 5, 4};
        HeapSort.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
