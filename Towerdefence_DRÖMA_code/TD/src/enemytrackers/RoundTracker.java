package enemytrackers;

import enemies.MonsterFactory;
import enemies.MonsterFactory.Builder;
import game.GameLogic;
import player.Player;
import sound.SoundPlayer;
import windows.GameWindow;

/**
 * 
 * @author Ömer 
 * @version 4 
 * Keeps track of which enemies should be in each round
 * RoundTracker makes a new list of enemies for MonsterFactory to use when making enemies
 */
public class RoundTracker {

	private SoundPlayer soundPlayer;

	private int round;
	private int startGreen;
	private int startRed;
	private int startYellow;
	private int startSpookey;
	private int startMikey;
	private int startInvis;
	
	private int lastGreen;
	private int lastRed;
	private int lastYellow;
	private int lastSpookey;
	private int lastMikey;
	private int lastInvis;

	/**
	 * 
	 * @param startGreen - start amount of green
	 * @param round - start round
	 * this constructor is used when creating new game
	 * 
	 */
	public RoundTracker(int startGreen, int round) {
		this.round = round;
		this.startGreen = startGreen;
		this.startRed = 0;
		this.startYellow = 0;
		this.startSpookey = 0;
		this.startMikey = 0;
		this.startInvis = 0;
		
		lastGreen = startGreen;
		lastRed = startRed;
		lastYellow = startYellow;
		lastSpookey = startSpookey;
		lastMikey = startMikey;
		lastInvis = startInvis;
		
		
	}
	/**
	 * 
	 * @param enemiesArray - enemy array to load
	 * @param round2 - round to load
	 * this constructor is used when loading an old game
	 */
	public RoundTracker(int[] enemiesArray, int round2) {
		this.round = round2;
		this.startGreen = enemiesArray[0];
		this.startRed = enemiesArray[1];
		this.startYellow = enemiesArray[2];
		this.startSpookey = enemiesArray[3];
		this.startMikey = enemiesArray[4];
		this.startInvis = enemiesArray[5];
		
		lastGreen = startGreen;
		lastRed = startRed;
		lastYellow = startYellow;
		lastSpookey = startSpookey;
		lastMikey = startMikey;
		lastInvis = startInvis;
	}
	/**
	 * checks what round it is and creating enemies based on what round it is
	 */
	public void changeRound() {
		soundPlayer = new SoundPlayer("music\\Song1.wav");
		
		lastGreen = startGreen;
		lastRed = startRed;
		lastYellow = startYellow;
		lastSpookey = startSpookey;
		lastMikey = startMikey;
		lastInvis = startInvis;
		
		if (round == 1) {
			MonsterFactory f = new MonsterFactory.Builder().green(startGreen).build();
			GameLogic.enemies.createEnemy(f);
		} else if (round % 2 == 0) {
			if (round % 10 == 0) {
				this.startGreen += 1;
				this.startRed += 1;
				this.startYellow += 1;
				this.startMikey += 1;
				this.startInvis += 1;
				this.startSpookey += 1;

				MonsterFactory f = new MonsterFactory.Builder().green(startGreen).red(startRed).yellow(startYellow)
						.mikey(startMikey).invis(startInvis).spookey(startSpookey).build();
				GameLogic.enemies.createEnemy(f);
			} else {
				this.startGreen += 2;
				this.startYellow += 2;
				MonsterFactory f = new MonsterFactory.Builder().green(startGreen).yellow(startYellow).build();
				GameLogic.enemies.createEnemy(f);
			}
		} else if (round % 3 == 0) {
			if (round == 6) {
				this.startGreen += 3;
				this.startYellow += 2;
				this.startSpookey += 2;

				MonsterFactory f = new MonsterFactory.Builder().green(startGreen).yellow(startYellow)
						.spookey(startSpookey).mikey(startMikey).invis(startInvis).build();
				GameLogic.enemies.createEnemy(f);
			} else if (round % 15 == 0) {
				this.startGreen += 10;
				this.startRed += 3;
				this.startYellow += 10;
				this.startMikey += 10;
				this.startInvis += 10;
				this.startSpookey += 10;
				MonsterFactory f = new MonsterFactory.Builder().green(startGreen).red(startRed).yellow(startYellow)
						.mikey(startMikey).invis(startInvis).spookey(startSpookey).build();
				GameLogic.enemies.createEnemy(f);
			} else {
				this.startGreen += 1;
				this.startYellow += 1;
				this.startSpookey += 1;

				MonsterFactory f = new MonsterFactory.Builder().green(startGreen).yellow(startYellow)
						.spookey(startSpookey).mikey(startMikey).invis(startInvis).build();
				GameLogic.enemies.createEnemy(f);
			}
		} else if (round == 5) {
			this.startGreen += 2;
			this.startRed += 2;
			this.startYellow += 2;
			MonsterFactory f = new MonsterFactory.Builder().green(startGreen).red(startRed).yellow(startYellow).build();
			GameLogic.enemies.createEnemy(f);
		} else {
			
			this.startGreen += 1;
			this.startYellow += 1;
			this.startSpookey += 1;

			MonsterFactory f = new MonsterFactory.Builder().green(startGreen).yellow(startYellow).spookey(startSpookey)
					.mikey(startMikey).invis(startInvis).build();
			GameLogic.enemies.createEnemy(f);
		}
		

		Player.getInstance().changeRound(round);
		round++;
	}
	/**
	 * 
	 * @return +lastGreen+ "\n" +lastRed+ "\n" +lastYellow+ "\n" +lastSpookey+ "\n" +lastMikey+ "\n" +lastInvis
	 * returning enemies from the round so when loaded it loads properly
	 */
	String getEnemies() {
		return +lastGreen+ "\n" +lastRed+ "\n" +lastYellow+ "\n" +lastSpookey+ "\n" +lastMikey+ "\n" +lastInvis;
	}
}
