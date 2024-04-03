package ie.atu.sw;

import java.io.FileWriter;
import java.io.IOException;

/**
 * The FileWriterUtils class provides methods for writing content to
 * an output file and printing the progress bars to the console.
 * 
 * If the file does not exist, it will be created. If the file already
 * exists it will be overwritten.
 */

public class FileWriterUtils {

	/**
	 * Writes strings passed into content to the user specified output
	 * file.
	 * 
	 * -Runs at o(n) time. operation speed depends on size of content string.
	 * @param outputFilePath Path to the output text file.
	 * @param content The content that will be written to the file.
	 */
	
    public void writeToOutputFile(String outputFilePath, String content) {
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            writer.write(content);
            System.out.println("Results have been written to the output file: " + outputFilePath);
        } catch (IOException e) { // catches if file cannot be found
            System.out.println("An error occurred while writing to the output file: " + outputFilePath);
            e.printStackTrace();
        }
    }

    /**
     * Prints a progress bar animation to the console
     * 
     * -Runs at o(1) time. size is constant.
     * @param index The current progress value
     * @param total The maximum value
     */
    public void printProgress(int index, int total) {
        if (index > total)
            return; // Out of range
        int size = 50; // Must be less than console width
        char done = '█'; // Change to whatever you like.
        char todo = '░'; // Change to whatever you like.

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append((i < size * index / total) ? done : todo);
        }

        System.out.print("\r" + sb + "] " + (index * 100 / total) + "%");

        if (done == total)
            System.out.println("\n");
    }
}
