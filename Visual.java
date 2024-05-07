package tree;


public class Visual{

//having something so that when you initalize the game you can see the pole, eerything but the man
	public static void main(String[] args){
	System.out.println("Want to play? (yes/no)");
	//String response = scanner.nextLine();
	//if(response.equalsIgnoreCase("yes")){
		//System.out.println("What difficulty level? (easy/medium/hard)")
		//make sure we run difficulty logic here
		// if(reponse.equalsIgnoreCase("easy")){
		// 	//prob do very basic words
		// }
		// if(response.equalsIgnoreCase("medium")){
		// 	//prob a mediu level word
		// }
		// if(response.equalsIgnoreCase("hard")){
		// 	//prob do heard level word, we can divide these words into small subsets from the lexicon
		// }
		System.out.println("You have five guesses to guess the words, your guessed letterrs will be dispalyed.  Good luck!");
		System.out.println("Guess a letter:");
		//game logic method calls
				System.out.println("	___________");
				System.out.println("	|			|");
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
				System.out.println("____|____");
				System.out.println();
				//System.out.println("You have" + numGuess + "remaining!")
		//}
	}

	public static void hangmanVisual(){
		//if (numLetters == 1){
			System.out.println("Incorret guess!");
			System.out.println("	___________");
			System.out.println("	|			_|_");
			System.out.println("	|		   /   \\");
			System.out.println("	|		  |		|");
			System.out.println("	|		   \\__//");
			System.out.println("	|");
			System.out.println("	|");
			System.out.println("	|");
			System.out.println("	|");
			System.out.println("	|");
			System.out.println("	|");
			System.out.println("	|");
			System.out.println("____|____");
			System.out.println();
			System.out.println();//print out array of letter space for correct word guess)
			System.out.println();//print out guessed letters)
			//System.out.println("You have" + numGuess + "remaining!"); 
			//also run game logic for end of game after each guess
		//}
		//if (numLetters == 1){
			System.out.println("Incorret guess!");
			System.out.println("	___________");
			System.out.println("	|			_|_");
			System.out.println("	|		   /   \\");
			System.out.println("	|		  |		|");
			System.out.println("	|		   \\__//");
			System.out.println("	|			 |");
			System.out.println("	|			 |");
			System.out.println("	|			 |");
			System.out.println("	|			 |");
			System.out.println("	|			 |");
			System.out.println("	|");
			System.out.println("	|");
			System.out.println("____|____");
			System.out.println();
			System.out.println();//print out array of letter space for correct word guess)
			System.out.println();//print out guessed letters)
			//System.out.println("You have" + numGuess + "remaining!");

		//if (numLetters == 3){
			System.out.println("Incorret guess!");
			System.out.println("	___________");
			System.out.println("	|			_|_");
			System.out.println("	|		   /   \\");
			System.out.println("	|		  |		|");
			System.out.println("	|		   \\__//");
			System.out.println("	|			 |");
			System.out.println("	|			 |");
			System.out.println("	|			 |");
			System.out.println("	|			 |");
			System.out.println("	|			 |");
			System.out.println("	|			/");
			System.out.println("	|		   /");
			System.out.println("____|____");
			System.out.println();
			System.out.println();//print out array of letter space for correct word guess)
			System.out.println();//print out guessed letters)
			//System.out.println("You have" + numGuess + "remaining!");

		//if (numLetters == 3){
			System.out.println("Incorret guess!");
			System.out.println("	___________");
			System.out.println("	|			_|_");
			System.out.println("	|		   /   \\");
			System.out.println("	|		  |		|");
			System.out.println("	|		   \\__//");
			System.out.println("	|			 |");
			System.out.println("	|			 |");
			System.out.println("	|			 |");
			System.out.println("	|			 |");
			System.out.println("	|			 |");
			System.out.println("	|			/ \\");
			System.out.println("	|		   /  \\");
			System.out.println("____|____");
			System.out.println();
			System.out.println();//print out array of letter space for correct word guess)
			System.out.println();//print out guessed letters)
			//System.out.println("You have" + numGuess + "remaining!");
			//this should be where we allow hints/suggetions to the rest of the word (we can decide
			//based on difficuult level decision)

		//if (numLetters == 4){
			System.out.println("Incorret guess!");
			System.out.println("	___________");
			System.out.println("	|			_|_");
			System.out.println("	|		   /   \\");
			System.out.println("	|		  |		|");
			System.out.println("	|		   \\__//");
			System.out.println("	|			 |");
			System.out.println("	|		    /|");
			System.out.println("	|		   / |");
			System.out.println("	|			 |");
			System.out.println("	|			 |");
			System.out.println("	|			/ \\");
			System.out.println("	|		   /  \\");
			System.out.println("____|____");
			System.out.println();
			System.out.println();//print out array of letter space for correct word guess)
			System.out.println();//print out guessed letters)
			//System.out.println("You have" + numGuess + "remaining!");


		//if (numLetters == 5){
			System.out.println("Incorret guess!");
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
			System.out.println("____|____");
			System.out.println();
			System.out.println();//print out array of letter space for correct word guess)
			System.out.println();//print out guessed letters)
			//System.out.println("You have" + numGuess + "remaining!");

		//condition for wining, print last visual and state that they won the game

	}
}