package enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import enemytrackers.EnemyTracker;
import game.ConstantIntEnum;
import game.Panel;
import handlers.TextureHandler;
import player.Player;
import windows.GameWindow;

/**
 * implements the monster behaviour for RedEnemy
 * 
 * @author Ömer
 * @version 4
 */
public class RedEnemy extends EnemyAbstract implements Runnable {
	/*
	 * Creating the monsters with specified health, gold, speed and a enemyID that
	 * might be used later for other stuff
	 */
	public RedEnemy() {
		super(ConstantIntEnum.RED_HEALTH.val, ConstantIntEnum.RED_GOLD.val, ConstantIntEnum.RED_DAMAGE_TO_PLAYER.val,
				ConstantIntEnum.RED_SCORE.val, ConstantIntEnum.RED_WHEN_TO_WALK.val);
	}

	public void init() {
		super.init();
	}

	public void changeHealth(int change) {
		super.changeHealth(change);
	}

	public void passedToBase() {
		super.passedToBase();
	}

	public void damagePlayer() {
		super.damagePlayer();
	}

	public boolean continuePath(int t) {
		return super.continuePath(t);
	}

	public void enemyDirections() {
		super.enemyDirections();
	}

	public void move() {
		new Thread(this).start();

	}

	/*
	 * @param Graphics component draws the image on the graphic component
	 */
	public void draw(Graphics g) {
		if (spawn) {
			g.drawImage(TextureHandler.RED_MONSTER.img, x, y, width, height, null);
			g.setColor(Color.GREEN);
			g.fillRect(x, y - (healthbar + emptySpace), (int) displayHP, healthbar);
		}
	}

	@Override
	public void run() {
		if (timeToWalk()) {
			enemyDirections();

		}

	}

	@Override
	public int getHealth() {
		return currentHP;
	}

	@Override
	public boolean getSpawn() {
		return spawn;
	}

	@Override
	public int getEnemyID() {
		return enemyID;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	@Override
	public void startThread() {
		new Thread(this).start();

	}
}
