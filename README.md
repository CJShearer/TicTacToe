# TicTacToe
This is a java implementation of TicTacToe for an N sized board. 

# Getting Started
This project contians all the necessary files to be compiled. Either clone or download the project into the workspace of your favorite IDE and run TicTacToe.java.

# Instructions
Run TicTacToe.java to begin a normal game with a 3x3 board. Moves are entered in space-separated X Y coordiate pairs, which can easily be found by looking at the sides of the board.

	It is X's turn. Enter a space separated coordinate pair: X Y
	 x 0   1   2  
	y+---+---+---+
	0|   |   |   |
	 +---+---+---+
	1|   |   |   |
	 +---+---+---+
	2|   |   |   |
	 +---+---+---+
	 1 1
   
	It is O's turn. Enter a space separated coordinate pair: X Y
	 x 0   1   2  
	y+---+---+---+
	0|   |   |   |
	 +---+---+---+
	1|   | X |   |
	 +---+---+---+
	2|   |   |   |
	 +---+---+---+

The board size can be changed independently from the number of consequtivly placed pieces required to win.
```java
	public static final int BOARD_SIZE = 5;
	public static final int NUM_TO_WIN = 3;
```
	X wins!
	x 0   1   2   3   4  
	y+---+---+---+---+---+
	0| X |   |   |   |   |
	 +---+---+---+---+---+
	1| X |   |   |   |   |
	 +---+---+---+---+---+
	2| X |   |   |   |   |
	 +---+---+---+---+---+
	3|   |   |   |   |   |
	 +---+---+---+---+---+
	4|   |   |   | O | O |
	 +---+---+---+---+---+
