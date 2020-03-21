package mapbuilder;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import drawers.TileMap;
import game.GameLogic;
import game.Panel;
import handlers.TextureHandler;
import inpurdevices.ClickFunctionality;
import inpurdevices.MouseControls;
import towers.RedTower;

/**
 * The panel where the map is drawn/held during its creation.
 * @author Robin
 * @version 2
 * 
 *
 */
@SuppressWarnings("serial")
public class MapPanel extends JPanel implements MapPanelIfc, ClickFunctionality {

	private boolean building;
	private TileMap tileMap;
	private Point mousePosition = new Point(0, 0);
	private int buildMode;

	public MapPanel() {
		init();
	}

	private void init() {
		building = true;
		tileMap = new TileMap("map/blank.txt");
		threadInit();
	}

	@Override
	public void threadInit() {
		this.addMouseListener(MouseControls.getInstance());
		this.addMouseMotionListener(MouseControls.getInstance());
		MouseControls.getInstance().setClickUser(this);
		mousePosition = MouseControls.getInstance().getPoint();
		new Thread(this).start();
	}

	@Override
	public void threadStop() {
		building = false;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		tileMap.draw(g);
	}

	@Override
	public void run() {

		while (building) {
			long start = System.currentTimeMillis();
			repaint();
			long end = System.currentTimeMillis();
			if (10 - (end - start) > 0) {
				try {
					Thread.sleep(10 - (end - start));
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}

		}

	}
	
	
	@Override
	public void setBuildMode(int newMode) {
		buildMode = newMode;
	}
	
	public void saveMapAs(String name) {
		tileMap.saveMap(name, tileMap.getTile());
	}

	@Override
	public void mouseClick(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e))
			tileMap.setSingleTile((int) mousePosition.getY() / 64, (int) mousePosition.getX() / 64, buildMode);
		else if (SwingUtilities.isRightMouseButton(e))
			tileMap.setSingleTile((int) mousePosition.getY() / 64, (int) mousePosition.getX() / 64, 10);

	}

	@Override
	public void mouseHeld(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e))
			tileMap.setSingleTile((int) mousePosition.getY() / 64, (int) mousePosition.getX() / 64, buildMode);
		else if (SwingUtilities.isRightMouseButton(e))
			tileMap.setSingleTile((int) mousePosition.getY() / 64, (int) mousePosition.getX() / 64, 10);

	}
	
	
	@Override
	public void randomMap() {
		new MapGenerator(tileMap);
	}

	@Override
	public void clearMap() {
		tileMap = new TileMap("map/blank.txt");
		
		
	}

}