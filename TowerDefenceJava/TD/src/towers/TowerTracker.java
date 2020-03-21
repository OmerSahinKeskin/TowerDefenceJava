package towers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import game.ConstantIntEnum;
import game.Panel;
import handlers.TextureHandler;
import inpurdevices.ClickFunctionality;
import inpurdevices.MouseControls;
import player.Player;
import windows.GameWindow;

/**
 * Class used to keep track of active towers and current build mode for towers. 
 * Tells towers to draw themselves and makes them act. 
 * It remembers the active towers at the beginning of the current round
 * Finally it also uses the build mode to control which tower to build when the user attempts to build one.
 * -- Save thrown in last second
 * @author Robin
 * @version 8
 *
 */
public class TowerTracker implements ClickFunctionality, Runnable{

	public static TextureHandler buildMode;
	private List<Tower> towerList;
	private List<Tower> towerToDelete;
	private List<Tower> roundTowers;
	private Point mousePosition;
	private int savedRound;

	public TowerTracker() {
		buildMode = TextureHandler.EMPTY;
		towerList = new ArrayList<Tower>();
		towerToDelete = new ArrayList<Tower>();
		roundTowers = new ArrayList<Tower>();
		mousePosition = MouseControls.getInstance().getPoint();
		MouseControls.getInstance().setClickUser(this);
		savedRound = Player.getInstance().getRound();

	}

	/**
	 * What to do when mouse is clicked within game window
	 * 
	 * @param e - which mouse button is pressed
	 */
	@Override
	public void mouseClick(MouseEvent e) {
		Player player = Player.getInstance();
		int mouseButton = e.getButton();
		boolean occupied = false;
		Tower temp = null;

		// Collision detection
		for (int i = 0; i < towerList.size(); i++) {
			Tower t = towerList.get(i);
			if (t.getXCoord() == 64 * (mousePosition.x / 64) && t.getYCoord() == 64 * (mousePosition.y / 64)) {
				temp = t;
				occupied = true;
			}

		}

		if (mouseButton == 1) {
			int tileType = Panel.getTilemap().getTile()[(mousePosition.y / 64)][(mousePosition.x / 64)].getType();
			
			// if player can afford selected tower and tile is not occupied -> create tower
			// and paint
			if (buildMode == TextureHandler.RED_TOWER && player.getGold() >= ConstantIntEnum.REDT_VALUE.val
					&& !occupied && tileType != 0 && tileType != 2 && tileType != 6 && tileType != 8 && tileType != 9) {

				player.changeGold(-ConstantIntEnum.REDT_VALUE.val);
				towerList.add(new RedTower(64 * (mousePosition.x / 64), 64 * (mousePosition.y / 64)));
			}

			if (buildMode == TextureHandler.BLUE_TOWER && player.getGold() >= ConstantIntEnum.BLUET_VALUE.val
					&& !occupied && (tileType == 2 || tileType == 8 || tileType == 9)) {
				player.changeGold(-ConstantIntEnum.BLUET_VALUE.val);
				towerList.add(new BlueTower(64 * (mousePosition.x / 64), 64 * (mousePosition.y / 64)));
			}

			if (buildMode == TextureHandler.YELLOW_TOWER && player.getGold() >= ConstantIntEnum.YELLOWT_VALUE.val
					&& !occupied && tileType != 0 && tileType != 2 && tileType != 6 && tileType != 8 && tileType != 9) {
				player.changeGold(-ConstantIntEnum.YELLOWT_VALUE.val);
				towerList.add(new YellowTower(64 * (mousePosition.x / 64), 64 * (mousePosition.y / 64)));
			}

			// Prepare sold towers for deletion
			// Prevents java.util.ConcurrentModificationException
			if (buildMode == TextureHandler.SELL && occupied) {

				player.changeGold(temp.getValue() / 2);
				towerToDelete.add(temp);

			}
			

		}
		if (mouseButton == 2) {

		}
		if (mouseButton == 3) {
			buildMode = TextureHandler.EMPTY;

		}

	}
	/**
	 * Make all active towers act
	 */
	public void towerAct() {
		
		new Thread(this).start();
	}
	
