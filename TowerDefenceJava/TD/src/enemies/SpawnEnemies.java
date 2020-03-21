package enemies;

import enemytrackers.EnemyTracker;

/**
 * 
 * @author Ömer
 * @version 4 spawning the enemies that has been created in the List
 */
public class SpawnEnemies implements Runnable {

	private int waittime;
	private int waitedtime;

	/**
	 * @param waittime   between spawning the monsters
	 * 
	 * @param waitedtime time always 0 but the developer can specify where the time
	 *                   should start
	 */
	public SpawnEnemies(int waittime, int waitedtime) {
		this.waittime = waittime;
		this.waitedtime = waitedtime;
	}

	/**
	 * checking the List to see what enemies hasn't been spawned yes and spawning
	 * them. Spawning monsters in a time that the developer has specified when its
	 * spawn resets the timer to 0 again
	 */
	public void enemySpawner() {

		for (int i = 0; i < EnemyTracker.getEnemyList().size(); i++) {
			try {
				if (waitedtime >= waittime) {
					if (!EnemyTracker.getEnemyList().get(i).getSpawn()
							&& !EnemyTracker.getEnemyList().get(i).isDead()) {
						EnemyTracker.getEnemyList().get(i).init();
						waitedtime = 0;
						Thread.sleep(1);
					}
				} else {
					waitedtime++;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void startThread() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		enemySpawner();

	}
}
