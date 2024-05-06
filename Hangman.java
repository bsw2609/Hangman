// Hangman file
public class Hangman{

	int numLetters;
	int difficulty;
	int numIncorrectAttempts;
	boolean gameOver;
	Map<Word, Hint> wordTable;

	
	// Constructor
	public Hangman(int numLetters, int difficulty){

		this.numLetters = numLetters;
		this.difficulty = difficulty;
		this.numIncorrectAttempts = 0;
		this.wordTable = new HashMap<>();
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

	}

	public void makeSuggestions(){

	}

	public void offerHint(){
		return wordTable.get(this.word);
	}

	




}