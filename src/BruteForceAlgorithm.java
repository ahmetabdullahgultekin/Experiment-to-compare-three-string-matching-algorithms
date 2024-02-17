import java.util.ArrayList;
import java.util.List;

public class BruteForceAlgorithm {
    public static List<Integer> findAllOccurrences(String text, String pattern) {
        int comparisonCounter = 0;
        List<Integer> occurrences = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();

        //search until end of text
        int i = 0;
        while (i <= n - m) {
            comparisonCounter++;
            int j;
            for (j = 0; j < m; j++)
                if (text.charAt(i + j) != pattern.charAt(j))
                    break;
            //found a match
            if (j == m)
                occurrences.add(i);
            i++;
        }

        System.out.println("Number of comparisons: " + comparisonCounter);
        return occurrences;
    }
}