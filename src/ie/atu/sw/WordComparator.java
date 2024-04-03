package ie.atu.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * The WordComparator class includes methods for comparing words
 * with other words in the map, and finding the highest similarity
 * scores. Also includes methods for calculating the dot product 
 * between two vectors and finding the cosine similarity.
 */
public class WordComparator {

	/**
	 * Finds the highest similarity score for each target word in the
	 * output text file.
	 * 
	 * -Runs at o(n) time complexity. as number of lines in output file
	 * varies.
	 * 
	 * @param outputFilePath the path of the file the results are written to.
	 * @return
	 */
	public static Map<String, Double> findHighestSimilarities(String outputFilePath) {
	    Map<String, Double> highestSimilarities = new HashMap<>();

	    try (Scanner scanner = new Scanner(new File(outputFilePath))) {
	        String currentWord = null;

	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();

	            // Check if the line contains the target word
	            if (line.startsWith("Similar words to")) {
	                // Extract the target word
	                currentWord = line.split("'")[1];
	            }

	            // check if line starts with the most similar word and its similarity score
	            if (line.startsWith("The most similar word to")) {
	                // extract the similarity score
	                String similarityScoreStr = line.split(" ")[line.split(" ").length - 1];
	                
	                // Remove single quotes from the similarity score string
	                similarityScoreStr = similarityScoreStr.replaceAll("'", "");
	                
	                // Parse the similarity score
	                double similarityScore = Double.parseDouble(similarityScoreStr);

	                // Update the highest similarity for the current word
	                highestSimilarities.put(currentWord, similarityScore);
	            }
	        }
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }

	    return highestSimilarities;
	}

	

	/**
	 * Calculates the dot product of two vectors
	 * 
	 * -Runs at o(1) time as the size of the vectors are constant.
	 * 
	 * @param vector1 The first vector (target word vector)
	 * @param vector2 The second vector (vector of all the words the target word is compared to)
	 * @return The dot product of the two vectors.
	 */
	private static double calculateDotProduct(double[] vector1, double[] vector2) {
		double dotProduct = 0;
		for (int i = 0; i < vector1.length; i++) {
			dotProduct += vector1[i] * vector2[i];
		}
		return dotProduct;
	}

	/**
	 * Calculates the cosine similarity between two vectors, using 
	 * the dotproduct divided by the product of both magnitudes.
	 * 
	 * 
	 * 
	 * -Runs at o(1) time as the size of the vectors are constant, o(50).
	 * 
	 * @param vector1 The first vector (target word vector)
	 * @param vector2 The second vector (vector of all the words the target word is compared to)
	 * @return the cosine similarity between the two vectors.
	 */
	private static double calculateCosineSimilarity(double[] vector1, double[] vector2) {
		double dotProduct = calculateDotProduct(vector1, vector2);
		double magnitude1 = 0;
		double magnitude2 = 0;
		//loops over each element in the vector and squares the value
		//the value is added to the magnitude for each element
		for (int i = 0; i < vector1.length; i++) {
			magnitude1 += Math.pow(vector1[i], 2);
			magnitude2 += Math.pow(vector2[i], 2);
		}
		//square root the magnitudes
		magnitude1 = Math.sqrt(magnitude1);
		magnitude2 = Math.sqrt(magnitude2);
		//returns the cosine similarity / angle between the vectors
		return dotProduct / (magnitude1 * magnitude2);
	}
	
	/**
	 * Compares the target word with all other words in the map
	 * and prints similar words with a similarity score of greater
	 * than 0.75.
	 * 
	 * @param targetWord The word the user enters for comparison
	 * @param embeddings the embedding's map containing all words and their vectors
	 * @return
	 */
	public static String compareWithAllWords(String targetWord, Map<String, double[]> embeddings) {
		//gets the vector of the target word from the map and
		//stores it to a double array
		double[] targetVector = embeddings.get(targetWord);// o(1)
		//check if target word exists
		if (targetVector == null) {
			return "Target word '" + targetWord + "' not found in embeddings.";
		}
		//initialise string joiner to add results.
		StringJoiner joiner = new StringJoiner("\n");
		joiner.add("Similar words to '" + targetWord + "' with similarity score > 0.75:");

		//variablse to keep track of highest score.
		double maxSimilarity = 0;
		String mostSimilarWord = "";

		for (Map.Entry<String, double[]> entry : embeddings.entrySet()) { // iterates over embeddings map o(n)
			if (!entry.getKey().equals(targetWord)) { // compares target word against every other word but itself(1)
				double similarity = calculateCosineSimilarity(targetVector, entry.getValue());// o(n)
				if (similarity > 0.75) {
					joiner.add(entry.getKey() + ": " + similarity);
					if (similarity > maxSimilarity) // keeps track of highest similarity o(1)
					{
						maxSimilarity = similarity;
						mostSimilarWord = entry.getKey();
					}
				}
			}
		}
		//print highest score.
		if (!mostSimilarWord.isEmpty()) {
			joiner.add(String.format("\nThe most similar word to '%s' is '%s' with a similarity score of '%f'",
					targetWord, mostSimilarWord, maxSimilarity));
		} else {
			joiner.add("\nNo similar words found");

		}
		return joiner.toString();
	}
}
