package tree;

public class Hangman{

	// Instance variables
	int numLetters; // Length of word 
	int difficulty; // Level of difficulty

	int numIncorrectAttempts; // Number of current incorrect guesses
	int maxIncorrectAtempts; // Number of allowed incorrect guesses
	boolean gameOver; // Boolean to detrmine when game is over

	String targetWord; // Target word that will be randomly generated
	Map<String, String> wordTable; // Word table to hold words as keys and hints as values
	ArrayList<String> words; // Array list to hold words also in Radix tree

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
		this.wordTable = new HashMap<>();
		this.words = new ArrayList<>();
		this.gameOver = false;
	}


	/**
	 * Injest method used to add words to RadixTree
	 **/ 
	public void injestWords(){	//// Will have to modify to place words into rdix tree
	
	// Read in data
	String filePath = ""; 
	filePath = words.txt;
	
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
			radixTree.insert(word); // Assuming insert method for RadixTree is implemented 
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
	public boolean checkGameOver(){

		if(this.difficulty == 3){
			return this.numIncorrectAttempts == HARD_MAX_NUM_INCORRECT;
		}
		else if(this.difficulty == 2){
			return this.numIncorrectAttempts == MEDIUM_MAX_NUM_INCORRECT;
		}
		else{
			return this.numIncorrectAttempts == EASY_MAX_NUM_INCORRECT;
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
		return;
	}

	/**
	 * offerHint method allows us to return cross-word style hint associated with given word in wordTable map
	 **/
	public void offerHint(){
		return wordTable.get(randomWord);
	}




}