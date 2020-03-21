package windows;

import javax.swing.JOptionPane;

/**
 * 
 * Class used to create warning dialog windows.
 *
 */
public class PopupInfo implements Window {

	/**
	 * Create a customizeable popup dialog
	 * 
	 * @param message - message to be displayed
	 */
	public PopupInfo(String message) {
		createPopup(message);
	}

	/**
	 * Creates the popup window with the provided parameters
	 * 
	 * @param message
	 * @param type
	 */
	private void createPopup(String message) {
		JOptionPane.showMessageDialog(null, message, "Inane error", JOptionPane.ERROR_MESSAGE);

	}

}
