package projectiles;

import java.awt.Graphics;

import enemies.Enemy;
import game.ConstantIntEnum;
import handlers.TextureHandler;
/**
 * Implements yellow tower's projectile behaviour
 * @author Robin
 * @version 2
 *
 */
public class YellowTowerProjectile implements TowerProjectile, Runnable {

	private int xCoord;
	private int yCoord;
	private Enemy target = null;
	private int speed = 6;
	// Damage received from tower
	private int damage;
	private boolean targetHit;

	public YellowTowerProjectile(int xCoord, int yCoord, Enemy target, int damage) {
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
		g.drawImage(TextureHandler.YELLOW_PROJECTILE.img, xCoord, yCoord, ConstantIntEnum.WIDTH.val / 2,
				ConstantIntEnum.HEIGHT.val / 2, null);

	}
	@Override
	public boolean getTargetHit() {
		return targetHit;
	}

}
