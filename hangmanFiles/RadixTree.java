package tree;
 
import java.util.*;


class RadixTreeNode{
	HashMap<Character, RadixTreeNode> children;
 	boolean isWord;
	String edge;

	//constructor with edge label and word indicator 
	RadixTreeNode(String edge, boolean isWord){
		this.edge = edge;
		this.children = new HashMap<>();
		this.isWord= isWord;
	}
	//constructor that defaluts word as false and has only edge label 
	RadixTreeNode(String edge){
		this(edge, false);
	}
}

public class RadixTree{
	RadixTreeNode root;

	//constructor, initalizes root with empty label
	public RadixTree(){
		this.root = new RadixTreeNode("");
	}

	/**
	 * add word to radix tree
	 **/
	public void add(String word){
		word = word.toLowerCase();
		RadixTreeNode currentNode = this.root;

		for(int i=0; i<word.length(); i++){
			char currentChar = word.charAt(i);

			//checks if the current character currentChar exists as a key in the children of the currentNode
			if(currentNode.children.containsKey(currentChar)){
				String edge = currentNode.children.get(currentChar).edge;
				String commonPre = getCommonPre(edge, word.substring(i));

				if(edge.equals(word.substring(i))){ //entire edge eqauls substring of remaining word
					currentNode.children.get(currentChar).isWord = true; //indicates end of word 
					return;
				}
				if(commonPre.length() < edge.length() && commonPre.length() == word.substring(i).length()){
					RadixTreeNode newNode = new RadixTreeNode(word.substring(i), true); //with remaining part of word
					//split the edge label at end of common prefix and insert a new node
					newNode.children.get(edge.charAt(commonPre.length())).children.put(currentChar, currentNode.children.get(currentChar));
					newNode.children.get(edge.charAt(commonPre.length())).edge = edge.substring(commonPre.length());
					currentNode.children.put(currentChar, newNode);
					return;
				}

				if(commonPre.length() < edge.length() && commonPre.length() < word.substring(i).length()){ //shorter 
					RadixTreeNode betweenNode = new RadixTreeNode(commonPre);
					//create a new in beteen node to split the edge label
					betweenNode.children.put(edge.charAt(commonPre.length()), currentNode.children.get(currentChar)); //edge label of existing child
					betweenNode.children.get(edge.charAt(commonPre.length())).edge = edge.substring(commonPre.length()); //becomes edge label of in between node
					currentNode.children.put(currentChar, betweenNode); //inserted as child of currentNode
					//add remaining part of the word as a child of the in between node
					betweenNode.children.put(word.charAt(i + commonPre.length()), new RadixTreeNode(word.substring(i + commonPre.length()), true)); //add remaning part, ends word
					return;
				}
				i += edge.length() -1; //esnures loop contiunes at correct position 
				currentNode = currentNode.children.get(currentChar); //allows loopto keep traversing to next node
			}
			else{
				//if current charcter is not found in the children, we create a new child node
				RadixTreeNode newNode = new RadixTreeNode(word.substring(i), true); //complete word
				currentNode.children.put(currentChar, newNode); //added to childrens map, key is current char and value is new node
				return;
			}
		}
	}
	/**
	 * helper method that finds common prefix between two strings
	 * */
	private String getCommonPre(String a, String b){
		StringBuilder commonPre = new StringBuilder();
		for(int i=0; i<Math.min(a.length(), b.length()); i++){ //iterate through each to check characters
			if(a.charAt(i) != b.charAt(i)){ //ends when chars are different
				return commonPre.toString();
			}
			commonPre.append(a.charAt(i)); //if same, append
		}
		return commonPre.toString(); //returns string rep
	}

