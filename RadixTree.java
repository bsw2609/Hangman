package tree;
 
import java.util.*;


class RadixTreeNode{
	Map<Character, RadixTreeNode> nodes;
	boolean isLeaf;
	String prefix;

	public RadixTreeNode(){
		this("", false);
	}
	public RadixTreeNode(String prefix, boolean isLeaf){
		this.prefix = prefix;
		this.isLeaf = isLeaf;
		this.nodes = new HashMap<>();
	}
	public RadixTreeNode match(String word){
		int sharedPreLen = 0;
		while(sharedPreLen < prefix.length() && sharedPreLen<word.length() && prefix.charAt(sharedPreLen) == word.charAt(sharedPreLen)){
			sharedPreLen++;
		}
		return new RadixTreeNode(prefix.substring(0, sharedPreLen), sharedPreLen == prefix.length());
	}

	
	public void insert(String word){
		//if current prefix is in word
		if(word.startsWith(prefix)){
			//if word is prefix
			if(word.equals(prefix)){
				isLeaf = true; //mark current node as leaf
			}else{
			//get remainder
				String remainder = word.substring(prefix.length());
				//check if theirs a child node 
				if(nodes.containsKey(remainder.charAt(0))){
					//recursivley add remainder 
					nodes.get(remainder.charAt(0)).insert(remainder);
				}else{
					//no child, create new node w remainder as prefix
					RadixTreeNode newNode = new RadixTreeNode(remainder, true);
					nodes.put(remainder.charAt(0), newNode);
				}
			}
		}else{
			//if current prefix is not a prefix of word 
			//no common prefix, split
			int sharedPreLen = getSharedPreLen(word);
			if(sharedPreLen >0){
				//split current node into two nodes 
				String sharedPre = word.substring(0, sharedPreLen);
				String thisRem = prefix.substring(sharedPreLen);
				String wordRem = word.substring(sharedPreLen);
				prefix = sharedPre;
				isLeaf = false;
				//create new node for remaining part of prefix
				RadixTreeNode remainderNode = new RadixTreeNode(thisRem, isLeaf);
				remainderNode.nodes = nodes; //move child nodes to new node
				nodes = new HashMap<>(); //clear child nodes of the current node
				nodes.put(thisRem.charAt(0), remainderNode);
				//recursivley insert remaining part of word 
				RadixTreeNode wordRemNode = new RadixTreeNode(wordRem, true);
				nodes.put(wordRem.charAt(0), wordRemNode);
				RadixTreeNode newNode = new RadixTreeNode(word, true);
				nodes.put(word.charAt(0), newNode);
			}else{
				//no common prefix, insert as new node 
				nodes.put(word.charAt(0), new RadixTreeNode(word, true));
			}

		}
	}
	private int getSharedPreLen(String word){
		int sharedLen = 0;
		int minLen = Math.min(prefix.length(), word.length());
		for(int i = 0; i<minLen; i++){
			if(prefix.charAt(i) == word.charAt(i)){
				sharedLen++;
			}
			else{
				break;
			}
		}
		return sharedLen;
	}

	public boolean search(String word){
		if(!word.startsWith(prefix)){
			return false;
		}
		if(prefix.equals(word)){
			return isLeaf;
		}
		RadixTreeNode matchNode = nodes.get(word.charAt(0));
		if(matchNode == null){
			return false;
		}
		return matchNode.search(word.substring(matchNode.prefix.length()));
	}

	public boolean delete(String word){
		if(!word.startsWith(prefix)){
			return false;
		}
		if(prefix.equals(word)){
			if(!isLeaf){
				return false;
			}
			isLeaf = false;
			return nodes.isEmpty();
		}
		RadixTreeNode matchNode = nodes.get(word.charAt(0));
		if(matchNode == null){
			return false;
		}
		if(!matchNode.delete(word.substring(matchNode.prefix.length()))){
			return false;
		}
		nodes.remove(word.charAt(0));
		return nodes.isEmpty();
	}

	public List<String> suggestWords(String prefix){
		List<String> suggestions = new ArrayList<>();
		RadixTreeNode prefixNode = findPreNode(prefix);
		if(prefixNode != null){
			collect(prefixNode, prefix, suggestions);
		}
		return suggestions;
	}

	private RadixTreeNode findPreNode(String prefix){
		RadixTreeNode current = this;
		for(int i=0; i<prefix.length(); i++){
			char ch = prefix.charAt(i);
			Map<Character, RadixTreeNode> children = current.nodes;
			if(!children.containsKey(ch)){
				return null; //prefix not found
			}
			current = children.get(ch);
		}
		return current;

	}