	private void saveCurrentTowers() {
		roundTowers = new ArrayList<Tower>();
		for (Tower t: towerList) {
			roundTowers.add(t);
			
			}
		
		
	}

	/**
	 * Draw everything related to towers Line 1-3: Draw towers in current list Line
	 * 4-5: Make the selected tower follow mouse pointer Line 8+: Display the range
	 * of the selected tower
	 * 
	 * @param g - Where to draw
	 */
	public void draw(Graphics g) {

		for (int i = 0; i < towerList.size(); i++) {
			towerList.get(i).draw(g);
		}

		g.drawImage(buildMode.img, (int) mousePosition.getX(), (int) mousePosition.getY(), ConstantIntEnum.WIDTH.val,
				ConstantIntEnum.HEIGHT.val, null);

		// Display range while placing tower
		if (buildMode == TextureHandler.RED_TOWER) {
			g.setColor(Color.RED);
			g.drawOval((int) mousePosition.getX() - (ConstantIntEnum.REDT_RANGE.val - 32),
					(int) mousePosition.getY() - (ConstantIntEnum.REDT_RANGE.val - 32), 2 *ConstantIntEnum.REDT_RANGE.val,
					2 * ConstantIntEnum.REDT_RANGE.val);
		}
		if (buildMode == TextureHandler.YELLOW_TOWER) {
			g.setColor(Color.YELLOW);
			g.drawOval((int) mousePosition.getX() - (ConstantIntEnum.YELLOWT_RANGE.val - 32),
					(int) mousePosition.getY() - (ConstantIntEnum.YELLOWT_RANGE.val - 32), 2 * ConstantIntEnum.YELLOWT_RANGE.val,
					2 * ConstantIntEnum.YELLOWT_RANGE.val);
		}
		if (buildMode == TextureHandler.BLUE_TOWER) {
			g.setColor(Color.BLUE);
			g.drawOval((int) mousePosition.getX() - (ConstantIntEnum.BLUET_RANGE.val - 32),
					(int) mousePosition.getY() - (ConstantIntEnum.BLUET_RANGE.val - 32), 2 * ConstantIntEnum.BLUET_RANGE.val,
					2 * ConstantIntEnum.BLUET_RANGE.val);
		}

	}

	// Safely remove sold towers and clear list of sold towers
	public void deleteSoldTowers() {
		towerList.removeAll(towerToDelete);
		towerToDelete.clear();
	}

	@Override
	public void mouseHeld(MouseEvent e) {
		// Do nothing?

	}
	
	public int[][] saveTowers(){
		int[][] towerArr = new int[ConstantIntEnum.GRID_HEIGHT.val][ConstantIntEnum.GRID_WIDTH.val];
		
		for (Tower t: roundTowers) {
			towerArr[t.getYCoord()/64][t.getXCoord()/64] = t.getSaveValue();
		}
		return towerArr;
	}
	
	public void loadTower(int[][] towerArr) {
		for (int i = 0; i<ConstantIntEnum.GRID_HEIGHT.val; i++) {
			for (int j = 0; j<ConstantIntEnum.GRID_WIDTH.val; j++) {
				switch(towerArr[i][j]) {
				case 1: towerList.add(new RedTower(j*64,i*64));
					break;
				case 2: towerList.add(new YellowTower(j*64,i*64));
					break;
				case 3:towerList.add(new BlueTower(j*64,i*64));
					break;
				}
				
			}
		}
	}

	@Override
	public void run() {
		for (int i = 0; i < towerList.size(); i++) towerList.get(i).shoot();
		deleteSoldTowers();
		
		
		if (Player.getInstance().getRound() > savedRound) {
			saveCurrentTowers();
			savedRound = Player.getInstance().getRound();
		}
	}
	
	

}
