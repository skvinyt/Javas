import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class extwo {

    public static void main(String[] args) {
        String filePath = "input.txt";
        Map<String, Integer> wordCountMap = new HashMap<>();
        String longestWord = "";
        int totalWords = 0;

        // Read the file and process words
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    totalWords++;
                    word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
                    if (!word.isEmpty()) {
                        wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
                        if (word.length() > longestWord.length()) {
                            longestWord = word;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        // Print results
        System.out.println("Total words: " + totalWords);
        System.out.println("Longest word: " + longestWord);
        System.out.println("Word frequencies:");
        wordCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }
}