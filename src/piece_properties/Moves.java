package piece_properties;

import java.awt.Point;
import java.util.Vector;

import main_components.*;

/**
 * \brief
 * Moves are meant to be part of each ChessPiece
 * @author Rodney Shaghoulian
 *
 */
public class Moves {
	/* Data */
	public Vector<Point> attackTiles; ///< Tiles that can be attacked, regardless of "discover check" on self.
	public Vector<Point> validTiles;  ///< Tiles that can be moved to that don't result in "discover check" on self.

	/** Constructor: Standard Constructor that initializes 2 new position vectors */
	public Moves(){
		attackTiles = new Vector<Point>();
		validTiles  = new Vector<Point>();
	}

	/**
	 * Update "Attack Tiles" for a single ChessPiece
	 * @param board Chessboard game is being played on
	 * @param piece The ChessPiece we want to update "Attack Tiles" for
	 */
	public void setAttackTiles(ChessBoard board, ChessPiece piece){
		piece.moves.attackTiles.clear();
		
		/* Find and set all attack moves */
		if(piece.type == Type.ROOK || piece.type == Type.QUEEN || piece.type == Type.DECOY || piece.type == Type.SHAPESHIFTER){
			attacksLeft(board, piece);
			attacksRight(board, piece);
			attacksDown(board, piece);
			attacksUp(board, piece);
		}
		if(piece.type == Type.BISHOP || piece.type == Type.QUEEN || piece.type == Type.DECOY || piece.type == Type.SHAPESHIFTER){
			attacksNW(board, piece); 	//NW = NorthWest
			attacksNE(board, piece); 	//NE = NorthEast
			attacksSW(board, piece);    //SW = SouthWest
			attacksSE(board, piece);    //SE = SouthEast
		}
		if (piece.type == Type.PAWN)
  			attacksPawn(board, piece);
		if (piece.type == Type.KNIGHT)
  			attacksKnight(board, piece);
		if (piece.type == Type.KING)
  			attacksKing(board, piece);	
		if (piece.type == Type.CENTAUR)
			attacksCentaur(board, piece);
		if (piece.type == Type.HORIZON){
			attacksLeft(board, piece);
			attacksRight(board, piece);
		}
	}

	/**
	 * Updates "Valid Tiles" for a single ChessPiece
	 * @param board Chessboard game is being played on
	 * @param piece The ChessPiece we want to update "Valid Tiles" for
	 */
	public void setValidTiles(ChessBoard board, ChessPiece piece){
		piece.moves.validTiles.clear();
		
		if (piece.type == Type.PAWN)
			setValidTilesForPawns(board, piece);
		else
			setValidTilesForMostPieces(board, piece, piece.moves.attackTiles);
	}
	
	/**********************************/
	/* Functions to find attack tiles */
	/**********************************/

	/**
	 * Updates Attacks for ROOK, QUEEN, HORIZON
	 * @param board ChessBoard game is being played on
	 * @param piece The ChessPiece we want to update "Left Attacks" for
	 */
	public void attacksLeft(ChessBoard board, ChessPiece piece){
		Color color = Color.NONE;
		for (int offset = -1;  color == Color.NONE  ; offset--)
			color = possibleAttackSquare(board, piece, offset, 0);
	}
	
	/**
	 * Updates Attacks for ROOK, QUEEN, HORIZON
	 * @param board ChessBoard game is being played on
	 * @param piece The ChessPiece we want to update "Right Attacks" for
	 */
	public void attacksRight(ChessBoard board, ChessPiece piece){
		Color color = Color.NONE;
		for (int offset = 1;  color == Color.NONE  ; offset++)
			color = possibleAttackSquare(board, piece, offset, 0);
	}
	
	/**
	 * Updates Attacks for ROOK, QUEEN
	 * @param board ChessBoard game is being played on
	 * @param piece The ChessPiece we want to update "Down Attacks" for
	 */
	public void attacksDown(ChessBoard board, ChessPiece piece){
		Color color = Color.NONE;
		for (int offset = -1;  color == Color.NONE  ; offset--)
			color = possibleAttackSquare(board, piece, 0, offset);
	}
	
