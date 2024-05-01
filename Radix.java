package tree; 

import java.util.Hashtable; 
import java.util.ArrayList; 
import java.util.*; 

public class Radix{

	private RadixNode root; //root of radix tree 

    // Inner node 
    public class RadixNode  {
        char key;
        public RadixNode[] children;
        public boolean isLeaf;

        public RadixNode() {
            this.children = new RadixNode[26]; // Intialized as one slot for each of the 
                                          // 26 (lower-case) letters in the English alphabet
                                          // Initially this is [null, null, null, ..., ]
            this.isLeaf = false;
        }
    }

    public Radix(){
        root = new RadixNode();
    }

    /** 
     * Adds the word to the tree
     * @return true if the method successfully added the word
     * @return false if the word was already in the lexicon
     */
    //insertion method for key-value pair
    //traverse the tree based on the characters of the word, creating nodes as needed.
    public boolean add(String word){
        return add(root, word);
    }
    //creating nodes as needed and marking the last node as a leaf node when the end of the word is reached
    private boolean add(RadixNode node, String word){
        // If word is empty, mark current node as leaf
        if(word.isEmpty()){
            if(node.isLeaf){
                //word already in tee
                return false;
            } else{
                node.isLeaf = true;
                return true;
            }
        }
        char firstChar = word.charAt(0);
        int index = firstChar - 'a';
          if (node.children[index] == null) {
            node.children[index] = new RadixNode();
            node.children[index].key = firstChar;
        }    
        return add(node.children[index], word.substring(1));
    }


    //method to search for a key
    //public Object serach(String key){


    //}

    //method to delete a key
    //public void delete(String key){


    //}
}
