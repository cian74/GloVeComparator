package ie.atu.sw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The MapParser class contains a method for parsing embedding's from a file
 */

public class MapParser {

	/**
	 * Reads embedding's from a user specified embedding file.
	 * 
	 * -Runs at o(n) time. n being the number  of lines in the file.
	 * 
	 * @param fileName the name of the file the user specified
	 * @return
	 */
	
	public static Map<String, double[]> readEmbeddings(String fileName) {
		Map<String, double[]> embeddings = new HashMap<>();//o(1)
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while((line = br.readLine()) != null) { //reading lines while the line is not empty -o(n)
				String[] parts = line.split(", ");
				if(parts.length == 51) { // reads vector part of txt file -o(1)
					String word = parts[0];
					double[] vector = new double[50];
					for(int i = 0; i < 50; i++) { //looping to parse doubles -o(1)
						vector[i] = Double.parseDouble(parts[i + 1]);
					}
					embeddings.put(word, vector); //inserting parts into map -o(1)
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return embeddings;
	}
}