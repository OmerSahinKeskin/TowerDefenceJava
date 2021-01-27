package projectiles;

import java.awt.Graphics;

import enemies.Enemy;
import game.ConstantIntEnum;
import handlers.TextureHandler;
/**
 * Implements the behaviour of the blue tower's projectile
 * @author Robin
 * @version 1
 * 
 *
 */
public class BlueTowerProjectile implements TowerProjectile, Runnable {

	private int xCoord;
	private int yCoord;
	private Enemy target = null;
	private int speed = 10;
	// Damage received from tower
	private int damage;
	private boolean targetHit;

	public BlueTowerProjectile(int xCoord, int yCoord, Enemy target, int damage) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.target = target;
		this.damage = damage;
		targetHit = false;
	}

	@Override
	public void travel() {
		new Thread(this).start();

	}

	@Override
	public void run() {
		if (!target.isDead()) {
			double xDiff = target.getX() + 16 - xCoord;
			double yDiff = target.getY() + 16 - yCoord;
			double vector = Math.sqrt(xDiff * xDiff + yDiff * yDiff);

			if (Math.abs(xDiff) < 6 && Math.abs(yDiff) < 6) {
				target.changeHealth(-damage);
				targetHit = true;
			}

			xCoord = (int) (xCoord + (xDiff / vector) * speed);
			yCoord = (int) (yCoord + (yDiff / vector) * speed);
		} else
			targetHit = true;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(TextureHandler.BLUE_PROJECTILE.img, xCoord, yCoord, ConstantIntEnum.WIDTH.val,
				ConstantIntEnum.HEIGHT.val, null);

	}
	

	public boolean getTargetHit() {
		return targetHit;
	}

}
