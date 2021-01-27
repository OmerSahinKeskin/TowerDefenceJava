package windows;
/**
 * Methods needed to update the information to the player during gameplay
 * Contains methods to update gold, score, health and rounds.
 * Also a method for a general UI update
 * @author Robin
 * @version 2
 *
 */
public interface UserInterface {
	/**
	 * Update the interface (all of it)
	 */
	public void updateInterface();

	/**
	 * Update gold display to player's current gold
	 */
	public void updateGoldDisplay();

	/**
	 * Update score display to player's current score
	 */
	public void updateScoreDisplay();

	/**
	 * Update health display to player's current health
	 */
	public void updateHealthDisplay();

	/**
	 * Update player progress display
	 */
	public void updateProgressDisplay();
}
