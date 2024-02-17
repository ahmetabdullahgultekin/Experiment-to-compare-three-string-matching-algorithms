import java.util.*;

public class BoyerMooreAlgorithm {
    static int[] goodSuffix;
    static Map<Character, Integer> badSymbol = new HashMap<>();

    public static List<Integer> findAllOccurrences(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();
        int match = 0;
        int comparisonCounter = 0;

        goodSuffix = new int[m - 1];

        //create tables
        createBadSymbol(pattern);
        createGoodSuffix(pattern);

        //search string
        int i = 0;
        while (i <= n - m) {
            comparisonCounter++;
            int j = m - 1;
            while (j >= 0 && pattern.charAt(j) == text.charAt(i + j)) { //character matches
                j--;
                match++;
            }
            if (j < 0) {   //found a match
                matches.add(i);
                i++;
                match = 0;
            } else {
                if (match == 0) //just look bad symbol table
                    i += badSymbol.getOrDefault(text.charAt(i + j), m);
                else { // look at both tables
                    i += Math.max(goodSuffix[match - 1], badSymbol.getOrDefault(text.charAt(i + j), m) - match);
                    match = 0;
                }
            }
        }
        System.out.println("Number of comparisons: " + comparisonCounter);
        return matches;
    }

    private static void createBadSymbol(String pattern) {
        for (int i = 0; i < pattern.length() - 1; i++) {
            badSymbol.put(pattern.charAt(i), pattern.length() - i - 1);
        }
    }

    private static void createGoodSuffix(String pattern) {
        Arrays.fill(goodSuffix, pattern.length());
        String suffix;
        char previousCh;

        for (int k = 1; k <= goodSuffix.length; k++) {
            suffix = pattern.substring(pattern.length() - k);
            previousCh = pattern.charAt(pattern.length() - k - 1);
            for (int i = 0; i < pattern.length() - k; i++) {
                if (pattern.substring(i).startsWith(suffix)) { //check if suffix is in pattern
                    if (i == 0) {
                        goodSuffix[k - 1] = pattern.length() - k - i;
                        continue;
                    } else if (previousCh != pattern.charAt(i - 1)) {
                        goodSuffix[k - 1] = pattern.length() - k - i;
                        continue;
                    }
                }
                if (goodSuffix[k - 1] == pattern.length()) { //if shift value is not assigned yet. (it is default value)
                    for (int j = 0; j < suffix.length(); j++) {  //check if suffix is at the beginning of the pattern
                        if (pattern.startsWith(suffix.substring(j))) {
                            goodSuffix[k - 1] = pattern.length() - (k - j);
                            break;
                        }
                    }
                }
            }
        }
    }
}
