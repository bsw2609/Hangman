package tree;

import java.util.Scanner;
import java.util.Random;
import java.io.*;
import java.util.ArrayList;


public class Game{
	

	public static void main(String[] args){

		System.out.println("Welcome to Hangman!");

		Scanner scan = new Scanner(System.in);

		System.out.println("Enter length of word: 3-10");
		int length = scan.nextInt();

		System.out.println("Select difficulty: Easy (1), Medium (2), Hard (3)");
		int difficulty = scan.nextInt();

		Hangman hangmanGame = new Hangman(length, difficulty);



		hangmanGame.injestWords();
		hangmanGame.filterWords(length);
		hangmanGame.generateNewWord();
		hangmanGame.setEmptyWord();


		// hangmanGame.radixTree.printTree();

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
		





		while(hangmanGame.numIncorrectAttempts < maxIncorrectGuesses && hangmanGame.gameWin == false){

		Scanner scan2 = new Scanner(System.in);

		System.out.println("Guess a letter or press '@' for a hint: "); 

		// System.out.println(hangmanGame.targetWord);



		String guessedLetter = scan2.nextLine(); // Add if statement to check length of string 
		char guessedLetter2 = guessedLetter.charAt(0);

		if(guessedLetter2 == '@'){
			hangmanGame.offerHint();
			continue;
		}

		hangmanGame.checkForLetter(guessedLetter2);


		ArrayList<Integer>  indicesOfLetterinWord = hangmanGame.checkForLetter(guessedLetter2);

		if(indicesOfLetterinWord.size() > 0){
			System.out.println();
			System.out.println("Great guess!");
			hangmanGame.addTo(guessedLetter2, indicesOfLetterinWord);
			hangmanGame.checkforWin();

		}
		else{
			hangmanGame.numIncorrectAttempts++;
			System.out.println("Sorry the letter " + guessedLetter2+ " is not in the word. You have " + (hangmanGame.maxIncorrectAtempts - hangmanGame.numIncorrectAttempts) + " attempts left.");
		}

		hangmanGame.hangmanVisual();

		System.out.println(hangmanGame.emptyWord.toString());
		System.out.println();




		}

		if(hangmanGame.gameWin == true){
			System.out.println("Congratulations, you win!");
			System.out.println("The secret word is: " + hangmanGame.targetWord+ "!");

		}
		else{
			System.out.println("Sorry, you lose :( Better luck next time!");
			System.out.println("The secret word is: " + hangmanGame.targetWord+ "!");

		}

	}
}