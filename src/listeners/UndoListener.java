package listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main_components.CommandManager;
import main_components.Controller;

/**
 * \brief
 * Enables a player to "undo" a move
 * @author Rodney Shaghoulian
 */
public class UndoListener extends MouseAdapter {
	public CommandManager commandManager;					///< Keeps track of undos and redos
	
	/**
	 * Constructor that saves CommandManager information
	 * @param controller	The Controller that has access to CommandManager
	 */
	public UndoListener(Controller controller){
		this.commandManager = controller.commandManager;
	}
	
	/**
	 * Undos a move if an undo is available. Beeps otherwise
	 */
	public void mouseClicked(MouseEvent event){
		if (commandManager.undoAvailable())
			commandManager.undo();
		else
			java.awt.Toolkit.getDefaultToolkit().beep();
	}
}
