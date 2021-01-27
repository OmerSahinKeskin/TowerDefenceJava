package inpurdevices;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import game.GameLogic;

/**
 * Singleton mouse controller Add actions by adding an implementation of
 * ClickFunctionality using setClickUser(ClickFunctionality user)
 * 
 * 
 * @author Robin
 * @version 8
 *
 */
public class MouseControls implements MouseMotionListener, MouseListener {

	private static MouseControls single_instance = null;
	private ClickFunctionality clickUser;
	private Point mousePosition;

	private MouseControls() {
		mousePosition = new Point(0, 0);
	}

	public static MouseControls getInstance() {
		if (single_instance == null)
			single_instance = new MouseControls();
		return single_instance;
	}

	/**
	 * Set current user of the click functionality provided by the listener
	 * @param user - The new user of click functions
	 */
	public void setClickUser(ClickFunctionality user) {
		clickUser = user;
	}
	
	/**
	 * Returns current mouse position
	 * @return mouse position (Point)
	 */
	public Point getPoint() {
		return mousePosition;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (clickUser != null)
			clickUser.mouseClick(e);
		mousePosition.setLocation(e.getPoint());

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (clickUser != null) {
			clickUser.mouseHeld(e);
			mousePosition.setLocation(e.getPoint());
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePosition.setLocation(e.getPoint());

	}

}
