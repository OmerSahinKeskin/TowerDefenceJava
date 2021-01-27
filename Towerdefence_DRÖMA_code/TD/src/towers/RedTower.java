package towers;

import java.awt.Graphics;

import enemies.Enemy;
import enemytrackers.EnemyTracker;
import game.ConstantIntEnum;
import handlers.TextureHandler;
import projectiles.ProjectileTracker;
import projectiles.RedTowerProjectile;

/**
 * Implements behaviour of the red tower Keeps track of its own projectiles
 * 
 * @author Robin
 * @author Dalibor
 * @version 5
 *
 */
public class RedTower implements Tower, Runnable {

	private int xCoord;
	private int yCoord;
	private int attackCounter;
	private int col = 1;

	private ProjectileTracker myProjectiles;

	public RedTower(int xCoord, int yCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		attackCounter = 200;
		myProjectiles = new ProjectileTracker();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(TextureHandler.RED_TOWER.img, xCoord, yCoord, ConstantIntEnum.WIDTH.val, ConstantIntEnum.HEIGHT.val,
				null);
		myProjectiles.draw(g);
	}

	private void aim() {
		if (attackCounter >= ConstantIntEnum.REDT_SHOOTINGSPEED.val) {
			for (int i = 0; i < EnemyTracker.getEnemyList().size(); i++) {
				Enemy g = EnemyTracker.getEnemyList().get(i);
				int xDiff = Math.abs(xCoord - (int) g.getX());
				int yDiff = Math.abs(yCoord - (int) g.getY());
				int vector = (int) Math.sqrt(xDiff * xDiff + yDiff * yDiff);

				if (g.getSpawn() == true && g.getHealth() > 0 && vector - 16 < ConstantIntEnum.REDT_RANGE.val) {
					myProjectiles
							.addProjectile(new RedTowerProjectile(xCoord, yCoord, g, ConstantIntEnum.REDT_DAMAGE.val));
					attackCounter = 0;
					break;
				}

			}
		} else
			attackCounter++;

	}

	@Override
	public void shoot() {
		new Thread(this).start();

	}

	public int getValue() {
		return ConstantIntEnum.REDT_VALUE.val;
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
		return ConstantIntEnum.REDT_SAVE_VAL.val;
	}
	public int getCol() {
		return col;
	}
}
