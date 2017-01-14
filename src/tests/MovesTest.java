package tests;

import java.awt.Point;
import static org.junit.Assert.*;

import org.junit.Test;

import main_components.*;
import piece_properties.*;

/**
 * \brief
 * Tests the fundamental 12 different types of moves as defined by each function \n
 * Also tests "Attack Tiles" and "Valid Tiles"
 * @author Rodney Shaghoulian
 */
public class MovesTest {

	/** Tests moves() in initial board configuration */
	@Test
	public void testMoves() {
		/* Set up data */
		Moves testMoves = new Moves();
		
		assertEquals(testMoves.attackTiles.isEmpty(), true);
		assertEquals(testMoves.validTiles.isEmpty(), true);
	}

	/**
	 *  Tests setAttackTiles() for all 32 ChessPieces at initial board configuration \n
	 *  Then moves a ChessPiece, and tests the ChessPieces again
	 */
	@Test
	public void testSetAttackTiles() {
		ChessBoard board = new ChessBoard(8, 8, true);
		
		/* Test Pawn Rows */
		for (int row = 1; row < board.rows; row += 5){
			assertEquals(board.tile[row][0].moves.attackTiles.size(), 1);
			assertEquals(board.tile[row][1].moves.attackTiles.size(), 2);
			assertEquals(board.tile[row][2].moves.attackTiles.size(), 2);
			assertEquals(board.tile[row][3].moves.attackTiles.size(), 2);
			assertEquals(board.tile[row][4].moves.attackTiles.size(), 2);
			assertEquals(board.tile[row][5].moves.attackTiles.size(), 2);
			assertEquals(board.tile[row][6].moves.attackTiles.size(), 2);
			assertEquals(board.tile[row][7].moves.attackTiles.size(), 1);
		}
		
		/* Test Back Rows*/
		for (int row = 0; row < board.rows; row += 7){ //Tests 1st and last row
			assertEquals(board.tile[row][0].moves.attackTiles.size(), 0);
			assertEquals(board.tile[row][1].moves.attackTiles.size(), 2);
			assertEquals(board.tile[row][2].moves.attackTiles.size(), 0);
			assertEquals(board.tile[row][3].moves.attackTiles.size(), 0);
			assertEquals(board.tile[row][4].moves.attackTiles.size(), 0);
			assertEquals(board.tile[row][5].moves.attackTiles.size(), 0);
			assertEquals(board.tile[row][6].moves.attackTiles.size(), 2);
			assertEquals(board.tile[row][7].moves.attackTiles.size(), 0);
		}
		
		/* Move a piece */
		board.movePiece(board.whiteKing, new Point(0, 4));
		board.updateAttackTiles();   
		board.updateInCheck();
		board.updateTurn();
		
		/* Test new attack tiles for WHITE */
		assertEquals(board.tile[0][0].moves.attackTiles.size(), 0);
		assertEquals(board.tile[0][1].moves.attackTiles.size(), 2);
		assertEquals(board.tile[0][2].moves.attackTiles.size(), 0);
		assertEquals(board.tile[0][3].moves.attackTiles.size(), 1);  // Queen has a new attack spot
		assertEquals(board.tile[4][0].moves.attackTiles.size(), 5);  // King has moved
		assertEquals(board.tile[0][5].moves.attackTiles.size(), 0);
		assertEquals(board.tile[0][6].moves.attackTiles.size(), 2);
		assertEquals(board.tile[0][7].moves.attackTiles.size(), 0);
		assertEquals(board.tile[1][0].moves.attackTiles.size(), 1);
		assertEquals(board.tile[1][1].moves.attackTiles.size(), 2);
		assertEquals(board.tile[1][2].moves.attackTiles.size(), 2);
		assertEquals(board.tile[1][3].moves.attackTiles.size(), 2);
		assertEquals(board.tile[1][4].moves.attackTiles.size(), 2);
		assertEquals(board.tile[1][5].moves.attackTiles.size(), 2);
		assertEquals(board.tile[1][6].moves.attackTiles.size(), 2);
		assertEquals(board.tile[1][7].moves.attackTiles.size(), 1);
		
		/* Test new attack tiles for BLACK */
		assertEquals(board.tile[7][0].moves.attackTiles.size(), 0);
		assertEquals(board.tile[7][1].moves.attackTiles.size(), 2);
		assertEquals(board.tile[7][2].moves.attackTiles.size(), 0);
		assertEquals(board.tile[7][3].moves.attackTiles.size(), 0);
		assertEquals(board.tile[7][4].moves.attackTiles.size(), 0);
		assertEquals(board.tile[7][5].moves.attackTiles.size(), 0);
		assertEquals(board.tile[7][6].moves.attackTiles.size(), 2);
		assertEquals(board.tile[7][7].moves.attackTiles.size(), 0);
		assertEquals(board.tile[6][0].moves.attackTiles.size(), 1);
		assertEquals(board.tile[6][1].moves.attackTiles.size(), 2);
		assertEquals(board.tile[6][2].moves.attackTiles.size(), 2);
		assertEquals(board.tile[6][3].moves.attackTiles.size(), 2);
		assertEquals(board.tile[6][4].moves.attackTiles.size(), 2);
		assertEquals(board.tile[6][5].moves.attackTiles.size(), 2);
		assertEquals(board.tile[6][6].moves.attackTiles.size(), 2);
		assertEquals(board.tile[6][7].moves.attackTiles.size(), 1);
	}

