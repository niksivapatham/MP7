/**
 * Houses the board as well as logic for whether a piece
 * may move to a certain location.
 * @author Nikhil
 *
 */
public class Board {
	/**
	 * 2D array of players representing the actual board
	 */
	private Piece[][] board;
	/**
	 * ASCII I guess for bars dividing rows
	 */
	private String row = "  #################################################";
	/**
	 * ASCII I guess for bars dividing columns
	 */
	private String rowish = "  #     #     #     #     #     #     #     #     #";
	/**
	 * The x coordinates to be displayed at the top of the board
	 */
	private String xCoordinates = "     0     1     2     3     4     5     6     7";
	
	/**
	 * Constructor for the board
	 */
	public Board() {
		this.board = new Piece[8][8];
	}
	
	/**
	 * Draws/Redraws the board.
	 */
	public void draw() {
		System.out.println(xCoordinates);
		for (int i = 0; i < board[0].length; i++) {			
			System.out.println(row);
			System.out.println(rowish);
			System.out.print(i + " ");
			for (int j = 0; j < board.length; j++) {
				String name = "   ";
				if (board[i][j] != null) {
					name = board[i][j].getType();
				}
				System.out.print("# " + name + " ");
			}
			System.out.println("#");
			System.out.println(rowish);
		}
		System.out.println(row);
		
	}
	
