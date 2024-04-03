GloVe Comparator
@author Cian Barrett 
@version Java 17
Description
This is a console-based application that compares a word entered by the
user against 59602 other words from a file using a map and comparison
algorithm’s which prints out the most similar words. The higher the
similarity score, the closer the word can relate to the target word.

To Run

From a console at the dsa.jar directory.
java -cp .:./dsa.jar ie.atu.sw.Runner
Navigate through options to set desired files to be used.
Default path for embedding file and output file are set but can be changed
by the user:
this.outputputFilePath = “./out.txt”;
this.embeddingsFilePath = “../word-embeddings.txt”; [assuming embedding
file is in project directory and running from jar file in bin folder].
User can choose different operations and will only exit by entering -1

Features

Specify embedding file
• Input path to file
Specify output file
• Input path/name to output file
Search the map + Word comparison
• Input a word or collection of words.
• -this calls the WordComparator class which uses comparison
algorithms to find the similarity scores for all the words entered.
• The results are then printed to an outout text file.
Highest similarity score
• The highest similarity score and its corresponding word can be
tracked in the compareWithAllWords method and stored in a map
highestSimilarities which is then printed to the file.
• When option 5 is chosen it calls the findHighestSimilarities method
which reads the lines in the output file that start with “the most
similar word to” and prints the similarity score to the console.
Configure options
• File paths can be re-configured at any time

References

https://stackoverflow.com/questions/520241/how-do-icalculate-the-cosine-similarity-of-two-vectors //cosine
similarity algorithm
https://stackoverflow.com/a/54972937 //reading from file
with startsWith
