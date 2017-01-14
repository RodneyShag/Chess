package main_components;

import java.awt.Point;

import main_components.ChessPiece;
import piece_properties.*;

/**
 * \brief
 * This is where the 2-player game of Chess will be played
 * @author Rodney Shaghoulian
 */
public class ChessBoard{
	/********/
	/* Data */
	/********/

	/* Essential Info */
	public final int rows;				///< Number of rows on ChessBoard
	public final int columns;			///< Number of columns on ChessBoard
	public ChessPiece[][] tile;			///< ChessBoard is a 2-dimensional array of ChessPieces
	
	/* Useful Info */
	public ChessPiece whiteKing; 		///< This gives us constant-time access to the whiteKing
	public ChessPiece blackKing; 		///< This gives us constant-time access to the blackKing
	
	/* Statuses */
	public Color playerTurn;			///< Either WHITE's turn or BLACK's turn
	public boolean whiteInCheck;		///< True if WHITE's KING is under attack
	public boolean blackInCheck;		///< True if BLACK's KING is under attack
	public boolean gameEnded;			///< True if game is over
	public boolean stalemate;			///< True if current player has no legal moves, and is not under check

	public Color winner;				///< Either Color.BLACK or Color.WHITE when not null
	public boolean classicMode;			///< true for classicMode. false for customMode.
	
	/****************/
	/* Constructors */
	/****************/
	
	/**
	 * Constructor - Initializes a ChessBoard
	 * @param numRows 		Number of rows on ChessBoard
	 * @param numColumns 	Number of columns on ChessBoard
	 * @param classicMode	True or False. True = Classic Mode. False = Custom Mode
	 */
	public ChessBoard(int numRows, int numColumns, boolean classicMode) {
		/* Create Essential Info */
		this.classicMode = classicMode;
		rows = numRows;
		columns = numColumns;
		tile = new ChessPiece[rows][columns];
		create32Pieces();
		
		/* Get access to 2 Kings */
		whiteKing = tile[0][4];
		blackKing = tile[7][4];
		
		/* Create Statuses */
		playerTurn = Color.WHITE;
		whiteInCheck = false;
		blackInCheck = false;
		gameEnded = false;
		stalemate = false;
		
		winner = null;
		classicMode = true;

		updateAttackTiles();   
		updateValidTiles(); 
	}

	/**
	 * Copy Constructor - Important: Will then need to call updateValidTiles() manually (which calls this function), to avoid infinite loop \n
	 * We use this to create another board to try moves on
	 * @param otherBoard The ChessBoard to create a copy of
	 */
	public ChessBoard(ChessBoard otherBoard) {
		/* WANT TO DO A DEEP COPY */
		
		/* Create Essential Info, and get access to 2 Kings */
		rows    = otherBoard.rows;
		columns = otherBoard.columns;
		tile = new ChessPiece[rows][columns];	// DEEP COPY
		for (int row = 0; row < rows; row++){
			for (int column = 0; column < columns; column++){
				ChessPiece otherPiece = otherBoard.tile[row][column];	// Grab a reference to current ChessPiece
				if(otherPiece == null)
					tile[row][column] = null;
				else{
					tile[row][column] = new ChessPiece(otherPiece);
					if (otherPiece.type == Type.KING){
						if (otherPiece.color == Color.WHITE)
							whiteKing = tile[row][column];
						else
							blackKing = tile[row][column];
					}
				}
			}
		}
	
		/* Copy Statuses */
		playerTurn   = otherBoard.playerTurn;
		whiteInCheck = otherBoard.whiteInCheck;
		blackInCheck = otherBoard.blackInCheck;
		gameEnded    = otherBoard.gameEnded;
		stalemate    = otherBoard.stalemate;
		
		winner		 = otherBoard.winner;
		classicMode  = otherBoard.classicMode;
		
		updateAttackTiles();
	}
	
	/**
	 * Determines if a Position exists on a ChessBoard
	 * @param pos	The Position we are checking x and y coordinates for
	 * @return		true/false depending on whether Position is valid on ChessBoard
	 */
	public boolean validPosition(Point pos){
		return (pos.x >= 0 && pos.y >= 0 && pos.x < columns && pos.y < rows);
	}
	
	/**
	 * Determines if any ChessPiece exists at a given Position. \n
	 * Assumes the Position is valid.
	 * @param pos	The Position we are checking the ChessBoard for a ChessPiece
	 * @return		"true" if any ChessPiece exists at the given Position. "false" otherwise.
	 */
	public boolean pieceExists(Point pos){
		if (tile[pos.y][pos.x] == null)
			return false;
		else
			return true;
	}
	
	
	/**
	 * Assumes piece exists on tile.
	 * @param pos	The Position of the ChessBoard that has a ChessPiece
	 * @return		COLOR of ChessPiece on tile.
	 */
	public Color pieceColor(Point pos){
		return tile[pos.y][pos.x].color;
	}

