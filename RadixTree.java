package tree;

import java.util.List; 
import java.util.ArrayList; 
import java.util.*;


//ADT is a list 
class RadixTreeNode{
	private Map<Character, RadixTreeNode> children;
	private boolean isLeaf;

	public RadixTreeNode(){
		this.children = new HashMap<>();
		this.isLeaf = false;
	}
	public Map<Character, RadixTreeNode> getChildren(){
		return children;
	}
	public boolean isLeaf(){
		return isLeaf;
	}
	public void setLeaf(boolean leaf){
		isLeaf= leaf;
	}
}

public class RadixTree{
	private RadixTreeNode root;

	public RadixTree(){
		this.root = new RadixTreeNode();
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
			Map.Entry<Character, RadixTreeNode> entry = node.getChildren().entrySet().iterator().next();
			char cLabel = entry.getKey(); //child
			RadixTreeNode childNode = entry.getValue();

			//merge current node with child
			String newLabel = prefix + cLabel;
			node.getChildren().clear();
			node.getChildren().put(newLabel.charAt(0), childNode); //update parents children with merged node

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
	//make prefix method for suggestions


	
	public static void main(String[] args){
		RadixTree trie = new RadixTree();

		trie.insert("hello");
		trie.insert("bye");
		trie.insert("apple");
		trie.insert("banana");
		trie.insert("app");
		trie.insert("bat");

		System.out.println("Search for 'apple': " + trie.search("apple")); // true
		System.out.println("Search for 'bye': " + trie.search("bye")); // true
		System.out.println("Search for 'bruh': " + trie.search("bruh")); // false

		//structure before compression
		System.out.println("Tree before compression:");
		printTree(trie.getRoot(), "");

		trie.compress();

		//post compression
		System.out.println("\nTree after compression:");
		printTree(trie.getRoot(), "");

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


}