	/**
	 *  Tests setValidTiles() for all 32 ChessPieces at initial board configuration \n
	 *  Then moves a WHITE PAWN (twice), and tests the ChessPieces again for each movement
	 */
	@Test
	public void testSetValidTiles() {
		ChessBoard board = new ChessBoard(8, 8, true);
	
		/* Test Pawn Rows */
		for (int row = 1; row < board.rows; row += 5){
			assertEquals(board.tile[row][0].moves.validTiles.size(), 2);
			assertEquals(board.tile[row][1].moves.validTiles.size(), 2);
			assertEquals(board.tile[row][2].moves.validTiles.size(), 2);
			assertEquals(board.tile[row][3].moves.validTiles.size(), 2);
			assertEquals(board.tile[row][4].moves.validTiles.size(), 2);
			assertEquals(board.tile[row][5].moves.validTiles.size(), 2);
			assertEquals(board.tile[row][6].moves.validTiles.size(), 2);
			assertEquals(board.tile[row][7].moves.validTiles.size(), 2);
		}
		
		/* Test Back Rows*/
		for (int row = 0; row < board.rows; row += 7){ //Tests 1st and last row
			assertEquals(board.tile[row][0].moves.validTiles.size(), 0);
			assertEquals(board.tile[row][1].moves.validTiles.size(), 2);
			assertEquals(board.tile[row][2].moves.validTiles.size(), 0);
			assertEquals(board.tile[row][3].moves.validTiles.size(), 0);
			assertEquals(board.tile[row][4].moves.validTiles.size(), 0);
			assertEquals(board.tile[row][5].moves.validTiles.size(), 0);
			assertEquals(board.tile[row][6].moves.validTiles.size(), 2);
			assertEquals(board.tile[row][7].moves.validTiles.size(), 0);
		}
		
		/* Move a Pawn */
		ChessPiece whitePawn = board.tile[1][0];
		board.movePiece(whitePawn, new Point(0, 3));
		board.updateAttackTiles();   
		board.updateInCheck();
		board.updateTurn();
		board.updateValidTiles();
		board.updateGameStatus();
		
		/* Test new valid tiles for WHITE */
		assertEquals(board.tile[3][0].moves.validTiles.size(), 1); //whitePawn has moved
		assertEquals(board.tile[0][0].moves.validTiles.size(), 2); //the rook behind the moved pawn
		
		/* Move Pawn again */
		board.movePiece(whitePawn, new Point(7, 5));
		board.updateAttackTiles();   
		board.updateInCheck();
		board.updateTurn();
		board.updateValidTiles();
		board.updateGameStatus();
		
		/* Test new valid tiles for WHITE */
		assertEquals(board.tile[5][7].moves.validTiles.size(), 1); //whitePawn has moved
		assertEquals(board.tile[0][0].moves.validTiles.size(), 6); //the rook behind the moved pawn
	}

