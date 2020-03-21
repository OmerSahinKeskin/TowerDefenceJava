package projectiles;

import java.awt.Graphics;

import enemies.Enemy;
import game.ConstantIntEnum;
import handlers.TextureHandler;
/**
 * Implements behaviour of the red tower's projectile
 * 
 * @author Robin
 * @version 5
 *
 */
public class RedTowerProjectile implements TowerProjectile, Runnable {

	private int xCoord;
	private int yCoord;
	private Enemy target = null;
	private int speed = 6;
	// Damage received from tower
	private int damage;
	private boolean targetHit;

	public RedTowerProjectile(int xCoord, int yCoord, Enemy target, int damage) {
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

			if (Math.abs(xDiff) < 9 && Math.abs(yDiff) < 9) {
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
		g.drawImage(TextureHandler.RED_PROJECTILE.img, xCoord, yCoord, ConstantIntEnum.WIDTH.val / 3,
				ConstantIntEnum.HEIGHT.val / 3, null);

	}
	
	
	@Override
	public boolean getTargetHit() {
		return targetHit;
	}

}
