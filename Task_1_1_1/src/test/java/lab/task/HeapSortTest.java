package lab.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * tests for heapsort
 */
public class HeapSortTest {
    private boolean isSorted(int[] arr) {
        if (arr == null) {
            return true;
        }
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) return false;
        }
        return true;
    }

    @Test
    void commonArr() {
        int[] arr = new int[] {1, 2, 7, 9, 8, 5, 4};
        HeapSort.sort(arr);
        assertTrue(isSorted(arr));
    }

    @Test
    void oddLength() {
        int[] arr = new int[] {1, 2, 7, 9, 8, 5};
        HeapSort.sort(arr);
        assertTrue(isSorted(arr));
    }

    @Test
    void sortedArr() {
        int[] arr = new int[] {1, 2, 3, 4, 5, 6, 7};
        HeapSort.sort(arr);
        assertTrue(isSorted(arr));
    }

    @Test
    void singleElement() {
        int[] arr = new int[] {1};
        HeapSort.sort(arr);
        assertTrue(isSorted(arr));
    }

    @Test
    void twoElements() {
        int[] arr = new int[] {1, 0};
        HeapSort.sort(arr);
        assertTrue(isSorted(arr));
    }

    @Test
    void emptyArr() {
        int[] arr = new int[] {};
        HeapSort.sort(arr);
        assertTrue(isSorted(arr));
    }

    @Test
    void nullArr() {
        int[] arr = null;
        HeapSort.sort(arr);
        assertTrue(isSorted(arr));
    }

    @Test
    void equalElements() {
        int[] arr = new int[] {1, 1, 1, 1, 1, 1, 1, 1};
        HeapSort.sort(arr);
        assertTrue(isSorted(arr));
    }
}
