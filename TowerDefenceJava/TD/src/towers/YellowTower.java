package towers;

import java.awt.Graphics;

import enemies.Enemy;
import enemytrackers.EnemyTracker;
import game.ConstantIntEnum;
import handlers.TextureHandler;
import projectiles.ProjectileTracker;
import projectiles.YellowTowerProjectile;

/**
 * Implements the yellow tower's behaviour
 * 
 * @author Robin
 *@version 3
 */
public class YellowTower implements Tower {

	private int xCoord;
	private int yCoord;
	private int col = 2;
	private int attackCounter;
	private ProjectileTracker myProjectiles;

	public YellowTower(int xCoord, int yCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		attackCounter = 200;
		myProjectiles = new ProjectileTracker();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(TextureHandler.YELLOW_TOWER.img, xCoord, yCoord, ConstantIntEnum.WIDTH.val,
				ConstantIntEnum.HEIGHT.val, null);
		myProjectiles.draw(g);
	}

	private void aim() {
		if (attackCounter >= ConstantIntEnum.YELLOWT_SHOOTINGSPEED.val) {
			int targets = 0;
			for (int i = 0; i < EnemyTracker.getEnemyList().size(); i++) {
				Enemy g = EnemyTracker.getEnemyList().get(i);
				int xDiff = Math.abs(xCoord - (int) g.getX());
				int yDiff = Math.abs(yCoord - (int) g.getY());
				int vector = (int) Math.sqrt(xDiff * xDiff + yDiff * yDiff);

				if (g.getSpawn() == true && g.getHealth() > 0 && vector - 16 < ConstantIntEnum.YELLOWT_RANGE.val) {
					myProjectiles.addProjectile(
							new YellowTowerProjectile(xCoord, yCoord, g, ConstantIntEnum.YELLOWT_DAMAGE.val));
					attackCounter = 0;
					targets++;
				}
				if (targets >= 3)
					break;

			}
		} else
			attackCounter++;

	}

	@Override
	public void shoot() {
		new Thread(this).start();

	}

	public int getValue() {
		return ConstantIntEnum.YELLOWT_VALUE.val;
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
		return ConstantIntEnum.YELLOWT_SAVE_VAL.val;
	}
	
	public int getCol() {
		return col;
	}
}
