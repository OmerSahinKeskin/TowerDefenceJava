package towers;

import java.awt.Graphics;

import enemies.Enemy;
import enemies.GreenEnemy;
import enemytrackers.EnemyTracker;
import game.ConstantIntEnum;
import handlers.TextureHandler;
import projectiles.BlueTowerProjectile;
import projectiles.ProjectileTracker;

/**
 * Implements the behaviour of the blue tower Keeps track of its own projectile
 * Attack speed varies by distance to target
 * 
 * @author Robin
 * @version 6
 *
 */
public class BlueTower implements Tower {

	private int xCoord;
	private int yCoord;
	private int col = 3;
	private ProjectileTracker myProjectiles;
	private Enemy target = new GreenEnemy();

	public BlueTower(int xCoord, int yCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		myProjectiles = new ProjectileTracker();
		target.changeHealth(-400);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(TextureHandler.BLUE_TOWER.img, xCoord, yCoord, ConstantIntEnum.WIDTH.val,
				ConstantIntEnum.HEIGHT.val, null);
		myProjectiles.draw(g);
	}

	private void aim() {
		if (myProjectiles.size() < 1) {

			if (!target.isDead() && getVector(target) < ConstantIntEnum.BLUET_RANGE.val) {
				myProjectiles.addProjectile(new BlueTowerProjectile(xCoord, yCoord, target, ConstantIntEnum.BLUET_DAMAGE.val));

			} else {
				for (int i = 0; i < EnemyTracker.getEnemyList().size(); i++) {
					Enemy g = EnemyTracker.getEnemyList().get(i);
					if (g.getSpawn() == true && !g.isDead() && getVector(g) < ConstantIntEnum.BLUET_RANGE.val) {
						target = g;
						myProjectiles.addProjectile(new BlueTowerProjectile(xCoord, yCoord, g, ConstantIntEnum.BLUET_DAMAGE.val));

						break;
					}

				}
			}
		}

	}

	private int getVector(Enemy en) {
		int xDiff = Math.abs(xCoord - (int) en.getX());
		int yDiff = Math.abs(yCoord - (int) en.getY());
		int vector = (int) Math.sqrt(xDiff * xDiff + yDiff * yDiff);
		return vector - 16;
	}

	@Override
	public void shoot() {
		new Thread(this).start();

	}

	public int getValue() {
		return ConstantIntEnum.BLUET_VALUE.val;
	}

	public int getXCoord() {
		return xCoord;
	}

	public int getYCoord() {
		return yCoord;
	}

	@Override
	public void run() {
		aim();
		myProjectiles.projectileAct();
		myProjectiles.deleteOldProjectile();

	}

	@Override
	public int getSaveValue() {
		return ConstantIntEnum.BLUET_SAVE_VAL.val;
	}
	public int getCol() {
		return col;
	}
}