	/**
	 * Moves Piece. Only updates Positions, and saves captured ChessPieces.
	 * @param piece			The ChessPiece we are moving
	 * @param endPoint	The destination Position we want to move the ChessPiece to.
	 */
	public void movePiece(ChessPiece piece, Point endPoint){
		
		/* Move Piece On Board */
		tile[endPoint.y][endPoint.x] = tile[piece.position.y][piece.position.x]; // Clever Trick: Garbage Collector Captures Pieces 
		tile[piece.position.y][piece.position.x] = null;
		
		/* Update Piece's Position */
		piece.position = endPoint;
		
		/* SHAPESHIFTER and DECOY pieces change after they move */
		if (piece.type == Type.DECOY)
			piece.changeIconRandomly();
		else if (piece.type == Type.SHAPESHIFTER)
			piece.changeTypeRandomly();
	}

	/**
	 * Simple helper function that calls updateWhiteInCheck() and updateBlackInCheck()
	 */
	public void updateInCheck(){
		updateWhiteInCheck();
		updateBlackInCheck();
	}
	
	/**
	 * Toggles playerTurn from WHITE to BLACK, and vice versa
	 */
	public void updateTurn(){
		if (playerTurn == Color.WHITE)
			playerTurn = Color.BLACK;
		else
			playerTurn = Color.WHITE;
	}

	/**
	 * Determines if WHITE is in check. Updates boolean variable whiteInCheck
	 */
	public void updateWhiteInCheck(){
		/* Loop through board */
		for (int row = 0; row < rows; row++){
			for (int column = 0; column < columns; column++){
				/* If any piece is threatening white's king, player is in check */
				ChessPiece otherPiece = tile[row][column];
				if (otherPiece != null && otherPiece.color == Color.BLACK){
					for (Point attackingPosition : otherPiece.moves.attackTiles){ 
						if (attackingPosition.equals(whiteKing.position)){	//can't use " == " here since it compares addresses
							whiteInCheck = true;
							return;
						}
					}
				}
			}
		}
		whiteInCheck = false;		
	}
	
	/**
	 * Determines if BLACK is in check. Updates boolean variable blackInCheck
	 */
	public void updateBlackInCheck(){
		ChessPiece otherPiece;
		/* Loop through board */
		for (int row = 0; row < rows; row++){
			for (int column = 0; column < columns; column++){
				/* If any piece is threatening black's king, player is in check */
				otherPiece = tile[row][column];
				if (otherPiece != null && otherPiece.color == Color.WHITE){
					for (Point attackingPosition : otherPiece.moves.attackTiles){
						if (attackingPosition.equals(blackKing.position)){	//can't use " == " here since it compares addresses
							blackInCheck = true;
							return;
						}
					}
				}
			}
		}
		blackInCheck = false;		
	}

	/**
	 * Determines if Checkmate or Stalemate \n
	 * Updates variables: gameEnded, stalemate
	 */
	public void updateGameStatus(){
		if (playerTurn == Color.WHITE){
			if (whiteHasNoMoves()){
				gameEnded = true;
				if (whiteInCheck)
					winner = Color.BLACK;
				else
					stalemate = true;	
			}
		}
		else{
			if (blackHasNoMoves()){
				gameEnded = true;
				if (blackInCheck)
					winner = Color.WHITE;
				else
					stalemate = true;
			}
		}
	}
	
