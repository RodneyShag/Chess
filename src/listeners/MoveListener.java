package listeners;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main_components.Button;
import main_components.ChessBoard;
import main_components.ChessPiece;
import main_components.Command;
import main_components.CommandManager;
import main_components.Controller;
import main_components.View;

/**
 * \brief
 * Enables a player to move a ChessPiece with a mouse
 * @author Rodney Shaghoulian
 */
public class MoveListener extends MouseAdapter {
	public Controller controller;							///< The Controller in the MVC structure
	public ChessBoard chessBoard;							///< The Model in the MVC structure
	public View view;										///< The View in the MVC structure
	public CommandManager commandManager;					///< Keeps track of Commands for undos and redos
	
	/*
	 * A constructor that saves Model, View, Controller in the MVC structure, along with the CommandManager
	 */
	public MoveListener(Controller controller){
		this.controller 	= controller;
		this.chessBoard 	= controller.chessBoard;
		this.view 			= controller.view;
		this.commandManager = controller.commandManager;
	}
	
	/**
	 * Let's us do move with 2 mouse clicks
	 * @param event		the event corresponding to the mouse click
	 */
	public void mouseClicked(MouseEvent event){
		if ( ! controller.inMovement)
			getReadyForMove(event);
		else
			tryMove(event);
	}
	
	/**
	 * On Click #1: Valid tiles are highlighted for the user.
	 * @param event		the event corresponding to the mouse click
	 */
	public void getReadyForMove(MouseEvent event){
		/* Find the valid tiles we can move to */
		Button currentButton = (Button) event.getSource();	//gets the Button that this MouseEvent happened on
		ChessPiece chessPiece = chessBoard.tile[currentButton.yPos][currentButton.xPos];
		if (chessPiece != null && chessPiece.color == chessBoard.playerTurn){
			controller.buttonsToHighlight.add(currentButton);
			for (Point point : chessPiece.moves.validTiles){
				Button button = view.getButton(point);
				controller.buttonsToHighlight.add(button);
				controller.buttonsCanMoveTo.add(button);
			}
			/* Highlight the tiles */
			for (Button button : controller.buttonsToHighlight){
				button.setBackground(Color.BLUE);
			}
			controller.inMovement = true;
			controller.originPoint = currentButton.createPoint();
		}
	}
	
	/**
	 * On Click #2: The ChessPiece is moved if it is a valid move.
	 * @param event		the event corresponding to the mouse click
	 */
	public void tryMove(MouseEvent event){
		Button currentButton = (Button) event.getSource();
		if (controller.buttonsCanMoveTo.contains(currentButton)){
			Point destinationPoint = currentButton.createPoint();
			Command command = new Command(chessBoard, view, controller.originPoint, destinationPoint);
			commandManager.executeCommand(command);
		}
		else
			javax.swing.JOptionPane.showMessageDialog(null, "Illegal Move. Try again!");;
		for (Button button : controller.buttonsToHighlight){
			view.setBackground(button);
		}
		controller.buttonsToHighlight.clear();
		controller.buttonsCanMoveTo.clear();
		controller.inMovement = false;
		controller.originPoint = null;
	}
}
