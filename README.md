## Hangman Game

## Overview

This is a fun implementation of the classic game Hangman. However, in this version the player can ask for hints or sugggestions for the words that they are gueesing for, as well as select difficulty level. 

## Design

# Game Setup

The player will first be prompted with a choice between three difficulties levels to play. Each increase in difficulty means that the user is allowed less incorrect guesses. Then, the user is asked for the length of the word that they would like to play with. The program randomly generates a word that matches the specified length.  Then, the game will begin!

# Gameplay

The player is allowed to ask for a hint or a suggestion for the word that they are guessing. If the player asks for a hint, the program will search for that key in the symbol table "wordTable" and return the associated hint to that word. If the player asks for a suggestion, the program will traverse through the RadixTree, exploring different possibilites for the next letter and provide a list of possible words to pursue.

The player is prompted for their guess for one of the correct letters in the word. If the letter does not exist in the word, we add one body part to the hanging man. If the player guesses correctly, the letter is placed into the word. After each turn the player is prompted for their next guess and the game finshes after they have reached the specified number of incorrect guesses associated with the difficulty they select or if they are able to guess all letters correct before losing.



## words.txt

words.txt is a toy data set where each word is asscoiated with a cross-word style description. This serves as the hint if users request one.

Agriculture/Farming production
Agribusiness/Commercial activities derived from farming
Agronomy/Science of soil management
Agrarian/Relating to land and its cultivation
Agriculturalist/Expert in the science of farming and land management 
Tree/Surrounds Williamstown
Treat/Late-night snack
Tread/____ carefully!
Treated/Fixed by the healthcenter
Tram/Would be nice to get to Albany
Trade/Thoughtful exchange
Troop/Military personnel

Here we can see several words with common prefixes with the hints for each word seperated from that word with a comma.



## Radix Tree
For some reference of what a Radix Tree is, a Radix Tree is a compact version of a prefix tree.  This means that child nodes are merged with their parent nodes if their is only one associated with the parent node itself.  This allows for much more memory and space as their are less nodes within the tree itself.  It is also convenient for this game in the sense that prefixes are a staple in how the Radix Tree is implemented.  The more words that we have within the tree with a common prefix, the more compact the tree can be.  This is why we wanted to use it for the Hangman game in the sense that it is great for providing suggestinos for the word that is being guessed.  Our toy data set was made to have hold words that share a multitude of different prefixes so that we can get the most optimal functinoality of the tree!




