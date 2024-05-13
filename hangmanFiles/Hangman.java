package tree;

import java.util.Random;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;



public class Hangman{

	// Instance variables
	int numLetters; // Length of word 
	int difficulty; // Level of difficulty

	int numIncorrectAttempts; // Number of current incorrect guesses
	int maxIncorrectAtempts; // Number of allowed incorrect guesses
	boolean gameWin; // Boolean to detrmine when game is over

	String targetWord; // Target word that will be randomly generated
	ArrayList<Character> emptyWord;
	HashMap<String, String> wordTable; 
	ArrayList<String> words; // Array list to hold words also in Radix tree
	RadixTree radixTree; //hold radix tree instances 

	// Number of allowed incorrect guesses, given different difficulties
	public final int EASY_MAX_NUM_INCORRECT = 10; 
	public final int MEDIUM_MAX_NUM_INCORRECT = 8;
	public final int HARD_MAX_NUM_INCORRECT = 6;
	
	// Constructor Method
	public Hangman(int numLetters, int difficulty){

		this.numLetters = numLetters;
		this.difficulty = difficulty;

		if(difficulty == 1){
			this.maxIncorrectAtempts = EASY_MAX_NUM_INCORRECT;
		}
		else if(difficulty == 2){
			this.maxIncorrectAtempts = MEDIUM_MAX_NUM_INCORRECT;
		}
		else if(difficulty == 3){
			this.maxIncorrectAtempts = HARD_MAX_NUM_INCORRECT;
		}
		this.numIncorrectAttempts = 0;
		this.wordTable = new HashMap<String, String>();
		this.words = new ArrayList<>();
		this.gameWin = false;
		this.emptyWord = new ArrayList<Character>();
		this.radixTree = new RadixTree();
	}


	/**
	 * Injest method used to add words to RadixTree
	 **/ 
	public void injestWords(){	//// Will have to modify to place words into rdix tree
	
	// Read in data
	String filePath; 
	filePath = "HangmanFiles/words.txt";

	
	try{
		File file = new File(filePath);
		Scanner scanner = new Scanner(file);
	
		boolean skipped = false;
		while (scanner.hasNextLine()) {

			String line = scanner.nextLine();
			String[] parts = line.split("/");

			if(parts.length > 1){
				String word = parts[0].trim();
				String hint = parts[1].trim();

				radixTree.add(word); 
				words.add(word); // Add words to arrayList of words so that we can easily generate a random 
				wordTable.put(word, hint);
			} 
			else if(parts.length == 1){

				String word = parts[0].trim();

				radixTree.add(word); 
				words.add(word); // Add words to arrayList of words so that we can easily generate a random 
				wordTable.put(word, "Sorry not hint");
			}
		}
	}
	
	catch(FileNotFoundException e){
		 System.err.println("File not found: " + e.getMessage());
	}

	}


	/**
	 * 
	 * checkGameOver method allows us to determine when the game is over
	 * 
	 * If the current number of incorrect guess equals the number of allowed, 
	 * incorrect guesses return true, else return false
	 * 
	 **/
	public void checkforWin(){

		ArrayList<Character> temp = new ArrayList<Character>();

		for(int i = 0; i < emptyWord.size(); i++){
			if(emptyWord.get(i) != '-'){
				temp.add(emptyWord.get(i));
			}

		}

		if(temp.size() != targetWord.length()){
			gameWin = false;
		}
		else{

		for(int i = 0; i < temp.size(); i++){
			if(targetWord.charAt(i) == temp.get(i)){
				continue;
			}
			gameWin = false;
		}
		gameWin = true;

		}
	}


	/**
	 * generateNewWord helper method to 
	 **/

	public void filterWords(int len){ // Our game only allows words of length 3 - 10 we must ensure our words.txt has words of each length 
		if(2 < len && len < 11){

			ArrayList<String> newList = new ArrayList<String>();
			for(String word : this.words){

				if(word.length() == len){
					newList.add(word);
				}
			}

			this.words = newList;
		}
	
	}


	/**
	 * 
	 **/

