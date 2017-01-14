/** \brief Modular Tests for our Chess Program */
package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * \brief
 * A Test Suite to run all written J-Unit tests
 * @author Rodney Shaghoulian
 */
@RunWith(Suite.class)
@SuiteClasses({ ChessPieceTest.class, ChessBoardTest.class, MovesTest.class, ButtonTest.class, 
	CommandTest.class, CommandManagerTest.class, ControllerTest.class})
public class AllTests {

}
