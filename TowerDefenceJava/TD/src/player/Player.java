package player;

import observerfiles.ExampleModel;
import windows.GameWindow;
import windows.UserInterface;

/**
 * Singleton class to keep track of everything related to the player 
 * and their progress.
 * Health, score, gold and round.
 * It is also able to hold player name if needed (like when saving score)
 * Singleton to make it easier to access from various places in the program and make sure
 * that there is only a single player.
 * @author Ömer
 * @author Robin
 * @author Mojtaba 
 * @version 7
 * 
 */
public class Player {
	
	final ExampleModel model1;
	private static Player player;
	private int health = 0;
	private int roundHP = 0;
	private int score = 0;
	private int roundScore = 0;
	private int gold = 0;
	private int roundGold = 0;
	private int round = 0;
	private String currentMap;
	private String playerName;


	
	private Player() {
		resetPlayer();
		model1 = new ExampleModel("Model 1", 0);
	}
	
	public static Player getInstance() {
		if (player == null) player = new Player();
		return player;
	}
	
	public String GetMapName() {
		 return currentMap;
	}
	
	public void setMapName(String newMap) {
		currentMap = newMap;
	}
	 

	/**
	 * Returns current health as an int
	 * 
	 * @return current health as int
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * Used when loading a player. Sets loaded round health to current health 
	 * and loaded round health to current health
	 * @param newHP - loaded health
	 */	
	public void setHealth(int newHP) {
		health = newHP;
		roundHP = newHP;
	}
	
	/**
	 * Used when loading a player. Sets loaded round gold to current gold 
	 * and loaded round gold to current score
	 * @param newGold - loaded gold
	 */	
	public void setGold(int newGold) {
		gold = newGold;
		roundGold = newGold;
	}
	
	/**
	 * Used when loading a player. Sets loaded round score to current score 
	 * and loaded round score to current score
	 * @param newScore - loaded score
	 */
	public void setScore(int newScore) {
		score = newScore;
		roundScore = newScore;
	}
	
	public void resetPlayer() {
		this.health = 100;
		this.score = 0;
		this.gold = 10;
		this.round = 1;
	}

	
	public void addObserver(GameWindow ui) {
		model1.addObserver(ui);
	}

	/**
	 * Adjust player health, accepts + and -
	 * 
	 * @param change amount to be changed
	 * 
	 */
	public void changeHealth(int change) {

		health = health + change;
		if (health < 0)
			health = 0;
		model1.updateState();
	}

	public void changeGold(int change) {
		gold += change;
		if (gold < 0) {
			gold = 0;
		}
		model1.updateState();
	}

	public void changeRound(int currentRound) {
		round = currentRound;
		roundScore = score;
		roundGold = gold;
		roundHP = health;
		model1.updateState();
	}

	/**
	 * Increase or decrease score
	 * 
	 * @param change - Amount to change score by
	 */
	public void changeScore(int change) {

		score = score + change;
		if (score < 0) {
			score = 0;
		}
		
		model1.updateState();
	}

	/**
	 * Returns current score
	 * 
	 * @return score
	 */
	public int getScore() {
		return score;

	}

	/**
	 * Returns current gold as an int
	 * 
	 * @return current gold as int
	 */
	public int getGold() {
		return gold;

	}

	/**
	 * Set player name
	 * 
	 * @param name player name
	 * @return returns true if successful
	 */
	public boolean setName(String name) {

		if (name.equals(null) || name.trim().equals(null) || name.length() > 15)
			return false;

		playerName = name.trim().toUpperCase();
		return true;

	}

	/**
	 * returns player info as string ready for HighScore
	 * 
	 * @return string
	 */
	public String toString() {

		return "PLAYER: " + playerName + "    : SCORE: " + score + "\n";

	}

	/**
	 * Return player name
	 * 
	 * @return players name
	 */
	public String getPlayerName() {

		return playerName;
	}

	public int getRound() {
		return round;
	}
	
	/**
	 * 
	 * @return returns score from the start of the round
	 */
	public int getRoundScore() {
		return roundScore;
	}
	/**
	 * 
	 * @return returns gold from the start of the round
	 */
	public int getRoundGold() {
		return roundGold;
	}
	
	/**
	 * 
	 * @return returns HP from the start of the round
	 */
	public int getRoundHP() {
		return roundHP;
	}


}
