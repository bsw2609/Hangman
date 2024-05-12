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
	boolean gameOver; // Boolean to detrmine when game is over

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
			this.maxIncorrectAtempts = MEDIUM_MAX_NUM_INCORRECT;
		}
		this.numIncorrectAttempts = 0;
		this.wordTable = new HashMap<String, String>();
		this.words = new ArrayList<>();
		this.gameOver = false;
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

			if(!skipped){
				skipped = true;
				continue;
			}
		
			String[] parts = line.split("/");
			String word = parts[0];
			String hint = parts[1];

			words.add(word);
			// radixTree.insert(word); // Assuming insert method for RadixTree is implemented 
			wordTable.put(word, hint);
		}
		scanner.close();
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
	public void checkGameOver(){

		if(this.difficulty == 3){

			if(this.numIncorrectAttempts == HARD_MAX_NUM_INCORRECT){
				gameOver = true;
			}
		}
		else if(this.difficulty == 2){

			if(this.numIncorrectAttempts == MEDIUM_MAX_NUM_INCORRECT){
				gameOver = true;
			}
		}
		else{
			if(this.numIncorrectAttempts == EASY_MAX_NUM_INCORRECT){
				gameOver = true;
			}
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


	public boolean checkForLetter(char letter){

		for(int i = 0; i<targetWord.length(); i++){
			if(letter == targetWord.charAt(i)){
				this.emptyWord.add(i, letter);
				return true;
			}
		}
		return false;

	}

	/**
	*Offer suggestions to complete the word based on the given prefix.
	* @param prefix The prefix to use for offering suggestions.
 	* @return A list of suggested words.
 	**/
	public List<String> offerSuggestions(String prefix){
	 	return radixTree.findWordsWPrefix(prefix);
	}






}