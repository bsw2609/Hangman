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

	//add word to radix tree
	public void add(String word){
		word = word.toLowerCase();
		RadixTreeNode currentNode = this.root;

		for(int i=0; i<word.length(); i++){
			char currentChar = word.charAt(i);

			if(currentNode.children.containsKey(currentChar)){
				String edge = currentNode.children.get(currentChar).edge;
				String commonPre = getCommonPre(edge, word.substring(i));

				if(edge.equals(word.substring(i))){
					currentNode.children.get(currentChar).isWord = true;
					return;
				}
				if(commonPre.length() < edge.length() && commonPre.length() == word.substring(i).length()){
					RadixTreeNode newNode = new RadixTreeNode(word.substring(i), true);
					//split the edge label and insert a new node
					newNode.children.get(edge.charAt(commonPre.length())).children.put(currentChar, currentNode.children.get(currentChar));
					newNode.children.get(edge.charAt(commonPre.length())).edge = edge.substring(commonPre.length());
					currentNode.children.put(currentChar, newNode);
					return;
				}

				if(commonPre.length() < edge.length() && commonPre.length() < word.substring(i).length()){
					RadixTreeNode betweenNode = new RadixTreeNode(commonPre);
					//create a new in beteen node to split the edge label
					betweenNode.children.put(edge.charAt(commonPre.length()), currentNode.children.get(currentChar));
					betweenNode.children.get(edge.charAt(commonPre.length())).edge = edge.substring(commonPre.length());
					currentNode.children.put(currentChar, betweenNode);
					//add remaining part of the word as a child of the in between node
					betweenNode.children.put(word.charAt(i + commonPre.length()), new RadixTreeNode(word.substring(i + commonPre.length()), true));
					return;
				}
				i += edge.length() -1;
				currentNode = currentNode.children.get(currentChar);
			}
			else{
				//if current charcter is not found in the children, we create a new child node
				RadixTreeNode newNode = new RadixTreeNode(word.substring(i), true);
				currentNode.children.put(currentChar, newNode);
				return;
			}
		}
	}

	private String getCommonPre(String a, String b){
		StringBuilder commonPre = new StringBuilder();
		for(int i=0; i<Math.min(a.length(), b.length()); i++){
			if(a.charAt(i) != b.charAt(i)){
				return commonPre.toString();
			}
			commonPre.append(a.charAt(i));
		}
		return commonPre.toString();
	}

	//traverse thorugh tree and use DFS, we want to find edges that match a common prefix
	public List<String> getWords(String prefix){
		prefix = prefix.toLowerCase();
		String word = "";
		RadixTreeNode currentNode = this.root;

		for (int i=0; i < prefix.length(); i++){
			char character = prefix.charAt(i);

			if(currentNode.children.containsKey(character)){
				String edge = currentNode.children.get(character).edge;
				String commonPre = getCommonPre(edge, prefix.substring(i));

				if(commonPre.length() != edge.length() && commonPre.length() != prefix.substring(i).length()){
					return new ArrayList<>();
				}

				word = word.concat(currentNode.children.get(character).edge);
				i += currentNode.children.get(character).edge.length() - 1;
				currentNode = currentNode.children.get(character);
			}
			else{
				return new ArrayList<>();
			}
			
		}
		List<String> words = new ArrayList<>();
		depthFS(currentNode, word, words);
		return words;
	}

	private void depthFS(RadixTreeNode startNode, String word, List<String> words){
		if(startNode.isWord){
			words.add(word);
		}
		if(startNode.children.isEmpty()){
			return;
		}
		for(char character : startNode.children.keySet()){
			depthFS(startNode.children.get(character), word + startNode.children.get(character).edge, words);
		}
	}

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
	public List<String> findWordsWPrefix(String prefix){
		prefix = prefix.toLowerCase();
		List<String> words = new ArrayList<>();
		RadixTreeNode currentNode = findNodeByPrefix(root, prefix);

		if(currentNode != null){
			//if found, perform search 
			depthFS(currentNode, prefix, words);
		}
		return words;
	}
	private RadixTreeNode findNodeByPrefix(RadixTreeNode node, String prefix){
		//traverse tree to find the node corrsponding to the prefix 
		for(char c : prefix.toCharArray()){
			if(node.children.containsKey(c)){
				node = node.children.get(c);
			}
			// else{
			// 	return null; //prefix not found 
			// }
		}
		return node;
	}
	//version for finding words with a certain prefix
	private void dfs(RadixTreeNode node, String prefix, String word, List<String> words){
		//construct word by appending the currend node's edge label
		String newWord = word + node.edge;
		//if current node is a word and it's prefix matches given
		if (node.isWord && newWord.startsWith(prefix)) {
			words.add(word); //add the word if the current node is a word
		}
		//recursivley traverse all child nodes 
		for(RadixTreeNode child : node.children.values()){
			dfs(child, prefix, newWord, words);
		}
	}









	public static void main(String[] args){
		RadixTree tree = new RadixTree();
		tree.add("apple");
        tree.add("apply");
        tree.add("applicant");
        tree.add("application");
        tree.add("are");
        tree.add("append");
        tree.add("you");
        tree.add("doing");
        System.out.println(tree.getWords("h"));

        tree.printTree();
        List<String> wordsWithPrefix = tree.findWordsWPrefix("app");
		System.out.println("Words with prefix 'app': " + wordsWithPrefix);
		List<String> wordsWithPrefix2 = tree.findWordsWPrefix("pre");
		System.out.println("Words with prefix 'pre': " + wordsWithPrefix2); //should be empty, it appends word
	}


}