	/**
	 * Updates Attacks for ROOK, QUEEN
	 * @param board ChessBoard game is being played on
	 * @param piece The ChessPiece we want to update "Up Attacks" for
	 */
	public void attacksUp(ChessBoard board, ChessPiece piece){
		Color color = Color.NONE;
		for (int offset = 1;  color == Color.NONE  ; offset++)
			color = possibleAttackSquare(board, piece, 0, offset);
	}
	
	/**
	 * Updates Attacks for BISHOP, QUEEN
	 * @param board ChessBoard game is being played on
	 * @param piece The ChessPiece we want to update "NW Attacks" for
	 */
	public void attacksNW(ChessBoard board, ChessPiece piece){
		int x_offset = -1;
		int y_offset =  1;
		Color color = Color.NONE;
		while(color == Color.NONE){
			color = possibleAttackSquare(board, piece, x_offset, y_offset);
			x_offset--;
			y_offset++;
		}
	}
	
	/**
	 * Updates Attacks for BISHOP, QUEEN
	 * @param board ChessBoard game is being played on
	 * @param piece The ChessPiece we want to update "NE Attacks" for
	 */
	public void attacksNE(ChessBoard board, ChessPiece piece){
		int x_offset =  1;
		int y_offset =  1;
		Color color = Color.NONE;
		while(color == Color.NONE){
			color = possibleAttackSquare(board, piece, x_offset, y_offset);
			x_offset++;
			y_offset++;
		}
	}

	/**
	 * Updates Attacks for BISHOP, QUEEN
	 * @param board ChessBoard game is being played on
	 * @param piece The ChessPiece we want to update "SW Attacks" for
	 */
	public void attacksSW(ChessBoard board, ChessPiece piece){
		int x_offset = -1;
		int y_offset = -1;
		Color color = Color.NONE;
		while(color == Color.NONE){
			color = possibleAttackSquare(board, piece, x_offset, y_offset);
			x_offset--;
			y_offset--;
		}
	}

	/**
	 * Updates Attacks for BISHOP, QUEEN
	 * @param board ChessBoard game is being played on
	 * @param piece The ChessPiece we want to update "SE Attacks" for
	 */
	public void attacksSE(ChessBoard board, ChessPiece piece){
		int x_offset =  1;
		int y_offset = -1;
		Color color = Color.NONE;
		while(color == Color.NONE){
			color = possibleAttackSquare(board, piece, x_offset, y_offset);
			x_offset++;
			y_offset--;
		}
	}
	
	/**
	 * Updates (diagonal) attacks for PAWN.
	 * @param board ChessBoard game is being played on
	 * @param piece The PAWN we want to update attacks for
	 */
	public void attacksPawn(ChessBoard board, ChessPiece piece){
		if (piece.color == Color.WHITE){
			possibleAttackSquare(board, piece, -1,  1);  //NW attack
			possibleAttackSquare(board, piece,  1,  1);  //NE attack
		}
		else{ //color == BLACK
			possibleAttackSquare(board, piece, -1, -1);  //SW attack
			possibleAttackSquare(board, piece,  1, -1);	 //SE attack	
		}
	}

	/**
	 * Updates (jump) attacks for KNIGHT.
	 * @param board ChessBoard game is being played on
	 * @param piece The KNIGHT we want to update attacks for
	 */
	public void attacksKnight(ChessBoard board, ChessPiece piece){
		possibleAttackSquare(board, piece, -1, -2);
		possibleAttackSquare(board, piece, -1,  2);
		possibleAttackSquare(board, piece,  1, -2);
		possibleAttackSquare(board, piece,  1,  2);
		possibleAttackSquare(board, piece, -2, -1);
		possibleAttackSquare(board, piece, -2,  1);
		possibleAttackSquare(board, piece,  2, -1);
		possibleAttackSquare(board, piece,  2,  1);
	}

	/**
	 * Updates (1-step) attacks for KING.
	 * @param board ChessBoard game is being played on
	 * @param piece The KING we want to update attacks for
	 */
	public void attacksKing(ChessBoard board, ChessPiece piece){
		possibleAttackSquare(board, piece, -1, -1);
		possibleAttackSquare(board, piece, -1,  0);
		possibleAttackSquare(board, piece, -1,  1);
		possibleAttackSquare(board, piece,  0,  1);
		possibleAttackSquare(board, piece,  1,  1);
		possibleAttackSquare(board, piece,  1,  0);
		possibleAttackSquare(board, piece,  1, -1);
		possibleAttackSquare(board, piece,  0, -1);
	}

