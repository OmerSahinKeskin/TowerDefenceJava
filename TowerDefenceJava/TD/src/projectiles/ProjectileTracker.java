package projectiles;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Manages the active projectiles and gets rid of the inactive ones
 * @author Robin
 *
 */
public class ProjectileTracker {

	private List<TowerProjectile> projectileList;
	private List<TowerProjectile> projectileStillActive;

	public ProjectileTracker() {

		projectileList = new ArrayList<TowerProjectile>();
		projectileStillActive = new ArrayList<TowerProjectile>();

	}

	/**
	 * Tells its projectiles to "act" and remove them if they hit their target 
	 * 
	 */
	public void projectileAct() {
		//Iterator<TowerProjectile> it = projectileList.iterator();
		/*for (int i = 0; i<projectileList.size(); i++) {
			TowerProjectile tp = projectileList.get(i);
			tp.travel();
			if (!tp.getTargetHit())
				projectileStillActive.add(tp);
		}*/
		/*
		while (it.hasNext()) {
			TowerProjectile tp = it.next();
			tp.travel();
			if (!tp.getTargetHit())
				projectileStillActive.add(tp);

		}*/
		
		for (TowerProjectile tp: projectileList) {
			tp.travel();
			if (!tp.getTargetHit())
				projectileStillActive.add(tp);
		}

	}
	
	/**
	 * Draws all active projectiles 
	 * @param g - where to draw
	 */
	public void draw(Graphics g) {
		for (int i = 0; i < projectileList.size(); i++) {
			TowerProjectile tp = projectileList.get(i);
			tp.draw(g);
		}

	}
	
	/**
	 * Deletes projectiles that are no longer used
	 */
	public void deleteOldProjectile() {
		projectileList = projectileStillActive;
		projectileStillActive = new ArrayList<TowerProjectile>();
	}
	
	/**
	 * Add projectile to current/active list of projectiles
	 * @param tp - projectile to add
	 */
	public void addProjectile(TowerProjectile tp) {
		projectileList.add(tp);
	}
	
	/**
	 * returns the amount of projectiles currently active
	 * @return # of active projectiles
	 */
	public int size() {
		return projectileList.size();
	}

}