// 	public RadixTreeNode(){
// 		this("", false);
// 	}
// 	public RadixTreeNode(String prefix, boolean isLeaf){
// 		this.prefix = prefix;
// 		this.isLeaf = isLeaf;
// 		this.nodes = new HashMap<>();
// 	}
// 	public RadixTreeNode match(String word){
// 		int sharedPreLen = 0;
// 		while(sharedPreLen < prefix.length() && sharedPreLen<word.length() && prefix.charAt(sharedPreLen) == word.charAt(sharedPreLen)){
// 			sharedPreLen++;
// 		}
// 		return new RadixTreeNode(prefix.substring(0, sharedPreLen), sharedPreLen == prefix.length());
// 	}

	
// 	public void insert(String word){
// 		//if current prefix is in word
// 		if(word.startsWith(prefix)){
// 			//if word is prefix
// 			if(word.equals(prefix)){
// 				isLeaf = true; //mark current node as leaf.
// 			}else{
// 			//get remainder
// 				String remainder = word.substring(prefix.length());
// 				//check if theirs a child node 
// 				if(nodes.containsKey(remainder.charAt(0))){
// 					//recursivley add remainder 
// 					nodes.get(remainder.charAt(0)).insert(remainder);
// 				}else{
// 					//no child, create new node w remainder as prefix
// 					RadixTreeNode newNode = new RadixTreeNode(remainder, true);
// 					nodes.put(remainder.charAt(0), newNode);
// 				}
// 			}
// 		}else{
// 			//if current prefix is not a prefix of word 
// 			//no common prefix, split
// 			int sharedPreLen = getSharedPreLen(word);
// 			if(sharedPreLen >0){
// 				//split current node into two nodes 
// 				String sharedPre = word.substring(0, sharedPreLen);
// 				String thisRem = prefix.substring(sharedPreLen);
// 				String wordRem = word.substring(sharedPreLen);
// 				prefix = sharedPre;
// 				isLeaf = false;
// 				//create new node for remaining part of prefix
// 				RadixTreeNode remainderNode = new RadixTreeNode(thisRem, isLeaf);
// 				remainderNode.nodes = nodes; //move child nodes to new node
// 				nodes = new HashMap<>(); //clear child nodes of the current node
// 				nodes.put(thisRem.charAt(0), remainderNode);
// 				//recursivley insert remaining part of word 
// 				RadixTreeNode wordRemNode = new RadixTreeNode(wordRem, true);
// 				nodes.put(wordRem.charAt(0), wordRemNode);
// 				RadixTreeNode newNode = new RadixTreeNode(word, true);
// 				nodes.put(word.charAt(0), newNode);
// 			}else{
// 				//no common prefix, insert as new node 
// 				nodes.put(word.charAt(0), new RadixTreeNode(word, true));
// 			}