	/** Tests left attacks (using BLACK ROOK) (Used by ROOKS, QUEENS) */
	@Test
	public void testAttacksLeft() {
		/* Set up data */
		ChessBoard board = new ChessBoard(8, 8, true);
		ChessPiece blackRook = board.tile[7][7];
		
		/* Test blackRook at beginning */
		assertEquals(blackRook.moves.attackTiles.size(), 0);
		
		/* Test blackRook in middle row */
		board.movePiece(blackRook, new Point(7,4));
		blackRook.moves.attackTiles.clear();
		blackRook.moves.attacksLeft(board, blackRook);
		assertEquals(blackRook.moves.attackTiles.size(), 7);
	}

	/** Tests right attacks (using BLACK QUEEN) (Used by ROOKS, QUEENS) */
	@Test
	public void testAttacksRight() {
		/* Set up data */
		ChessBoard board = new ChessBoard(8, 8, true);
		ChessPiece blackQueen = board.tile[0][3];
		
		/* Test blackQueen at beginning */
		assertEquals(blackQueen.moves.attackTiles.size(), 0);
		
		/* Test blackQueen in middle row */
		board.movePiece(blackQueen, new Point(4,4));
		blackQueen.moves.attackTiles.clear();
		blackQueen.moves.attacksRight(board, blackQueen);
		assertEquals(blackQueen.moves.attackTiles.size(), 3);
	}

	/** Tests down attacks (using BLACK ROOK) (Used by ROOKS, QUEENS) */
	@Test
	public void testAttacksDown() {
		/* Set up data */
		ChessBoard board = new ChessBoard(8, 8, true);
		ChessPiece blackRook = board.tile[7][7];
		
		/* Test blackRook at beginning */
		assertEquals(blackRook.moves.attackTiles.size(), 0);
		
		/* Test blackRook in middle row */
		board.movePiece(blackRook, new Point(7,4));
		blackRook.moves.attackTiles.clear();
		blackRook.moves.attacksDown(board, blackRook);
		assertEquals(blackRook.moves.attackTiles.size(), 3); //also tests attacking opponent piece!
	}

	/** Tests up attacks (using BLACK QUEEN) (Used by ROOKS, QUEENS) */
	@Test
	public void testAttacksUp() {
		/* Set up data */
		ChessBoard board = new ChessBoard(8, 8, true);
		ChessPiece blackQueen = board.tile[7][3];
		
		/* Test blackQueen at beginning */
		assertEquals(blackQueen.moves.attackTiles.size(), 0);
		
		/* Test blackQueen in middle row */
		board.movePiece(blackQueen, new Point(4,4));
		blackQueen.moves.attackTiles.clear();
		blackQueen.moves.attacksUp(board, blackQueen);
		assertEquals(blackQueen.moves.attackTiles.size(), 1);
	}

	/** Tests NW attacks (using WHITE BISHOP) (Used by BISHOPS, QUEENS) */
	@Test
	public void testAttacksNW() {
		/* Set up data */
		ChessBoard board = new ChessBoard(8, 8, true);
		ChessPiece whiteBishop = board.tile[0][2];
		
		/* Test whiteBishop at beginning */
		assertEquals(whiteBishop.moves.attackTiles.size(), 0);
		
		/* Test whiteBishop in middle row */
		board.movePiece(whiteBishop, new Point(4,4));
		whiteBishop.moves.attackTiles.clear();
		whiteBishop.moves.attacksNW(board, whiteBishop);
		assertEquals(whiteBishop.moves.attackTiles.size(), 2);
	}

