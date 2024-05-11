package tree;

import java.util.Scanner;
import java.util.Random;
import java.io.*;
import java.util.ArrayList;


public class Game{
	

	public static void main(String[] args){

		System.out.println("Welcome to Hangman!");

		Scanner scan = new Scanner(System.in);

		System.out.println("Enter length of word");
		int length = scan.nextInt();

		System.out.println("Select difficulty: Easy (1), Medium (2), Hard (3)");
		int difficulty = scan.nextInt();

		

		Hangman hangmanGame = new Hangman(length, difficulty);
		
		hangmanGame.injestWords();
		// hangmanGame.filterWords(length);
		hangmanGame.generateNewWord();
		hangmanGame.radixTree.printTree();

		System.out.println(hangmanGame.targetWord);

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
		

		while(hangmanGame.gameOver == false){

		Scanner scan2 = new Scanner(System.in);

		System.out.println(hangmanGame.targetWord);
		System.out.println("Guess a letter: "); 

		String guessedLetter = scan2.nextLine(); // Add if statement to check length of string 
		char guessedLetter2 = guessedLetter.charAt(0);

		boolean isIn = hangmanGame.checkForLetter(guessedLetter2);

		if(!isIn){ 
			hangmanGame.numIncorrectAttempts++;
			hangmanGame.checkGameOver();
		}


		// System.out.println(hangmanGame.emptyWord.toString());


		}

		System.out.println("Sorry, you lose :( Better luck next time!");









	}
}