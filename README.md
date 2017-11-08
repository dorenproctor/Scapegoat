Author: Doren Proctor
doren.proctor@wsu.edu

## Description ##
A scapegoat tree is a self-balancing binary search tree that provides worst-case O(log n) lookup time, and O(log n) amortized insertion and deletion time. Instead of the small incremental rebalancing operations used by most balanced tree algorithms, scapegoat trees rarely but expensively choose a "scapegoat" and completely rebuild the subtree rooted at the scapegoat into a complete binary tree.  


## Usage ##
Option 1: Use your favorite Java IDE.  

Option 2: Open the directory in your terminal and run:  
`make`  
`java Main`

There must be a `tree.txt` in the same directory. This contains the intructions for the tree.

## Input ##

Put the following commands into `tree.txt`. One command per line. Note that capitalization matters and there must be whitespace in lines that take multiple words.

• BuildTree alpha key - The input file must contain one call to BuildTree on the first line. This call creates a new tree with alpha weight and a first node containing key as a value. All operations for that input file are on this tree. Scapegoat trees support any alpha such that 0.5 ≤ alpha < 1. A high alpha value results in fewer balances, making insertion quicker but lookups and deletions slower, and vice versa for low alpha.

• Insert key – given an integer key create a new node with that key and insert it into the tree.  

• Search key – find a specified key in the tree if it exists.  

• Delete key – delete the specified key from the tree if it exists.  

• Print – print the tree structure.  

• Done – exit the program.

## Output ##
The output goes into a file named `output.svg`. The pretty printer that creates the svg file was written by Wayne Cochran, a previous professor at WSUV.

## Debug info ##
Toward the top of `Scapegoat.java` there is a public global int named `debug`. It has several different values and the program prints different output to the terminal depending on its value. 

• debug = 0 -- prints something when you use the Search, Print, or Done command.

• debug = 1 -- also prints when you use Insert and when the tree rebalances itself.

• debug = 2 -- also prints the depth and maxDepth on every insert.  When depth>maxDepth, the tree rebalances.

• debug = 3 -- also prints every viable scapegoat when the tree is rebalancing.

## Contained files ##
• `tree.txt` - the input for the program. This is the file you edit to alter the tree. 
• `Main.java` - the executable program. Parses `tree.txt` and creates a scapegoat tree from it.
• `Scapegoat.java` - does all the logic for the scapegoat tree.
• `Node.java` - small object that Scapegoat uses for each element of the tree.
• `Main.java` - pretty printer that creates `output.svg` file to view the tree
• `Makefile` - this compiles the program. In the terminal, you can run `make` to compile everything or `make clean` to remove the `.class` files and `output.svg`.
• `README.md` - this file.