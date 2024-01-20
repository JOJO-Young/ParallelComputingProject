package algorithm.quicksort;

import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

public class ParallelQuickSort extends RecursiveAction {

    int threshold;
    int left;
    int right;
    ArrayList<Integer> sortedNumbers;

    public ParallelQuickSort(int threshold, int left, int right, ArrayList<Integer> sortedNumbers) {
        this.threshold = threshold;
        this.left = left;
        this.right = right;
        this.sortedNumbers = sortedNumbers;
    }

    @Override
    protected void compute() {
        if (right - left > threshold) {
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
            invokeAll(new ParallelQuickSort(threshold, left, pivotPos - 1, sortedNumbers),
                    new ParallelQuickSort(threshold, pivotPos + 1, right, sortedNumbers));
        } else {
            QuickSort quickSort = new QuickSort(sortedNumbers);
            quickSort.quickSortRecursive(left, right);
        }
    }

    private void swap(int i, int j) {
        int temp = sortedNumbers.get(i);
        sortedNumbers.set(i, sortedNumbers.get(j));
        sortedNumbers.set(j, temp);
    }
}