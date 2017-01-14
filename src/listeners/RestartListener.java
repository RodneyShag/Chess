package listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import main_components.Controller;
import main_components.View;

/**
 * \brief
 * Enables a player to restart a game if both WHITE and BLACK players agree
 * @author Rodney Shaghoulian
 */
public class RestartListener extends MouseAdapter {
	public Controller controller;	///< The Controller for the current game
	public View view;				///< The "View" in the MVC structure
	
	/**
	 * Constructor to save the Controller and View information
	 * @param controller
	 */
	public RestartListener(Controller controller){
		this.controller 	= controller;
		this.view 			= controller.view;
	}
	
	/**
	 * Enables players to restart a game if both of their "restart" buttons are clicked
	 * @param event		The corresponding event to a mouse click
	 */
	public void mouseClicked(MouseEvent event){
		JButton currentButton = (JButton) event.getSource();
		if (currentButton.getBackground() != java.awt.Color.GREEN)
			currentButton.setBackground(java.awt.Color.GREEN);
		else
			currentButton.setBackground(null);
		if (view.whiteRestart.getBackground() == java.awt.Color.GREEN && view.blackRestart.getBackground() == java.awt.Color.GREEN){
				javax.swing.JOptionPane.showMessageDialog(null, "Both players agreed to restart. Score will be tied (i.e. remain the same)");
				controller.reset();
		}
	}
}