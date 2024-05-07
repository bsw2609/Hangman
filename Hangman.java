// Hangman file
public class Hangman{

	// Instance variables
	int numLetters;
	int difficulty;
	int numIncorrectAttempts;
	boolean gameOver;
	Map<Word, Hint> wordTable;
	ArrayList<String> words;

	// Number of allowed incorrect guesses, given different difficulties
	public final int EASY_MAX_NUM_INCORRECT = 10;
	public final int MEDIUM_MAX_NUM_INCORRECT = 8;
	public final int HARD_MAX_NUM_INCORRECT = 6;

	
	// Constructor
	public Hangman(int numLetters, int difficulty){

		this.numLetters = numLetters;
		this.difficulty = difficulty;
		this.numIncorrectAttempts = 0;
		this.wordTable = new HashMap<>();
		this.words = new ArrayList<>();
		this.gameOver = false;
	}


	public void injestWords(){	//// Will have to modify to place words into rdix tree
	
	// Read in data
	String filePath = ""; 
	filePath = "";
	
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
		
			// Use scanner to injest the word entries in .txt file 
			String[] parts = line.split(",");
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


	public boolean checkGameOver(){

		if(this.difficulty == 3){
			return this.numIncorrectAttempts == 6;
		}
		if(this.difficulty == 2){
			return this.numIncorrectAttempts == 8;
		}
		if(this.difficulty == 1){
			return this.numIncorrectAttempts == 10;
		}

	}

	

	public void generateNewWord(){

		Random rand = new Random();
		int randInd = rand.nextInt(words.size());
		String randomWord = words.get(randInd);
		return ;
	}


	public void offerHint(){
		return wordTable.get(randomWord);
	}


	public void makeSuggestions(){

		}
	




}