import java.util.*;
import java.util.concurrent.*;
import java.io.*;

import algorithm.quicksort.*;
import algorithm.ranksort.*;
import algorithm.mergesort.*;

public class Sort {
    ArrayList<Integer> rawNumbers;
    ArrayList<Integer> sortedNumbers;

    long startTime;
    long endTime;
    ArrayList<Integer> quickTime = new ArrayList<>();
    ArrayList<Integer> quickTimePar = new ArrayList<>();
    ArrayList<Integer> rankTime = new ArrayList<>();
    ArrayList<Integer> rankTimePar = new ArrayList<>();
    ArrayList<Integer> mergeTime = new ArrayList<>();
    ArrayList<Integer> mergeTimePar = new ArrayList<>();

    public static void main(String[] args) {
        Sort sort = new Sort();
        sort.rawNumbers = sort.readFile("input/random30_000.txt");
        for (int i = 0; i < 10; i++) {
            sort.quickSort();
            sort.rankSort();
            sort.mergeSort();
        }
        long time1 = 0, time2 = 0, time3 = 0, time4 = 0, time5 = 0, time6 = 0;
        for (int i = 0; i < 10; i++) {
            time1 += sort.quickTime.get(i);
            time2 += sort.quickTimePar.get(i);
            time3 += sort.rankTime.get(i);
            time4 += sort.rankTimePar.get(i);
            time5 += sort.mergeTime.get(i);
            time6 += sort.mergeTimePar.get(i);
        }
        System.out.println("quickSort AvgTime: " + time1 / 10);
        System.out.println("quickSortPar AvgTime: " + time2 / 10);
        System.out.println("rankSort AvgTime: " + time3 / 10);
        System.out.println("rankSortPar AvgTime: " + time4 / 10);
        System.out.println("mergeSort AvgTime: " + time5 / 10);
        System.out.println("mergeSortPar AvgTime: " + time6 / 10);
    }

    private void quickSort() {
        // 串行算法
        startTime = System.currentTimeMillis();

        ArrayList<Integer> sortedNumbers = new ArrayList<>(rawNumbers);
        QuickSort quickSort = new QuickSort(sortedNumbers);
        quickSort.quickSort();

        endTime = System.currentTimeMillis();
        quickTime.add((int) (endTime - startTime));

        writeFile(sortedNumbers, "output/order1.txt");

        // 并行算法
        startTime = System.currentTimeMillis();

        ArrayList<Integer> parallelSortedNumbers = new ArrayList<>(rawNumbers);
        RecursiveAction parallelQuickSort = new ParallelQuickSort(2048, 0, rawNumbers.size() - 1,
                parallelSortedNumbers);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(parallelQuickSort);

        endTime = System.currentTimeMillis();
        quickTimePar.add((int) (endTime - startTime));

        writeFile(parallelSortedNumbers, "output/order2.txt");
    }

    private void rankSort() {
        // 串行算法
        startTime = System.currentTimeMillis();

        ArrayList<Integer> sortedNumbers = new ArrayList<>(rawNumbers);
        RankSort rankSort = new RankSort(rawNumbers, sortedNumbers);
        rankSort.rankSort();

        endTime = System.currentTimeMillis();
        rankTime.add((int) (endTime - startTime));

        writeFile(sortedNumbers, "output/order3.txt");

        // 并行算法
        startTime = System.currentTimeMillis();

        ArrayList<Integer> parallelSortedNumbers = new ArrayList<>(rawNumbers);
        ExecutorService executor = Executors.newFixedThreadPool(8);
        for (int pos = 0; pos < rawNumbers.size(); pos++) {
            ParallelRankSort task = new ParallelRankSort(rawNumbers, parallelSortedNumbers, pos);
            executor.submit(task);
        }
        executor.shutdown();
        try {
            executor.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        endTime = System.currentTimeMillis();
        rankTimePar.add((int) (endTime - startTime));

        writeFile(parallelSortedNumbers, "output/order4.txt");
    }

    private void mergeSort() {
        // 串行算法
        startTime = System.currentTimeMillis();

        ArrayList<Integer> sortedNumbers = new ArrayList<>(rawNumbers);
        MergeSort mergeSort = new MergeSort(sortedNumbers);
        mergeSort.mergeSort();

        endTime = System.currentTimeMillis();
        mergeTime.add((int) (endTime - startTime));

        writeFile(sortedNumbers, "output/order5.txt");

        // 并行算法
        startTime = System.currentTimeMillis();

        ArrayList<Integer> parallelSortedNumbers = new ArrayList<>(rawNumbers);
        RecursiveAction parallelMergeSort = new ParallelMergeSort(2048, 0, parallelSortedNumbers.size() - 1,
                parallelSortedNumbers);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(parallelMergeSort);

        endTime = System.currentTimeMillis();
        mergeTimePar.add((int) (endTime - startTime));

        writeFile(parallelSortedNumbers, "output/order6.txt");
    }

    private ArrayList<Integer> readFile(String filePath) {
        ArrayList<Integer> numbers = new ArrayList<>();
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextInt()) {
                int num = scanner.nextInt();
                numbers.add(num);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return numbers;
    }

    private void writeFile(ArrayList<Integer> numbers, String filePath) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < numbers.size(); i++) {
                bufferedWriter.write(numbers.get(i).toString());
                if (i != numbers.size())
                    bufferedWriter.write(" ");
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}