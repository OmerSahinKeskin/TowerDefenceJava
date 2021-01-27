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
 * implements the monster behaviour for GreenEnemy
 * 
 * @author Ömer
 * @version 5
 */
public class GreenEnemy extends EnemyAbstract implements Runnable {

	private int enemyID = 1;

	/*
	 * Creating the monsters with specified health, gold, speed and a enemyID that
	 * might be used later for other stuff
	 */
	public GreenEnemy() {

		super(ConstantIntEnum.GREEN_HEALTH.val, ConstantIntEnum.GREEN_GOLD.val,
				ConstantIntEnum.GREEN_DAMAGE_TO_PLAYER.val, ConstantIntEnum.GREEN_SCORE.val,
				ConstantIntEnum.GREEN_WHEN_TO_WALK.val);

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
		if (super.spawn) {
			g.drawImage(TextureHandler.GREEN_MONSTER.img, x, y, width, height, null);
			g.setColor(Color.GREEN);
			g.fillRect(x, y - (super.healthbar + super.emptySpace), (int) super.displayHP, super.healthbar);
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
