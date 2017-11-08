## Description ##
A scapegoat tree is a self-balancing binary search tree that provides worst-case O(log n) lookup time, and O(log n) amortized insertion and deletion time. Instead of the small incremental rebalancing operations used by most balanced tree algorithms, scapegoat trees rarely but expensively choose a "scapegoat" and completely rebuild the subtree rooted at the scapegoat into a complete binary tree.  


## Usage ##
Option 1: Use your favorite Java IDE.  

Option 2: Open the directory in your terminal and run:
`make`
`java Main`

There must be a `tree.txt` in the same directory. This contains the intructions for the tree.

### Input ###

Put the following commands into `tree.txt`. One command per line.

• BuildTree alpha key - The input file must contain one call to BuildTree on the first line. This call creates a new tree with alpha weight and a first node containing key as a value. All operations for that input file are on this tree. Scapegoat trees support any alpha such that 0.5 ≤ alpha < 1. A high alpha value results in fewer balances, making insertion quicker but lookups and deletions slower, and vice versa for low alpha.

• Insert key – given an integer key, create a new node with key value and insert it into the tree.  

• Search key – find a specified key in the tree if it exists.  

• Delete key – delete the specified key from the tree if it exists.  

• Print – prints the tree structure.  

• Done – exit the program.

### Output ###
The output goes into a file named `output.svg`. The pretty printer that creates the svg file was written by Wayne Cochran, a previous professor at WSUV.