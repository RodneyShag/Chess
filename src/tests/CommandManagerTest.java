package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import main_components.ChessBoard;
import main_components.Command;
import main_components.CommandManager;
import main_components.Controller;
import main_components.View;
import piece_properties.Type;

/**
 * \brief
 * Tests the CommandManager for execute, undo, redo commands
 * @author Rodney Shaghoulian
 */
public class CommandManagerTest {

	/**
	 * Tests Command Manager. Moves a BLACK Rook into the middle of the board using the Command Manager. Tests "Undo" and "Redo" of this move.
	 */
	@Test
	public void testCommandManager() {
		/* Set up data */
		Controller controller = new Controller();
		ChessBoard chessBoard = controller.chessBoard;
		View view = controller.view;
		CommandManager commandManager = controller.commandManager;
		Command command = new Command(chessBoard, view, new Point (0, 0), new Point(4, 4));
		
		/* Test Undo, Redo stack sizes */
		assertEquals(commandManager.undos.size(), 0);
		assertEquals(commandManager.redos.size(), 0);
		
		/* Test executeCommand */
		commandManager.executeCommand(command);
		assertEquals(chessBoard.tile[0][0], null);
		assertEquals(chessBoard.tile[4][4].type, Type.ROOK);
		assertEquals(commandManager.undos.size(), 1);
		assertEquals(commandManager.redos.size(), 0);
		
		/* Test Undo */
		commandManager.undo();
		assertEquals(chessBoard.tile[0][0].type, Type.ROOK);
		assertEquals(chessBoard.tile[4][4], null);
		assertEquals(commandManager.undos.size(), 0);
		assertEquals(commandManager.redos.size(), 1);
		
		/* Test Redo */
		commandManager.redo();
		assertEquals(chessBoard.tile[0][0], null);
		assertEquals(chessBoard.tile[4][4].type, Type.ROOK);
		assertEquals(commandManager.undos.size(), 1);
		assertEquals(commandManager.redos.size(), 0);
	}

}