	/**
	 * Copies the pieces of the player into the appropriate 
	 * locations based on color. Used only at beginning of game
	 * @param player 
	 */
	public void copyPlayer(Player player) {
		//If player is red
		if (player.getColor() == 0) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 8; j++) {
					this.board[i][j] = player.getPiece(i, j);
				}
			}
		} 
		//If player is black
		else {
			for (int i = 7; i > 4; i--) {
				for (int j = 0; j < 8; j++) {
					this.board[i][j] = player.getPiece(i, j);
				}
			}
		}
	}
	
	/**
	 * The bulk of the logic of the game. Decides whether
	 * a piece can move to the specified location. Also 
	 * kills enemy piece if move jumps one (Because that 
	 * would be hard to handl in the game class fight me).
	 * @param xCurrent current x-coordinate of the piece
	 * @param yCurrent current y-coordinate of the piece
	 * @param xNew proposed x-coordinate for move
	 * @param yNew proposed y-coordinate for move
	 * @return whether or not the move is applicable.
	 */
	public boolean canMove(int xCurrent, int yCurrent, int xNew, int yNew) {
		//Rendered invalid if out of bounds of board
		if (xNew < 0 || xNew > 7
				|| yNew < 0 || yNew > 7) {
			return false;
		}
		//Rendered invalid if there is any type of piece
		//in the proposed location
		if (this.board[yNew][xNew] != null) {
			return false;
		}
		Piece piece = board[yCurrent][xCurrent];
		//Moveset for if piece is not king
		if (!piece.isKing()) {
			//Handles moves for red piece
			if (piece.getColor() == 0) {
				//If move is diagonally in front
				if ((yNew - yCurrent == 1) && Math.abs(xNew-xCurrent) == 1) {
					return true;
				}
				//If an enemy is diagonally in front and the piece is attempting a jump
				if (xCurrent + 1 < 8 && yCurrent + 1 < 8 && (this.board[yCurrent + 1][xCurrent + 1] != null &&
						this.board[yCurrent + 1][xCurrent + 1].getColor() == 1 )
						&& (yNew-yCurrent == 2 && xNew-xCurrent == 2)) {
					this.kill(xCurrent + 1, yCurrent + 1);
					return true;
				}
				//If an enemy is diagonally in front and the piece is attempting a jump
				if (xCurrent - 1 > -1 && yCurrent + 1 < 8 && (this.board[yCurrent + 1][xCurrent - 1] != null
						&& this.board[yCurrent + 1][xCurrent - 1].getColor() == 1)
						&& (yNew-yCurrent == 2 && xNew-xCurrent == -2)) {
					this.kill(xCurrent - 1, yCurrent + 1);
					return true;
				}
			}
			//Handles moves for black piece
			else {
				//If move is diagonally in front
				if (yNew - yCurrent == -1 && Math.abs(xNew-xCurrent) == 1) {
					return true;
				}
				//If an enemy is diagonally in front and the piece is attempting a jump
				if (xCurrent + 1 < 8 && yCurrent - 1 > -1 && (this.board[yCurrent - 1][xCurrent + 1] != null
						&& this.board[yCurrent - 1][xCurrent + 1].getColor() == 0) 
						&& (yNew-yCurrent == -2 && xNew-xCurrent == 2)) {
					this.kill(xCurrent + 1, yCurrent - 1);
					return true;
				}
				//If an enemy is diagonally in front and the piece is attempting a jump
				if (xCurrent - 1 > -1 && yCurrent - 1 > -1 && (this.board[yCurrent - 1][xCurrent - 1] != null
						&& this.board[yCurrent - 1][xCurrent - 1].getColor() == 0)
						&& (yNew-yCurrent == -2 && xNew-xCurrent == -2)) {
					this.kill(xCurrent-1, yCurrent - 1);
					return true;
				}
			}
		}
		//Handles King movement
		else {
			//Handles Red King
			if (piece.getColor() == 0) {
				//If move is diagonally in front
				if (Math.abs(yNew-yCurrent) == 1 && Math.abs(xNew - xCurrent) == 1) {
					return true;
				}
				//If an enemy is diagonally in front and the piece is attempting a jump
				if (xCurrent + 1 < 8 && yCurrent + 1 < 8 && (this.board[yCurrent + 1][xCurrent + 1] != null &&
						this.board[yCurrent + 1][xCurrent + 1].getColor() == 1 )
						&& (yNew-yCurrent == 2 && xNew-xCurrent == 2)) {
					this.kill(xCurrent + 1, yCurrent + 1);
					return true;
				}
				//If an enemy is diagonally in front and the piece is attempting a jump
				if (xCurrent - 1 > -1 && yCurrent + 1 < 8 && (this.board[yCurrent + 1][xCurrent - 1] != null
						&& this.board[yCurrent + 1][xCurrent - 1].getColor() == 1)
						&& (yNew-yCurrent == 2 && xNew-xCurrent == -2)) {
					this.kill(xCurrent - 1, yCurrent + 1);
					return true;
				}
				//If an enemy is diagonally in front and the piece is attempting a jump
				if (xCurrent + 1 < 8 && yCurrent - 1 > -1 && (this.board[yCurrent - 1][xCurrent + 1] != null
						&& this.board[yCurrent - 1][xCurrent + 1].getColor() == 1) 
						&& (yNew-yCurrent == -2 && xNew-xCurrent == 2)) {
					this.kill(xCurrent + 1, yCurrent - 1);
					return true;
				}
				//If an enemy is diagonally in front and the piece is attempting a jump
				if (xCurrent - 1 > -1 && yCurrent - 1 > -1 && (this.board[yCurrent - 1][xCurrent - 1] != null
						&& this.board[yCurrent - 1][xCurrent - 1].getColor() == 1)
						&& (yNew-yCurrent == -2 && xNew-xCurrent == -2)) {
					this.kill(xCurrent-1, yCurrent - 1);
					return true;
				}
			}
			//Handles Black King
			else {
				//If move is diagonally in front
				if (Math.abs(yNew-yCurrent) == 1 && Math.abs(xNew - xCurrent) == 1) {
					return true;
				}
				//If an enemy is diagonally in front and the piece are attempting a jump
				if (xCurrent + 1 < 8 && yCurrent + 1 < 8 && (this.board[yCurrent + 1][xCurrent + 1] != null &&
						this.board[yCurrent + 1][xCurrent + 1].getColor() == 0 )
						&& (yNew-yCurrent == 2 && xNew-xCurrent == 2)) {
					this.kill(xCurrent + 1, yCurrent + 1);
					return true;
				}
				//If an enemy is diagonally in front and the piece are attempting a jump
				if (xCurrent - 1 > -1 && yCurrent + 1 < 8 && (this.board[yCurrent + 1][xCurrent - 1] != null
						&& this.board[yCurrent + 1][xCurrent - 1].getColor() == 0)
						&& (yNew-yCurrent == 2 && xNew-xCurrent == -2)) {
					this.kill(xCurrent - 1, yCurrent + 1);
					return true;
				}
				//If an enemy is diagonally in front and the piece are attempting a jump
				if (xCurrent + 1 < 8 && yCurrent - 1 > -1 && (this.board[yCurrent - 1][xCurrent + 1] != null
						&& this.board[yCurrent - 1][xCurrent + 1].getColor() == 0) 
						&& (yNew-yCurrent == -2 && xNew-xCurrent == 2)) {
					this.kill(xCurrent + 1, yCurrent - 1);
					return true;
				}
				//If an enemy is diagonally in front and the piece are attempting a jump
				if (xCurrent - 1 > -1 && yCurrent - 1 > -1 && (this.board[yCurrent - 1][xCurrent - 1] != null
						&& this.board[yCurrent - 1][xCurrent - 1].getColor() == 0)
						&& (yNew-yCurrent == -2 && xNew-xCurrent == -2)) {
					this.kill(xCurrent-1, yCurrent - 1);
					return true;
				}
			}
		}
		//Default return if the move is not valid (approved
		//by one of the prior conditionals)
		return false;
	}
	
	/**
	 * "Moves a piece" from one location to another by
	 * making a deep copy, putting that in the new location
	 * and then making the old locaion null
	 * @param xP x-coordinate of piece
	 * @param yP y-coordinate of piece
	 * @param xN x-coordinate that it is moving to
	 * @param yN y-coordinate that it is moving to
	 */
	public void move(int xP, int yP, int xN, int yN) {
		Piece p = Piece.makeCopy(this.board[yP][xP]);
		this.board[yN][xN] = p;
		this.board[yP][xP] = null;
	}
	
	/**
	 * Removes a piece if it is jumped by an enemy piece.
	 * @param x x-coordinate of executed piece
	 * @param y y-coordinate of executed piece
	 */
	public void kill(int x, int y) {
		this.board[y][x] = null;
	}
	
	/**
	 * Determines if a piece can be kinged
	 * @param y y-coordinate piece is moving to
	 * @param color color of piece- need in determination
	 * @return boolean telling if the piece can king or not
	 */
	public boolean canKing(int y, int color) {
		if (color == 0 && y == 7
				|| color == 1 && y == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * "Kings" the piece
	 * @param x x-coordinate of piece to king
	 * @param y y-coordinate of piece to king
	 */
	public void king(int x, int y) {
		this.board[y][x].king();
	}

}
