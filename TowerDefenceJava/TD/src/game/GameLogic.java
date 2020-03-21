package game;

import java.awt.Graphics;
import java.util.Random;

import enemytrackers.EnemyTracker;
import handlers.TextureHandler;
import player.Player;
import towers.TowerTracker;
import windows.GameWindow;

/**
 * 
 * @author Ömer
 * @author Robin 
 * @version 4
 *
 *         Takes care of all calculations in the game, takes care of calling all
 *         the methods required for each "logic loop"
 */
public class GameLogic implements Runnable {

	public static EnemyTracker enemies;
	public static TowerTracker towerGrid;
	public static boolean paused;
	private int randomNumber;
	private Player player;

	public static boolean gameOver;

	/**
	 * initilizes the classes needed for this class to work
	 * used when new game
	 */
	public GameLogic() {
		setUpTrackers();
		paused = false;
		gameOver = false;
		player = Player.getInstance();
	}
	/**
	 * initilizes the classes needed for this class to work
	 * used when loading game
	 * 
	 * @param mobArray - mobs to load
	 */
	public GameLogic(int[] mobArray) {
		setUpTrackers(mobArray);
		paused = false;
		gameOver = false;
		player = Player.getInstance();
	}

	/**
	 * setting up towers and enemies used in the game, called from the constructor
	 * in this class for loading games
	 * @param mobArray - mobs to load
	 */
	private void setUpTrackers(int[] mobArray) {
		enemies = new EnemyTracker(mobArray);
		towerGrid = new TowerTracker();
	}
	/**
	 * setting up towers and enemies used in the game, called from the constructor
	 * in this class for new game
	 */
	private void setUpTrackers() {
		enemies = new EnemyTracker();
		towerGrid = new TowerTracker();
	}

	public void loadTowers(int[][] towerArr) {
		towerGrid.loadTower(towerArr);
	}

	/**
	 * 
	 * @param g graphics component used to draw everything checks if the player has
	 *          lost or not if its lost draw GameOver screen if not let panel draw
	 *          the enemies and towers called by Panel
	 * 
	 */
	public void draw(Graphics g) {
		if (!gameOver && (player.getHealth() == 0)) {
			Random rn = new Random();
			int r = rn.nextInt(3);
			randomNumber = r;
		}
		if (player.getHealth() == 0) {
			if (randomNumber == 0) {
				g.drawImage(TextureHandler.GAME_OVER_SCREEN.img, 0, 0, 1920, 1080, null);
			} else if (randomNumber == 1) {
				g.drawImage(TextureHandler.GAME_OVER_SCREEN2.img, 0, 0, 1920, 1080, null);
			} else if (randomNumber == 2) {
				g.drawImage(TextureHandler.GAME_OVER_SCREEN3.img, 0, 0, 1920, 1080, null);
			}

		} else {
			enemies.draw(g);
			towerGrid.draw(g);
		}
	}

	/**
	 * starts the thread
	 */
	public void gameLogicStart() {
		new Thread(this).start();
	}

	/**
	 * checks if the game is running or paused if its paused or the player has lost
	 * stop the game if the time for calculations exceeds the specified time don't
	 * make the thread sleep and if don't exceeds let it sleep for the remaining
	 * time
	 */
	@Override
	public void run() {
		while (Panel.isOpenGame()) {
			while (paused || gameOver) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}
			long start = System.currentTimeMillis();
			if (!Panel.isTurnOn()) {
				enemies.startSpawner();
				enemies.move();
			}
			if (player.getHealth() == 0) {
				gameOver = true;
			} else if (enemies.getEnemyList().size() == 0 && !gameOver) {
				enemies.endRound();
			}

			towerGrid.towerAct();
			long end = System.currentTimeMillis();

			if ((20 - (end - start)) > 0) {
				try {
					Thread.sleep(20 - (end - start));
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			} 
		}

	}

	/**
	 * returns the state of the game
	 * 
	 * @return gameOver
	 */
	public boolean gameOver() {
		return gameOver;
	}


}
