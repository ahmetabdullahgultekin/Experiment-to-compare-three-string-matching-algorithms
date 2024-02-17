import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomBitStrings {
    public static void main(String[] args) {
        String fileName = "src/Texts/bitString.html";
        long fileSize = 5 * 1024L * 1024L; // define size as 5MB

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            StringBuilder str = new StringBuilder();
            Random random = new Random();

            for (long i = 0; i < fileSize; i++){
                str.append(random.nextInt(2)); //create random bit and add it to str
            }

            // write the created bit string into output file
            fileWriter.write(String.valueOf(str));
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