	/**
	 * Updates (jump) attacks for CENTAUR.
	 * @param board ChessBoard game is being played on
	 * @param piece The CENTAUR we want to update attacks for
	 */
	public void attacksCentaur(ChessBoard board, ChessPiece piece){
		possibleAttackSquare(board, piece, -1, -3);
		possibleAttackSquare(board, piece, -1,  3);
		possibleAttackSquare(board, piece,  1, -3);
		possibleAttackSquare(board, piece,  1,  3);
		possibleAttackSquare(board, piece, -3, -1);
		possibleAttackSquare(board, piece, -3,  1);
		possibleAttackSquare(board, piece,  3, -1);
		possibleAttackSquare(board, piece,  3,  1);
	}
	
	/**
	 * Determines if a tile is a possible attack square based on the "Color" of the piece that is on the square.
	 * @param board ChessBoard game is being played on
	 * @param attackingPiece the ChessPiece that we will calculate attack squares for
	 * @param x_offset the "x" offset from the current ChessPiece's location that we want to attack
	 * @param y_offset the "y" offset from the current ChessPiece's location that we want to attack
	 * @return Returns "Color" (WHITE, BLACK) of piece on square. (NULL for invalid squares, "NONE" for empty squares)
	 */
	private Color possibleAttackSquare(ChessBoard board, ChessPiece attackingPiece, int x_offset, int y_offset){
		Point testPos = new Point(attackingPiece.position.x + x_offset, attackingPiece.position.y + y_offset);
		
		if ( ! board.validPosition(testPos)) //Invalid Square: Return NULL
			return null;

		if ( ! board.pieceExists(testPos)){  //Empty Square: Add it as "AttackTile". Return NONE
			attackingPiece.moves.attackTiles.add(testPos);
			return Color.NONE;
		}
		if (board.pieceExists(testPos)){
			if(board.pieceColor(testPos) == attackingPiece.color)	//My own Piece: Return proper color
				return board.pieceColor(testPos);
			else{													///Opponent Piece: Add it as attackTile. Return proper color.
				attackingPiece.moves.attackTiles.add(testPos);
				return board.pieceColor(testPos);
			}
		}	
		return null; //should never execute
	}
	
	/**
	 * Returns valid squares for pawns - not accounting for discover check - assumes valid pawn offset input
	 * @param board ChessBoard game is being played on
	 * @param attackingPiece the ChessPiece that we will see if we can move
	 * @param x_offset the "x" offset from the current ChessPiece's location that we want to attack
	 * @param y_offset the "y" offset from the current ChessPiece's location that we want to attack
	 * @return Returns true or false, depending on whether the "attackingPiece" can move to the offset location
	 */
	private boolean possiblePawnSquare(ChessBoard board, ChessPiece attackingPiece, int x_offset, int y_offset){
		
		Point testPos = new Point(attackingPiece.position.x + x_offset, attackingPiece.position.y + y_offset);
		
		/* Invalid Square */
		if ( ! board.validPosition(testPos))
			return false;
		
		/* Diagonal Hit */
		if (x_offset != 0 && board.pieceExists(testPos) && board.pieceColor(testPos) != attackingPiece.color)
			return true;
		
		/* Move 1 square vertically */
		else if (x_offset == 0 && (y_offset == 1 || y_offset == -1) && ( ! board.pieceExists(testPos)))
			return true;
		
		/* Move White Pawn 2 Squares Up */
		else if (attackingPiece.color == Color.WHITE && attackingPiece.position.y == 1 && y_offset == 2){
			/* Check to see if a piece is in the way */
			Point testPos2 = new Point (attackingPiece.position.x + x_offset, attackingPiece.position.y + y_offset - 1);
			if(!board.pieceExists(testPos) && !board.pieceExists(testPos2))
				return true;
		}
		
		/* Move Black Pawn 2 Squares Down */
		else if (attackingPiece.color == Color.BLACK && attackingPiece.position.y == 6 && y_offset == -2){
			/* Check to see if a piece is in the way */
			Point testPos2 = new Point (attackingPiece.position.x + x_offset, attackingPiece.position.y + y_offset + 1);
			if(!board.pieceExists(testPos) && !board.pieceExists(testPos2))
				return true;
		}
		return false; //should never happen
	}
	
