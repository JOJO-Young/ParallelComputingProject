package algorithm.mergesort;

import java.util.*;

public class MergeSort {

    ArrayList<Integer> sortedNumbers;

    public MergeSort(ArrayList<Integer> sortedNumbers) {
        this.sortedNumbers = sortedNumbers;
    }

    public void mergeSort() {
        mergeSortRecursive(0, sortedNumbers.size() - 1);
    }

    /** Merge two sorted lists */
    public void mergeSortRecursive(int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSortRecursive(left, middle);
            mergeSortRecursive(middle + 1, right);
            merge(left, middle, right);
        }
    }

    private void merge(int left, int mid, int right) {
        ArrayList<Integer> leftArray = new ArrayList<>(sortedNumbers.subList(left, mid + 1));
        ArrayList<Integer> rightArray = new ArrayList<>(sortedNumbers.subList(mid + 1, right + 1));
        int i = 0;
        int j = 0;
        for (int k = left; k <= right; k++) {
            if (i == leftArray.size()) {
                sortedNumbers.set(k, rightArray.get(j));
                j++;
            } else if (j == rightArray.size()) {
                sortedNumbers.set(k, leftArray.get(i));
                i++;
            } else if (leftArray.get(i) <= rightArray.get(j)) {
                sortedNumbers.set(k, leftArray.get(i));
                i++;
            } else {
                sortedNumbers.set(k, rightArray.get(j));
                j++;
            }
        }
    }
}