import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HorspoolAlgorithm {
    static Map<Character, Integer> badSymbol = new HashMap<>();

    public static List<Integer> findAllOccurrences(String text, String pattern) {

        int comparisonCounter = 0;
        List<Integer> matches = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();

        // Create bad symbol table
        createBadSymbol(pattern);


        // Find matches
        int i = m - 1;
        while (i < n) {
            comparisonCounter++;
            int k = 0;
            while ((k < m) && (text.charAt(i - k) == pattern.charAt(m - 1 - k))) { //character matches
                k++;
            }
            if (k == m) { //found a match
                matches.add(i - m + 1);
                i++;
                continue;
            }
            //shift according to table
            i += badSymbol.getOrDefault(text.charAt(i), m);
        }
        System.out.println("Number of comparisons: " + comparisonCounter);
        return matches;
    }

    private static void createBadSymbol(String pattern) {
        for (int i = 0; i < pattern.length() - 1; i++) {
            badSymbol.put(pattern.charAt(i), pattern.length() - i - 1);
        }
    }
}
