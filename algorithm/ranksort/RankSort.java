package algorithm.ranksort;

import java.util.*;

public class RankSort {

    ArrayList<Integer> rawNumbers;
    ArrayList<Integer> sortedNumbers;

    public RankSort(ArrayList<Integer> rawNumbers, ArrayList<Integer> sortedNumbers){
        this.rawNumbers = rawNumbers;
        this.sortedNumbers = sortedNumbers;
    }

    public void rankSort() {
        for (int i = 0; i < rawNumbers.size(); i++) {
            int rank = 0;
            for (int j = 0; j < rawNumbers.size(); j++)
                if (rawNumbers.get(i) > rawNumbers.get(j) || (rawNumbers.get(i) == rawNumbers.get(j) && i > j))
                    rank++;
            sortedNumbers.set(rank, rawNumbers.get(i));
        }
    }
}