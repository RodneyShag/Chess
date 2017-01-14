package main_components;

import listeners.ForfeitListener;
import listeners.RestartListener;
import listeners.MoveListener;
import listeners.NewGameListener;
import listeners.RedoListener;
import listeners.UndoListener;

import java.util.ArrayList;
import java.awt.Point;

/**
 * \brief
 * The Controller in the MVC structure
 * @author Rodney Shaghoulian
 */
public class Controller {
	public ChessBoard chessBoard; 					///< The Model in MVC structure
	public CommandManager commandManager;			///< Saves Commands so we can undo and redo them
	public boolean inMovement;						///< true if a player is mid-move
	public ArrayList<Button> buttonsToHighlight; 	///< Buttons to highlight for the user to display valid moves for a ChessPiece
	public ArrayList<Button> buttonsCanMoveTo;		///< Buttons that a current ChessPiece can move to
	public Point originPoint;						///< The origin of a moving ChessPiece

	public View view;								///< The "View" in MVC structure.
	public int whiteGamesWon = 0;					///< Number of games WHITE has won
	public int blackGamesWon = 0;					///< Number of games BLACK has won
	public boolean isClassicMode = true;			///< true for Classic Mode. false for Custom Mode.
	
	/**
	 * Constructor: Initialize the Controller with a new ChessBoard and new View. Add mouseListeners to the View
	 */
	public Controller() {
		initializeVariables();
		view = new View(chessBoard, this);
		addMouseListeners();
	}
	
	/**
	 * Reset the Controller with the new ChessBoard and UPDATED View. Add mouseListeners to the View
	 */
	public void reset(){
		initializeVariables();
		view.initialize(chessBoard, this);
		addMouseListeners();
	}
	
	/**
	 * Create ChessBoard and CommandManager. Initialize local variables in Controller.
	 */
	public void initializeVariables(){
		chessBoard = new ChessBoard(8, 8, isClassicMode);	
		commandManager = new CommandManager();
		inMovement = false;
		buttonsToHighlight = new ArrayList<Button>(); 
		buttonsCanMoveTo = new ArrayList<Button>();
		originPoint = null;
	}
	
	/**
	 * Add mouseListeners to the View
	 */
	public void addMouseListeners(){
		/* Add MoveListener to all tiles */
		for (int i = 0; i < chessBoard.rows; i++){
			for (int j = 0; j < chessBoard.columns; j++){
				view.addMouseListener(new MoveListener(this), view.button[i][j]);
			}
		}
		view.addMouseListener(new UndoListener(this), view.undoButton);
		view.addMouseListener(new RedoListener(this), view.redoButton);
		view.addMouseListener(new ForfeitListener(this), view.whiteForfeit);
		view.addMouseListener(new ForfeitListener(this), view.blackForfeit);
		view.addMouseListener(new RestartListener(this), view.whiteRestart);
		view.addMouseListener(new RestartListener(this), view.blackRestart);	
		view.addMouseListener(new NewGameListener(this, true), view.classicMode);
		view.addMouseListener(new NewGameListener(this, false), view.customMode);
	}
}
