
public class Player {
	/**
	 * The name of the player
	 */
	private String name;
	/**
	 * A "board" with only the respective player's pieces
	 * on it. This helps in the intialization of the game,
	 * as well as with checking if a piece selection is valid
	 * and keeping track of kings.
	 */
	private Piece[][] pieces = new Piece[8][8];
	/**
	 * Tells whether player is red (0) or black (1)
	 */
	private int color;
	
	/**
	 * Initializes the player object.
	 * @param name String name
	 */
	public Player(String name, int color) {
		this.name = name;
		this.color = color;
		this.fillSide();
		
	}
	
	/**
	 * Gets name of player object
	 * @return string name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns color of player
	 * @return integer color
	 */
	public int getColor() {
		return color;
	}
	
	/**
	 * Gets the piece at a specific point for this player
	 * (Only used in copyPlayer method)
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return Piece class piece
	 */
	public Piece getPiece(int x, int y) {
		return this.pieces[x][y];
	}
	
	/**
	 * Fills up the side of the Player's board depending
	 * on their color. Only used at initialization of game.
	 */
	private void fillSide() {
		if (color == 0) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 8; j++) {
					if ((i == 0 || i == 2) && ((double)(j))%(2.0) == 1.0) {
						pieces[i][j] = new Piece(0);
					} else if (i == 1 && j%2 == 0) {
						pieces[i][j] = new Piece(0);
					}
					
				}
			}
		} else {
			for (int i = 7; i > 4; i--) {
				for (int j = 0; j < 8; j++) {
					if ((i == 7 || i == 5) && j%2 == 0)
					{
						pieces[i][j] = new Piece(1);
					} else if (i == 6 && j%2 == 1) {
						pieces[i][j] = new Piece(1);
					}
					
				}
			}
		}
	}
	
	/**
	 * Determines if a given location has a piece,
	 * and if so whether it belons to the Player
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return if the location has a piece belonging to the player
	 */
	public boolean isMyPiece(int x, int y) {
		if (x < 0 || x > 7
				|| y < 0 || y > 7) {
			return false;
		}
		if (pieces[y][x] == null) {

			return false;
		} 
		return true;
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
		pieces[yN][xN] = Piece.makeCopy(pieces[yP][xP]);
		pieces[yP][xP] = null;
	}
	
	/**
	 * Decided whether or not this player has any pieces
	 * left on the board - Utilized in win condition
	 * @return boolean telling if there are no pieces left
	 */
	public boolean noPieces() {
		for (int i = 0; i < this.pieces[0].length; i++) {
			for (int j = 0; j < this.pieces.length; j++) {
				if (this.pieces[i][j] != null) {
					return false;
				}
			}
		}
		return true;
	}	
	
	/**
	 * Kings a piece
	 * @param x x-coordinate of the king to be
	 * @param y y-coordinate of the king to be
	 */
	public void king(int x, int y) {
		pieces[y][x].king();
		System.out.println(this.getName() + " has recieved a King!");
	}
	


}
