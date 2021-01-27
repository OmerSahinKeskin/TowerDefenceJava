package game;

import java.awt.*;
import javax.swing.JPanel;

import drawers.TileMap;

/**
 * The class that draws everything
 * @author Ömer
 * @author Robin
 * @version 5
 * 
 */
public class Panel extends JPanel implements Runnable {

	private static boolean turnOn = true;

	private static TileMap tilemap;
	private GameLogic gamelogic;
	private static boolean openGame;
	private String currentMap;

	private Thread gamethread;

	/**
	 * 
	 * @param mapName the map that is going to load initilizes the classes needed
	 *                for everything used when new game
	 */
	public Panel(String mapName) {
		setOpenGame(true);
		currentMap = mapName;
		init();
	}
	/**
	 * 
	 * @param mapName - map to load
	 * @param mobArray - monsters to load
	 *  the map that is going to load initilizes the classes needed
	 *                for everything used when loading game
	 */
	public Panel(String mapName, int[] mobArray) {
		setOpenGame(true);
		currentMap = mapName;
		init(mobArray);
	}
	/**
	 * creates instances of the classes needed and calls the method to start the
	 * thread used when loading
	 */
	public void init() {
		tilemap = new TileMap("map/" + currentMap);
		gamelogic = new GameLogic();
		threadInit();
	}
	/**
	 * creates instances of the classes needed and calls the method to start the
	 * thread used when loading
	 * @param mobArray - mobs to be loaded
	 */
	public void init(int[] mobArray) {
		tilemap = new TileMap("map/" + currentMap);
		gamelogic = new GameLogic(mobArray);
		threadInit();
	}

	/**
	 * creats a thread and puts class in an own thread and starts it
	 */
	public void threadInit() {
		gamethread = new Thread(this);
		gamethread.start();
	}

	/**
	 * Used to draw the game and its components Call repeatedly for to "animate"
	 */
	@Override
	public void paintComponent(Graphics g) {
		if (isTurnOn()) {

			setTurnOn(false);
		}

		g.clearRect(0, 0, getWidth(), getHeight());
		getTilemap().draw(g);
		gamelogic.draw(g);

	}

	/**
	 * Game loop This will be done repeatedly until stopped
	 * 
	 */
	@Override
	public void run() {
		gamelogic.gameLogicStart();
		while (isOpenGame()) {
			long start = System.currentTimeMillis();
			repaint();
			long end = System.currentTimeMillis();
			if (10 - (end - start) > 0) {
				try {
					Thread.sleep(10 - (end - start));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else System.out.println("DRAW LONG");

		}

	}

	public static boolean isTurnOn() {
		return turnOn;
	}

	public static void setTurnOn(boolean turnOn) {
		Panel.turnOn = turnOn;
	}

	public static TileMap getTilemap() {
		return tilemap;
	}

	public static boolean isOpenGame() {
		return openGame;
	}

	public static void setOpenGame(boolean openGame) {
		Panel.openGame = openGame;
	}
	
	public void loadTowers(int[][] towerArr) {
		gamelogic.loadTowers(towerArr);
	}

}