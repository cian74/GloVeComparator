package ie.atu.sw;

import java.util.Map;
import java.util.Scanner;

/**
 * The menu class provides a menu-driven interface for interacting
 * with the similarity search and word embedding's map. It allows 
 * the user to change the embedding file and the output file to their
 * specification, enter target words, reconfigure file options, and 
 * view words with the highest similarity score.
 * 
 * This class manages user inputs and invokes methods to handle various
 * operations chosen by the user.
 * 
 * Note:
 * -The default path of the embedding files and output files are provided
 * but can be changed by the user.
 * -No character case sensitivity for word comparisons.
 * -Progress bar may not work in some consoles.
 * 
 * @author cianbarrett
 * @version 1.0
 */

public class Menu {
	
    private Scanner scanner;
    private String embeddingsFilePath;
    private String outputFilePath;
    private Map<String, double[]> embeddings;
    private FileWriterUtils fileWriterUtils;

    /**
     * Initialise's a new menu set with default file paths
     * for the word embedding's file and output. Sets up a 
     * Scanner for user input.
     * 
     * @param fileWriterUtils An instance of {@link FileWriterUtils} to handle file writing
     */
    public Menu(FileWriterUtils fileWriterUtils) {
        this.scanner = new Scanner(System.in);
        this.embeddingsFilePath = "./word-embeddings.txt"; // Default paths
        this.outputFilePath = "./out.txt";
        this.embeddings = MapParser.readEmbeddings(embeddingsFilePath);// o(n) as operation depends on file size.
        this.fileWriterUtils = fileWriterUtils;
    }

    /**
     * Starts the menu interface and allows the user to choose
     * different options in the application until the user decides
     * to exit.
     * 
     * -Runs at o(n) time complexity. keeps calling methods until -1 is chosen.
     */
    public void run() { 
        String choice = "";
        //sentinel loop to keep app running
        while (!choice.equals("-1")) {
            displayOptions();
            choice = scanner.nextLine();
            processChoice(choice);
        }
        scanner.close();
    }

    /**
     * Displays a fixed number of print statements to the console
     * for the user to read and then enter an option.
     * 
     * -Runs at o(1) time complexity.
     */
    private void displayOptions() {
        System.out.println("(1) Specify Embedding File");
        System.out.println("(2) Specify an Output File (default: ./out.txt)");
        System.out.println("(3) Enter a Word or Text");
        System.out.println("(4) Configure Options");
        System.out.println("(5) Highest similarity");
        System.out.println("(-1) Quit");
        System.out.println("Select option [1-5]>:");
    }

    /**
     * Processes the user's choice and executes methods
     * based on their choice. Invalid responses wont 
     * be recognised and the user will be prompted to 
     * re-enter their choice.
     * 
     * @param choice the choice the user enter to run different operations
     */
    private void processChoice(String choice) {
        switch (choice) {
            case "1":
                System.out.println("Specify the path of the embeddings file: ");
                embeddingsFilePath = scanner.nextLine(); //o(1)
                break;
            case "2":
                System.out.println("Specify an output file: ");
                outputFilePath = scanner.nextLine(); // o(1)
                break;
            case "3":
                System.out.println("Enter words you would like to find in the map (separated by spaces): ");
                String inputLine = scanner.nextLine().toLowerCase(); // Convert input to lowercase
                String[] words = inputLine.split("\\s+"); // Split input into individual words
                StringBuilder resultBuilder = new StringBuilder();
                for (String word : words) { //o(n) as reading the input and writing to file depends on the file size
                    // Capture the returned string of similar words for each word
                    long compareTime = System.currentTimeMillis();
                    String similarWords = WordComparator.compareWithAllWords(word, embeddings);
                    System.out.println("Running time (ms): " + (System.currentTimeMillis() - compareTime));
                    // append the results for each word to the overall result string
                    resultBuilder.append(similarWords).append("\n");
                }
                // get combined results for all words entered
                String combinedResults = resultBuilder.toString();
                System.out.println("Results for all entered words:\n" + combinedResults);
                long writeTime = System.currentTimeMillis();
                fileWriterUtils.writeToOutputFile(outputFilePath, combinedResults);
                System.out.println("Running time(ms): " + (System.currentTimeMillis() - writeTime));
                // progress animation
                for (int i = 0; i < 100; i++) {
                    fileWriterUtils.printProgress(i + 1, 100);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println();
                System.out.println(combinedResults);
                break;
            case "4":
            	//o(1)
                System.out.println("Configure options");
                System.out.println("Enter the path of the embeddings file: ");
                embeddingsFilePath = scanner.nextLine();
                System.out.println("Enter the path of the output file: ");
                outputFilePath = scanner.nextLine();
                break;
            case "5":
            	//Runs at o(n) time as you read the highest score from 
            	//the file as many times as a word comparison has been made
                System.out.println("Highest Similarity");
                Map<String, Double> highestSimilarities = WordComparator.findHighestSimilarities(outputFilePath);
                if (highestSimilarities.isEmpty()) {
                    System.out.println("No similarity scores found in the output file.");
                } else {
                    for (Map.Entry<String, Double> entry : highestSimilarities.entrySet()) {
                        System.out.println("Word: " + entry.getKey() + ", Highest Similarity: " + entry.getValue());
                    }
                }
                break;
            default:
                System.out.println("Invalid choice. Please try again");
        }
    }
}
