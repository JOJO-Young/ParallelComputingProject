package algorithm.quicksort;

import java.util.*;

public class QuickSort {

    ArrayList<Integer> sortedNumbers;

    public QuickSort(ArrayList<Integer> sortedNumbers) {
        this.sortedNumbers = sortedNumbers;
    }

    public void quickSort() {
        quickSortRecursive(0, sortedNumbers.size() - 1);
    }

    public void quickSortRecursive(int left, int right) {
        if (left > right)
            return;
        int pivot = sortedNumbers.get(right);
        int nowPos = left;
        int pivotPos;
        for (int i = left; i < right; i++) {
            if (sortedNumbers.get(i) <= pivot) {
                swap(nowPos, i);
                nowPos++;
            }
        }
        pivotPos = nowPos;
        swap(pivotPos, right);
        quickSortRecursive(left, pivotPos - 1);
        quickSortRecursive(pivotPos + 1, right);
    }

    private void swap(int i, int j) {
        int temp = sortedNumbers.get(i);
        sortedNumbers.set(i, sortedNumbers.get(j));
        sortedNumbers.set(j, temp);
    }
}
