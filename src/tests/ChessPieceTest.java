package tests;

import java.awt.Point;
import static org.junit.Assert.*;

import org.junit.Test;

import main_components.*;
import piece_properties.*;

/**
 * \brief
 * Tests the simple ChessPiece class
 * @author Rodney Shaghoulian
 */
public class ChessPieceTest {

	/**
	 * This is a simple test to make sure the constructor works properly, meaning
 	 * 1) Correct Fields  2) No null values  3) empty attack and valid tiles
	 */
	@Test
	public void testChessPiece() {
		/* Set up data */
		ChessPiece whiteRook = new ChessPiece(Type.ROOK, Color.WHITE, new Point(0,0));
		
		/* Verify Correct Properties */
		assertNotNull(whiteRook);
		assertEquals(whiteRook.type, Type.ROOK);
		assertEquals(whiteRook.color, Color.WHITE);
		assertEquals(whiteRook.position.x, 0);
		assertEquals(whiteRook.position.y, 0);
		assertNotNull(whiteRook.moves);
		assertTrue(whiteRook.moves.attackTiles.isEmpty());
		assertTrue(whiteRook.moves.validTiles.isEmpty());
	}

}
