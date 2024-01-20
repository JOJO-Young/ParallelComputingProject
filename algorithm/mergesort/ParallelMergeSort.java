package algorithm.mergesort;

import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

import algorithm.quicksort.ParallelQuickSort;

public class ParallelMergeSort extends RecursiveAction {

    int threshold;
    int left;
    int right;
    ArrayList<Integer> sortedNumbers;

    public ParallelMergeSort(int threshold, int left, int right, ArrayList<Integer> sortedNumbers) {
        this.threshold = threshold;
        this.left = left;
        this.right = right;
        this.sortedNumbers = sortedNumbers;
    }

    @Override
    protected void compute() {
        if (right - left > threshold) {
            int mid = (left + right) / 2;
            invokeAll(
                    new ParallelMergeSort(threshold, left, mid, sortedNumbers),
                    new ParallelQuickSort(threshold, mid + 1, right, sortedNumbers));
            merge(left, mid, right);
        } else {
            MergeSort mergeSort = new MergeSort(sortedNumbers);
            mergeSort.mergeSortRecursive(left, right);
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
