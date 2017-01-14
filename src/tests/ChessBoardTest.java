package tests;

import java.awt.Point;
import static org.junit.Assert.*;

import org.junit.Test;

import main_components.*;
import piece_properties.*;

/**
 * \brief
 * Tests the ChessBoard class thoroughly
 * @author Rodney Shaghoulian
 */
public class ChessBoardTest {

	/** Tests the standard constructor and verifies the initial configuration of the ChessBoard */
	@Test
	public void testChessBoard() {
		/* Set up data */
		ChessBoard testBoard = new ChessBoard(8, 8, true);

		/* Check ChessBoard setup in detail */
		verifyInitialConfiguration(testBoard);
	}

	/**
	 * Tests the copy constructor and verifies the initial configuration of the ChessBoard \n
	 * Ensures the deep copy results in the copied board not affecting another board.
	 * */
	@Test
	public void testChessBoardCopyConstructor() {
		/* Set up data */
		ChessBoard firstBoard = new ChessBoard(8, 8, true);
		ChessBoard virtualBoard = new ChessBoard(firstBoard);
		
		/* Make sure both board pass initial configuration Tests */
		verifyInitialConfiguration(firstBoard);
		verifyInitialConfiguration(virtualBoard);
		
		/* Move a piece --> Other board should remain unchanged */
		virtualBoard.movePiece(virtualBoard.whiteKing, new Point(4,4));
		verifyInitialConfiguration(firstBoard);
	}

	/** Moves a Piece and makes sure positions, checks, and playerTurn is updated correctly */
	@Test
	public void testMovePiece() {
		/* Set up data */
		ChessBoard board = new ChessBoard(8, 8, true);
		ChessPiece testPiece = board.tile[1][1];
		board.movePiece(testPiece, new Point(1,3));
		board.updateAttackTiles();   
		board.updateInCheck();
		board.updateTurn();
		
		/* Check ChessBoard setup in detail */
		assertEquals(testPiece.position.x, 1);
		assertEquals(testPiece.position.y, 3);
		assertEquals(board.tile[3][1].type, Type.PAWN); //this one is the important test
		assertEquals(board.playerTurn, Color.BLACK);
		assertEquals(board.whiteInCheck, false);
		assertEquals(board.blackInCheck, false);
		assertNull(board.tile[1][1]);
	}

	/** Puts WHITE's KING under attack and makes sure whiteInCheck is updated correctly */
	@Test
	public void testUpdateWhiteInCheck() {
		/***************/
		/* Set up data */
		/***************/
		ChessBoard board = new ChessBoard(8, 8, true);
		/* Move King to in front of black Pawn */
		board.movePiece(board.whiteKing, new Point(0, 5));
		board.updateAttackTiles();   
		board.updateInCheck();
		/* Test Board */
		assertEquals(board.whiteInCheck, true); //the important test

		/***************/
		/* Set up data */
		/***************/
		ChessBoard board2 = new ChessBoard(8, 8, true);
		/* Move blackRook to attack King */
		ChessPiece blackKnight = board2.tile[7][6];
		board2.movePiece(blackKnight, new Point(5, 2));
		board2.updateAttackTiles();   
		board2.updateInCheck();
		/* Test Board */
		assertTrue(blackKnight.moves.attackTiles.size() == 8);
		assertEquals(board2.whiteInCheck, true); //the important test
		
		/***************/
		/* Set up data */
		/***************/
		ChessBoard board3 = new ChessBoard(8, 8, true);
		/* Move blackRook to attack King */
		ChessPiece blackRook = board3.tile[7][7];
		board3.movePiece(blackRook, new Point(5, 0));
		board3.updateAttackTiles();   
		board3.updateInCheck();
		/* Test Board */
		assertTrue(blackRook.moves.attackTiles.size() == 3);
		assertEquals(board3.whiteInCheck, true); //the important test
	}
	
	/** Puts BLACK's KING under attack and makes sure whiteInCheck is updated correctly */
	@Test
	public void testUpdateBlackInCheck() {
		/* Set up data */
		ChessBoard board = new ChessBoard(8, 8, true);
		board.movePiece(board.blackKing, new Point(3, 2));
		board.updateAttackTiles();   
		board.updateInCheck();
		board.updateTurn();
		
		assertEquals(board.blackInCheck, true);
	}

	/** Makes sure the game doesn't prematurely end at beginning of game */
	@Test
	public void testUpdateGameStatus() {
		/* Set up data */
		ChessBoard board = new ChessBoard(8, 8, true);

		/* Make sure game didn't end */
		if ( ! board.whiteHasNoMoves())
			assertEquals(board.gameEnded, false);
	}

	/**
	 * Easy "false" test: Make sure WHITE does have moves at beginning of game
	 * Harder "true" test: Create a game scenario where WHITE has no moves left. Make sure function returns "true"
	 */
	@Test
	public void testWhiteHasNoMoves() {
		/* Set up data */
		ChessBoard board = new ChessBoard(8, 8, true);
		
		/* Tests that White Has Moves */
		assertEquals(board.whiteHasNoMoves(), false);
		
		/* Remove all pieces except WHITE KING */
		board.tile[0][0] = null;
		board.tile[0][1] = null;
		board.tile[0][2] = null;
		board.tile[0][3] = null;
		    /* skip king */
		board.tile[0][5] = null;
		board.tile[0][6] = null;
		board.tile[0][7] = null;
		board.tile[1][0] = null;
		board.tile[1][1] = null;
		board.tile[1][2] = null;
		board.tile[1][3] = null;
		board.tile[1][4] = null;
		board.tile[1][5] = null;
		board.tile[1][6] = null;
		board.tile[1][7] = null;	
		
		/* Surround White King with 5 pieces for checkmate */
		ChessPiece blackRook1 = board.tile[7][0];
		ChessPiece blackRook2 = board.tile[7][7];
		ChessPiece blackPawn1 = board.tile[6][0];
		ChessPiece blackPawn2 = board.tile[6][7];
		ChessPiece blackQueen = board.tile[7][3];
		board.movePiece(blackPawn1, new Point(3, 0));
		board.movePiece(blackPawn2, new Point(5, 0));
		board.movePiece(blackRook1, new Point(3, 1));
		board.movePiece(blackRook2, new Point(5, 1));
		board.movePiece(blackQueen, new Point(4, 1));

		board.updateAttackTiles();   
		board.updateInCheck();
		board.updateTurn();
		board.updateValidTiles();
		
		/* Test CheckMate ! */
		assertTrue(board.whiteInCheck);
		assertTrue(board.whiteHasNoMoves());
		board.playerTurn = Color.WHITE; // updateGameStatus() Logic Depends on who's turn it is
		board.updateGameStatus();
		assertTrue(board.gameEnded);
		assertFalse(board.stalemate);
	}
	
