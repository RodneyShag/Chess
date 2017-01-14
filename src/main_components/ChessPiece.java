package main_components;

import java.awt.Point;
import javax.swing.ImageIcon;

import piece_properties.*;

/**
 * \brief
 * A representation of the standard 6 ChessPieces, plus 2 new ChessPieces
 * @author Rodney Shaghoulian
 *
 */
public class ChessPiece {
	/* Data */
	public Type type;			///< An enumerated type. Each piece has a type "Pawn, King, etc."
	public Color color;			///< An enumerated type. "White, Black, None"
	public Point position;		///< The Euclidean (x,y) position of a piece
	public Moves moves;			///< The Attack and Valid Tiles for the ChessPiece on a ChessBoard
	public ImageIcon image;		///< Lets us create images from PNGs
	
	/**
	 * Constructor. Also allocates new Moves object
	 * @param pieceType	PAWN, KING, etc.
	 * @param col The Color of the ChessPiece
	 * @param pos The Position of the ChessPiece
	 */
	public ChessPiece(Type pieceType, Color col, Point pos) {
		type = pieceType;
		position = pos;
		color = col;
		moves = new Moves(); // will then need to update board to get attacking and valid moves
		initializeImage();
	}
	
	/**
	 * Copy Constructor - Deep Copy
	 * @param otherPiece - The other ChessPiece to create a deep copy of
	 */
	public ChessPiece(ChessPiece otherPiece) {
		type     = otherPiece.type;
		color    = otherPiece.color;
		position = new Point(otherPiece.position);	//DEEP COPY
		moves    = new Moves(); // will then need to update board to get attacking and valid moves
		image = null;	// no need to copy image data in our implementation.
	}
	
	/**
	 * Calculates Moves given a ChessBoard
	 * @param board The ChessBoard we are calculating Moves on
	 */
	public void setMoves(ChessBoard board){
		moves.setAttackTiles(board, this);
		moves.setValidTiles(board, this);
	}
	
	/**
	 * Initializes an image for current ChessPiece
	 */
	public void initializeImage(){
		if (color == Color.BLACK){
			if (type == Type.QUEEN || type == Type.SHAPESHIFTER || type == Type.DECOY)
				image = new ImageIcon("images/75px_black_queen.png");
			else if (type == Type.PAWN)
				image = new ImageIcon("images/75px_black_pawn.png");
			else if (type == Type.ROOK)
				image = new ImageIcon("images/75px_black_rook.png");
			else if (type == Type.KNIGHT)
				image = new ImageIcon("images/75px_black_knight.png");
			else if (type == Type.BISHOP)
				image = new ImageIcon("images/75px_black_bishop.png");
			else if (type == Type.KING)
				image = new ImageIcon("images/75px_black_king.png");
			else if (type == Type.HORIZON)
				image = new ImageIcon("images/75px_black_horizon.png");
			else if (type == Type.CENTAUR)
				image = new ImageIcon("images/75px_black_centaur.png");
		}
		else if (color == Color.WHITE){
			if (type == Type.QUEEN || type == Type.SHAPESHIFTER || type == Type.DECOY)
				image = new ImageIcon("images/75px_white_queen.png");
			else if (type == Type.PAWN)
				image = new ImageIcon("images/75px_white_pawn.png");
			else if (type == Type.ROOK)
				image = new ImageIcon("images/75px_white_rook.png");
			else if (type == Type.KNIGHT)
				image = new ImageIcon("images/75px_white_knight.png");
			else if (type == Type.BISHOP)
				image = new ImageIcon("images/75px_white_bishop.png");
			else if (type == Type.KING)
				image = new ImageIcon("images/75px_white_king.png");
			else if (type == Type.HORIZON)
				image = new ImageIcon("images/75px_white_horizon.png");
			else if (type == Type.CENTAUR)
				image = new ImageIcon("images/75px_white_centaur.png");
		}
	}
	
	/**
	 * Randomly changes the icon of a piece. Color may change also.
	 */
	public void changeIconRandomly(){
			int rand = (int) Math.random() * 8;
			if (rand == 0)
				image = new ImageIcon("images/75px_White_Pawn.png");
			else if (rand == 1)
				image = new ImageIcon("images/75px_White_Rook.png");
			else if (rand == 2)
				image = new ImageIcon("images/75px_White_Knight.png");
			else if (rand == 3)
				image = new ImageIcon("images/75px_White_Bishop.png");
			else if (rand == 4)
				image = new ImageIcon("images/75px_Black_Pawn.png");
			else if (rand == 5)
				image = new ImageIcon("images/75px_Black_Rook.png");
			else if (rand == 6)
				image = new ImageIcon("images/75px_Black_Knight.png");
			else if (rand == 7)
				image = new ImageIcon("images/75px_Black_Bishop.png");
	}
	
	/**
	 * Randomly changes a piece to another to either a ROOK, KNIGHT, BISHOP, or PAWN. Color will not change.
	 */
	public void changeTypeRandomly(){
		int rand = (int) (Math.random() * 4);
		if (rand == 0)
			type = Type.ROOK;
		else if (rand == 1)
			type = Type.PAWN;
		else if (rand == 2)
			type = Type.KNIGHT;
		else if (rand == 3)
			type = Type.BISHOP;
		initializeImage();
	}
}