// 		}
// 	}
// 	private int getSharedPreLen(String word){
// 		int sharedLen = 0;
// 		int minLen = Math.min(prefix.length(), word.length());
// 		for(int i = 0; i<minLen; i++){
// 			if(prefix.charAt(i) == word.charAt(i)){
// 				sharedLen++;
// 			}
// 			else{
// 				break;
// 			}
// 		}
// 		return sharedLen;
// 	}

// 	public boolean search(String word){
// 		if(!word.startsWith(prefix)){
// 			return false;
// 		}
// 		if(prefix.equals(word)){
// 			return isLeaf;
// 		}
// 		RadixTreeNode matchNode = nodes.get(word.charAt(0));
// 		if(matchNode == null){
// 			return false;
// 		}
// 		return matchNode.search(word.substring(matchNode.prefix.length()));
// 	}

// 	public boolean delete(String word){
// 		if(!word.startsWith(prefix)){
// 			return false;
// 		}
// 		if(prefix.equals(word)){
// 			if(!isLeaf){
// 				return false;
// 			}
// 			isLeaf = false;
// 			return nodes.isEmpty();
// 		}
// 		RadixTreeNode matchNode = nodes.get(word.charAt(0));
// 		if(matchNode == null){
// 			return false;
// 		}
// 		if(!matchNode.delete(word.substring(matchNode.prefix.length()))){
// 			return false;
// 		}
// 		nodes.remove(word.charAt(0));
// 		return nodes.isEmpty();
// 	}

// 	public List<String> suggestWords(String prefix){
// 		List<String> suggestions = new ArrayList<>();
// 		RadixTreeNode prefixNode = findPreNode(prefix);
// 		if(prefixNode != null){
// 			collect(prefixNode, prefix, suggestions);
// 		}
// 		return suggestions;
// 	}

// 	private RadixTreeNode findPreNode(String prefix){
// 		RadixTreeNode current = this;
// 		for(int i=0; i<prefix.length(); i++){
// 			char ch = prefix.charAt(i);
// 			Map<Character, RadixTreeNode> children = current.nodes;
// 			if(!children.containsKey(ch)){
// 				return null; //prefix not found
// 			}
// 			current = children.get(ch);
// 		}
// 		return current;

// 	}

// 	private void collect(RadixTreeNode node, String prefix, List<String> suggestions){
// 		if(node.isLeaf){
// 			suggestions.add(prefix); //add prefix if represents complete word
// 		}
// 		for(Map.Entry<Character, RadixTreeNode> entry : node.nodes.entrySet()){
// 			collect(entry.getValue(), prefix + entry.getKey(), suggestions);
// 		}
// 	}

// 	public void printTree(int height){
// 		if(!prefix.isEmpty()){
// 			StringBuilder sb = new StringBuilder();
// 			for(int i=0; i<height; i++){
// 				sb.append("-");
// 			}
// 			sb.append(" ").append(prefix);
// 			if(isLeaf){
// 				sb.append(" (leaf)");
// 			}
// 			System.out.println(sb.toString());
// 		}
// 		for(RadixTreeNode node : nodes.values()){
// 			node.printTree(height + 1);
// 		}
// 	}
// }

