package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import main_components.Controller;

/**
 * \brief Tests the Controller class for initialization
 * @author Rodney Shaghoulian
 */
public class ControllerTest {

	/**
	 * Tests the Constructor for initial variables
	 */
	@Test
	public void testController() {
		/* Set up data */
		Controller controller = new Controller();
		assertNotNull(controller.chessBoard);
		assertNotNull(controller.view);
	}

	/**
	 * Tests reseting the Controller and still having MVC structure intact
	 */
	@Test
	public void testReset() {
		/* Set up data */
		Controller controller = new Controller();
		controller.reset();
		assertNotNull(controller.chessBoard);
		assertNotNull(controller.view);
	}

	/**
	 * Tests all 6 variable initializations
	 */
	@Test
	public void testInitializeVariables() {
		/* Set up data */
		Controller controller = new Controller();
		assertNotNull(controller.chessBoard);
		assertNotNull(controller.commandManager);
		assertEquals(controller.inMovement, false);
		assertEquals(controller.buttonsToHighlight.size(), 0);
		assertEquals(controller.buttonsCanMoveTo.size(), 0);
		assertNull(controller.originPoint);
	}
}
