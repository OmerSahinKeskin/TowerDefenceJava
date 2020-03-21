package enemies;

import java.awt.Graphics;

/**
 * 
 * @author Ömer 
 * @version 2 
 * what methods the enemies should have
 *
 */
public interface Enemy extends Runnable {

	/**
	 * How the monsters travel through the map
	 */
	public void enemyDirections();
	
	public void startThread();

	/**
	 * @param change changes health bassed on what the tower damage is. calulates
	 *               the displayHP
	 */
	public void changeHealth(int change);

	/**
	 * removes the health from the player
	 */
	public void damagePlayer();

	/**
	 * Creating the monster at the starting location(always on the left and on x =
	 * 0) after creating the monster changing its boolean to true that signifies
	 * that the monster is alive
	 */
	public void init();

	
	/**
	  * checks if the monster can start to walk or not returns true if it can walk
	 * returns false if it cant walk 
	 * @return - time to walk?
	 */
	public boolean timeToWalk();

	/**
	 * type of tile checks if monster can spawn on tile returns true if it
	 *             can spawn on the tile returns false if it cant spawn on the tile
	 * @return spawn point ok
	 * @param y - tile to check
	 */
	public boolean checkSpawmPoint(int y);

	/**
	 * if the monster has passed to the end remove it
	 */
	public void passedToBase();

	/**
	 * moves the Enemies
	 */
	public void move();

	/**
	 * Graphics component draws enemies on the panel
	 * @param g - where to draw
	 */
	public void draw(Graphics g);

	/**
	 * returns health
	 * @return health
	 */
	public int getHealth();

	/**
	 * returns x-cords of enemy return x
	 * @return x-coord
	 */
	public double getX();

	/**
	 * returns y-cords of enemy return y
	 * @return y-coord
	 */
	public double getY();

	/**
	 * checks if the enemies has spawned return spawn
	 * @return spawn
	 */
	public boolean getSpawn();

	/**
	 * checks what type of enemy it is returns enemyID
	 * @return enemyID
	 */
	public int getEnemyID();

	/**
	 * Check if dead
	 * @return dead status
	 */
	public boolean isDead();

}