	/** Tests NE attacks (using WHITE BISHOP) (Used by BISHOPS, QUEENS) */
	@Test
	public void testAttacksNE() {
		/* Set up data */
		ChessBoard board = new ChessBoard(8, 8, true);
		ChessPiece whiteBishop = board.tile[0][2];
		
		/* Test whiteBishop at beginning */
		assertEquals(whiteBishop.moves.attackTiles.size(), 0);
		
		/* Test whiteBishop in middle row */
		board.movePiece(whiteBishop, new Point(4,4));
		whiteBishop.moves.attackTiles.clear();
		whiteBishop.moves.attacksNE(board, whiteBishop);
		assertEquals(whiteBishop.moves.attackTiles.size(), 2);
	}

	/** Tests NW attacks (using WHITE BISHOP) (Used by BISHOPS, QUEENS) */
	@Test
	public void testAttacksSW() {
		/* Set up data */
		ChessBoard board = new ChessBoard(8, 8, true);
		ChessPiece whiteBishop = board.tile[0][2];
		
		/* Test whiteBishop at beginning */
		assertEquals(whiteBishop.moves.attackTiles.size(), 0);
		
		/* Test whiteBishop in middle row */
		board.movePiece(whiteBishop, new Point(4,4));
		whiteBishop.moves.attackTiles.clear();
		whiteBishop.moves.attacksSW(board, whiteBishop);
		assertEquals(whiteBishop.moves.attackTiles.size(), 2);
	}

	/** Tests SE attacks (using WHITE BISHOP) (Used by BISHOPS, QUEENS) */
	@Test
	public void testAttacksSE() {
		/* Set up data */
		ChessBoard board = new ChessBoard(8, 8, true);
		ChessPiece whiteBishop = board.tile[0][2];
		
		/* Test whiteBishop at beginning */
		assertEquals(whiteBishop.moves.attackTiles.size(), 0);
		
		/* Test whiteBishop in middle row */
		board.movePiece(whiteBishop, new Point(4,4));
		whiteBishop.moves.attackTiles.clear();
		whiteBishop.moves.attacksSE(board, whiteBishop);
		assertEquals(whiteBishop.moves.attackTiles.size(), 2);
	}

	/** Tests 2 possible PAWN diagonal attacks */
	@Test
	public void testAttacksPawn() {
		/* Set up data */
		ChessBoard board = new ChessBoard(8, 8, true);
		ChessPiece whitePawn = board.tile[1][1]; //grab pawn not on edge
	
		/* Test Pawn at beginning */
		assertEquals(whitePawn.moves.attackTiles.size(), 2);
		
		/* Move Pawn to attacking row */
		board.movePiece(whitePawn, new Point(1,5));
		board.updateAttackTiles();   
		board.updateInCheck();
		board.updateTurn();
		assertEquals(whitePawn.moves.attackTiles.size(), 2);

		/* Move Pawn to attacking row on edge*/
		board.movePiece(whitePawn, new Point(7,5));
		board.updateAttackTiles();   
		board.updateInCheck();
		board.updateTurn();
		assertEquals(whitePawn.moves.attackTiles.size(), 1);
	}

	/** Tests 8 possible KNIGHT jump attacks */
	@Test
	public void testAttacksKnight() {
		/* Set up data */
		ChessBoard board = new ChessBoard(8, 8, true);
		ChessPiece whiteKnight = board.tile[0][1];
		ChessPiece blackKnight = board.tile[7][6];
		
		/* Test White Knight */
		assertEquals(whiteKnight.position.x, 1);
		assertEquals(whiteKnight.position.y, 0);
		assertEquals(whiteKnight.moves.attackTiles.size(), 2);

		/* Test Black Knight */
		assertEquals(blackKnight.position.x, 6);
		assertEquals(blackKnight.position.y, 7);
		assertEquals(blackKnight.moves.attackTiles.size(), 2);
		
		/* Move Knight, Test Again */
		board.movePiece(whiteKnight, new Point(4,4));
		board.updateAttackTiles();   
		board.updateInCheck();
		board.updateTurn();
		assertEquals(whiteKnight.moves.attackTiles.size(), 8);
	}