	private void collect(RadixTreeNode node, String prefix, List<String> suggestions){
		if(node.isLeaf){
			suggestions.add(prefix); //add prefix if represents complete word
		}
		for(Map.Entry<Character, RadixTreeNode> entry : node.nodes.entrySet()){
			collect(entry.getValue(), prefix + entry.getKey(), suggestions);
		}
	}

	public void printTree(int height){
		if(!prefix.isEmpty()){
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<height; i++){
				sb.append("-");
			}
			sb.append(" ").append(prefix);
			if(isLeaf){
				sb.append(" (leaf)");
			}
			System.out.println(sb.toString());
		}
		for(RadixTreeNode node : nodes.values()){
			node.printTree(height + 1);
		}
	}
}

public class RadixTree{
	public static void main(String[] args){
		RadixTreeNode root = new RadixTreeNode();
		String [] words = "romane romanus romulus ruben ruber rubicon rubicundus".split("\\s+");
		for(String word : words){
		 	root.insert(word);
		}
		System.out.println("Words:" + String.join(" ", words));
		System.out.println("Tree:");
		root.printTree(0);

		//test for suggestions 
		String prefix = "rom";
		List<String> suggestions = root.suggestWords(prefix);
		System.out.println("Suggestions for prefix'" + prefix + "':");
		for (String suggestion : suggestions){
			System.out.println(suggestion);
		}
	}
}

	
	//public List<String> suggestWords(String prefix){
	// 	List<String> suggestions = new ArrayList<>();
	// 	RadixTreeNode prefixNode = findPrefixNode(prefix);

	// 	if(prefixNode != null){
	// 		collect(prefixNode, prefix, suggestions);
	// 	}
	// 	return suggestions;
	// }
	// private RadixTreeNode findPrefixNode(String prefix){
	// 	RadixTreeNode current = root;
	// 	for(int i=0; i<prefix.length(); i++){
	// 		char ch = prefix.charAt(i);
	// 		Map<Character, RadixTreeNode> children = current.getChildren();
	// 		if(!children.containsKey(ch)){
	// 			return null; //prefix not found 
	// 		}
	// 		current = children.get(ch);
	// 	}
	// 	return current;
	// }
	// private void collect(RadixTreeNode node, String prefix, List<String> suggestions){
	// 	if(node.isLeaf()){
	// 		suggestions.add(prefix); //add prefix if it represents complete word
	// 	}
	// 	for(Map.Entry<Character, RadixTreeNode> entry : node.getChildren().entrySet()){
	// 		collect(entry.getValue(), prefix + entry.getKey(), suggestions);
	// 	}
	// }


	
	// public static void main(String[] args){
	// 	RadixTree trie = new RadixTree();

	// 	trie.insert("romane");
	// 	trie.insert("romanus");
	// 	trie.insert("romulus");
	// 	trie.insert("rubens");
	// 	trie.insert("ruber");
	// 	trie.insert("rubicon");
	// 	trie.insert("rubicundus");

	// 	System.out.println("Search for 'rubens': " + trie.search("rubens")); // true
	// 	System.out.println("Search for 'rubicon': " + trie.search("rubicon")); // true
	// 	System.out.println("Search for 'bruh': " + trie.search("bruh")); // false

	// 	//structure before compression
	// 	System.out.println("Tree before compression:");
	// 	printTree(trie.getRoot(), "");

	// 	trie.compress();

	// 	//post compression
	// 	System.out.println("\nTree after compression:");
	// 	printTree(trie.getRoot(), "");

	// 	testSuggestions(trie, "rom");
	// 	testSuggestions(trie, "rub");

	// }
	// private static void printTree(RadixTreeNode node, String prefix){
	// 	if(node == null){
	// 		return;
	// 	}
	// 	System.out.println(prefix);

	// 	//recursively print children
	// 	for(Map.Entry<Character, RadixTreeNode> entry : node.getChildren().entrySet()){
	// 		printTree(entry.getValue(), prefix + entry.getKey());
	// 	}
	// }
	// private static void testSuggestions(RadixTree trie, String prefix){
	// 	System.out.println("Suggestions for prefix '" + prefix + "':");
	// 	List<String> suggestions = trie.suggestWords(prefix);
	// 	for(String suggestion : suggestions){
	// 		System.out.println(suggestion);
	// 	}
	// 	System.out.println();

	// }