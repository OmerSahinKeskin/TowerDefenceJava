package enemies;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Creates monsters based on input parameters
 * @version 1
 *
 */
public class MonsterFactory {
	private final int red;
	private final int green;
	private final int yellow;
	private final int mikey;
	private final int invis;
	private final int spookey;

	private MonsterFactory(int green, int red, int yellow, int mikey, int invis, int spookey) {
		this.green = green;
		this.red = red;
		this.yellow = yellow;
		this.mikey = mikey;
		this.invis = invis;
		this.spookey = spookey;
	}

	public List<Enemy> getMonsters() {
		List<Enemy> enemyList = new ArrayList<>();
		for (int i = 0; i < this.green; i++) {
			enemyList.add(new GreenEnemy());
		}
		for (int i = 0; i < red; i++) {
			enemyList.add(new RedEnemy());
		}
		for (int i = 0; i < yellow; i++) {
			enemyList.add(new YellowEnemy());
		}
		for (int i = 0; i < mikey; i++) {
			enemyList.add(new MikeyEnemy());
		}
		for (int i = 0; i < invis; i++) {
			enemyList.add(new InvisEnemy());
		}
		for (int i = 0; i < spookey; i++) {
			enemyList.add(new SpookeyEnemy());
		}
		return enemyList;
	}

	public static class Builder {

		private int green;
		private int red;
		private int yellow;
		private int mikey;
		private int invis;
		private int spookey;

		public Builder() {
			// Nothing; default to zero.
		}

		public Builder green(int amount) {
			this.green = amount;
			return this;
		}

		public Builder red(int amount) {
			this.red = amount;
			return this;
		}

		public Builder yellow(int amount) {
			this.yellow = amount;
			return this;
		}

		public Builder mikey(int amount) {
			this.mikey = amount;
			return this;
		}

		public Builder invis(int amount) {
			this.invis = amount;
			return this;
		}

		public Builder spookey(int amount) {
			this.spookey = amount;
			return this;
		}

		public MonsterFactory build() {
			return new MonsterFactory(green, red, yellow, mikey, invis, spookey);
		}
	}

}
