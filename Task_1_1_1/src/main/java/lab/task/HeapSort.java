package lab.task;

import java.util.Arrays;

/**
 * implementation of heapsort for arrays of numbers
 */
public class HeapSort {
    /**
     * @param arr
     * @param index1
     * @param index2
     * swaps two elements in arr on index1 and index2
     */
    private static void swap(int[] arr, int index1, int index2) {
        int tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }

    /**
     * @param arr
     * @param index
     * @param end
     * sifts down arr[index] until arr doesn't satisfy the binary heap condition
     */
    private static void siftDown(int[] arr, int index, int end) {
        int curr = index;
        while (curr * 2 + 1 < end) {
            int next1 = curr * 2 + 1;
            int next2 = next1 + 1 < end ? next1 + 1 : next1;
            if (arr[curr] > arr[next1] && arr[curr] > arr[next2]) {
                return;
            }

            int smaller = arr[next1] > arr[next2] ? next1 : next2;
            swap(arr, curr, smaller);
            curr = smaller;
        }
    }

    /**
     * @param arr
     * turns an array into a binary heap
     */
    private static void arrayToHeap(int[] arr) {
        int middle = arr.length / 2;
        while (middle > -1) {
            siftDown(arr, middle, arr.length);
            middle--;
        }
    }

    /**
     * the heapsort algorythm itself
     * @param arr
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length == 1) {
            return;
        }
        arrayToHeap(arr);
        for (int end = arr.length; end > 0; end--) {
            swap(arr, 0, end - 1);
            siftDown(arr, 0, end - 1);
        }
    }

    /**
     * demonstration of heapsort algorythm
     * @param args
     */
    public static void main(String[] args) {
        int[] arr = new int[] {9, 8, 8, 7, 3, 4, 7, 6, 5, 4};
        HeapSort.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
