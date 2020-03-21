package enemies;

import java.awt.Rectangle;

import enemytrackers.EnemyTracker;
import game.ConstantIntEnum;
import game.Panel;
import player.Player;

public abstract class EnemyAbstract extends Rectangle implements Enemy {

	private int notWalkingTime;
	private int whenToWalk;

	private int health;
	private int gold;
	protected int enemyID;
	private int damageToPlayer;
	protected int currentHP;

	protected double displayHP;

	protected int healthbar = 10;
	protected int emptySpace = 4;
	private int hpBarSize = 64;

	private int xStart;
	private int yStart;

	private int enemyMoved;
	private int moveToRight;
	private int moveToLeft;
	private int moveToDown;
	private int moveToUp;
	private int defaultWay;

	private boolean movedToRight;
	private boolean movedToLeft;
	private boolean movedToDown;
	private boolean movedToUp;

	private int score;

	public boolean spawn;
	protected boolean dead;

	public EnemyAbstract(int health, int gold, int damageToPlayer, int score, int whenToWalk) {

		this.health = health;
		this.gold = gold;
		this.damageToPlayer = damageToPlayer;
		this.score = score;
		this.currentHP = health;
		this.displayHP = ((double) currentHP / (double) health) * hpBarSize;
		this.whenToWalk = whenToWalk;

		notWalkingTime = 0;

		spawn = false;
		dead = false;

		moveToRight = 0;
		moveToLeft = 1;
		moveToDown = 2;
		moveToUp = 3;
		defaultWay = moveToRight;

		movedToUp = false;
		movedToDown = false;
		movedToRight = false;
		movedToLeft = false;
	}

	public void enemyDirections() {
		if (defaultWay == moveToRight) {
			x += 2;
		} else if (defaultWay == moveToLeft) {
			x -= 2;
		} else if (defaultWay == moveToUp) {
			y += 2;
		} else if (defaultWay == moveToDown) {
			y -= 2;
		}
		enemyMoved += 2;

		if (enemyMoved == ConstantIntEnum.GRID_SIZE.val) {
			if (defaultWay == moveToRight) {
				movedToRight = true;
				xStart++;

			} else if (defaultWay == moveToLeft) {
				movedToLeft = true;
				xStart--;
			} else if (defaultWay == moveToDown) {
				movedToDown = true;
				yStart--;
			} else if (defaultWay == moveToUp) {
				movedToUp = true;

				yStart++;
			}

			if (!continuePath(defaultWay)) {
				if (((Panel.getTilemap().getTile()[yStart + 1][xStart].getType() == ConstantIntEnum.ENEMY_ROAD.val)
						|| (Panel.getTilemap().getTile()[yStart + 1][xStart]
								.getType() == ConstantIntEnum.EXIT_ROAD.val))
						&& !movedToDown) {
					defaultWay = moveToUp;
				}
				if (((Panel.getTilemap().getTile()[yStart - 1][xStart].getType() == ConstantIntEnum.ENEMY_ROAD.val)
						|| (Panel.getTilemap().getTile()[yStart - 1][xStart]
								.getType() == ConstantIntEnum.EXIT_ROAD.val))
						&& !movedToUp) {
					defaultWay = moveToDown;
				}
				if (((Panel.getTilemap().getTile()[yStart][xStart + 1].getType() == ConstantIntEnum.ENEMY_ROAD.val)
						|| (Panel.getTilemap().getTile()[yStart][xStart + 1]
								.getType() == ConstantIntEnum.EXIT_ROAD.val))
						&& !movedToLeft) {
					defaultWay = moveToRight;
				}
				if (((Panel.getTilemap().getTile()[yStart][xStart - 1].getType() == ConstantIntEnum.ENEMY_ROAD.val)
						|| (Panel.getTilemap().getTile()[yStart][xStart - 1]
								.getType() == ConstantIntEnum.EXIT_ROAD.val))
						&& !movedToRight) {
					defaultWay = moveToLeft;
				}
			}

			if (Panel.getTilemap().getTile()[yStart][xStart].getType() == 6) {
				passedToBase();
				damagePlayer();

			}

			movedToUp = false;
			movedToDown = false;
			movedToRight = false;
			movedToLeft = false;

			enemyMoved = 0;

		}
	}

	public boolean continuePath(int t) {
		switch (t) {
		case 0: {
			if (((Panel.getTilemap().getTile()[yStart][xStart + 1].getType() == ConstantIntEnum.ENEMY_ROAD.val)
					|| (Panel.getTilemap().getTile()[yStart][xStart + 1].getType() == ConstantIntEnum.EXIT_ROAD.val))) {
				return true;
			} else
				return false;
		}
		case 1: {
			if (((Panel.getTilemap().getTile()[yStart][xStart - 1].getType() == ConstantIntEnum.ENEMY_ROAD.val)
					|| (Panel.getTilemap().getTile()[yStart][xStart - 1].getType() == ConstantIntEnum.EXIT_ROAD.val))) {
				return true;
			} else
				return false;

		}
		case 3: {
			if (((Panel.getTilemap().getTile()[yStart + 1][xStart].getType() == ConstantIntEnum.ENEMY_ROAD.val)
					|| (Panel.getTilemap().getTile()[yStart + 1][xStart].getType() == ConstantIntEnum.EXIT_ROAD.val))) {
				return true;
			} else
				return false;
		}
		case 2: {
			if ((Panel.getTilemap().getTile()[yStart - 1][xStart].getType() == ConstantIntEnum.ENEMY_ROAD.val)
					|| (Panel.getTilemap().getTile()[yStart - 1][xStart].getType() == ConstantIntEnum.EXIT_ROAD.val)) {
				return true;
			} else
				return false;

		}
		default:
			return false;
		}
	}

	public void init() {
		for (int y = 0; y < Panel.getTilemap().getTile().length; y++) {
			if (checkSpawmPoint(y)) {
				setBounds(Panel.getTilemap().getTile()[y][0].x, Panel.getTilemap().getTile()[y][0].y,
						ConstantIntEnum.GRID_SIZE.val, ConstantIntEnum.GRID_SIZE.val);
				yStart = y;
				xStart = 0;
			}
		}

		spawn = true;
	}

	public boolean timeToWalk() {
		if (notWalkingTime >= whenToWalk) {
			notWalkingTime = 0;
			return true;
		} else {
			notWalkingTime++;
			return false;
		}

	}

	public boolean checkSpawmPoint(int y) {

		if (Panel.getTilemap().getTile()[y][0].getType() == ConstantIntEnum.ENEMY_ROAD.val) {
			return true;
		}
		return false;

	}

	public void changeHealth(int change) {
		currentHP += change;
		displayHP = ((double) currentHP / (double) health) * hpBarSize;
		if (currentHP <= 0) {
			currentHP = 0;
			Player.getInstance().changeGold(this.gold);
			Player.getInstance().changeScore(this.score);
			spawn = false;
			dead = true;
			EnemyTracker.removeEnemies();
		}
	}

	public void passedToBase() {
		spawn = false;
		dead = true;
		EnemyTracker.removeEnemies();
	}

	public void damagePlayer() {
		Player.getInstance().changeHealth(damageToPlayer);
	}

}
