package tree;

import java.util.Scanner;
import java.util.Random;
import java.io.*;
import java.util.ArrayList;


public class Game{
	

	public static void main(String[] args){

		// Greeting to player and game initialization //
		System.out.println("Welcome to Hangman!");

		// Prompting user for length of word they would like to play with and with which difficulty level, then initializing new hangman object //
		Scanner scan = new Scanner(System.in);

		System.out.println("Enter length of word: 3-10");
		int length = scan.nextInt();

		System.out.println("Select difficulty: Easy (1), Medium (2), Hard (3)");
		int difficulty = scan.nextInt();
		Hangman hangmanGame = new Hangman(length, difficulty);

		// More initialization calling methods implemented in hangman class //
		hangmanGame.injestWords();
		hangmanGame.filterWords(length);
		hangmanGame.generateNewWord();
		hangmanGame.setEmptyWord();

		// Last of the game initialization
		int maxIncorrectGuesses = hangmanGame.maxIncorrectAtempts;

		System.out.println("You have "+ maxIncorrectGuesses+" guesses to guess the word. Goodluck!");

		System.out.println("	_____________");
		System.out.println("	|	  |");
		System.out.println("	|");
		System.out.println("	|");
		System.out.println("	|");
		System.out.println("	|");
		System.out.println("	|");
		System.out.println("	|");
		System.out.println("	|");
		System.out.println("	|");
		System.out.println("	|");
		System.out.println("	|");
		System.out.println("    ____|____");
		System.out.println();
		


		// Game logic //

		// Continue to prompt user for next guess until, the player either wins or loses //
		while(hangmanGame.numIncorrectAttempts < maxIncorrectGuesses && hangmanGame.gameWin == false){

		// Prompt for guess or hint //
		Scanner scan2 = new Scanner(System.in);

		String strPrefix1 = hangmanGame.toStringforPrefix();

		if(strPrefix1.length() == 3){
			System.out.println("Guess a letter or press! Or request a hint '@'. Or request a suggestion for the next letter '?':"); 
		}
		else{
			System.out.println("Guess a letter or press! Or request a hint '@'."); 
		}


		// Processing guess //
		String guessedLetter = scan2.nextLine(); // Add if statement to check length of string 
		char guessedLetter2 = guessedLetter.charAt(0);

		// If they ask for a hint simply return the cross word style hint associated with word and skip rest of code below //
		if(guessedLetter2 == '@'){
			hangmanGame.offerHint();
			continue;
		}


		if(!hangmanGame.alreadyGuessed.contains(guessedLetter2)){
			hangmanGame.alreadyGuessed.add(guessedLetter2);
		}
		else{
			System.out.println("Already guessed");
			hangmanGame.numIncorrectAttempts--; // Decrementing here so that the player does not lose a turn by guessing the same letter as before //
		}

		// Check to see if the guessed letter is contained in the word, storing indices at which the guessed letter is found within the targetWord //
		ArrayList<Integer>  indicesOfLetterinWord = hangmanGame.checkForLetter(guessedLetter2);

		// If the array contains at least one index, we know the guessed letter is found in the word //
		if(indicesOfLetterinWord.size() > 0){
			System.out.println();
			System.out.println("Great guess! Current word:");

			int firstIndex = indicesOfLetterinWord.get(0);

			hangmanGame.currentPrefix.set(firstIndex, guessedLetter2);

			hangmanGame.addTo(guessedLetter2, indicesOfLetterinWord);
			hangmanGame.checkforWin();

		}

		else if(guessedLetter2 == '?'){
			
			String strPrefix = hangmanGame.toStringforPrefix();
			if(strPrefix.length() >= 3){
				System.out.println("A list of possible words: "+hangmanGame.offerSuggestions(strPrefix));
				continue;

			}
			
		}


		// Wrong guess, increment numIncorrectAttempts and let player know they are wrong
		else{
			hangmanGame.numIncorrectAttempts++;
			System.out.println("Sorry the letter " + guessedLetter2+ " is not in the word. You have " + (hangmanGame.maxIncorrectAtempts - hangmanGame.numIncorrectAttempts) + " attempts left.");
		}
		// Print the visual for current number of incorrect guesses //
		hangmanGame.hangmanVisual();

		// Print out the portion of the completed word for the player
		System.out.println(hangmanGame.emptyWord.toString()+"               Guessed letters: "+hangmanGame.alreadyGuessed);
		System.out.println();

		}
		// If the the game is over and the user won, congratulate them and print the targetWord //
		if(hangmanGame.gameWin == true){
			System.out.println("Congratulations, you win!");
			System.out.println("The secret word is: " + hangmanGame.targetWord+ "!");

		}
		// If the game is over and they did not win, the player mustg have lost //
		else{
			System.out.println("Sorry, you lose :( Better luck next time!");
			System.out.println("The secret word is: " + hangmanGame.targetWord+ "!");
		}
	}
}