package main_components;

/** 
 * \brief The main game engine/logic
 * @author Rodney Shaghoulian
 */
public class GameLoop {

	/**
	 * \brief This is the game loop. 
	 * We create a controller. This creates the Model (ChessBoard) and the View. \n
	 * The game continuously loops as it waits for user input, so the GameLoop has been abstracted away.
	 * @param args
	 */
	public static void main(String[] args) {
		new Controller();
	}
}


