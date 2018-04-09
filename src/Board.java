/**
 * @author		Cody Shearer <cjshearer@live.com>
 * @version		1.0
 * @since		1.0
*/
public class Board {
	public class Cell {
		/**
		 * Numeric representation of Tic-Tac-Toe piece
		*/
		private int val;
		public Cell()
		{
			val = 0;
		}
		public int getVal()
		{
			return val;
		}
		/**
		 * Set the value of a cell
		 * <p>
		 * The cell's values are bounded between -1 and 1.
		 * This restriction to three values corresponds to 
		 * the possible pieces that may exist on the board:
		 * (-1,0,1)->(O, ,X) 
		*/
		public void setVal(int aValue)
		{
			if(aValue>=-1&&aValue<=1)
				val = aValue;
		}
		/**
		 * Compare the values of a cell
		*/
		public boolean equals(Cell aCell)
		{
			if(aCell==null)
				return false;
			if(aCell.getVal()==this.val)
				return true;
			return false;
		}
		/**
		 * Print the numeric value of a cell
		*/
		public String toString()
		{
			return Integer.toString(val);
		}
	}
	/**
	 * The possible directions that a search can be directed in 
	 * <p>
	 * Is used by {@link Board#searchInDirection(int, int, int, DIRECTION, int)}
	*/
	public static enum DIRECTION {UP,DOWN,LEFT,RIGHT,UP_LEFT,UP_RIGHT,DOWN_RIGHT,DOWN_LEFT};
	/**
	 * A 2D array of cells which represent a Tic-Tac-Toe board
	*/
	private Cell[][] board;
	/**
	 * The length and width of a board	
	*/
	private int boardSize;
	/**
	 * The number of identical consecutive pieces required for a player to win
	*/
	private int numToWin;
	/**
	 * Numeric representation of a winner.
	*/
	private int winner;
	/**
	 * Represents whether the game is over.
	*/
	private boolean gameOver;
	/**
	 * Represents whether it is X's turn. If it is not, then it is O's turn
	*/
	private boolean xsTurn;
	//Constructor
	public Board()
	{
		boardSize = numToWin = 3;
		board = new Cell[boardSize][boardSize];
		this.resetBoard();
		winner = 0;
		gameOver = false;
		xsTurn = true;
	}
	//Getters
	/**
	 * Checks whose turn it is.
	 * @return true if it is X's turn or false if it is O's turn 
	*/
	public boolean isXsTurn()
	{
		return xsTurn;
	}
	/**
	 * Checks if the game is over.
	 * @return true if the game is over, otherwise false
	*/
	public boolean isGameOver()
	{
		return gameOver;
	}
	/**
	 * Checks if the board is full.
	 * @return true if any spaces remain unoccupied by a player, otherwise false
	*/
	public boolean isBoardFull()
	{
		for(int y=0;y<board.length;y++)
		{
			for(int x=0;x<board.length;x++)
			{
				if(board[y][x].val==0)
					return false;
			}
		}
		return true;
	}
	/**
	 * @return the name of the player who's turn it is
	*/
	public String getCurrPlayer()
	{
		if(isXsTurn())
			return "X";
		return "O";
	}
	/**
	 * @return the name of the game's winner
	*/
	public String getWinner()
	{
		switch(winner)
		{
		case -1: return ("O");
		case 0: return("Nobody");
		case 1: return("X");
		default: return("Huh?");
		}
	}
	/**
	 * Checks if the given position is unoccupied by a player. 
	 * @param X the horizontal position on the board
	 * @param Y the vertical position on the board
	 * @return true if the given board coordinates are unoccupied and in bounds of the board, otherwise false
	*/
	public boolean isPositionEmpty(int X, int Y)
	{
		if(X<0||X>=board.length||Y<0||Y>=board.length)
			return false;
		if(board[Y][X].val!=0)
			return false;
		return true;
	}
	//Setters
	/**
	 * Sets the size of the board.
	 * <p>
	 * Size must be greater than 1 but is also arbitrarily bound to be less than 9.
	 * It could be any size greater than 1, but display and performance becomes problematic.
	 * <p>
	 * This method also:
	 * <ul>
	 * 		<li>updates {@link #numToWin} with {@link #setNumToWin(int)} to be sure it is not greater than
	 * 			the board's size, else the game would be impossible to win.
	 * 		<li>resets the board with {@link #resetBoard()}
	 * </ul>
	 * @param aBoardSize the new length and width of the board
	*/
	public void setSize(int aBoardSize)
	{ 
		if(aBoardSize>1&&aBoardSize<9)
		{
			boardSize = aBoardSize;
			setNumToWin(numToWin);
			resetBoard();
		}
	}
	/**
	 * Sets the number of identical, consecutive pieces needed in a line for a player to win
	 * <p>
	 * {@link #numToWin} is bounded to be less than or equal to the board size.
	 * When the method is given a value greater than the board size, {@link #numToWin}
	 * is set equal to the size of the board.
	 * @param aNumToWin the number of consecutive pieces needed in a line to win
	*/
	public void setNumToWin(int aNumToWin)
	{
		if(aNumToWin>boardSize)
			numToWin=boardSize;
		else
			numToWin=aNumToWin;
	}
	/**
	 * Swaps the current player turn
	*/
	private void changeTurn()
	{
		if(isXsTurn())
			xsTurn = false;
		else
			xsTurn = true;
	}
	public void placeCell()
	{
		//TODO implement computer player
	}
	/**
	 * Places an X or O on the board depending on whose turn it is.
	 * <p>
	 * This method takes a coordinate pair that a player intends to place a piece on.
	 * If the game is not over and the X Y coordinates represent an unoccupied position,
	 * the method will find whose turn it is and set the appropriate piece on the board.
	 * <p>
	 * After completion, this method will also:
	 * <ul>
	 * 		<li>Call {@link #updateBoardStatus()} to be sure the game has not ended
	 * 		<li>Call {@link #changeTurn()} to swap current player's turn.
	 * </ul>
	 * @param X the horizontal position on the board
	 * @param Y the vertical position on the board
	*/
	public void placeCell(int X, int Y)
	{//handles placement of pieces, including checking invalid values, updating board status (win/draw/gameOver), and changing turn
		if(gameOver||!this.isPositionEmpty(X, Y))//if game is over or the position is not empty
			return;
		int currPlayer = -1;
		if(isXsTurn())
			currPlayer = 1;
		board[Y][X].setVal(currPlayer);
		updateBoardStatus();
		changeTurn();
	}
	/**
	 * Re-initializes board[][] with new Cells
	*/
	public void resetBoard()
	{
		board = new Cell[boardSize][boardSize];
		for(int i=0;i<board.length;i++)
		{
			for(int j=0;j<board.length;j++)
				board[i][j] = new Cell();
		}
	}
	/**
	 * Sets the gameOver state to true and declares a winner
	*/
	private void setWinner()
	{
		gameOver = true;
		if(xsTurn)
			winner=1;
		else
			winner=-1;
	}
	/**
	 * If the board is full and no player has won, gameOver is set to true
	*/
	private void checkForDrawGame()
	{//end the game if the board is full
		if(isBoardFull())
			gameOver=true;
	}
	/**
	 *Updates the win, loss, draw, gameOver status.
	 *<p>
	 *Upon finding an occupied cell which has a given number of consecutive 
	 *identical pieces in any direction, this method calls {@link #setWinner()}
	 *to end the game and declare a winner.
	 *<p>
	 *After checking for a winner, this method also calls {@link #checkForDrawGame()}
	 *to be sure the game has not ended without a winner
	*/
	private void updateBoardStatus()
	{
		//TODO: optimize search - search only the column, row and diagonal(s)
		//		that are updated by placement of a new piece 
		int currValue;
		for(int y=0;y<board.length;y++)
		{
			for(int x=0;x<board.length;x++)
			{
				currValue = board[y][x].val;
				if(currValue!=0)
				{
					if(searchAllDirections(x,y,currValue,numToWin))
						setWinner();
				}
			}
		}
		//check for draw game (board full) only after checking for a win
		checkForDrawGame();
	}
	/**
	 * Checks if a straight lines of a given number of identical consecutive integers
	 * exists in any direction from a given origin
	 * <p>
	 * This method is used for brute-force win detection. A better method would
	 * be to search only the column, row and diagonal(s) that are updated by placement
	 * of a new piece.
	 * @param X					the horizontal position on the board
	 * @param Y					the vertical position on the board
	 * @param originValue		the value of the origin cell given by X,Y
	 * @param searchesRemaining	The remaining number of spaces that can be searched
	*/
	private boolean searchAllDirections(int X, int Y, int originValue, int searchesRemaining)
	{
		return  searchInDirection(X,Y,originValue,DIRECTION.UP,searchesRemaining)||
				searchInDirection(X,Y,originValue,DIRECTION.DOWN,searchesRemaining)||
				searchInDirection(X,Y,originValue,DIRECTION.LEFT,searchesRemaining)||
				searchInDirection(X,Y,originValue,DIRECTION.RIGHT,searchesRemaining)||
				searchInDirection(X,Y,originValue,DIRECTION.UP_LEFT,searchesRemaining)||
				searchInDirection(X,Y,originValue,DIRECTION.UP_RIGHT,searchesRemaining)||
				searchInDirection(X,Y,originValue,DIRECTION.DOWN_LEFT,searchesRemaining)||
				searchInDirection(X,Y,originValue,DIRECTION.DOWN_RIGHT,searchesRemaining);
	}
	/**
	 * Checks if a given number of identical consecutive integers exist on the board.
	 * <p>
	 * This method begins a search at a given origin on the board and looks in a given 
	 * direction for a given number of identical consecutive integers in a straight line.
	 * @param X					the horizontal position on the board
	 * @param Y					the vertical position on the board
	 * @param originValue		the value of the origin cell given by X,Y
	 * @param aDirection		a direction in which to search
	 * @param searchesRemaining	The remaining number of spaces that can be searched
	*/
	private boolean searchInDirection(int X, int Y, int originValue, DIRECTION aDirection, int searchesRemaining)
	{
		searchesRemaining--;
		int newX = X;
		int newY = Y;
		switch(aDirection)
		{
		case UP:		newY--;
			break;
		case DOWN:		newY++;
			break;
		case LEFT:		newX--;
			break;
		case RIGHT:		newX++;
			break;
		case UP_LEFT:	newX--;
						newY--;
			break;
		case UP_RIGHT:	newX++;
						newY--;
			break;
		case DOWN_RIGHT:newX++;
						newY++;
			break;
		case DOWN_LEFT: newX--;
						newY++;
			break;
		}
		//if the new(X,Y) pair is out of bounds, return false
		if(newX<0||newX>=board.length||newY<0||newY>=board.length)
			return false;
		//if the new Cell's value does not match the origin's value, return false
		if(board[newY][newX].val!=originValue)
			return false;
		//if no more searches can be done in the given direction, then the given number of consecutive values to find are present
		if(searchesRemaining==0)
			return true;
		return searchInDirection(newX,newY,originValue,aDirection,searchesRemaining-1);
	}
	/**
	 * Either print the winner of the game or {@link #printInstructions()}
	 */
	public void printGameStatus()
	{
		if(gameOver)
			System.out.println(getWinner()+" wins!");
		else
			printInstructions();		
	}
	/**
	 * Inform players of whose turn it is along with instructions on how to input moves
	*/
	private void printInstructions()
	{
		System.out.println("It is "+getCurrPlayer()+"'s turn. Enter a space separated coordinate pair: X Y");
	}
	/**
	 * Print a fully formatted Tic-Tac-Toe board for the user
	*/
	public void printBoard()
	{
		String xLabel=" x",rowSeparator = "",separatorPrefix= "y",row;
		//build xLabel and row separator
		for(int i=0;i<board.length;i++)
		{
			xLabel+=" "+i+"  ";
			rowSeparator+="+---";
		}
		rowSeparator+="+";
		System.out.println(xLabel);//print xLabel---------->	 x 0   1   2  
		//print rowSeparators with prefixes where needed--->	y+---+---+---+
		//build and print numbered rows-------------------->	0| X |   | O |
		for(int i=0;i<board.length;i++)
		{
			//set prefix then print with row separator 
			if(i>0) separatorPrefix=" ";
			System.out.println(separatorPrefix+rowSeparator);
			//number and print row
			row=Integer.toString(i);
			for(int j=0;j<board.length;j++)
			{
				row+="| ";
				switch(board[i][j].val)
				{
				case -1: row+="O ";
					break;
				case 0: row+="  ";
					break;
				case 1: row+="X ";
					break;
				}
			}
			row+="|";
			System.out.println(row);
		}
		System.out.println(" "+rowSeparator);
	}
	/**
	 * @return A string containing a minimally formatted Tic-Tac-Toe board
	*/
	public String toString()
	{
		String row= "";
		//build row separator
		for(int i=0;i<board.length;i++)
		{
			row+="+---";
		}
		row+="+\n";
		String ret ="";
		for(int i=0;i<board.length;i++)
		{
			ret+=row;//add a row border
			for(int j=0;j<board.length;j++)
			{
				ret+="| "+board[i][j]+" ";//add leading vertical bar-number pairs 
			}
			ret+="|\n";//"cap off" bar-number pairs with final bar and newline
		}
		ret+=row;
		return ret;
	}
}
