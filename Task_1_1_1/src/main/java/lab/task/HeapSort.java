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
        while (index * 2 + 1 < end) {
            int next = index * 2 + 1;
            if (next + 1 < end && arr[next] < arr[next + 1]) {
                next++;
            }
            if (arr[index] > arr[next]) {
                return;
            }
            swap(arr, index, next);
            index = next;
        }
    }
    /**
     * turns an array (can't be null) into a binary heap.
     * @param arr an array
     */
    private static void arrayToHeap(int[] arr) {
        for (int middle = arr.length / 2; middle > -1; middle--) {
            siftDown(arr, middle, arr.length);
        }
    }
    /**
     * the in-place heapsort algorythm itself.
     * @param arr an array (can be null and algorythm does nothing in that case)
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
     * main method for java command and demonstration.
     * @param args arguments(there's none)
     */
    public static void main(String[] args) {
        int[] arr = new int[] {1, 2, 7, 9, 8, 5, 4};
        HeapSort.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
