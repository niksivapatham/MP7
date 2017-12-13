import java.util.Scanner;

/*
 * The main engine for the game, holds the board and players.
 */
public class Game {
	/**
	 * The main board for the game
	 */
	private Board board;
	/**
	 * The player with the black pieces.
	 */
	private Player black;
	/**
	 * player with the white pieces
	 */
	private Player red;
	/**
	 * The introductory message/rules
	 */
	private String intro = "Hello and welcome to Checkers aka MP 7. The rules are simple:"
			+ "\r\n1.You may only move diagonally forward"
			+ "\r\n2.If there is an enemy to you diagonal and no enemy to the diagonal behind, you may jump them and kill them"
			+ "\r\n3.If a piece reaches the end, it becomes a king and can move forward or backward"
			+ "\r\nPlease enter all coordinates as spaced x and y entries ie: x y"
			+ "\r\nHave fun and good luck";
			
	
	/**
	 * Initializes and starts the game
	 */
	private void start() {
		this.board = new Board();
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the name of the player who will be playing Black: ");
		this.black = new Player(in.nextLine(), 1);
		System.out.println("Enter the name of the player who will be playing Red: ");
		this.red = new Player(in.nextLine(), 0);
		board.copyPlayer(this.black);
		board.copyPlayer(this.red);
		System.out.println(intro);
		board.draw();		
		this.play();
	}
	/**
	 * The method that is called on start
	 * @param args whatever
	 */
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	/**
	 * Runs the actual game in a loop until it ends.
	 * Cycles between player turns, redrawing board, and check if game has ended
	 */
	private void play() {
		while (!this.gameOver()) {
			this.playerTurn(this.red);
			this.board.draw();
			if (this.gameOver()) {
				this.gameEnding();
			}
			this.playerTurn(this.black);
			this.board.draw();
			
		}
		this.gameEnding();
	}
	
	/**
	 * Checks if game is over by check if either the red
	 * or black player have run out of pieces
	 * @return boolean telling if games if over or not
	 */
	private boolean gameOver() {
		if (this.red.noPieces() || this.black.noPieces()) {
			return true;
		} 
		return false;
	}
	
	/**
	 * Runs the player's (argument) turn. Asks them what
	 * piece they want to move, determins if that is valid.
	 * Then moves the piece, decided whether or not to king the
	 * piece, and then moves the piece in both the player and board's
	 * array. Then redraws it.
	 * @param player whose turn it is.
	 */
	private void playerTurn(Player p) {
		Scanner in = new Scanner(System.in);
		int xPiece;
		int yPiece;
		int xNew;
		int yNew;
		System.out.println(p.getName() + ", what piece would you like to move? - x y : ");
		xPiece = in.nextInt();
		yPiece = in.nextInt();
		//Determines if the location the player has selected has a piece and whether it belongs
		//to them
		while (!p.isMyPiece(xPiece, yPiece)) {
			System.out.println("Invalid Piece selection, please try again: ");
			xPiece = in.nextInt();
			yPiece = in.nextInt();
		}
		System.out.println(p.getName() + ", where would you like to move to? - x y : ");
		xNew = in.nextInt();
		yNew = in.nextInt();
		//Determines whether the selected piece can move to the specified location, using a 
		//method in the board class that is quite complicated, running through all move possibilities
		while (!this.board.canMove(xPiece, yPiece, xNew, yNew)) {
			System.out.println("Invalid destination selection, "
					+ "please try again (If you would like to choose a different piece, type 9 9): ");
			xNew = in.nextInt();
			yNew = in.nextInt();
			if (xNew == 9 && yNew == 9) {
				break;
			}
		}
		//Restarts method if they want a different piece
		if (xNew == 9 && yNew == 9) {
			this.playerTurn(p);
			return;
		}
		
		
		//"Kings" the piece in the eyes of both the Player and the board
		if (this.board.canKing(yNew, p.getColor())) {
			p.king(xPiece, yPiece);
			this.board.king(xPiece, yPiece);
		}
		//Moves piece for "both" boards
		p.move(xPiece, yPiece, xNew, yNew);
		this.board.move(xPiece, yPiece, xNew, yNew);
		
	}
	
	/*
	 * Runs a congratulatory message for the winner, and asks
	 * whether they would like to play again. If they do, creates 
	 * a new game.
	 */
	private void gameEnding() {
		if (this.red.noPieces()) {
			System.out.println(this.black.getName() + " has won! Congratulations!");
		} else {
			System.out.println(this.red.getName() + " has won! Congratulations!");
		}
		System.out.println("Would you like to play again?: ");
		Scanner in = new Scanner(System.in);
		String[] arg = new String[0];
		if (in.nextLine().toLowerCase().trim().equals("yes")) {
			Game.main(arg);
		}
	}

}
