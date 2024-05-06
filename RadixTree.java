package tree;

import java.util.List; 
import java.util.ArrayList; 
import java.util.*;


//ADT is a list 
class RadixTreeNode{
	private Map<Character, RadixTreeNode> children;
	private boolean isLeaf;
	private String label;

	public RadixTreeNode(){
		this.label="";
		this.children = new HashMap<>();
		this.isLeaf = false;
	}
	public String label(){
		return label;
	}
	public void setLabel(String label){
		this.label = label;
	}
	public Map<Character, RadixTreeNode> getChildren(){
		return children;
	}
	public boolean isLeaf(){
		return isLeaf;
	}
	public void setLeaf(boolean leaf){
		isLeaf = leaf;
	}
}

public class RadixTree{
	private RadixTreeNode root;

	public RadixTree(){
		//initialized with empty label
		root = new RadixTreeNode();
	}
	public RadixTreeNode getRoot(){
		return root;
	}

	public void insert(String word){
		RadixTreeNode current = root;
		for(int i = 0; i < word.length(); i++){
			char ch = word.charAt(i);
			Map<Character, RadixTreeNode> children = current.getChildren();
			RadixTreeNode node;
			if(children.containsKey(ch)){
				node = children.get(ch);
			}
			else{
			node = new RadixTreeNode();
			children.put(ch, node);
			}
			current=node;
		}
		current.setLeaf(true);
	}

	public boolean search(String word){
		RadixTreeNode current = root;
		for(int i=0; i<word.length(); i++){
			char ch = word.charAt(i);
			Map<Character, RadixTreeNode> children = current.getChildren();
			if(!children.containsKey(ch)){
				return false;
			}
			current = children.get(ch);
		}
		return current != null && current.isLeaf();
	}

	/**
	*This compress() method initiates the compression process by calling the compress() 
	*helper method recursively starting from the root node. The compress() helper method 
	*compresses each node in the tree by merging it with its single child, if applicable.
	*/
	public void compress(){ //IT WORKS WRONG
		compress(root, "");
	}
	private void compress(RadixTreeNode node, String prefix){
		// If the node is null or a leaf node, return
		if(node == null || node.isLeaf()){
			return;
		}
		//if node has only one child
		if(node.getChildren().size() == 1){
			//retrives first and only child from map, converts to entry in Map.Entry(a nexted interface)
			//entrySet()- get key-value pair from entry, iterator()- access elements in Set next()- next element
			Map.Entry<Character, RadixTreeNode> entry = node.getChildren().entrySet().iterator().next();
			char cLabel = entry.getKey(); //gets key from child node, reps edge label connecting current node to child
			RadixTreeNode childNode = entry.getValue(); //get value of child node from entry

			//merge current node with child
			String newLabel = prefix + cLabel;
			childNode.setLabel(newLabel); //update label of current node
			//node.setLeaf(childNode.isLeaf()); //update leaf status
			//node.getChildren().clear(); //clear current nodes children

			//recursively compress child node
			compress(childNode, newLabel);
		}
		else{
			//recursivley compress each child node
			for (Map.Entry<Character, RadixTreeNode> entry : node.getChildren().entrySet()){
				compress(entry.getValue(), prefix + entry.getKey());
			}
		}
	}
	public List<String> suggestWords(String prefix){
		List<String> suggestions = new ArrayList<>();
		RadixTreeNode prefixNode = findPrefixNode(prefix);

		if(prefixNode != null){
			collect(prefixNode, prefix, suggestions);
		}
		return suggestions;
	}
	private RadixTreeNode findPrefixNode(String prefix){
		RadixTreeNode current = root;
		for(int i=0; i<prefix.length(); i++){
			char ch = prefix.charAt(i);
			Map<Character, RadixTreeNode> children = current.getChildren();
			if(!children.containsKey(ch)){
				return null; //prefix not found 
			}
			current = children.get(ch);
		}
		return current;
	}
	private void collect(RadixTreeNode node, String prefix, List<String> suggestions){
		if(node.isLeaf()){
			suggestions.add(prefix); //add prefix if it represents complete word
		}
		for(Map.Entry<Character, RadixTreeNode> entry : node.getChildren().entrySet()){
			collect(entry.getValue(), prefix + entry.getKey(), suggestions);
		}
	}


	
	public static void main(String[] args){
		RadixTree trie = new RadixTree();

		trie.insert("romane");
		trie.insert("romanus");
		trie.insert("romulus");
		trie.insert("rubens");
		trie.insert("ruber");
		trie.insert("rubicon");
		trie.insert("rubicundus");

		System.out.println("Search for 'rubens': " + trie.search("rubens")); // true
		System.out.println("Search for 'rubicon': " + trie.search("rubicon")); // true
		System.out.println("Search for 'bruh': " + trie.search("bruh")); // false

		//structure before compression
		System.out.println("Tree before compression:");
		printTree(trie.getRoot(), "");

		trie.compress();

		//post compression
		System.out.println("\nTree after compression:");
		printTree(trie.getRoot(), "");

		testSuggestions(trie, "rom");
		testSuggestions(trie, "rub");

	}
	private static void printTree(RadixTreeNode node, String prefix){
		if(node == null){
			return;
		}
		System.out.println(prefix);

		//recursively print children
		for(Map.Entry<Character, RadixTreeNode> entry : node.getChildren().entrySet()){
			printTree(entry.getValue(), prefix + entry.getKey());
		}
	}
	private static void testSuggestions(RadixTree trie, String prefix){
		System.out.println("Suggestions for prefix '" + prefix + "':");
		List<String> suggestions = trie.suggestWords(prefix);
		for(String suggestion : suggestions){
			System.out.println(suggestion);
		}
		System.out.println();

	}


}