	public void setEmptyWord(){
		for(int i = 0; i < targetWord.length(); i++){
			emptyWord.add((char) '-');
		}
	}


	/**
	 * generateNewWord method allows us to randomly generate a letter of length n, determined by player
	 **/

	public void generateNewWord(){

		// Initialize new Random class to generate random index
		Random rand = new Random();
		int randInd = rand.nextInt(words.size());

		// Get the word associated with the random index from the arrayList of words
		String randomWord = words.get(randInd);
		targetWord = randomWord;
	}



	/**
	 * offerHint method allows us to return cross-word style hint associated with given word in wordTable map
	 **/
	public String offerHint(){
		return wordTable.get(targetWord);
	}


	public ArrayList<Integer>  checkForLetter(char letter){

		ArrayList<Integer> indices = new ArrayList<Integer>();

		for(int i = 0; i<targetWord.length(); i++){

			if(letter != targetWord.charAt(i)){
				continue;	
			}
			indices.add(i); // Array of indices where the letter exists in target word				
		}
		return indices;
	}


	/**
	 * 
	 **/
	public void addTo(char letter, ArrayList<Integer> indices){

		for(int i : indices){
			emptyWord.add(i, letter);
		}	
	}

	/**
	*Offer suggestions to complete the word based on the given prefix.
	* @param prefix The prefix to use for offering suggestions.
 	* @return A list of suggested words.
 	**/
	public List<String> offerSuggestions(String prefix){
	 	return radixTree.findWordsWPrefix(prefix);
	}



