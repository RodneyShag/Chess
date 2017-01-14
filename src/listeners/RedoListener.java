package listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main_components.CommandManager;
import main_components.Controller;

/**
 * \brief
 * Enables a player to "redo" a move
 * @author Rodney Shaghoulian
 */
public class RedoListener extends MouseAdapter {
	public CommandManager commandManager;
	
	/**
	 * Constructor that saves CommandManager information
	 * @param controller	The Controller that has access to CommandManager
	 */
	public RedoListener(Controller controller){
		this.commandManager = controller.commandManager;
	}
	
	/**
	 * Redos a move if an undo is available. Beeps otherwise
	 */
	public void mouseClicked(MouseEvent event){
		if (commandManager.redoAvailable())
			commandManager.redo();
		else
			java.awt.Toolkit.getDefaultToolkit().beep();
	}
}