// Hangman file
public class Hangman{


	int numLetters;
	int difficulty;
	boolean gameOver;
	
	// Constructor
	public Hangman(int numLetters, int difficulty){

		this.numLetters = numLetters;
		this.difficulty = difficulty;
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
		
			// Use scanner to injest the student entries in the .txt files 
			String[] parts = line.split(",");
			System.out.println(parts[0]);
			System.out.println(parts[1]);
			System.out.println(parts[2]);


			String domainName = parts[0];
			String iP = parts[1];
			String timeVisited = parts[2];

			Event event = new Event(iP, timeVisited);
		

			String key = domainName;

			bst.put(key, event, new StringComparator());
		}
		scanner.close();
	}
	
	catch(FileNotFoundException e){
		 System.err.println("File not found: " + e.getMessage());
	}

	}






	

	public void generateNewWord(){

	}

	public void makeSuggestions(){

	}

	public boolean checkGameOver(){
		return false;
	}

	public void addBodyPart(){

	}






}