	/**********************************/
	/* Functions to find valid tiles */
	/**********************************/
	
	/**
	 * Finds valid tiles for a ChessPiece. For PAWNS, the user must call setValidTilesForPawns first.
	 * @param board ChessBoard game is being played on
	 * @param piece The ChessPiece we are calculating valid tiles for.
	 * @param possiblePositions simply a vector of "Positions" that are valid tiles
	 */
	private void setValidTilesForMostPieces(ChessBoard board, ChessPiece piece, Vector<Point> possiblePositions){	
		/* Tricky Logic: See Comments */
		
		/* Create a virtual world: a virtual ChessBoard */
		ChessBoard virtualBoard = new ChessBoard(board);		

		/* Grab the virtual ChessPiece we are trying to move */
		ChessPiece virtualPiece = virtualBoard.tile[piece.position.y][piece.position.x];
		
		/* Figure out colors */
		Color ourColor = virtualPiece.color;
		Color opponentColor;
		if (virtualPiece.color == Color.BLACK)
			opponentColor = Color.WHITE;
		else
			opponentColor = Color.BLACK;
			
		/* Save the ChessPiece's original position */
		Point originalPosition = new Point(virtualPiece.position);	// Must be DEEP COPY
		
		for (Point newPosition : possiblePositions)
		{
			/* Save any pieces on tile we are moving to in case they're captured */
			ChessPiece possibleCapturedPiece = virtualBoard.tile[newPosition.y][newPosition.x];
		
			virtualBoard.movePiece(virtualPiece, newPosition);
			virtualBoard.updateAttackTilesOneColor(opponentColor);   
			virtualBoard.updateInCheck();
			
			/* Test for valid moves - avoid "Discover Check" */
			 //if (Doesn't cause "Discover check" on own King)
			if ( ! (ourColor == Color.WHITE && virtualBoard.whiteInCheck || ourColor == Color.BLACK && virtualBoard.blackInCheck))
				piece.moves.validTiles.add(new Point(newPosition));	//add the moves to the ORIGINAL "piece"
			
			/* Undo Move */
			virtualBoard.movePiece(virtualPiece, originalPosition);
			virtualBoard.updateAttackTilesOneColor(opponentColor);   
			virtualBoard.updateInCheck();
			
			virtualBoard.tile[newPosition.y][newPosition.x] = possibleCapturedPiece; 												 
		}
	}
	
	/**
	 * Finds valid tiles for a PAWN
	 * @param board ChessBoard game is being played on
	 * @param piece The PAWN we are calculating valid tiles for.
	 */
	private void setValidTilesForPawns(ChessBoard board, ChessPiece piece){

		Vector<Point> pawnMoves = new Vector<Point>();
		pawnMoves.clear();
		
		/* Find Tiles Pawn can possibly move to (not accounting for discover check YET) */
		if (piece.color == Color.WHITE){
			if(possiblePawnSquare(board, piece,  -1,  1))
				pawnMoves.add(new Point(piece.position.x - 1, piece.position.y + 1));
			if(possiblePawnSquare(board, piece,   1,  1))
				pawnMoves.add(new Point(piece.position.x + 1, piece.position.y + 1));
			if(possiblePawnSquare(board, piece,   0,  1))
				pawnMoves.add(new Point(piece.position.x + 0, piece.position.y + 1));
			if(possiblePawnSquare(board, piece,   0,  2))
				pawnMoves.add(new Point(piece.position.x + 0, piece.position.y + 2));
		}
		else{ // Black Pawn
			if(possiblePawnSquare(board, piece,  -1,  -1))
				pawnMoves.add(new Point(piece.position.x - 1, piece.position.y - 1));
			if(possiblePawnSquare(board, piece,   1,  -1))
				pawnMoves.add(new Point(piece.position.x + 1, piece.position.y - 1));
			if(possiblePawnSquare(board, piece,   0,  -1))
				pawnMoves.add(new Point(piece.position.x + 0, piece.position.y - 1));
			if(possiblePawnSquare(board, piece,   0,  -2))
				pawnMoves.add(new Point(piece.position.x + 0, piece.position.y - 2));
		}
		
		/* This accounts for "Discover Checks" */
		setValidTilesForMostPieces(board, piece, pawnMoves);

	}
}