	/** Tests 8 possible KING 1-step attacks */
	@Test
	public void testAttacksKing() {
		/* Set up data */
		ChessBoard board = new ChessBoard(8, 8, true);
		ChessPiece testPiece = board.tile[0][4];
		
		/* Test King at beginning */
		assertEquals(testPiece.moves.attackTiles.size(), 0);
		
		/* Move King to center, Test Again */
		board.movePiece(testPiece, new Point(4,4));
		board.updateAttackTiles();   
		assertEquals(testPiece.moves.attackTiles.size(), 8);

		/* Move King to edge, Test Again */
		board.movePiece(testPiece, new Point(7,4));
		board.updateAttackTiles();
		assertEquals(testPiece.moves.attackTiles.size(), 5);
	}
	
	/** Tests White Rook capturing Black Rook */
	@Test
	public void testCapturingPiece() {
		ChessBoard board = new ChessBoard(8, 8, true);
		ChessPiece testPiece = board.tile[0][0]; //White Rook
		
		/* (Illegally) move White Rook to Capture Black Rook */
		board.movePiece(testPiece, new Point(7,0));
		
		/* Make sure Black Rook is captured */
		assertEquals(board.tile[0][7].type, Type.ROOK);
		assertEquals(board.tile[0][7].color, Color.WHITE);
		
	}
	
	/**
	 * Test the new special CENTAUR ChessPiece
	 */
	@Test
	public void testCentaurChessPiece() {
		/* Set up data */
		ChessBoard board = new ChessBoard(8, 8, true);
		board.tile[2][0] = new ChessPiece(Type.CENTAUR, Color.WHITE, new Point(0, 2));
		ChessPiece centaur = board.tile[2][0];
		centaur.setMoves(board);
		
		/* Test CENTAUR Movement */
		assertEquals(centaur.moves.attackTiles.size(), 2);
		assertEquals(centaur.moves.validTiles.size(), 2);
	}
	
	/**
	 * Test the new special HORIZON ChessPiece
	 */
	@Test
	public void testHorizonChessPiece() {
		/* Set up data */
		ChessBoard board = new ChessBoard(8, 8, true);
		board.tile[3][3] = new ChessPiece(Type.HORIZON, Color.WHITE, new Point(3, 3));
		ChessPiece horizon = board.tile[3][3];
		horizon.setMoves(board);
		
		/* Test HORIZON movement */
		assertEquals(horizon.moves.attackTiles.size(), 7);
		assertEquals(horizon.moves.validTiles.size(), 7);
	}
	
	/**
	 * Test the new special DECOY ChessPiece
	 */
	@Test
	public void testDecoyChessPiece() {
		/* Set up data */
		ChessBoard board = new ChessBoard(8, 8, true);
		board.tile[2][0] = new ChessPiece(Type.DECOY, Color.WHITE, new Point(0, 2));
		ChessPiece decoy = board.tile[2][0];
		decoy.setMoves(board);
		
		/* Test DECOY movement */
		assertEquals(decoy.moves.attackTiles.size(), 15);
		assertEquals(decoy.moves.validTiles.size(), 15);
		
		//will have to manually test that the image changes
	}
	
	/**
	 * Test the new special SHAPESHIFTER ChessPiece
	 */
	@Test
	public void testShapeshifterChessPiece() {
		/* Set up data */
		ChessBoard board = new ChessBoard(8, 8, true);
		board.tile[2][0] = new ChessPiece(Type.SHAPESHIFTER, Color.WHITE, new Point(0, 2));
		ChessPiece shapeshifter = board.tile[2][0];
		shapeshifter.setMoves(board);
		
		/* Test DECOY movement */
		assertEquals(shapeshifter.moves.attackTiles.size(), 15);
		assertEquals(shapeshifter.moves.validTiles.size(), 15);
		
		/* Test to see if it actually Shapeshifts */
		board.movePiece(shapeshifter, new Point(4, 4));
		assertNotEquals(shapeshifter.type, Type.SHAPESHIFTER);
		
		//will have to manually test that the image changes
	}
}
