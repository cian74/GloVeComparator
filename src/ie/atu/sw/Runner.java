package ie.atu.sw;

/**
 * @author cianbarrett
 * @version 1.0
 * 
 *          The Runner class allows the user to interact with the similarity
 *          search and word embedding's in a menu driven interface that allows
 *          the user to change the output files, embedding files, enter a target
 *          word, edit current out files and embedding files, and view the word
 *          with the highest similarity score.
 * 
 *          The class includes methods for reading the word embedding's from a
 *          file, comparing the target word against other words in the map,
 *          writing the results to a specified output text file and displaying a
 *          progress meter.
 * 
 *          Note: 
 *          -The embedding file and text file have a default path but can
 *          be changed by the user. 
 *          -Supports case insensitive words for word
 *          comparisons. 
 *          -Progress bar may not work in certain consoles.
 * 
 */

public class Runner {

	public static void main(String[] args) throws Exception {
		// You should put the following code into a menu or Menu class
		System.out.println(ConsoleColour.WHITE);
		System.out.println("************************************************************");
		System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		System.out.println("*                                                          *");
		System.out.println("*          Similarity Search with Word Embeddings          *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		
		//creates new instance of menu
		Menu menu = new Menu(new FileWriterUtils());
		
		//calls run method on menu object
		menu.run();
	}
}