	/**
	 * Traverse thorugh tree and use DFS, we want to find edges that match a common prefix
	 **/
	public List<String> getWords(String prefix){
		prefix = prefix.toLowerCase();
		String word = "";
		RadixTreeNode currentNode = this.root;

		for (int i=0; i < prefix.length(); i++){
			char character = prefix.charAt(i);

			if(currentNode.children.containsKey(character)){
				String edge = currentNode.children.get(character).edge; //retrieves the edge label of the child node corresponding to the current character being traversed
				String commonPre = getCommonPre(edge, prefix.substring(i));//finds common prefix between edge label and substring

				//if they are unequal, it implies that the edge label and the remaining prefix do not match completely
				if(commonPre.length() != prefix.substring(i).length()){
					return new ArrayList<>();
				}

				word = word.concat(currentNode.children.get(character).edge); //appends the edge label to the word formed so far during the traversal
				i += currentNode.children.get(character).edge.length() - 1; //move the traversal index past the characters that have already been traversed in the edge label
				currentNode = currentNode.children.get(character); //updates to be the child node corresponding to the current character.
			}
			else{
				return new ArrayList<>();
			}
			
		}
		List<String> words = new ArrayList<>();
		depthFS(currentNode, prefix, "", words);
		return words;
	}
	/**
	 * Depth first search method that helps us traverse through the tree to retrieve words
	 **/
	//i dont think it's running prefixes properly which is why it gets lost when one doesnt exist
	private void depthFS(RadixTreeNode startNode, String prefix, String word, List<String> words){
		System.out.println("Visiting node: " + startNode.edge);
		System.out.println("Current word: " + word);
		//System.out.println("Prefix: " + prefix);
		if(startNode.isWord && !word.equals("")){
			System.out.println("Found word: " + word);
			words.add(word);
		}
		if(startNode.children.isEmpty()){
			System.out.println("No children, returning");
			return;
		}
		for(char character : startNode.children.keySet()){
			RadixTreeNode childNode = startNode.children.get(character);
			System.out.println("Traversing edge: " + childNode.edge);
			depthFS(childNode, prefix, word + childNode.edge, words);
		}
	}

	/**
	 * Method to provide visual for the tree structure.
	 **/
	public void printTree(){
		System.out.println("Tree structure:");
		printRecursion(root, 0);
	}
	private void printRecursion(RadixTreeNode node, int level){
		StringBuilder prefix = new StringBuilder();
		for(int i=0; i<level; i++){
			prefix.append(" ");
		}
		System.out.println(prefix.toString() + (node.edge.isEmpty() ? "ROOT" : node.edge) + (node.isWord ? " (end)" : ""));


		//recursion call for each child node
		for(RadixTreeNode child : node.children.values()){
			printRecursion(child, level+1);
		}
	}

	/**
	 * This method finds the node corresponding to the prefix and then initiates 
	 * a DFS search from that node to collect words with the given prefix.
	 **/
	public List<String> findWordsWPrefix(String prefix){
		prefix = prefix.toLowerCase();
		List<String> words = new ArrayList<>();
		RadixTreeNode currentNode = findNodeByPrefix(root, prefix);

		if(currentNode != null){
			//if found, perform search 
			depthFS(currentNode, "" , prefix, words);
			//dfs(currentNode, prefix, "", words);
		}
		else{
			//if not found return empty list
			return words;
		}
		return words;
	}
	/**
	 * Helper method to find nodes that corresponf to a provided prefixm this helps us 
	 * in our specifc search method
	 **/
	private RadixTreeNode findNodeByPrefix(RadixTreeNode node, String prefix){
		//traverse tree to find the node corrsponding to the prefix 
		for(char c : prefix.toCharArray()){
			if(node.children.containsKey(c)){
				node = node.children.get(c);
			}
			//else{
			 	//return null; //prefix not found 
			//}
		}
		return node;
	}
	
	public static void main(String[] args){
		RadixTree tree = new RadixTree();
		tree.add("apple");
		tree.add("appreciate");
        tree.add("apply");
        tree.add("applicant");
        tree.add("application");
        tree.add("are");
        tree.add("append");
        // test comment
        tree.add("predicate");
        tree.add("predispose");
        tree.add("pretend");
        tree.add("you");
        tree.add("doing");
        System.out.println(tree.getWords("h"));

        tree.printTree();
        List<String> wordsWithPrefix = tree.findWordsWPrefix("app");
		System.out.println("Words with prefix 'app': " + wordsWithPrefix);
		List<String> wordsWithPrefix2 = tree.findWordsWPrefix("pre");
		System.out.println("Words with prefix 'pre': " + wordsWithPrefix2); 
		//still need to debug for prefix that doesnt exist!!
	}


}

