package listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main_components.Controller;

/**
 * \brief
 * Enables a player to start a new game in Custom or Classic mode.
 * @author Rodney Shaghoulian
 */
public class NewGameListener extends MouseAdapter {
	public Controller controller;	///< The Controller in the MVC structure
	boolean classicMode;			///< true for classic mode. false for custom mode
	
	/**
	 * 
	 * @param controller		The Controller in the MVC structure
	 * @param classicMode		true for classic mode. false for custom mode
	 */
	public NewGameListener(Controller controller, boolean classicMode){
		this.controller 	= controller;
		this.classicMode	= classicMode;
	}
	
	/**
	 * Restarts game in classic or custom mode when JButton is clicked
	 * @param event		the corresponding MouseEvent
	 */
	public void mouseClicked(MouseEvent event){
		if (classicMode){
			javax.swing.JOptionPane.showMessageDialog(null, "Starting New Game: Classic Mode");
			controller.isClassicMode = true;
		}
		else{
			javax.swing.JOptionPane.showMessageDialog(null, "Starting New Game: Custom Mode!");
			controller.isClassicMode = false;
		}
		controller.reset();
	}
}