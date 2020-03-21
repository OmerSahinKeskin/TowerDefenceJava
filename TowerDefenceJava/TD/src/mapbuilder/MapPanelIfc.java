package mapbuilder;
/**
 * Map panel interface requires:
 * threadInit, threadStop, setBuildMode,
 * saveMapAs, clearMap, randomMap
 * @author Robin
 * @version 1
 *
 */
public interface MapPanelIfc extends Runnable{
	/**
	 * Tell the panel to start drawing the current map This is done on a separate
	 * thread
	 */
	public void threadInit();

	/**
	 * Tell panel to stop drawing and stop thread
	 */
	public void threadStop();

	/**
	 * Tell the panel which texture to set as active
	 * 
	 * @param newMode - mode indicator
	 */
	public void setBuildMode(int newMode);
	
	/**
	 * Saves current/active map as name in the map folder
	 * @param name - chosen name for the map
	 */
	public void saveMapAs(String name);
	
	/**
	 * Resets map back to blank state
	 */
	public void clearMap();
	
	/**
	 * Generates random map
	 */
	public void randomMap();
}
