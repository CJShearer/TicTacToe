import java.util.Scanner;
public class TicTacToe {
	public static final int BOARD_SIZE = 3;
	public static final int NUM_TO_WIN = 3;
	public static void main(String[] args) {
		Board board = new Board();
		board.setSize(BOARD_SIZE);
		board.setNumToWin(NUM_TO_WIN);
		Scanner s = new Scanner(System.in);
		System.out.println("Welcome to tic-tac-toe");
		while(!board.isGameOver())
		{
			board.printGameStatus();
			board.printBoard();
			board.placeCell(s.nextInt(), s.nextInt());
		}
		board.printGameStatus();
		board.printBoard();
	}

}
