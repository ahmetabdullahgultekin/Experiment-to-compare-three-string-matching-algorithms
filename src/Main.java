import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class Main {
    private static void printResult(String text, List<Integer> list, int size) throws IOException {
        String fileName = "output.html";
        FileWriter fileWriter = new FileWriter(fileName);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        String markedText = String.valueOf(markText(text, list, size));
        bufferedWriter.write(markedText);
        bufferedWriter.close();
    }

    public static StringBuilder markText(String text, List<Integer> list, int size) {
        Collections.reverse(list);
        StringBuilder result = new StringBuilder(text);
        int startIndex, endIndex;


        for (int i = 0; i < list.size(); i++) {
            endIndex = list.get(i);
            if ((i != list.size() - 1) && (list.get(i) - list.get(i + 1) < size)) { //overlapping part
                for (int j = i + 1; j < list.size(); j++, i++) {
                    startIndex = list.get(j);
                    if (j == list.size() - 1) {
                        result.replace(startIndex, endIndex + size, "<mark>" + text.substring(startIndex, endIndex + size) + "</mark>");
                        break;
                    } else if (list.get(j) - list.get(j + 1) >= size) {
                        result.replace(startIndex, endIndex + size, "<mark>" + text.substring(startIndex, endIndex + size) + "</mark>");
                        break;
                    }
                }
                i++;
            } else {
                result.replace(endIndex, endIndex + size, "<mark>" + text.substring(endIndex, endIndex + size) + "</mark>");
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String textFileName = "src/Texts/SampleInput.html";
        String pattern = "AT_THAT";
        String text = "";
        List<Integer> occurrences;
        long startTime, endTime, totalTime;

        try {
            text = new String(Files.readAllBytes(Paths.get(textFileName)));
            text = text.replace("\r\n", " "); //remove all new line characters

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ////////////////////////////////////////////////////////////////////////////////////////////

        // Brute Force algorithm
        System.out.println("----------Brute Force----------");
        startTime = System.currentTimeMillis();
        occurrences = BruteForceAlgorithm.findAllOccurrences(text, pattern);
        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;

        //Print results
        System.out.println(occurrences.size() + " match");
        System.out.println(totalTime + " milliseconds\n");

        ////////////////////////////////////////////////////////////////////////////////////////////

        // Horspool's algorithm
        System.out.println("----------Horspool----------");
        startTime = System.currentTimeMillis();
        occurrences = HorspoolAlgorithm.findAllOccurrences(text, pattern);
        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;

        //Print results
        System.out.print("Bad Symbol table -> " + HorspoolAlgorithm.badSymbol);
        System.out.printf(" , {Others = %d}\n", pattern.length());

        System.out.println(occurrences.size() + " match");
        System.out.println(totalTime + " milliseconds\n");

        ////////////////////////////////////////////////////////////////////////////////////////////

        // Boyer-Moore algorithm
        System.out.println("----------Boyer-Moore----------");
        startTime = System.currentTimeMillis();

        occurrences = BoyerMooreAlgorithm.findAllOccurrences(text, pattern);
        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;

        //Print results
        System.out.print("Bad Symbol Table -> " + BoyerMooreAlgorithm.badSymbol);
        System.out.printf(" , {Others = %d}\n", pattern.length());
        System.out.println("Good Suffix Table");
        for (int i = 1; i <= BoyerMooreAlgorithm.goodSuffix.length; i++) {
            System.out.printf("k = %d -> %d\n", i, BoyerMooreAlgorithm.goodSuffix[i - 1]);
        }
        System.out.println(occurrences.size() + " match");
        System.out.println(totalTime + " milliseconds\n");

        ////////////////////////////////////////////////////////////////////////////////////////////

        try {
            printResult(text, occurrences, pattern.length());
        } catch (IOException e) {
            System.out.println("Error writing results to output file");
        }
    }
}