	/**
	 * Easy "false" test: Make sure BLACK does have moves at beginning of game
	 * Harder "true" test: Create a game scenario where BLACK has no moves left. Make sure function returns "true"
	 */
	@Test
	public void testBlackHasNoMoves() {
		/* Set up data */
		ChessBoard board = new ChessBoard(8, 8, true);
		
		/* Tests that Black Has Moves - it would be much harder to test the "true" case */
		assertEquals(board.blackHasNoMoves(), false);

		/* No need for more testing. It's similar in logic to "testWhiteHasNoMoves()" */
	}
	
	/*****************************/
	/* Helper Function for tests */
	/*****************************/
	
	/**
	 *  Helper function for verifying variables of ChessBoard Class. Used by Constructors \n
	 *  Verifies all 32 ChessPiece at startup, 32 null tiles at startup, and several members of ChessBoard class 
	 * */
	private void verifyInitialConfiguration(ChessBoard testBoard){
		/* Verify Simple Fields */
		assertEquals(testBoard.rows, 8);
		assertEquals(testBoard.columns, 8);
		assertEquals(testBoard.playerTurn, Color.WHITE);
		assertEquals(testBoard.whiteInCheck, false);
		assertEquals(testBoard.blackInCheck, false);
		assertEquals(testBoard.gameEnded, false);
		assertEquals(testBoard.stalemate, false);
		assertEquals(testBoard.whiteKing.type, Type.KING);
		assertEquals(testBoard.whiteKing.color, Color.WHITE);
		assertEquals(testBoard.whiteKing.position.x, 4);
		assertEquals(testBoard.whiteKing.position.y, 0);
		assertEquals(testBoard.blackKing.type, Type.KING);
		assertEquals(testBoard.blackKing.color, Color.BLACK);
		assertEquals(testBoard.blackKing.position.x, 4);
		assertEquals(testBoard.blackKing.position.y, 7);
		/*************************/
		/* Verify All 64 Squares */
		/*************************/
		
		/* Verify 1st 2 rows are white color, and correct positions */
		for (int row = 0; row < 2; row++){
			for (int column = 0; column < 8; column++){
				assertEquals(testBoard.tile[row][column].color, Color.WHITE);
				/* Makes sure the board and pieces agree on position */
				assertEquals(testBoard.tile[row][column].position.x, (new Point(column, row)).x);
				assertEquals(testBoard.tile[row][column].position.y, (new Point(column, row)).y);
			}
		}
		/* Verify last 2 rows are black color, and correct positions */
		for (int row = 6; row <= 7; row++){
			for (int column = 0; column < 8; column++){
				assertEquals(testBoard.tile[row][column].color, Color.BLACK);
				/* Makes sure the board and pieces agree on position */
				assertEquals(testBoard.tile[row][column].position.x, (new Point(column, row)).x);
				assertEquals(testBoard.tile[row][column].position.y, (new Point(column, row)).y);
			}	
		}
		/* Verify middle 4 rows are empty */
		for (int row = 2; row <= 5; row++){
			for (int column = 0; column < 8; column++){
				assertNull(testBoard.tile[row][column]);
			}	
		}
		/* Verify row 1 and row 6 are pawns */
		for (int column = 0; column < 8; column++){
			assertEquals(testBoard.tile[1][column].type, Type.PAWN);
			assertEquals(testBoard.tile[6][column].type, Type.PAWN);
		}
		/* Verify Remaining 16 Pieces */
		assertEquals(testBoard.tile[0][0].type, Type.ROOK);
		assertEquals(testBoard.tile[0][7].type, Type.ROOK);
		assertEquals(testBoard.tile[7][0].type, Type.ROOK);
		assertEquals(testBoard.tile[7][7].type, Type.ROOK);
		assertEquals(testBoard.tile[0][1].type, Type.KNIGHT);
		assertEquals(testBoard.tile[0][6].type, Type.KNIGHT);
		assertEquals(testBoard.tile[7][1].type, Type.KNIGHT);
		assertEquals(testBoard.tile[7][6].type, Type.KNIGHT);
		assertEquals(testBoard.tile[0][2].type, Type.BISHOP);
		assertEquals(testBoard.tile[0][5].type, Type.BISHOP);
		assertEquals(testBoard.tile[7][2].type, Type.BISHOP);
		assertEquals(testBoard.tile[7][5].type, Type.BISHOP);
		assertEquals(testBoard.tile[0][3].type, Type.QUEEN);
		assertEquals(testBoard.tile[7][3].type, Type.QUEEN);
		assertEquals(testBoard.tile[0][4].type, Type.KING);
		assertEquals(testBoard.tile[7][4].type, Type.KING);
		
	}
}
