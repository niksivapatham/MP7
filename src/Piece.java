/**
 * Abstract superclass from which all pieces inherit.
 * @author Nikhil
 *
 */
public class Piece {
	/**
	 * The color of the piece - 0 is red, 1 is black
	 */
	private int color;
	/**
	 * Type - black or white (To be use in board)
	 */
	private String type;
	/**
	 * Boolean that dictates whether or not this piece is a king
	 * Affects movement ability of piece.
	 */
	private boolean king;
	
	/**
	 * Constructor, initializes color and type 
	 * and makes it not king by default
	 * @param color
	 */
	public Piece(int color) {
		this.color = color;
		this.makeType();
		this.king = false;
		
	}
	
	/**
	 * I have no idea what this is and don't think 
	 * it's ever called but I'm not going to risk 
	 * deleting it.
	 * @return
	 */
	public boolean move() {
		return true;
	};
	
	/**
	 * Returns the color of the piece
	 * @return int representing color
	 */
	public int getColor() {
		return this.color;
	}
	
	/**
	 * Makes the string representation of the piece
	 * based on its color.
	 */
	private void makeType() {
		if (this.color == 0) {
			this.type = " R ";
		} else {
			this.type = " B ";
		}
	}
	
	/**
	 * Returns the type of the piece.
	 * @return String representing type.
	 */
	public String getType() {
		return this.type;
	}
	
	
	/**
	 * Kings the current piece, changing its
	 * String representation to three of the same
	 * character and changing the boolean representation
	 * to true.
	 */
	public void king() {
		this.king = true;
		String eh = this.type.trim();
		String newType = eh + eh + eh;
		this.type = newType;
	}

	/**
	 * Checks if the piece is a king 
	 * (Determines available moves).
	 * @return boolean telling if the piece is a king
	 */
	public boolean isKing() {
		return this.king;
	}
	
	/**
	 * Makes a deep copy of the piece passed in, 
	 * giving the new piece the same color and String type
	 * representation, and whether or not it is a king.
	 * @param p piece to be copied
	 * @return the piece copy
	 */
	public static Piece makeCopy(Piece p) {
		int color = p.getColor();
		Piece piece = new Piece(color);
		if (p.isKing()) {
			piece.king();
		}
		return piece;
	}
	
}