	/**
	 * Determines if WHITE is out of moves \n
	 * @return "true" if WHITE has no moves, "false" otherwise.
	 */
	public boolean whiteHasNoMoves(){
		/* Loop through board */
		for (int row = 0; row < rows; row++){
			for (int column = 0; column < columns; column++){
				ChessPiece possiblePiece = tile[row][column];
				/* If there EXISTS a WHITE piece with a VALID MOVE */
				if (possiblePiece != null && possiblePiece.color == Color.WHITE && possiblePiece.moves.validTiles.size() > 0)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Determines if BLACK is out of moves \n
	 * @return "true" if BLACK has no moves, "false" otherwise.
	 */
	public boolean blackHasNoMoves(){
		/* Loop through board */
		for (int row = 0; row < rows; row++){
			for (int column = 0; column < columns; column++){
				ChessPiece possiblePiece = tile[row][column];
				/* If there exists a BLACK piece with a valid move */
				if (possiblePiece != null && possiblePiece.color == Color.BLACK && possiblePiece.moves.validTiles.size() > 0)
					return false;
			}
		}
		return true;
	}	
	
	/**
	 * Updates "Attack Tiles" for every ChessPiece on ChessBoard \n
	 * Note: Usually called after movePiece()
	 */
	public void updateAttackTiles(){
		for (int row = 0; row < rows; row++){
			for (int column = 0; column < columns; column++){
				ChessPiece currentPiece = tile[row][column];
				if (currentPiece != null)
					currentPiece.moves.setAttackTiles(this, currentPiece);
			}
		}
	}
	
	/**
	 * Updates "Attack Tiles" for every ChessPiece on ChessBoard of a certain Color \n
	 * Note: Usually called after movePiece()
	 */
	public void updateAttackTilesOneColor(Color color){
		for (int row = 0; row < rows; row++){
			for (int column = 0; column < columns; column++){
				ChessPiece currentPiece = tile[row][column];
				if (currentPiece != null && currentPiece.color == color)
					currentPiece.moves.setAttackTiles(this, currentPiece);
			}
		}
	}
	
	/**
	 * Updates "Valid Tiles" for every ChessPiece on ChessBoard \n
	 * Note: Usually called sometime after updateTurn()
	 */
	public void updateValidTiles(){
		for (int row = 0; row < rows; row++){
			for (int column = 0; column < columns; column++){
				ChessPiece currentPiece = tile[row][column];
				if (currentPiece != null)
					currentPiece.moves.setValidTiles(this, currentPiece);
			}
		}
	}
	
	/** 
	 * Returns the ChessPiece at desired Point
	 * @param p		Point of desired ChessPiece
	 * @return		ChessPiece corresponding to Point
	 */
	public ChessPiece getPiece(Point point){
		return tile[point.y][point.x];
	}
	
	/** 
	 * Updates ChessBoard. Should be called after movePiece()
	 */
	public void updateChessBoard(){
		 /* The order these are updated is crucial */
		 updateAttackTiles();
		 updateInCheck();			//Updates: whiteInCheck blackInCheck.		Depends On: updateAttackTiles()
		 updateTurn();				//Updates: playerTurn.
		 updateValidTiles();
		 updateGameStatus();		//Updates: gameEnded, staleMate.			Depends On: updateTurn(), updateValidTiles() 
	}
	
	/**
	 *  Private Helper function to initialize 32 ChessPieces
	 */
	private void create32Pieces(){

		/* Create 16 White Pieces */
		createBackRow(0, Color.WHITE);
		createPawnRow(1, Color.WHITE);
		
		/* Create 16 Black Pieces */
		createPawnRow(6, Color.BLACK);
		createBackRow(7, Color.BLACK);
		
		/* Initialize middle 4 rows to null (no pieces) */
		for (int tempRow = 2; tempRow <= 5; tempRow++)
			for (int tempCol = 0; tempCol < columns; tempCol++)
				tile[tempRow][tempCol] = null;
	}

	/** Private Helper function to create standard row of non-pawn pieces on row 0, 7
	 * @param row		Row to create ChessPieces on
	 * @param color		Color of ChessPieces
	 */
	private void createBackRow(int row, Color color){
		if (classicMode){
			tile[row][0] = new ChessPiece(Type.ROOK  , color, new Point(0, row));
			tile[row][1] = new ChessPiece(Type.KNIGHT, color, new Point(1, row));
		}
		else{
			tile[row][0] = new ChessPiece(Type.HORIZON, color, new Point(0, row));
			tile[row][1] = new ChessPiece(Type.CENTAUR, color, new Point(1, row));
		}
		tile[row][2] = new ChessPiece(Type.BISHOP, color, new Point(2, row));
		tile[row][3] = new ChessPiece(Type.QUEEN , color, new Point(3, row));
		tile[row][4] = new ChessPiece(Type.KING  , color, new Point(4, row));
		tile[row][5] = new ChessPiece(Type.BISHOP, color, new Point(5, row));
		if (classicMode){
			tile[row][6] = new ChessPiece(Type.KNIGHT, color, new Point(6, row));
			tile[row][7] = new ChessPiece(Type.ROOK  , color, new Point(7, row));
		}
		else{
			tile[row][6] = new ChessPiece(Type.CENTAUR, color, new Point(6, row));
			tile[row][7] = new ChessPiece(Type.HORIZON, color, new Point(7, row));
		}
	}
	
	/** Private Helper Function to create a row of pawns on rows 1 and 6
	 * @param row		Row to create Pawns on
	 * @param color		Color of Pawns
	 */
	private void createPawnRow(int row, Color color){
		for (int col = 0; col < columns; col++)
			tile[row][col] = new ChessPiece(Type.PAWN, color, new Point(col, row));
	}
}