// public class RadixTree{
// 	public static void main(String[] args){
// 		RadixTreeNode root = new RadixTreeNode();
// 		String [] words = "romane romanus romulus ruben ruber rubicon rubicundus".split("\\s+");
// 		for(String word : words){
// 		 	root.insert(word);
// 		}
// 		System.out.println("Words:" + String.join(" ", words));
// 		System.out.println("Tree:");
// 		root.printTree(0);

// 		//test for suggestions 
// 		String prefix = "rom";
// 		List<String> suggestions = root.suggestWords(prefix);
// 		System.out.println("Suggestions for prefix'" + prefix + "':");
// 		for (String suggestion : suggestions){
// 			System.out.println(suggestion);
// 		}
// 	}
// }

// 	//need to implement this
// 	//public List<String> suggestWords(String prefix){
// 	// 	List<String> suggestions = new ArrayList<>();
// 	// 	RadixTreeNode prefixNode = findPrefixNode(prefix);

// 	// 	if(prefixNode != null){
// 	// 		collect(prefixNode, prefix, suggestions);
// 	// 	}
// 	// 	return suggestions;
// 	// }
// 	// private RadixTreeNode findPrefixNode(String prefix){
// 	// 	RadixTreeNode current = root;
// 	// 	for(int i=0; i<prefix.length(); i++){
// 	// 		char ch = prefix.charAt(i);
// 	// 		Map<Character, RadixTreeNode> children = current.getChildren();
// 	// 		if(!children.containsKey(ch)){
// 	// 			return null; //prefix not found 
// 	// 		}
// 	// 		current = children.get(ch);
// 	// 	}
// 	// 	return current;
// 	// }
// 	// private void collect(RadixTreeNode node, String prefix, List<String> suggestions){
// 	// 	if(node.isLeaf()){
// 	// 		suggestions.add(prefix); //add prefix if it represents complete word
// 	// 	}
// 	// 	for(Map.Entry<Character, RadixTreeNode> entry : node.getChildren().entrySet()){
// 	// 		collect(entry.getValue(), prefix + entry.getKey(), suggestions);
// 	// 	}
// 	// }


	
// 	// public static void main(String[] args){
// 	// 	RadixTree trie = new RadixTree();

// 	// 	trie.insert("romane");
// 	// 	trie.insert("romanus");
// 	// 	trie.insert("romulus");
// 	// 	trie.insert("rubens");
// 	// 	trie.insert("ruber");
// 	// 	trie.insert("rubicon");
// 	// 	trie.insert("rubicundus");

// 	// 	System.out.println("Search for 'rubens': " + trie.search("rubens")); // true
// 	// 	System.out.println("Search for 'rubicon': " + trie.search("rubicon")); // true
// 	// 	System.out.println("Search for 'bruh': " + trie.search("bruh")); // false

// 	// 	//structure before compression
// 	// 	System.out.println("Tree before compression:");
// 	// 	printTree(trie.getRoot(), "");

// 	// 	trie.compress();

// 	// 	//post compression
// 	// 	System.out.println("\nTree after compression:");
// 	// 	printTree(trie.getRoot(), "");

// 	// 	testSuggestions(trie, "rom");
// 	// 	testSuggestions(trie, "rub");

// 	// }
// 	// private static void printTree(RadixTreeNode node, String prefix){
// 	// 	if(node == null){
// 	// 		return;
// 	// 	}
// 	// 	System.out.println(prefix);

// 	// 	//recursively print children
// 	// 	for(Map.Entry<Character, RadixTreeNode> entry : node.getChildren().entrySet()){
// 	// 		printTree(entry.getValue(), prefix + entry.getKey());
// 	// 	}
// 	// }
// 	// private static void testSuggestions(RadixTree trie, String prefix){
// 	// 	System.out.println("Suggestions for prefix '" + prefix + "':");
// 	// 	List<String> suggestions = trie.suggestWords(prefix);
// 	// 	for(String suggestion : suggestions){
// 	// 		System.out.println(suggestion);
// 	// 	}
// 	// 	System.out.println();

// 	// 