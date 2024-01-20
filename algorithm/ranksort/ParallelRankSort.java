package algorithm.ranksort;

import java.util.*;

public class ParallelRankSort implements Runnable {
    int pos;
    ArrayList<Integer> rawNumbers;
    ArrayList<Integer> sortedNumbers;

    public ParallelRankSort(ArrayList<Integer> rawNumbers, ArrayList<Integer> sortedNumbers, int pos) {
        this.rawNumbers = rawNumbers;
        this.sortedNumbers = sortedNumbers;
        this.pos = pos;
    }

    @Override
    public void run() {
        int rank = 0;
        for (int i = 0; i < rawNumbers.size(); i++)
            if (rawNumbers.get(pos) > rawNumbers.get(i) || (rawNumbers.get(pos) == rawNumbers.get(i) && pos > i))
                rank++;
        sortedNumbers.set(rank, rawNumbers.get(pos));
    }
}
