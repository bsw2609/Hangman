## Hangman Game

## Overview

This is a fun implementation of the classic game Hangman. However, in this version the player can ask for hints or sugggestions for the next word. 

## Design

# Game Setup

The player will first be prompted with a choice between three difficulties to play. Each increase in difficulty means that the user is allowed less incorrect guesses. Then, the user is asked for the length of the word that they would like to play with. The program randomly generates a word that matvhes the specified length and the game is able to begin.

# Gameplay

The player is allowed to ask for a hint or a suggestion for the next word. If the player asks for a hint, the program will search for that key in symbol table "wordTable" and return the associated hint to that word. If the player asks for a suggestion, the program will traverse through the RadixTree, exploring different possibilites for the next letter and then reccomend one.

The player is prompted for their guess for one of the correct letters in the word. If the letter does not exist in the word, we add one body part to the hanging man. If the player guesses correctly, the letter is placed into the word. After each turn the player is prompted for their next guess and the game finshes after they have reached the specified number of incorrect guesses associated with the difficulty they select or if they are able to guess all letters correct before losing.



## words.txt

words.txt is a toy data set where each word is asscoiated with a category of some sort. This serves as the hint if users request one.

tree,plant
treat,food
tread,verb
treated,adjective

Here we can see 4 words with common prefixes with the hints for each word seperated from that word with a comma.





## Radix Tree



## Radix Tree





# Hangman
javac -d bin tree/*.java
