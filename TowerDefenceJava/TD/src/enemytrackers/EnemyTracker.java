package enemytrackers;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import enemies.Enemy;
import enemies.MonsterFactory;
import enemies.SpawnEnemies;
import game.GameLogic;
import player.Player;
import windows.GameWindow;

/**
 * 
 * @author Ömer 
 * @version 5 
 * Checks which enemies are still alive and calls to get them to
 *         spawn. moves the enemies and calls for them to be created
 */
public class EnemyTracker implements Runnable {

	private int waittime = 650;
	private int waitedtime = 0;

	private SpawnEnemies spawnEnemies = new SpawnEnemies(waittime, waitedtime);

	private static List<Enemy> enemyList;

	private int round = Player.getInstance().getRound();

	private static RoundTracker roundTracker;

	/**
	 * setting up the RoundTracker to what round it is and setting up the array
	 * used when new game
	 */
	public EnemyTracker() {
		setEnemyList(new ArrayList<Enemy>());

		roundTracker = new RoundTracker(10, round);
	}
	/**
	 * setting up the RoundTracker to what round it is and setting up the array
	 * used when loading game
	 * @param enemiesArray - array of enemies
	 */
	public EnemyTracker(int[] enemiesArray) {
		setEnemyList(new ArrayList<Enemy>());
		roundTracker = new RoundTracker(enemiesArray, round);
	}
	/**
	 * 
	 * @param f takes in the enemies from the factory putting them in the list of
	 *          enemies
	 */
	public void createEnemy(MonsterFactory f) {
		EnemyTracker.setEnemyList(f.getMonsters());
	}

	/**
	 * 
	 * @param g calls the draw method in enemyList
	 */
	public void draw(Graphics g) {
		for (Enemy e : enemyList) {
			if (e.getSpawn()) {
				e.draw(g);
			}
		}
	}

	/**
	 * calls the move method on each enemy in the list
	 */
	public void move() {
		new Thread(this).start();
	}

	/**
	 * starts the monsterSpawner
	 */
	public void startSpawner() {
		spawnEnemies.startThread();
	}

	/**
	 * checks if the round has ended and creates new monsters
	 */
	public void endRound() {
		if ((EnemyTracker.getEnemyList().size() == 0) && !GameLogic.gameOver) {
			roundTracker.changeRound();
		}
	}

	@Override
	public void run() {
		for (Enemy e : enemyList) {
			if (e.getSpawn()) {

				e.startThread();
			}
		}
	}

	/**
	 * removes the enemies from the list when they are dead
	 */
	public static void removeEnemies() {
		ArrayList<Enemy> tempList = new ArrayList<Enemy>();
		for (Enemy e : enemyList) {
			if (e.getSpawn() || !e.isDead()) {
				tempList.add(e);
			}
		}
		enemyList = tempList;
		setEnemyList(enemyList);
	}

	/**
	 * 
	 * @return enemyList
	 */
	public static List<Enemy> getEnemyList() {
		return enemyList;
	}

	/**
	 * 
	 * @param enemyList - enemy list to use
	 */
	public static void setEnemyList(List<Enemy> enemyList) {
		EnemyTracker.enemyList = enemyList;
	}
	
	/**
	 * used to return enemies in a round
	 * @return roundTracker.getEnemies();
	 */
	public static String getEnemies() {
		return roundTracker.getEnemies();
	}

}
