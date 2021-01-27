package projectiles;

import java.awt.Graphics;
/**
 * Interface for the projectiles.
 * Every projectile needs:
 * travel - travel towards target
 * draw - draw the projectile
 * getTargetHit - check if the target has been hit by the projectile
 * @author Robin
 * @version 1
 *
 */
public interface TowerProjectile extends Runnable {

	/**
	 * Make the projectile chase its target OR travel to target area
	 */
	public void travel();

	/**
	 * Draw projectile's current position
	 * 
	 * @param g - Where to draw
	 */
	public void draw(Graphics g);

	/**
	 * Returns whether the projectile has hit its target or not
	 * @return targetHit 
	 */
	public boolean getTargetHit();

}