	public void hangmanVisual(){

		if (numIncorrectAttempts == 1){
			System.out.println("	__________________");
			System.out.println("	|           _|_");
			System.out.println("	|          /   \\");
			System.out.println("	|         |     |");
			System.out.println("	|          \\__ /");
			System.out.println("	|");
			System.out.println("	|");
			System.out.println("	|");
			System.out.println("	|");
			System.out.println("	|");
			System.out.println("	|");
			System.out.println("	|");
			System.out.println("    ____|____");
			System.out.println();
			System.out.println();//print out array of letter space for correct word guess)
			System.out.println();//print out guessed letters)
			
		}
		else if (numIncorrectAttempts == 2){
			System.out.println("	__________________");
			System.out.println("	|           _|_");
			System.out.println("	|          /   \\");
			System.out.println("	|         |     |");
			System.out.println("	|          \\__ /");
			System.out.println("	|            |");
			System.out.println("	|            |");
			System.out.println("	|            |");
			System.out.println("	|            |");
			System.out.println("	|            |");
			System.out.println("	|");
			System.out.println("	|");
			System.out.println("    ____|____");
			System.out.println();
			System.out.println();//print out array of letter space for correct word guess)
			System.out.println();//print out guessed letters)
		}

		else if (numIncorrectAttempts == 3){
			System.out.println("	__________________");
			System.out.println("	|           _|_");
			System.out.println("	|          /   \\");
			System.out.println("	|         |     |");
			System.out.println("	|          \\__ /");
			System.out.println("	|            |");
			System.out.println("	|            |");
			System.out.println("	|            |");
			System.out.println("	|            |");
			System.out.println("	|            |");
			System.out.println("	|           /");
			System.out.println("	|          /");
			System.out.println("    ____|____");
			System.out.println();
			System.out.println();//print out array of letter space for correct word guess)
			System.out.println();//print out guessed letters)
		}

		else if (numIncorrectAttempts == 4){
			System.out.println("	__________________");
			System.out.println("	|           _|_");
			System.out.println("	|          /   \\");
			System.out.println("	|         |     |");
			System.out.println("	|          \\__ /");
			System.out.println("	|            |");
			System.out.println("	|            |");
			System.out.println("	|            |");
			System.out.println("	|            |");
			System.out.println("	|            |");
			System.out.println("	|           / \\");
			System.out.println("	|          /   \\");
			System.out.println("    ____|____");
			System.out.println();
			System.out.println();//print out array of letter space for correct word guess)
			System.out.println();//print out guessed letters)
		}		

		else if (numIncorrectAttempts == 5){
			System.out.println("	__________________");
			System.out.println("	|           _|_");
			System.out.println("	|          /   \\");
			System.out.println("	|         |     |");
			System.out.println("	|          \\__ /");
			System.out.println("	|            |");
			System.out.println("	|           /|");
			System.out.println("	|          / |");
			System.out.println("	|            |");
			System.out.println("	|            |");
			System.out.println("	|           / \\");
			System.out.println("	|          /   \\");
			System.out.println("    ____|____");
			System.out.println();
			System.out.println();//print out array of letter space for correct word guess)
			System.out.println();//print out guessed letters)
		}

		else if (numIncorrectAttempts == 6){
			System.out.println("	__________________");
			System.out.println("	|           _|_");
			System.out.println("	|          /   \\");
			System.out.println("	|         |     |");
			System.out.println("	|          \\__ /");
			System.out.println("	|            |");
			System.out.println("	|           /|\\");
			System.out.println("	|          / | \\");
			System.out.println("	|            |");
			System.out.println("	|            |");
			System.out.println("	|           / \\");
			System.out.println("	|          /   \\");
			System.out.println("    ____|____");
			System.out.println();
			System.out.println();//print out array of letter space for correct word guess)
			System.out.println();//print out guessed letters)
		}
		else if (numIncorrectAttempts == 7){
			System.out.println("	___________");
			System.out.println("	|			_|_");
			System.out.println("	|		   /   \\");
			System.out.println("	|		  |		|");
			System.out.println("	|		   \\__//");
			System.out.println("	|			 |");
			System.out.println("	|		    /|\\");
			System.out.println("	|		   / | \\");
			System.out.println("	|			 |");
			System.out.println("	|			 |");
			System.out.println("	|			/ \\");
			System.out.println("	|		   /  \\");
			System.out.println("    ____|____");
			System.out.println();
			System.out.println();//print out array of letter space for correct word guess)
			System.out.println();//print out guessed letters)
		}
		else if (numIncorrectAttempts == 8){
			System.out.println("	___________");
			System.out.println("	|			_|_");
			System.out.println("	|		   /   \\");
			System.out.println("	|		  |		|");
			System.out.println("	|		   \\__//");
			System.out.println("	|			 |");
			System.out.println("	|		    /|\\");
			System.out.println("	|		   / | \\");
			System.out.println("	|			 |");
			System.out.println("	|			 |");
			System.out.println("	|			/ \\");
			System.out.println("	|		   /  \\");
			System.out.println("    ____|____");
			System.out.println();
			System.out.println();//print out array of letter space for correct word guess)
			System.out.println();//print out guessed letters)
		}
		else if (numIncorrectAttempts == 9){
			System.out.println("	___________");
			System.out.println("	|			_|_");
			System.out.println("	|		   /x  \\");
			System.out.println("	|		  |		|");
			System.out.println("	|		   \\__//");
			System.out.println("	|			 |");
			System.out.println("	|		    /|\\");
			System.out.println("	|		   / | \\");
			System.out.println("	|			 |");
			System.out.println("	|			 |");
			System.out.println("	|			/ \\");
			System.out.println("	|		   /  \\");
			System.out.println("    ____|____");
			System.out.println();
			System.out.println();//print out array of letter space for correct word guess)
			System.out.println();//print out guessed letters)
			
		}
		else if (numIncorrectAttempts == 10){
			System.out.println("	___________");
			System.out.println("	|			_|_");
			System.out.println("	|		   /x  x\\");
			System.out.println("	|		  |		|");
			System.out.println("	|		   \\__//");
			System.out.println("	|			 |");
			System.out.println("	|		    /|\\");
			System.out.println("	|		   / | \\");
			System.out.println("	|			 |");
			System.out.println("	|			 |");
			System.out.println("	|			/ \\");
			System.out.println("	|		   /  \\");
			System.out.println("    ____|____");
			System.out.println();
			System.out.println();//print out array of letter space for correct word guess)
			System.out.println();//print out guessed letters)
		}
	}
}