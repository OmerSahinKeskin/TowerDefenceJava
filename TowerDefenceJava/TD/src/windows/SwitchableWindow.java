package windows;

import java.awt.Container;
/**
 * Indicate that this content pane can be swapped in/out
 * Contains method to return displayable container and method to switch to a new switchable window.
 * @author Robin
 * @version 1
 *
 */
public interface SwitchableWindow {
	/**
	 * Every switchable window needs to be able to return the content to be shown
	 * 
	 * @return Container that can be displayed
	 */
	public Container getContent();

	/**
	 * Window switch function
	 * 
	 * @param newcontent - New content to be displayed
	 */
	public void switchWindow(SwitchableWindow newcontent);

}
