package lab.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * tests for heapsort.
 */
public class HeapSortTest {
    private boolean eqArrays(int[] expected, int[] actual) {
        if (expected == null && actual == null) {
            return true;
        }

        if (expected == null || actual == null) {
            return false;
        }

        if (expected.length != actual.length) {
            return false;
        }

        for (int i = 0; i < expected.length; i++) {
            if (expected[i] != actual[i]) {
                return false;
            }
        }

        return true;
    }

    @Test
    void commonArr() {
        int[] arr = new int[] {1, 2, 7, 9, 8, 5, 4};
        HeapSort.sort(arr);
        int[] expected = new int[] {1, 2, 4, 5, 7, 8, 9};
        assertTrue(eqArrays(expected, arr));
    }

    @Test
    void oddLength() {
        int[] arr = new int[] {1, 2, 7, 9, 8, 5};
        HeapSort.sort(arr);
        int[] expected = new int[] {1, 2, 5, 7, 8, 9};
        assertTrue(eqArrays(expected, arr));
    }

    @Test
    void sortedArr() {
        int[] arr = new int[] {1, 2, 3, 4, 5, 6, 7};
        HeapSort.sort(arr);
        int[] expected = new int[] {1, 2, 3, 4, 5, 6, 7};
        assertTrue(eqArrays(expected, arr));
    }

    @Test
    void singleElement() {
        int[] arr = new int[] {1};
        HeapSort.sort(arr);
        int[] expected = new int[] {1};
        assertTrue(eqArrays(expected, arr));
    }

    @Test
    void twoElements() {
        int[] arr = new int[] {1, 0};
        HeapSort.sort(arr);
        int[] expected = new int[] {0, 1};
        assertTrue(eqArrays(expected, arr));
    }

    @Test
    void emptyArr() {
        int[] arr = new int[] {};
        HeapSort.sort(arr);
        int[] expected = new int[] {};
        assertTrue(eqArrays(expected, arr));
    }

    @Test
    void nullArr() {
        int[] arr = null;
        HeapSort.sort(arr);
        int[] expected = null;
        assertTrue(eqArrays(expected, arr));
    }

    @Test
    void equalElements() {
        int[] arr = new int[] {1, 1, 1, 1, 1, 1, 1, 1};
        HeapSort.sort(arr);
        int[] expected = new int[] {1, 1, 1, 1, 1, 1, 1, 1};
        assertTrue(eqArrays(expected, arr));
    }
}
