
package inpurdevices;

import java.awt.event.MouseEvent;

/**
 * Indicates that a class will use mouse inputs
 * @author Robin
 * @version 2
 *
 */
public interface ClickFunctionality {
	
	/**
	 * Action taken when any mouse button is pressed
	 * 
	 * @param e - Mouse click event
	 */
	public void mouseClick(MouseEvent e);
	
	/**
	 * Action taken when a button is pressed, held and moved around 
	 * 
	 * @param e - Mouse event
	 */
	public void mouseHeld(MouseEvent e);

}
