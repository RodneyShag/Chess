/** \brief The main classes for our Chess Program */
package main_components;

import javax.swing.JButton;

import java.awt.Point;

/**
 * \brief
 * An extension of JButton. Button knows its Position on the GUI.
 * @author Rodney Shaghoulian
 */
@SuppressWarnings("serial")
public class Button extends JButton{
	public int xPos;	///< x Position of Button
	public int yPos;	///< y Position of Button
	
	/**
	 * Creates a button given (x,y) coordinates
	 * @param xPos	x Position of Button
	 * @param yPos	y Position of Button
	 */
	public Button(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	/**
	 * Creates a Point to represent current button
	 * @return
	 */
	public Point createPoint(){
		return new Point(xPos, yPos);
	}
}