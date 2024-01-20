package input;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomDataGenerator {
    public static void main(String[] args) {
        String fileName = "input/random300_000.txt";
        int dataSize = 300_000;
        int minValue = -500_000;
        int maxValue = 500_000;

        generateRandomData(fileName, dataSize, minValue, maxValue);
    }

    public static void generateRandomData(String fileName, int dataSize, int minValue, int maxValue) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            Random random = new Random();

            for (int i = 0; i < dataSize; i++) {
                int randomNumber = random.nextInt(maxValue - minValue + 1) + minValue;
                writer.write(String.valueOf(randomNumber));

                if (i < dataSize - 1) {
                    writer.write(" ");
                }
            }

            writer.close();
            System.out.println("Random data generated successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while generating random data.");
            e.printStackTrace();
        }
    }
}