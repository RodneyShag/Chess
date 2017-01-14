package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import main_components.ChessBoard;
import main_components.View;
import main_components.Command;
import main_components.Controller;
import piece_properties.Type;

/**
 * \brief
 * Tests the Command class for executing and undoing Commands
 * @author Rodney Shaghoulian
 */
public class CommandTest {

	/**
	 * Tests executing a Command, and undoing a Command.
	 */
	@Test
	public void testExecuteUndo() {
		/* Set up data */
		Controller controller = new Controller();
		ChessBoard chessBoard = controller.chessBoard;
		View view = controller.view;
		Command command = new Command(chessBoard, view, new Point (0, 0), new Point(4, 4));
		/* Test execute */
		command.execute();
		assertEquals(chessBoard.tile[0][0], null);
		assertEquals(chessBoard.tile[4][4].type, Type.ROOK);
		/* Test Undo */
		command.undo();
		assertEquals(chessBoard.tile[0][0].type, Type.ROOK);
		assertEquals(chessBoard.tile[4][4], null);
	}
}
